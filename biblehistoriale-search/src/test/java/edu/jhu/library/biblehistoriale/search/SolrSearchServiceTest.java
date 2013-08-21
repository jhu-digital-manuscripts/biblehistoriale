package edu.jhu.library.biblehistoriale.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.profile.builder.ProfileBuilder;

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
    
    @Test
    public void testIndex() throws Exception {
        final String filename = "profiles/BrusselsKBR9001-2.xml";
        
        Path path = Paths.get(
                this.getClass().getClassLoader().getResource(filename)
                .toString().substring(6));
        
        Bible profile = ProfileBuilder.buildProfile(path);
        
        // Clear index, then add the new profile
        try {
            service.clear();
            service.index(profile);
        } catch (SearchServiceException e) {
            fail();
        }
        
        Query query = new Query(TermField.TITLE, "La S. Bible");
        QueryResult<QueryMatch> result = service.executeQuery(query,
                new QueryOptions());
        
        assertEquals(1, result.getTotal());
        System.out.println(result.matches().get(0).getId());
        
        testExecuteQuery();
    }
    
}
