package edu.jhu.library.biblehistoriale.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.Bibliography;
import edu.jhu.library.biblehistoriale.model.profile.Classification;
import edu.jhu.library.biblehistoriale.model.profile.PhysicalCharacteristics;
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
    public void testIndex() throws Exception {
        final String[] filename = {
                "profiles/BrusselsKBR9001-2.xml",
                "profiles/VatBarbLat613.xml"
        };
        
        Path[] path = {
                Paths.get(this.getClass().getClassLoader().getResource(filename[0])
                        .toString().substring(6)),
                Paths.get(this.getClass().getClassLoader().getResource(filename[1])
                        .toString().substring(6))
        };
                
        
        Bible[] profile = {
                ProfileBuilder.buildProfile(path[0]),
                ProfileBuilder.buildProfile(path[1])
        };
        
        // Clear index, then add the new profiles
        try {
            service.clear();
            service.index(profile[0]);
            service.index(profile[1]);
        } catch (SearchServiceException e) {
            fail();
        }
        
        QueryOptions opts = new QueryOptions();
        Query query = new Query(TermField.TITLE, "La S. Bible");
        QueryResult<QueryMatch> result = service.executeQuery(query, opts);
        
        assertEquals(1, result.getTotal());
        System.out.println();
        System.out.println("Testing results highlighting: ");
        System.out.println(result.matches().get(0).getId());
        System.out.println(result.matches().get(0).getContext());
        
        query = null;
        result = null;
        query = new Query(TermField.PEOPLE, "sneddon");
        result = service.executeQuery(query, opts);
        
        assertEquals(2, result.getTotal());
        System.out.println();
        System.out.println(result.matches().get(0).getId());
        System.out.println(result.matches().get(0).getContext());
        System.out.println(result.matches().get(1).getId());
        System.out.println(result.matches().get(1).getContext());
        
        query = null;
        result = null;
        query = new Query(QueryOperation.OR, new Query(TermField.TEXT, "vs. the world"),
                new Query(TermField.PEOPLE, "Scott Pilgrim"));
        result = service.executeQuery(query, opts);
        assertEquals(0, result.getTotal());
        
        System.out.println();
    }
    
    @Test
    public void testExecuteQuery() throws Exception {
        final String[] title = {
                "Test Bible 1", "Test Bible 2", "The Far Side Gallery"
        };
        final String[] id = {
                "TestBible1-ID1", "TestBible2-ID2", "BizarreComics01"
        };
        final String[] physicalNote = {
                "", "Suffering water damage from that one time someone threw it in a pond, to see if it would float.",
                "Grease marks from someone reading it while eating burgers"
        };
        final String[] authors = {
                "The Hand of God", "Christ, Jesus",
                "Dave Grohl", "jesus",
                "Larson, Gary", "Bill Waterson"
        };
        
        Bible[] bibles = new Bible[3];
        
        service.clear();
        for (int i = 0; i < bibles.length; i++) {
            Bible bible = new Bible();
            PhysicalCharacteristics phys = new PhysicalCharacteristics();
            Classification classif = new Classification();
            Bibliography bib = new Bibliography();
            
            bibles[i] = bible;
            
            bible.setId(id[i]);
            bible.setPhysChar(phys);
            bible.setBibliography(bib);
            bible.setClassification(classif);
            
            ArrayList<BiblioEntry> entries = new ArrayList<BiblioEntry> ();
            ArrayList<String> auth = new ArrayList<String> ();
            BiblioEntry ent = new BiblioEntry();
            entries.add(ent);
            
            phys.setPhysicaNotes(physicalNote[i]);
            classif.setCoverTitle(title[i]);
            bib.setBiblioEntries(entries);
            ent.setBibAuthors(auth);
            
            auth.add(authors[i * 2]);
            auth.add(authors[i * 2 + 1]);
            
            service.index(bible);
        }
        
        testOrs();
        testAnds();
    }

    private void testAnds() throws Exception {
        QueryOperation op = QueryOperation.AND;
        
        QueryOptions opts = new QueryOptions();
        Query query = new Query(op,
                new Query(TermField.TITLE, "bible"),
                new Query(TermField.PHYS_CHAR, "water damage"));
        QueryResult<QueryMatch> result = service.executeQuery(query, opts);
        
        assertEquals(1, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.TITLE, "test"),
                new Query(TermField.TITLE, "far side"));
        result = service.executeQuery(query, opts);
        
        assertEquals(0, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.TITLE, "far side"));
        result = service.executeQuery(query, opts);
        assertEquals(1, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.TITLE, "bible"),
                new Query(TermField.PHYS_CHAR, "someone"),
                new Query(TermField.NOTES, "suffering"),
                new Query(TermField.PEOPLE, "jesus")
        );
        result = service.executeQuery(query, opts);
        assertEquals(1, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.TITLE, "bible"),
                new Query(TermField.PHYS_CHAR, "someone"),
                new Query(TermField.NOTES, "grease marks"),
                new Query(TermField.PEOPLE, "larson")
        );
        result = service.executeQuery(query, opts);
        assertEquals(0, result.getTotal());
        
    }

    private void testOrs() throws Exception {
        QueryOperation op = QueryOperation.OR;
        
        QueryOptions opts = new QueryOptions();
        Query query = new Query(QueryOperation.OR, new Query(TermField.TITLE,
                "moo"), new Query(TermField.TITLE, "gorilla"));
        QueryResult<QueryMatch> result = service.executeQuery(query, opts);

        assertEquals(0, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.TITLE, "far side"),
                new Query(TermField.PHYS_CHAR, "water damage")
        );
        result = service.executeQuery(query, opts);
        assertEquals(2, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.TITLE, "bible"));
        result = service.executeQuery(query, opts);
        assertEquals(2, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.NOTES, "jfa oi)(^&*( //\\")
        );
        result = service.executeQuery(query, opts);
        assertEquals(0, result.getTotal());
     
        query = new Query(op,
                new Query(TermField.TITLE, "1"),
                new Query(TermField.TITLE, "Far"),
                new Query(TermField.PEOPLE, "grohl")
        );
        result = service.executeQuery(query, opts);
        assertEquals(3, result.getTotal());
        
        query = new Query(op,
                new Query(TermField.PEOPLE, "larson"),
                new Query(TermField.PEOPLE, "gary"),
                new Query(TermField.PEOPLE, "Bill"),
                new Query(TermField.PEOPLE, "waterson")
        );
        result = service.executeQuery(query, opts);
        assertEquals(1, result.getTotal());
        
    }
    
}
