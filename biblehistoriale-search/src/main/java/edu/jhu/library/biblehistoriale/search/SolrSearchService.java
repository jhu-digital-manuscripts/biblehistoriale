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
import org.apache.solr.common.SolrInputDocument;

import edu.jhu.library.biblehistoriale.model.Query;
import edu.jhu.library.biblehistoriale.model.QueryMatch;
import edu.jhu.library.biblehistoriale.model.QueryOptions;
import edu.jhu.library.biblehistoriale.model.QueryResult;
import edu.jhu.library.biblehistoriale.model.TermField;
import edu.jhu.library.biblehistoriale.model.profile.Annotation;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.Bibliography;
import edu.jhu.library.biblehistoriale.model.profile.CatechismsPrayersTreatise;
import edu.jhu.library.biblehistoriale.model.profile.Classification;
import edu.jhu.library.biblehistoriale.model.profile.ComestorLetter;
import edu.jhu.library.biblehistoriale.model.profile.Contributor;
import edu.jhu.library.biblehistoriale.model.profile.Guyart;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.Incipit;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;
import edu.jhu.library.biblehistoriale.model.profile.PersonalizationItem;
import edu.jhu.library.biblehistoriale.model.profile.PhysicalCharacteristics;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.ProvenPatronHistory;
import edu.jhu.library.biblehistoriale.model.profile.QuireStructure;
import edu.jhu.library.biblehistoriale.model.profile.SecundoFolio;
import edu.jhu.library.biblehistoriale.model.profile.Signature;
import edu.jhu.library.biblehistoriale.model.profile.TextualContent;
import edu.jhu.library.biblehistoriale.model.profile.Title;
import edu.jhu.library.biblehistoriale.model.profile.Title.TitleIncipit;

/**
 * Search service that allows manuscript profiles to be indexed and searched.
 * Implemented by solr.
 */
public class SolrSearchService {
    private static int MAX_MATCHES = 100;
    private static final Map<TermField, String[]> field_map;

    static {
        field_map = new HashMap<>();

        // all searchable fields need to map to solr fields
        // TODO include a term that includes every Solr field?
        field_map.put(TermField.TITLE, new String[] { 
                "title", "articleTitle", "bookOrJournalTitle", "shortName"
            });
        
        field_map.put(TermField.PEOPLE, new String[] { 
                "contributors", "ownerName", "bibAuthor"
            });
        
        field_map.put(TermField.TEXT, new String[] {
                "MasterTableOfContents", "catechismsFirstLines", "signatureText",
                "dedication", "legalInscriptions", "patronPortrait", "patronArms",
                "colophon", "annotationText", "secundoFolioText", "guyartIncipit", 
                "otherPrefaceText", "comestorLetterIncipit", "comestorText", 
                "bibleBookIncipit"
            });
        
        field_map.put(TermField.NOTES, new String[] { 
                "rubricNote", "pageLayoutNote", "physicalNote", "quireNote",
                "provenanceNote", "productionNote", "illustrationNote", "classificationNote",
                "contentNote", "prefatoryNote", "bookNote"
            });
        
        field_map.put(TermField.ILLUSTRATIONS, new String[] { 
                "illustrationKeywords", "illustrationNote"
            });
        
        field_map.put(TermField.PHYS_CHAR, new String[] { 
                "presentState", "quireTotal", "typicalQuire", "columns", "glossPlace",
                "runningHeads", "glossHeadings", "material", "bindDate", "rubricNote",
                "pageLayoutNote", "physicalNote", "volumeNote", "quireNote"
            });
        
        field_map.put(TermField.PATRON_HIST, new String[] { 
                "prodDate", "prodLoc", "contributors", "ownerName", "provenanceNote",
                "productionNote", "signatureText", "dedication", "legalInscriptions",
                "patronPortrait", "patronArms", "colophon", "annotationText"
            });
        
        field_map.put(TermField.CLASSIFICATION, new String[] { 
                "currentCity", "currentRepository", "currentShelfmark", "classificationNote",
                "secundoFolioText"
            });
        
        field_map.put(TermField.TEXTUAL_CONTENT, new String[] { 
                "MasterTableOfContents", "bibleBookTitle", "canticleType",
                "catechismsFirstLines", "contentNote", "prefatoryNote", "bookNote",
                "guyartIncipit", "otherPrefaceText", "comestorLetterIncipit",
                "comestorText", "bibleBookIncipit"
            });
        
        field_map.put(TermField.BIBLIOGRAPHY, new String[] { 
                "bibAuthor", "articleTitle", "bookOrJournalTitle", "publicationInfo"
            });
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
            // Highlighting setup in config.xml
            
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

    public void index(Bible profile) throws SearchServiceException {
        try {
            solr.add(buildSolrInputDocument(profile));
        } catch (SolrServerException | IOException e) {
            throw new SearchServiceException(e);
        }
    }
    
    private SolrInputDocument buildSolrInputDocument(Bible profile) {
        SolrInputDocument doc = new SolrInputDocument(); 
           // doc.addField("field_name", "field_value");
        
        doc.addField("id", profile.getId());
        doc.addField("shortName", profile.getShortName());
            
        doc.addField(field_map.get(TermField.TITLE)[0], 
                profile.getClassification().getCoverTitle());
        
        // Physical Characteristics fields
        PhysicalCharacteristics phys = profile.getPhysChar();
        
        doc.addField("presentState", phys.getVolumes().getPresentState());
        doc.addField("columns", phys.getPageLayout().getColumns());
        doc.addField("glossPlace", phys.getPageLayout().getGlossPlace());
        doc.addField("runningHeads", phys.getPageLayout().getRunningHeads());
        doc.addField("material", phys.getMaterials().getBindMaterial());
        doc.addField("bindDate", phys.getMaterials().getBindDate());
        doc.addField("rubricNote", phys.getRubricNotes());
        doc.addField("pageLayoutNote", phys.getPageLayoutNotes());
        doc.addField("physicalNote", phys.getPhysicaNotes());
        doc.addField("volumeNote", phys.getVolumes().volumeNotes());
        addListValues(doc, "glossHeadings", phys.glossHeadings());
        
        for (QuireStructure struct : phys.quireStructs()) {
            addListValues(doc, "quireNote", struct.quireNotes());
            addListValues(doc, "quireTotal", struct.quireTotal());
            addListValues(doc, "typicalQuire", struct.typicalQuires());
        }
        
        // Proven Patron History fields
        ProvenPatronHistory hist = profile.getProvenPatronHist();
        
        doc.addField("prodDate", hist.getProduction().getProdDate());
        doc.addField("prodLoc", hist.getProduction().getProdLoc());
        doc.addField("productionNote", hist.getProduction().getProdNotes());
        addListValues(doc, "provenanceNote", hist.provenanceNote());
        addListValues(doc, "dedication", hist.getPersonalization().dedications());
        
        for (PersonalizationItem item : hist.getPersonalization()
                .legalInscriptions()) {
            doc.addField("legalInscriptions", item.getValue());
        }
        for (PersonalizationItem item : hist.getPersonalization()
                .patronPortraits()) {
            doc.addField("patronPortrait", item.getValue());
        }
        for (PersonalizationItem item : hist.getPersonalization().patronArms()) {
            doc.addField("patronArms", item.getValue());
        }
        for (PersonalizationItem item : hist.getPersonalization().colophons()) {
            doc.addField("colophon", item.getValue());
        }
        for (Annotation ann : hist.annotations()) {
            doc.addField("annotationText", ann.getText());
        }
        for (Signature sig : hist.getPersonalization().signatures()) {
            doc.addField("signatureText", sig.getText());
        }
        for (Contributor cont : hist.getProduction().contributors()) {
            doc.addField("contributors", cont.getValue());
        }
        
        for (Ownership ownership : hist.ownerships()) {
            for (Owner owner : ownership) {
                doc.addField("ownerName", owner.getOwnerName());
            }
        }
        
        // Illustrations fields
        IllustrationList ills = profile.getIllustrations();
        
        doc.addField("illustrationNote", ills.getIllustrationNote());
        
        for (Illustration ill : ills) {
            doc.addField("illustrationKeywords", ill.getKeywords());
        }
        
        // Classification fields
        Classification classif = profile.getClassification();
        
        doc.addField("currentCity", classif.getCurrentCity());
        doc.addField("currentRepository", classif.getCurrentRepository());
        doc.addField("currentShelfmark", classif.getCurrentShelfmark());
        doc.addField("classificationNote", 
                classif.getClassification().getClassificationNote());
        
        for (SecundoFolio folio : classif.getClassification().secundoFolios()) {
            doc.addField("secundoFolioText", folio.getValue());
        }
        
        // Textual Content fields
        TextualContent cont = profile.getTextualContent();
        
        doc.addField("canticleType", cont.parascripturalItem().getCanticleType());
        addListValues(doc, "contentNote", cont.notes());
        
        for (PrefatoryMatter premat : cont.prefatoryMatters()) {
            doc.addField("MasterTableOfContents",
                   premat.getMasterTableOfContents().getText());
            doc.addField("prefatoryNote", premat.getPrefactoryNote());
            
            for (Guyart guy : premat.guyartList()) {
                doc.addField("guyartIncipit", guy.getIncipit().getText());
            }
            for (OtherPreface other : premat.otherPrefaces()) {
                doc.addField("otherPrefaceText", other.getText());
            }
            for (OtherPreface comestor : premat.comestorList()) {
                doc.addField("comestorText", comestor.getText());
            }
            for (ComestorLetter let : premat.comestorLetters()) {
                for (Incipit inc : let.incipits()) {
                    doc.addField("comestorLetterIncipit", inc.getText());
                }
            }
        }
        for (BibleBooks book : cont.bibleBooks()) {
            for (Title title : book) {
                doc.addField("bibleBookTitle", title.getBookName());
                doc.addField("bookNote", title.getBookNote());
                for (TitleIncipit inc : title) {
                    doc.addField("bibleBookIncipit", inc.getText());
                }
            }
        }
        for (CatechismsPrayersTreatise cpt : 
                    cont.parascripturalItem().catechismPrayersTreatises()) {
            addListValues(doc, "catechismsFirstLines", 
                    cpt.getDescriptionsFirstLines());
        }
        
        // Bibliography information fields
        Bibliography bib = profile.getBibliography();
        
        for (BiblioEntry entry : bib) {
            addListValues(doc, "bibAuthor", entry.bibAuthors());
            doc.addField("articleTitle", entry.getArticleTitle());
            doc.addField("bookOrJournalTitle", entry.getBookOrJournalTitle());
            doc.addField("publicationInfo", entry.getPublicationInfo());
        }
        
        return doc;
    }
    
    /**
     * Add multiple values to a field in a SolrInputDocument. The field
     * must have multiValued set to "true" in schema.xml
     * 
     * @param doc
     * @param fieldName
     * @param iterable
     *          The list of values to be added
     */
    private <T> void addListValues(SolrInputDocument doc, String fieldName,
            Iterable<T> iterable) {
        for (T t : iterable) {
            doc.addField(fieldName, t);
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
