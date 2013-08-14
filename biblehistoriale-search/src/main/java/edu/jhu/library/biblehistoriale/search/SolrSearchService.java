package edu.jhu.library.biblehistoriale.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import edu.jhu.library.biblehistoriale.model.Query;
import edu.jhu.library.biblehistoriale.model.QueryMatch;
import edu.jhu.library.biblehistoriale.model.QueryOptions;
import edu.jhu.library.biblehistoriale.model.QueryResult;
import edu.jhu.library.biblehistoriale.model.TermField;

/**
 * Search service that allows manuscript profiles to be indexed and searched.
 * Implemented by solr.
 */
public class SolrSearchService {
    private static int MAX_MATCHES = 100;
    private static final Map<TermField, String[]> field_map;

    static {
        field_map = new HashMap<>();

        // TODO all searchable fields need to map to solr fields
        field_map.put(TermField.TITLE, new String[] { "title" });
    }

    private final Solr solr;
    private final SolrQueryBuilder query_builder;

    public SolrSearchService(Solr solr) {
        this.solr = solr;
        this.query_builder = new SolrQueryBuilder(field_map);
    }

    public SolrSearchService(File solrhome) throws IOException {
        this(new Solr(solrhome));
    }

    QueryResult<QueryMatch> executeQuery(Query query, QueryOptions opts)
            throws SearchServiceException {
        long offset = opts.getOffset();
        int max_matches = opts.getMatches();

        if (offset < 0) {
            throw new IllegalArgumentException("offset < 0");
        }

        if (offset > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("offset too large");
        }

        if (max_matches <= 0 || max_matches > MAX_MATCHES) {
            max_matches = MAX_MATCHES;
        }

        String solr_query = query_builder.buildQuery(query);

        QueryResponse resp;

        try {
            // TODO enable highlighting with solr params

            resp = solr.search(solr_query, (int) offset, max_matches);
        } catch (SolrServerException e) {
            throw new SearchServiceException(e);
        }

        SolrDocumentList docs = resp.getResults();

        List<QueryMatch> matches = new ArrayList<>();

        QueryResult<QueryMatch> result = new QueryResult<QueryMatch>(offset,
                docs.getNumFound(), matches);

        for (SolrDocument doc : docs) {
            String id = (String) doc.getFieldValue("id");
            String context = null;

            // TODO do we want context?

            if (resp.getHighlighting() != null) {
                Map<String, List<String>> snippets = resp.getHighlighting()
                        .get(id);

                // Add "field: snippet" to context

                if (snippets != null) {
                    StringBuilder sb = new StringBuilder();

                    for (String field : snippets.keySet()) {
                        sb.append(field + ": '" + snippets.get(field) + "' ");
                    }

                    context = sb.toString();
                }
            }

            matches.add(new QueryMatch(id, context));
        }

        return result;
    }

    public void index(Object profile) throws SearchServiceException {
        try {
            // TODO do the transform to a SolrInputDocument
            solr.add(null);
        } catch (SolrServerException | IOException e) {
            throw new SearchServiceException(e);
        }
    }

    public void clear() throws SearchServiceException {
        try {
            solr.clear();
        } catch (IOException | SolrServerException e) {
            throw new SearchServiceException(e);
        }
    }

    public long size() throws SearchServiceException {
        try {
            return solr.size();
        } catch (SolrServerException e) {
            throw new SearchServiceException(e);
        }
    }

    public void close() {
        solr.close();
    }
}
