package edu.jhu.library.biblehistoriale.profile.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.jhu.library.biblehistoriale.model.profile.Annotation;
import edu.jhu.library.biblehistoriale.model.profile.Annotation.AnnotationType;
import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.Bibliography;
import edu.jhu.library.biblehistoriale.model.profile.BookType;
import edu.jhu.library.biblehistoriale.model.profile.BookType.CollectionType;
import edu.jhu.library.biblehistoriale.model.profile.BookType.Technology;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.Choice;
import edu.jhu.library.biblehistoriale.model.profile.Classification;
import edu.jhu.library.biblehistoriale.model.profile.Contributor.ContributorType;
import edu.jhu.library.biblehistoriale.model.profile.DecorationSummary;
import edu.jhu.library.biblehistoriale.model.profile.DecorationSummary.IllustrationStyle;
import edu.jhu.library.biblehistoriale.model.profile.Dimensions;
import edu.jhu.library.biblehistoriale.model.profile.Folios;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.Incipit.Accuracy;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.MasterTableOfContents;
import edu.jhu.library.biblehistoriale.model.profile.MasterTableOfContents.Detail;
import edu.jhu.library.biblehistoriale.model.profile.Materials;
import edu.jhu.library.biblehistoriale.model.profile.MiscContent;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;
import edu.jhu.library.biblehistoriale.model.profile.PageLayout;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem.AddedChoice;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem.LitanyForm;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem.SneddonId;
import edu.jhu.library.biblehistoriale.model.profile.Personalization;
import edu.jhu.library.biblehistoriale.model.profile.PersonalizationItem;
import edu.jhu.library.biblehistoriale.model.profile.PhysicalCharacteristics;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Production;
import edu.jhu.library.biblehistoriale.model.profile.ProvenPatronHistory;
import edu.jhu.library.biblehistoriale.model.profile.QuireStructure;
import edu.jhu.library.biblehistoriale.model.profile.SecundoFolio;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;
import edu.jhu.library.biblehistoriale.model.profile.TextualContent;
import edu.jhu.library.biblehistoriale.model.profile.Title;
import edu.jhu.library.biblehistoriale.model.profile.TitleIncipit;
import edu.jhu.library.biblehistoriale.model.profile.Volumes;

public class ProfilBuilderTest {
    private static final String filename = "profiles/BrusselsKBR9001-2.xml";
    
    private Bible bible;
    
    @Before
    public void  setup() throws SAXException, IOException, ProfileBuilderException {
        bible = null;
        
        InputStream in = 
                this.getClass().getClassLoader().getResourceAsStream(filename);
        
        Document doc = ProfileBuilder.createDocument(in);
        
        bible = ProfileBuilder.buildProfile(filename, doc);
        
        assertNotNull(bible);
        //System.out.println("###" + bible + "###");
    }
    
    @Test
    public void testPhysChar() {
        PhysicalCharacteristics phys = bible.getPhysChar();
        assertNotNull(phys);
        
        Volumes vols = phys.getVolumes();
        List<Dimensions> dims = phys.dimensions();
        Folios folios = phys.getFolios();
        List<QuireStructure> structs = phys.quireStructs();
        PageLayout layout = phys.getPageLayout();
        String rubric = phys.getRubricNotes();
        String layout_note = phys.getPageLayoutNotes();
        List<String> glosses = phys.glossHeadings();
        List<String> underlinings = phys.underlinings();
        Materials mats = phys.getMaterials();
        String phys_notes = phys.getPhysicaNotes();
        
        testVolumes(vols);
        testDimensions(dims);
        testFolios(folios);
        testQuireStructs(structs);
        testPageLayout(layout);
        testGlossHeadings(glosses);
        testUnderlinings(underlinings);
        testMaterials(mats);
        
        assertEquals("Rubric chapter headings appear irregularly in non-Guyart books. They have been added to the Gospels. In other places, they are sometimes used to highlight a moral lesson to be obtained from the text that follows.",
                rubric);
        assertEquals("Book names in running heads and rubrics sometimes irregularly refer to groups of books (Gospels, Epistles, Prophets) rather than ind4idual ones.",
                layout_note);
        assertEquals("Pages, quires, sheets of parchment or illuminations originally numbered bottom right in roman numerals. These have mostly been cut off. Edges of pages decorated with gold, red and blue ink in striped pattern (not marbled). A thin gold satin ribbon bookmarker is attached to the binding; currently rests between folios 194 and 195.",
                phys_notes);
        
        //System.out.println("__" + "PhysicalCharacteristics ok" + "__");
    }
    
    @Test
    public void testProvenPatronHist() {
        ProvenPatronHistory hist = bible.getProvenPatronHist();
        assertNotNull(hist);
        
        Production prod = hist.getProduction();
        List<Ownership> ownerships = hist.ownerships();
        Personalization person = hist.getPersonalization();
        List<Annotation> anns = hist.annotations();
        List<String> provs = hist.provenanceNote();
        
        testProduction(prod);
        testOwnerships(ownerships);
        testPersonalization(person);
        testAnnotations(anns);
        testProvenanceNotes(provs);
        
        //System.out.println("__" + "Proven Patron History ok" + "__");
    }
    
    @Test
    public void testIllustrationList() {
        IllustrationList illustrations = bible.getIllustrations();
        assertNotNull(illustrations);
        
        // All fields can potentially be absent
        DecorationSummary summary = illustrations.getDecorationSummary();
        String note = illustrations.getIllustrationNote();
        
        for (Illustration ill : illustrations) {
            testIllustration(ill);
        }
        
        testSummary(summary);
        assertNotNull(note);
        
        assertEquals(179, illustrations.size());
        
        Illustration ill = illustrations.illustration(0);
        assertEquals(1, ill.getNumber());
        assertEquals("Preface (glossary)", ill.getBook());
        assertEquals(1, ill.getVolume());
        assertEquals("1r", ill.getFolio());
        assertTrue(ill.getKeywords().endsWith("King Charles VI"));
        
        //System.out.println("__Illustration List ok__");
    }
    
    @Test
    public void testClassification() {
        Classification classification = bible.getClassification();
        assertNotNull(classification);
        
        BookType type = classification.getBookType();
        CatalogerClassification cc = classification.getClassification();

        testShelfmarks(classification);
        testBookType(type);
        testTitles(classification);
        testCatalogerClassification(cc);
        
        //System.out.println("__Classification ok__");
    }
    
    @Test
    public void testTextualContents() {
        TextualContent content = bible.getTextualContent();
        assertNotNull(content);
        
        List<PrefatoryMatter> matter = content.prefatoryMatters();
        List<BibleBooks> books = content.bibleBooks();
        List<MiscContent> miscs = content.miscContents();
        List<String> notes = content.notes();
        ParascripturalItem item = content.parascripturalItem();
        
        testPrefatoryMatters(matter);
        testBibleBooks(books);
        testMiscContents(miscs);
        testParascripturalItem(item);
        
        assertNotNull(notes);
        assertEquals(0, notes.size());
        
        //System.out.println("__TextualContents ok__");
    }
    
    @Test
    public void testBibliography() {
        Bibliography bib = bible.getBibliography();
        assertNotNull(bib);
        
        for (BiblioEntry entry : bib) {
            assertNotNull(entry.bibAuthors());
            assertNotNull(entry.getBookOrJournalTitle());
            assertNotNull(entry.articleLinks());
        }
        
        assertEquals(4, bib.size());
        
        BiblioEntry entry = bib.biblioEntry(3);
        assertEquals(1, entry.bibAuthors().size());
        assertEquals("Sneddon, Clive R.", entry.bibAuthors().get(0));
        assertTrue(entry.getBookOrJournalTitle()
                .endsWith("Translation of the Bible."));
        assertTrue(entry.getPublicationInfo().endsWith("1978."));
        assertEquals(0, entry.articleLinks().size());
        
        // System.out.println("__Bibliography ok__");
    }
    
    private void testParascripturalItem(ParascripturalItem item) {
        assertNotNull(item);
        
        assertNotNull(item.catechismPrayersTreatises());
        assertEquals(0, item.catechismPrayersTreatises().size());
        // No catechisms prayers treatises in example profile
        
        assertEquals(LitanyForm.NA, item.getForm());
        assertEquals(Choice.N, item.getLitanyPresence());
        assertEquals("0", item.getLocVol());
        assertEquals("0", item.getLocStart());
        assertEquals("0", item.getLocEnd());
        assertEquals(SneddonId.NA, item.getSneddonId());
        
        assertEquals(0, item.getVolume());
        assertEquals("0", item.getCanticleEndFolio());
        assertEquals("n", item.getCanticleStartFolio());
        assertEquals(Choice.N, item.getCanticlePresence());
        assertEquals("n/a", item.getCanticleType());
        
        assertEquals(AddedChoice.N, item.getJeanDeBlois());
        assertEquals(AddedChoice.Y, item.getJerome());
    }

    private void testMiscContents(List<MiscContent> miscs) {
        assertNotNull(miscs);
        assertEquals(0, miscs.size());
        // No misc contents in example profile
    }

    private void testBibleBooks(List<BibleBooks> books) {
        assertNotNull(books);
        
        for (BibleBooks b : books) {
            for (Title title : b) {
                assertNotNull(title.getBookName());
                assertNotNull(title.hasChapterNames());
                assertNotNull(title.getStartPage());
                assertNotNull(title.getGlossType());
                assertNotNull(title.getEndPage());
                assertNotNull(title.getTextVersion());
                
                for (TitleIncipit inc : title) {
                    assertNotNull(inc.getAccuracy());
                }
            }
        }
        
        assertEquals(2, books.size());
        
        // Volume 1
        BibleBooks book = books.get(0);
        assertEquals(24, book.size());
        assertEquals(1, book.getVolume());
        
        Title title = book.title(0);
        assertEquals("BH", title.getTextVersion());
        assertEquals("BH", title.getGlossType());
        assertEquals("multiple", title.getGlossType2());
        assertEquals("20v", title.getStartPage());
        assertEquals(Choice.Y, title.hasChapterNames());
        assertFalse(title.hasTableOfContents());
        assertEquals("Genesis", title.getBookName());
        assertTrue(title.getBookNote().endsWith("included here."));
        assertEquals(4, title.size());
        
        TitleIncipit incipit = title.incipit(3);
        assertEquals("questions", incipit.getTextType());
        assertEquals(Accuracy.ACTUAL, incipit.getAccuracy());
        assertTrue(incipit.getText().endsWith("la coste adam"));
    }

    private void testPrefatoryMatters(List<PrefatoryMatter> matter) {
        assertNotNull(matter);
        
        for (PrefatoryMatter m : matter) {
            // Only one required field
            assertNotNull(m.getMasterTableOfContents());
        }
        
        assertEquals(2, matter.size());
        
        PrefatoryMatter mat = matter.get(0);
        assertNotNull(mat.otherPrefaces());
        assertNotNull(mat.getPrefactoryNote());
        
        MasterTableOfContents master = mat.getMasterTableOfContents();
        assertNotNull(master);
        assertEquals("11v", master.getStartPage());
        assertEquals(Detail.MIXED, master.getTableDetail());
        assertTrue(master.matchesContents());
        assertTrue(master.getText().endsWith("creation du monde..."));
        
        assertEquals(2, mat.otherPrefaces().size());
        
        OtherPreface other = mat.otherPrefaces().get(0);
        assertTrue(other.getText().endsWith("deux manieres"));
        assertEquals(Accuracy.ACTUAL, other.getAccuracy());
        assertEquals("1r", other.getStartPage());
        assertEquals("", other.getTranscriptionUrl());
        
        assertTrue(mat.getPrefactoryNote()
                .endsWith("four senses of Scripture."));
    }

    private void testBookType(BookType type) {
        assertNotNull(type);
        
        assertNotNull(type.getType());
        assertNotNull(type.getTech());
        
        assertEquals(CollectionType.BIBLE, type.getType());
        assertEquals(Technology.MANUSCRIPT, type.getTech());
    }

    private void testCatalogerClassification(CatalogerClassification cc) {
        assertNotNull(cc);
        
        Berger berger = cc.getBergerClass();
        Sneddon sneddon = cc.getSneddonClass();
        List<SecundoFolio> secundos = cc.secundoFolios();
        
        assertNotNull(berger);
        assertNotNull(sneddon);
        assertNotNull(secundos);
        
        assertEquals(Berger.Category.BHC, berger.getCategory());
        assertEquals(Berger.BhcSubtype.PROLFULL, berger.getBhcSubtype());
        
        assertEquals(Sneddon.Category.BHC, sneddon.getCategory());
        assertEquals(Sneddon.SubcategoryOne.MIXED, sneddon.getSub1());
        assertEquals("Z2", sneddon.getSiglum());
        assertEquals("117, 120", sneddon.getEntry());
        
        assertEquals("10-11", cc.getFournieNumber());
        assertEquals("http://acrh.revues.org/1467#tocto1n10",
                cc.getFournieLink());
        
        assertEquals(2, secundos.size());
        for (int i = 0; i < secundos.size(); i++) {
            SecundoFolio secundo = secundos.get(i);
            
            assertEquals(i+1, secundo.getVolume());
            assertNotNull(secundo.getValue());
        }
        
        assertNotNull(cc.getClassificationNote());
        assertTrue(cc.getClassificationNote()
                .endsWith("Presles Psalms"));
    }

    private void testTitles(Classification classification) {
        String coverTitle = classification.getCoverTitle();
        String rubricTitle = classification.getRubricTitle();
        
        assertEquals("La S. Bible (Tome 1; Tome 2). Inside binding: Cest le premier?/second volume de la Bible", 
                coverTitle);
        assertNull(rubricTitle);
    }

    private void testShelfmarks(Classification classification) {
        // All fields are optional
        String currentCity = classification.getCurrentCity();
        String currentRepo = classification.getCurrentRepository();
        String currentShelfmark = classification.getCurrentShelfmark();
        String repoLink = classification.getRepositoryLink();
        List<String> former_shelfmarks = classification.formerShelfmarks();
        
        assertNotNull(currentCity);
        assertNotNull(currentRepo);
        assertNotNull(currentShelfmark);
        assertNotNull(repoLink);
        assertNotNull(former_shelfmarks);
        
        assertEquals("Brussels, Belgium", currentCity);
        assertEquals("Bibliothèque royale de Belgique (KBR)",
                currentRepo);
        assertEquals("9001-9002", currentShelfmark);
        assertEquals("http://www.kbr.be/", repoLink);
        assertEquals(0, former_shelfmarks.size());
    }

    private void testIllustration(Illustration ill) {
        assertNotNull(ill.getKeywords());
        assertNotNull(ill.getBook());
        assertNotNull(ill.getFolio());
        assertNotNull(ill.getNumber());
        assertNotNull(ill.getVolume());
    }

    private void testSummary(DecorationSummary summary) {
        if (summary == null) {
            return;
        }
        
        assertNotNull(summary.getNumber());
        assertNotNull(summary.getLargeIlls());
        assertNotNull(summary.getIllStyle());
        assertNotNull(summary.getFoliateBorder());
        assertNotNull(summary.getBasDePage());
        assertNotNull(summary.getDecoratedInitials());
        assertNotNull(summary.getArtistWorkshops());
        
        assertEquals(180, summary.getNumber());
        assertEquals(5, summary.getLargeIlls());
        assertEquals(IllustrationStyle.ILLUMINATION,
                summary.getIllStyle());
        assertEquals(Choice.Y, summary.getFoliateBorder());
        assertEquals(Choice.N, summary.getBasDePage());
        assertEquals(Choice.Y, summary.getDecoratedInitials());
        
        assertEquals(4, summary.getArtistWorkshops().size());
        assertEquals("Maître de la Cité des Dames",
                summary.getArtistWorkshops().get(3));
    }

    private void testProvenanceNotes(List<String> provs) {
        assertNotNull(provs);
        assertEquals(0, provs.size());
        // No provenance notes in example profile
    }

    private void testAnnotations(List<Annotation> anns) {
        assertNotNull(anns);
        assertEquals(19, anns.size());
        
        // Note: every field here is optional in the schema.
/*        for (Annotation ann : anns) {
            assertNotNull(ann.getVolume());
            assertNotNull(ann.getFolio());
            assertNotNull(ann.getBook());
            assertNotNull(ann.getType());
            assertNotNull(ann.getName());
            assertNotNull(ann.getDate());
            assertNotNull(ann.getTextReferenced());
            assertNotNull(ann.getText());
        }*/
        
        Annotation ann = anns.get(0);
        assertEquals(2, ann.getVolume());
        assertEquals("10r", ann.getFolio());
        assertEquals("Proverbs", ann.getBook());
        assertEquals(AnnotationType.MARKEDPASSAGE, ann.getType());
        
        assertTrue(ann.getName().endsWith("(Petrus Gilberti?)"));
        assertEquals("Early 15th century", ann.getDate());
        assertTrue(ann.getTextReferenced().endsWith("a moult d’amis"));
        assertEquals("Nota", ann.getText());
    }

    private void testPersonalization(Personalization person) {
        assertNotNull(person);
        
        assertNotNull(person.signatures());
        assertNotNull(person.dedications());
        assertNotNull(person.legalInscriptions());
        assertNotNull(person.patronPortraits());
        assertNotNull(person.patronArms());
        assertNotNull(person.colophons());
        
        assertEquals(0, person.signatures().size());
        assertEquals(0, person.dedications().size());
        assertEquals(0, person.legalInscriptions().size());
        assertEquals(0, person.patronPortraits().size());
        assertEquals(0, person.patronArms().size());
        assertEquals(2, person.colophons().size());
        
        PersonalizationItem colophon = person.colophons().get(1);
        assertEquals(2, colophon.getVolume());
        assertEquals("386r", colophon.getFolio());
        assertEquals("lxix ystiores et iii grandes",
                colophon.getValue());
    }

    private void testOwnerships(List<Ownership> ownerships) {
        assertNotNull(ownerships);
        
        for (Ownership ownership : ownerships) {
            for (Owner owner : ownership) {
                assertNotNull(owner.getOwnerName());
            }
        }
        
        assertEquals(1, ownerships.size());
        
        Ownership ownership = ownerships.get(0);
        assertEquals(5, ownership.size());
        
        Owner owner = ownership.owner(4);
        assertEquals("Bibliothèque royale de Belgique",
                owner.getOwnerName());
        assertEquals("Taken during the Revolution",
                owner.getOwnershipDate());
        assertEquals(1, owner.getOwnerPlace().size());
        assertEquals("Brussels, Belgium",
                owner.getOwnerPlace().get(0));
    }

    private void testProduction(Production prod) {
        assertNotNull(prod);
        
        assertEquals(1410, prod.getProdStartDate());
        assertEquals(1420, prod.getProdEndDate());
        assertEquals("Early 15th century", prod.getProdDate());
        assertEquals("Paris, France", prod.getProdLoc());
        
        // List can potentially be empty
        assertEquals(6, prod.contributors().size());
        assertEquals(ContributorType.SCRIBE,
                prod.contributors().get(5).getType());
        
        assertEquals("Artists identified by Fournié.",
                prod.getProdNotes().substring(501));
    }

    private void testMaterials(Materials mats) {
        assertNotNull(mats);
        
        assertEquals("parchment", mats.getSupport().support());
        assertEquals("Sueded vellum over cardboard",
                mats.getBindMaterial());
        
        assertNull(mats.getBindDate());
        assertEquals(0, mats.getBindDateEndYear());
        assertEquals(0, mats.getBindDateStartYear());
    }

    private void testUnderlinings(List<String> underlinings) {
        assertNotNull(underlinings);
        assertEquals(1, underlinings.size());
        assertEquals("Used for Latin citations and irregularly with other rubrics.",
                underlinings.get(0));
    }

    private void testGlossHeadings(List<String> glosses) {
        assertNotNull(glosses);
        assertEquals(1, glosses.size());
        assertEquals("texte, glose, histoire, exposicion, question, response, incident, addicions, moralite, oppinion",
                glosses.get(0));
    }

    private void testPageLayout(PageLayout layout) {
        assertNotNull(layout);
        assertNotNull(layout.getColumns());
        assertNotNull(layout.getRunningHeads());
        assertNotNull(layout.getGlossPlace());
        assertNotNull(layout.getChapterNumbers());
        assertNotNull(layout.getSmallLetterHist());
        assertNotNull(layout.getCatchphrases());
        
        assertEquals("2", layout.getColumns().column());
        assertEquals("y", layout.getRunningHeads().value());
        assertEquals("in-text", layout.getGlossPlace().place());
        assertEquals("y", layout.getChapterNumbers().choice());
        assertEquals("n", layout.getSmallLetterHist().choice());
        assertEquals("y", layout.getCatchphrases().value());
    }

    private void testQuireStructs(List<QuireStructure> structs) {
        assertNotNull(structs);
        
        for (QuireStructure struct : structs) {
            assertNotNull(struct.getVolume());
            // All other elements are optional
        }
        
        assertEquals(2, structs.size());
        
        QuireStructure struct = structs.get(0);
        assertEquals(1, struct.getVolume());
        
        List<Integer> total = struct.quireTotal();
        List<Integer> typical = struct.typicalQuires();
        List<String> full = struct.fullQuireStructs();
        List<String> notes = struct.quireNotes();
        
        assertNotNull(total);
        assertNotNull(typical);
        assertNotNull(full);
        assertNotNull(notes);
        
        assertEquals(1, total.size());
        assertEquals(1, typical.size());
        assertEquals(1, full.size());
        assertEquals(1, notes.size());
        
        assertEquals(54, total.get(0).intValue());
        assertEquals(8, typical.get(0).intValue());
        assertEquals("1^8, 2^8+1, 3-14^8, 15^8+1, 16-53^8, 54^8+1",
                full.get(0));
        assertEquals("Quire structures supplied by Cl4e Sneddon",
                notes.get(0));
    }

    private void testVolumes(Volumes vols) {
        assertNotNull(vols);
        assertNotNull(vols.getPreviousState());
        assertNotNull(vols.getPresentState());
        
        assertNull(vols.volumeNotes());
        assertEquals("2", vols.getPreviousState().value());
        assertEquals("2", vols.getPresentState().value());
    }
    
    private void testDimensions(List<Dimensions> dims) {
        assertNotNull(dims);
        assertEquals(2, dims.size());
        
        for (Dimensions d : dims) {
            assertNotNull(d.getUnits());
            assertNotNull(d.getVolume());
            
            assertEquals("mm", d.getUnits());
        }
        
        Dimensions dim = dims.get(0);
        assertEquals("460x325", dim.getPage());
        assertEquals("300x205", dim.getTextBlock());
        
        dim = dims.get(1);
        assertEquals("460x325", dim.getPage());
        assertEquals("300x205", dim.getTextBlock());
    }
    
    private void testFolios(Folios folios) {
        assertNotNull(folios);
        assertNotNull(folios.getTotalFolios());
        
        assertEquals(2, folios.size());
        assertEquals(820, folios.getTotalFolios());
        
        IndVolume vol = folios.indVolume(0);
        assertEquals(1, vol.getVolume());
        assertEquals("1-435", vol.getValue());
        
        vol = folios.indVolume(1);
        assertEquals(2, vol.getVolume());
        assertEquals("1-385", vol.getValue());
    }
}
