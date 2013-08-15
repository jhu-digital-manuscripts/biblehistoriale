package edu.jhu.library.biblehistoriale.profile.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.Dimensions;
import edu.jhu.library.biblehistoriale.model.profile.Folios;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.Materials;
import edu.jhu.library.biblehistoriale.model.profile.PageLayout;
import edu.jhu.library.biblehistoriale.model.profile.PhysicalCharacteristics;
import edu.jhu.library.biblehistoriale.model.profile.QuireStructure;
import edu.jhu.library.biblehistoriale.model.profile.Volumes;

public class ProfilBuilderTest {
    
    private static final String path_str = 
            "C:\\Users\\john\\BibleHistoriale\\Example bibles";
    private static final String filename = "BrusselsKBR9001-2.xml";
    
    private static final Path path = Paths.get(path_str + "/" + filename);
    
    private Bible bible;
    
    @Before
    public void  setup() throws SAXException, IOException {
        Document doc = ProfileBuilder.createDocument(path);
        bible = ProfileBuilder.buildProfile(doc);
        
        System.out.println("\n###" + bible + "###\n");
        
        assertNotNull(bible);
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
        
        System.out.println("__" + "PhysicalCharacteristics ok" + "__\n");
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
        
        struct = structs.get(1);
        assertEquals(2, struct.getVolume());
        
        total = struct.quireTotal();
        typical = struct.typicalQuires();
        full = struct.fullQuireStructs();
        notes = struct.quireNotes();
        
        assertNotNull(total);
        assertNotNull(typical);
        assertNotNull(full);
        assertNotNull(notes);
        
        assertEquals(1, total.size());
        assertEquals(1, typical.size());
        assertEquals(1, full.size());
        assertEquals(0, notes.size());
        
        assertEquals(51, total.get(0).intValue());
        assertEquals(8, typical.get(0).intValue());
        assertEquals("1^2, 2-8^8, 9^2, 10-29^8, 30^2, 31-43^8, 44^6+1, 45-50^8, 51^4",
                full.get(0));
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