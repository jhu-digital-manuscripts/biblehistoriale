package edu.jhu.library.biblehistoriale.search;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class SolrTest {

    @Test
    public void test() throws Exception {
        File solrhome = File.createTempFile(this.getClass().getName(), null);
        solrhome.delete();
        solrhome.mkdir();
        
        try {
            Solr.createSolrInstall(solrhome);

            Solr solr = new Solr(solrhome);

            assertEquals(0, solr.size());
            
            solr.clear();
            solr.close();
        } finally {
            FileUtils.deleteDirectory(solrhome);
        }
    }

}
