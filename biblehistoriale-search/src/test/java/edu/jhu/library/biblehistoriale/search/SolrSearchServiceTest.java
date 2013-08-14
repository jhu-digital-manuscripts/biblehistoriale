package edu.jhu.library.biblehistoriale.search;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.jhu.library.biblehistoriale.model.Query;
import edu.jhu.library.biblehistoriale.model.QueryMatch;
import edu.jhu.library.biblehistoriale.model.QueryOperation;
import edu.jhu.library.biblehistoriale.model.QueryOptions;
import edu.jhu.library.biblehistoriale.model.QueryResult;
import edu.jhu.library.biblehistoriale.model.TermField;

public class SolrSearchServiceTest {
    private SolrSearchService service;
    private File solrhome;

    @Before
    public void setup() throws IOException {
        solrhome = File.createTempFile(this.getClass().getName(), null);
        solrhome.delete();
        solrhome.mkdir();
        
        Solr.createSolrInstall(solrhome);
        
        service = new SolrSearchService(solrhome);
    }

    @After
    public void cleanup() throws IOException {
        try {
            service.close();
        } finally {
            FileUtils.deleteDirectory(solrhome);
        }
    }

    @Test
    public void testExecuteQuery() throws Exception {
        Query query = new Query(QueryOperation.OR, new Query(TermField.TITLE,
                "moo"), new Query(TermField.TITLE, "gorilla"));
        QueryOptions opts = new QueryOptions();

        QueryResult<QueryMatch> result = service.executeQuery(query, opts);

        assertEquals(0, result.getTotal());
    }
}
