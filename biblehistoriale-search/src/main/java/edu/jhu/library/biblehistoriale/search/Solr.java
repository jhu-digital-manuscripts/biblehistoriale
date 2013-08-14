package edu.jhu.library.biblehistoriale.search;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;

/**
 * Helper for providing access to a SolrServer. It includes helper functions for
 * accessing the index. The close method must be called when the SolrServer is
 * no longer needed.
 */

public class Solr {
    // Embedded solr seems to require this core name
    private final static String CORE_NAME = "collection1";

    private final CoreContainer container;
    private final SolrServer server;

    public Solr(File solrhome) throws IOException {
        this.container = CoreContainer.createAndLoad(
                solrhome.getCanonicalPath(), new File(solrhome, "solr.xml"));

        if (container.getCoreInitFailures().size() > 0) {
            throw new IOException("Failure loading cores: "
                    + container.getCoreInitFailures());
        }

        this.server = new EmbeddedSolrServer(container, CORE_NAME);
    }

    public Solr(SolrServer server) {
        this.server = server;
        this.container = null;
    }

    /**
     * Create a local Solr install in the given directory.
     * 
     * @throws IOException
     */
    public static void createSolrInstall(File solrhome) throws IOException {
        // Cannot list resources so have keep names here

        String[] conf_filenames = new String[] { "solrconfig.xml",
                "stopwords_en.txt", "stopwords_fr.txt", "schema.xml",
                "contractions_fr.txt" };

        File core_dir = new File(solrhome, CORE_NAME);
        File conf_dir = new File(core_dir, "conf");

        conf_dir.mkdirs();

        new File(core_dir, "core.properties").createNewFile();

        // copy_resource("/solr/solr.xml", new File(core_dir, "solr.xml"));

        for (String name : conf_filenames) {
            File out = new File(conf_dir, name);
            String path = "/solr/" + CORE_NAME + "/conf/" + name;

            copy_resource(path, out);
        }
    }

    private static void copy_resource(String path, File dest)
            throws IOException {
        InputStream is = Solr.class.getResourceAsStream(path);

        if (is == null) {
            throw new IOException("Cannot find resource " + path);
        }

        FileUtils.copyInputStreamToFile(is, dest);
        is.close();
    }

    /**
     * @return number of documents in the index
     * @throws SolrServerException
     */
    public long size() throws SolrServerException {
        SolrQuery q = new SolrQuery("*:*");
        q.setRows(0);

        return server.query(q).getResults().getNumFound();
    }

    public void clear() throws IOException, SolrServerException {
        server.deleteByQuery("*:*");
        server.commit();
    }

    /**
     * @param query
     * @param offset
     * @param matches
     * @param params
     *            name,value pairs of Solr params
     * @return
     * @throws SolrServerException
     */
    public QueryResponse search(String query, int offset, int matches,
            String... params) throws SolrServerException {
        SolrQuery q = new SolrQuery(query);

        q.setStart(offset);
        q.setRows(matches);

        for (int i = 0; i < params.length;) {
            String name = params[i++];
            String val = params[i++];

            q.setParam(name, val);
        }

        return server.query(q);
    }

    public void close() {
        if (container != null) {
            container.shutdown();
        }
    }

    public void add(SolrInputDocument doc) throws SolrServerException,
            IOException {
        server.add(doc);
        server.commit();
    }
}
