package edu.jhu.library.biblehistoriale.website.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.ComestorLetter;
import edu.jhu.library.biblehistoriale.model.profile.Guyart;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Title;

/**
 * This class contains some information for a single Bible volume.
 * 
 * <p>Mapping of illustrations to various contents items.
 * (Map key -> object :: index of prefatory matter in an associated list
 *  -> list of illustrations for that prefatory matter)</p>
 * 
 * <p>Prefatory matter and bible books for this volume</p>
 */
public class BibleVolume {
    // TODO does this actually work??
    private PrefatoryMatter prefatory;
    private BibleBooks books;
    
    private final int volume;
    
    private final Map<Integer, List<Illustration>> other_prefaces_ills;
    private final Map<Integer, List<Illustration>> guyart_ills;
    private final Map<Integer, List<Illustration>> comestor_letter_ills;
    private final Map<Integer, List<Illustration>> comestor_ills;
    private final List<Illustration> MasterTOC_ills;
    
    private final Map<String, List<Illustration>> book_ills;
    
    public BibleVolume(Bible bible, int volume) {
        this.volume = volume;
        
        for (PrefatoryMatter matter : 
            bible.getTextualContent().prefatoryMatters()) {
            if (matter.getVolume() == volume) {
                this.prefatory = matter;
            }
        }
        
        for (BibleBooks bb : bible.getTextualContent().bibleBooks()) {
            if (bb.getVolume() == volume) {
                this.books = bb;
            }
        }
        
        if (prefatory == null) {
            prefatory = new PrefatoryMatter();
        }
        
        if (books == null) {
            books = new BibleBooks();
        }
        
        IllustrationList ills = bible.getIllustrations();
        
        this.other_prefaces_ills = new HashMap<Integer, List<Illustration>> ();
        this.guyart_ills = new HashMap<Integer, List<Illustration>> ();
        this.comestor_letter_ills = new HashMap<Integer, List<Illustration>> ();
        this.comestor_ills = new HashMap<Integer, List<Illustration>> ();
        this.book_ills = new HashMap<String, List<Illustration>> ();
        this.MasterTOC_ills = setTOCIlls(ills);
        
        setIllustrationMaps(ills);
        setBookIlls(ills);
    }
    
    public BibleVolume(PrefatoryMatter prefatory,
            BibleBooks books, IllustrationList ills) {
        this.prefatory = prefatory;
        this.books = books;
        
        this.volume = prefatory.getVolume();
        
        this.other_prefaces_ills = new HashMap<Integer, List<Illustration>> ();
        this.guyart_ills = new HashMap<Integer, List<Illustration>> ();
        this.comestor_letter_ills = new HashMap<Integer, List<Illustration>> ();
        this.comestor_ills = new HashMap<Integer, List<Illustration>> ();
        this.book_ills = new HashMap<String, List<Illustration>> ();
        this.MasterTOC_ills = setTOCIlls(ills);
        
        setIllustrationMaps(ills);
        setBookIlls(ills);
    }
    
    public PrefatoryMatter getPrefatoryMatter() {
        return prefatory;
    }
    
    public BibleBooks getBibleBooks() {
        return books;
    }
    
    public List<Illustration> getOtherPrefacesIlls(OtherPreface other) {
        for (int i = 0; i < prefatory.otherPrefaces().size(); i++) {
            if (other.equals(prefatory.otherPrefaces().get(i))) {
                return other_prefaces_ills.get(i);
            }
        }
        
        return null;
    }
    
    public List<Illustration> getGuyartIlls(Guyart guyart) {
        for (int i = 0; i < prefatory.guyartList().size(); i++) {
            if (guyart.equals(prefatory.guyartList().get(i))) {
                return guyart_ills.get(i);
            }
        }
        
        return null;
    }
    
    public List<Illustration> getComestorLetterIlls(ComestorLetter cs) {
        for (int i = 0; i < prefatory.comestorLetters().size(); i++) {
            if (cs.equals(prefatory.comestorLetters().get(i))) {
                return comestor_letter_ills.get(i);
            }
        }
        
        return null;
    }
    
    public List<Illustration> getComestorIlls(OtherPreface comestor) {
        for (int i = 0; i < prefatory.comestorList().size(); i++) {
            if (comestor.equals(prefatory.comestorList().get(i))) {
                return comestor_ills.get(i);
            }
        }
        
        return null;
    }
    
    public List<Illustration> getMasterTOCIlls() {
        return MasterTOC_ills;
    }
    
    public List<Illustration> getTitleIlls(Title title) {
        return book_ills.get(title.getBookName());
    }
    
    private void setBookIlls(IllustrationList all_ills) {
        for (Title title : books) {
            List<Illustration> ills = new ArrayList<Illustration> ();
            
            for (Illustration ill : all_ills) {
                if (title.getBookName().trim().equalsIgnoreCase(
                        ill.getBook().trim()))
                    ills.add(ill);
            }
            
            book_ills.put(title.getBookName(), ills);
        }
    }
    
    private boolean inOnlyThisItem(String folio, OtherPreface item) {
        
        String start_page = item.getStartPage();
        
        if (folio.compareToIgnoreCase(start_page) < 0) {
            return false;
        }
        
        for (OtherPreface other : prefatory.otherPrefaces()) {
            // Ignore any items that occur before the item under test
            if (other.getStartPage() != null
                    && start_page.compareToIgnoreCase(other.getStartPage()) <= 0
                    && folio.compareToIgnoreCase(other.getStartPage()) > 0) {
                return false;
            }
        }
        
        for (OtherPreface other : prefatory.guyartList()) {
            if (other.getStartPage() != null
                    && start_page.compareToIgnoreCase(other.getStartPage()) <= 0
                    && folio.compareToIgnoreCase(other.getStartPage()) > 0) {
                return false;
            }
        }
        
        for (OtherPreface other : prefatory.comestorLetters()) {
            if (other.getStartPage() != null
                    && start_page.compareToIgnoreCase(other.getStartPage()) <= 0
                    && folio.compareToIgnoreCase(other.getStartPage()) > 0) {
                return false;
            }
        }
        
        for (OtherPreface other : prefatory.comestorList()) {
            if (other.getStartPage() != null
                    && start_page.compareToIgnoreCase(other.getStartPage()) <= 0
                    && folio.compareToIgnoreCase(other.getStartPage()) > 0) {
                return false;
            }
        }
        
        String TOC_start = prefatory.getMasterTableOfContents().getStartPage();
        if (TOC_start != null 
                && start_page.compareToIgnoreCase(TOC_start) <= 0
                && folio.compareToIgnoreCase(TOC_start) > 0) {
            return false;
        }
        
        return true;
    }
    
    private List<Illustration> inThisItem(IllustrationList all_ills, 
            OtherPreface other) {
        
        List<Illustration> ills = new ArrayList<Illustration> ();
        
        for (Illustration ill : all_ills) {
            if (ill.getVolume() == volume 
                    && inOnlyThisItem(ill.getFolio(), other)) {
                ills.add(ill);
            }
        }
        
        return ills;
    }
    
    private void setIllustrationMaps(IllustrationList ills) {
        for (int i = 0; i < prefatory.otherPrefaces().size(); i++) {
            other_prefaces_ills.put(i, 
                    inThisItem(ills, prefatory.otherPrefaces().get(i)));
        }
        
        for (int i = 0; i < prefatory.guyartList().size(); i++) {
            guyart_ills.put(i, 
                    inThisItem(ills, prefatory.guyartList().get(i)));
        }
        
        for (int i = 0; i < prefatory.comestorLetters().size(); i++) {
            comestor_letter_ills.put(i, 
                    inThisItem(ills, prefatory.comestorLetters().get(i)));
        }
        
        for (int i = 0; i < prefatory.comestorList().size(); i++) {
            comestor_ills.put(i, 
                    inThisItem(ills, prefatory.comestorList().get(i)));
        }
    }
    
    private List<Illustration> setTOCIlls(IllustrationList ills) {
        OtherPreface other = new OtherPreface();
        other.setStartPage(prefatory.getMasterTableOfContents().getStartPage());
        
        return inThisItem(ills, other);
    }
    
}
