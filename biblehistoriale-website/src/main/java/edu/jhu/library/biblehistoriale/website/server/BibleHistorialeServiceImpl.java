package edu.jhu.library.biblehistoriale.website.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

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

// TODO Update to use Path

public class BibleHistorialeServiceImpl extends RemoteServiceServlet implements
        BibleHistorialeService {
    private static final long serialVersionUID = 1L;

    private SolrSearchService search_service;
    private File bible_store;
    
    private File solrhome;

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
                } catch (IOException | ProfileBuilderException
                        | SearchServiceException e) {
                    throw new ServletException("Error indexing " + file, e);
                }
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
}