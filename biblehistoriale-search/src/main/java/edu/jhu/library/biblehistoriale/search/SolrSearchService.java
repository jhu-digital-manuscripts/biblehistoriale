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

import edu.jhu.library.biblehistoriale.model.profile.Annotation;
import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.Bibliography;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
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
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;
import edu.jhu.library.biblehistoriale.model.profile.TextualContent;
import edu.jhu.library.biblehistoriale.model.profile.Title;
import edu.jhu.library.biblehistoriale.model.profile.TitleIncipit;
import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryMatch;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.model.query.TermField;

/**
 * Search service that allows manuscript profiles to be indexed and searched.
 * Implemented with solr.
 */
public class SolrSearchService {
    private static int MAX_MATCHES = 100;
    private static final Map<TermField, String[]> field_map;

    static {
        field_map = new HashMap<>();

        // all searchable fields need to map to solr fields
        // TODO include a term that includes every Solr field?
        field_map.put(TermField.TITLE, new String[] { "title", "shortName",
                "currentShelfmark"});

        field_map.put(TermField.PEOPLE, new String[] { "contributors",
                "ownerName" });
        
        field_map.put(TermField.PLACES, new String[] { "prodLoc", "ownerPlace" });

        field_map.put(TermField.TEXT, new String[] { "MasterTableOfContents",
                "catechismsFirstLines", "bibleBookTitle", "canticleType",
                "contentNote", "prefatoryNote", "bookNote", "guyartIncipit",
                "comestorLetterIncipit", "comestorText",
                "annotationText", "otherPrefaceText", "bibleBookIncipit" });

        field_map.put(TermField.ILLUSTRATIONS, new String[] {
                "illustrationKeywords", "illustrationNote" });

        field_map.put(TermField.PHYS_CHAR, new String[] { "presentState",
                "quireTotal", "typicalQuire", "columns", "glossPlace",
                "runningHeads", "glossHeadings", "material", "bindDate",
                "rubricNote", "pageLayoutNote", "physicalNote", "volumeNote",
                "quireNote" });

        field_map.put(TermField.CLASSIFICATION, new String[] { "currentCity",
                "currentRepository", "currentShelfmark", "classificationNote",
                "secundoFolioText", "bergerCategory", "bergerBhcSubtype",
                "sneddonCategory", "sneddonSubcategory1", "sneddonSubcategory2",
                "sneddonSubcategory3", "classificationNote", "title"});

        field_map.put(TermField.BIBLIOGRAPHY, new String[] { "bibAuthor",
                "articleTitle", "bookOrJournalTitle", "publicationInfo" });
        
        field_map.put(TermField.ALL, new String[] { "title", "shortName",
                "presentState", "quireTotal", "typicalQuire", "columns",
                "glossPlace", "runningHeads", "glossHeadings", "material",
                "bindDate", "rubricNote", "pageLayoutNote", "physicalNote",
                "volumeNote", "quireNote", "prodDate", "prodLoc", "contributors",
                "ownerName", "provenanceNote", "productionNote", "signatureText",
                "dedication", "legalInscriptions", "patronPortrait", "patronArms",
                "colophon", "annotationText", "illustrationKeywords", 
                "illustrationNote", "currentCity", "currentRepository", 
                "currentShelfmark", "classificationNote", "secundoFolioText",
                "MasterTableOfContents", "bibleBookTitle", "canticleType", 
                "catechismsFirstLines", "contentNote", "prefatoryNote",
                "bookNote", "guyartIncipit", "otherPrefaceText", 
                "comestorLetterIncipit", "comestorText", "bibleBookIncipit", 
                "bibAuthor", "articleTitle", "bookOrJournalTitle", "publicationInfo",
                "bergerCategory", "bergerBhcSubtype", "sneddonCategory", 
                "sneddonSubcategory1", "sneddonSubcategory2", "sneddonSubcategory3",
                "ownerPlace"});
    }

    private final Solr solr;
    private final SolrQueryBuilder query_builder;

    public SolrSearchService(Solr solr) {
        this.solr = solr;
        this.query_builder = new SolrQueryBuilder(field_map);
    }

    // TODO Update to use Path
    
    public SolrSearchService(File solrhome) throws IOException {
        this(new Solr(solrhome));
    }

    public QueryResult executeQuery(Query query, QueryOptions opts)
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

        QueryResult result = new QueryResult(offset, docs.getNumFound(),
                matches);

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
                        sb.append(field + ": |" + snippets.get(field) + "| ");
                    }

                    context = sb.toString();
                }
            }

            matches.add(new QueryMatch(id, context));
        }

        return result;
    }

    /**
     * Add a Bible object to the Solr search index
     * 
     * @param profile
     * @throws SearchServiceException
     */
    public void index(Bible profile) throws SearchServiceException {
        try {
            solr.add(buildSolrInputDocument(profile));
        } catch (SolrServerException | IOException e) {
            throw new SearchServiceException(e);
        }
    }

    private SolrInputDocument buildSolrInputDocument(Bible profile) {
        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", profile.getId());
        doc.addField("shortName", profile.getShortName());

        doc.addField(field_map.get(TermField.TITLE)[0], profile
                .getClassification().getCoverTitle());

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
        for (String str : phys.glossHeadings()) {
            doc.addField("glossHeadings", str);
        }

        for (QuireStructure struct : phys.quireStructs()) {
            for (String str : struct.quireNotes()) {
                doc.addField("quireNote", str);
            }
            for (Integer in : struct.quireTotal()) {
                doc.addField("quireTotal", in.toString());
            }
            for (Integer in : struct.typicalQuires()) {
                doc.addField("typicalQuire", in.toString());
            }
        }

        // Proven Patron History fields
        ProvenPatronHistory hist = profile.getProvenPatronHist();

        doc.addField("prodDate", hist.getProduction().getProdDate());
        doc.addField("prodLoc", hist.getProduction().getProdLoc());
        doc.addField("productionNote", hist.getProduction().getProdNotes());
        for (String str : hist.provenanceNote()) {
            doc.addField("provenanceNote", str);
        }
        for (String str : hist.getPersonalization().dedications()) {
            doc.addField("dedication", str);
        }

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
                for (String place : owner.getOwnerPlace()) {
                    doc.addField("ownerPlace", place);
                }
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
        doc.addField("classificationNote", classif.getClassification()
                .getClassificationNote());
        CatalogerClassification cc = classif.getClassification();
        
        Berger berg = cc.getBergerClass();
        if (berg != null) {
            doc.addField("bergerCategory", berg.getCategory() != null
                    ? berg.getCategory().category() : "");
            doc.addField("bergerBhcSubtype", berg.getBhcSubtype() != null
                    ? berg.getBhcSubtype().subtype() : "");
        }
        
        Sneddon sned = cc.getSneddonClass();
        if (sned != null) {
            doc.addField("sneddonCategory", sned.getCategory() != null
                    ? sned.getCategory().category() : "");
            doc.addField("sneddonSubcategory1", sned.getSub1() != null
                    ? sned.getSub1().category() : "");
            doc.addField("sneddonSubcategory2", sned.getSub2() != null
                    ? sned.getSub2().category() : "");
            doc.addField("sneddonSubcategory3", sned.getSub3() != null
                    ? sned.getSub3().category() : "");
        }
        
        for (SecundoFolio folio : classif.getClassification().secundoFolios()) {
            doc.addField("secundoFolioText", folio.getValue());
        }

        // Textual Content fields
        TextualContent cont = profile.getTextualContent();

        doc.addField("canticleType", cont.parascripturalItem()
                .getCanticleType());
        for (String str : cont.notes()) {
            doc.addField("contentNote", str);
        }

        for (PrefatoryMatter premat : cont.prefatoryMatters()) {
            doc.addField("MasterTableOfContents", premat
                    .getMasterTableOfContents().getText());
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
        for (CatechismsPrayersTreatise cpt : cont.parascripturalItem()
                .catechismPrayersTreatises()) {
            for (String str : cpt.getDescriptionsFirstLines()) {
                doc.addField("catechismsFirstLines", str);
            }
        }

        // Bibliography information fields
        Bibliography bib = profile.getBibliography();

        for (BiblioEntry entry : bib) {
            for (String str : entry.bibAuthors()) {
                doc.addField("bibAuthor", str);
            }
            doc.addField("articleTitle", entry.getArticleTitle());
            doc.addField("bookOrJournalTitle", entry.getBookOrJournalTitle());
            doc.addField("publicationInfo", entry.getPublicationInfo());
        }

        return doc;
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
