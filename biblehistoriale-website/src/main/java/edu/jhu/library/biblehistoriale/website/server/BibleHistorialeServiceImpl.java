package edu.jhu.library.biblehistoriale.website.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.profile.builder.ProfileBuilder;
import edu.jhu.library.biblehistoriale.profile.builder.ProfileBuilderException;
import edu.jhu.library.biblehistoriale.search.SearchServiceException;
import edu.jhu.library.biblehistoriale.search.Solr;
import edu.jhu.library.biblehistoriale.search.SolrSearchService;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeService;
import edu.jhu.library.biblehistoriale.website.client.rpc.RPCException;
import edu.jhu.library.biblehistoriale.website.shared.BrowseCriteria;
import edu.jhu.library.biblehistoriale.website.shared.CriteriaNode;

// TODO Update to use Path

public class BibleHistorialeServiceImpl extends RemoteServiceServlet implements
        BibleHistorialeService {
    private static final long serialVersionUID = 1L;

    private SolrSearchService search_service;
    private File bible_store;
    
    private File solrhome;
    
    private CriteriaNode criteria;

    @Override
    public String processCall(String payload) throws SerializationException {
        String response = "";
        
        try {
            response = super.processCall(payload);
        } catch (SerializationException e) {
            System.out.println("### SERIALIZATION EXCEPTION ###" + e.getMessage());
        }
        
        return response;
    }
    
    public void init() throws ServletException {
        String s = getServletConfig().getInitParameter("bible.index");

        if (s == null) {
            throw new ServletException("bible.index not specified");
        }

        solrhome = new File(getServletContext().getRealPath(s));

        try {
            Solr.createSolrInstall(solrhome);
            search_service = new SolrSearchService(solrhome);
        } catch (IOException e) {
            throw new ServletException("Error accessing solr index: "
                    + solrhome, e);
        }

        s = getServletConfig().getInitParameter("bible.store");

        if (s == null) {
            throw new ServletException("bible.store not specified");
        }

        bible_store = new File(getServletContext().getRealPath(s));
        update_index();
    }
    
    public void destroy() {
        try {
            search_service.close();
        } finally {
            try {
                FileUtils.deleteDirectory(solrhome);
            } catch (IOException e) {  }
        }
    }

    // TODO For now index all bibles on startup.

    private void update_index() throws ServletException {
        try {
            search_service.clear();
        } catch (SearchServiceException e) {
            throw new ServletException("Error clearing index", e);
        }

        for (File file : bible_store.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                try {
                    Path path = FileSystems.getDefault().getPath(
                            file.getCanonicalPath());

                    Bible bible = ProfileBuilder.buildProfile(path);
                    
                    search_service.index(bible);
                    
                    add_to_criteria(bible);
                } catch (IOException | ProfileBuilderException
                        | SearchServiceException e) {
                    throw new ServletException("Error indexing " + file, e);
                }
            }
        }
    }
    
    /**
     * If the bible property exists already as a sub criteria,
     * add the bible id to the current list of bibles under that sub
     * criteria. Otherwise, add a new sub criteria with the bible id as
     * the only value under it.
     * 
     * @param bible
     */
    private void add_to_criteria(Bible bible) {
        if (criteria == null) {
            criteria = new CriteriaNode("");
            
            for (BrowseCriteria bc : BrowseCriteria.values()) {
                criteria.addChildNode(new CriteriaNode(bc.message()));
            }
        }
        
        for (BrowseCriteria bc : BrowseCriteria.values()) {
            
            CriteriaNode node = criteria.getChildNodeByText(bc.message());
            
            String[] strs = bc.getPropertyFromBible(bible);
            
            for (String str : strs) {
                CriteriaNode add = node.getChildNodeByText(str);
                
                if (add == null) {
                    add = new CriteriaNode(str,
                            new CriteriaNode(bible.getId()));
                } else {
                    add.addChildNode(new CriteriaNode(bible.getId()));
                }
                
                node.addChildNode(add);
            }
        }
    }

    @Override
    public Bible lookupBible(String id) throws RPCException {
        File file = new File(bible_store, id + ".xml");

        if (!file.exists()) {
            return null;
        }

        try {
            Path path = FileSystems.getDefault().getPath(
                    file.getCanonicalPath());
            
            return ProfileBuilder.buildProfile(path);
        } catch (IOException | ProfileBuilderException e) {
            throw new RPCException("Error loading " + id, e);
        }
    }

    @Override
    public QueryResult search(Query query, QueryOptions opts)
            throws RPCException {
        try {
            return search_service.executeQuery(query, opts);
        } catch (SearchServiceException e) {
            throw new RPCException("Error while executing query: " + query, e);
        }
    }
    
    @Override
    public CriteriaNode allProfilesByCriteria() {
        return criteria;
    }
}
