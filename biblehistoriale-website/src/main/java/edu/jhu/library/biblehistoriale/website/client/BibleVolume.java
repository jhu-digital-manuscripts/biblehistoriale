package edu.jhu.library.biblehistoriale.website.client;

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
import edu.jhu.library.biblehistoriale.model.profile.MiscContent;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Title;

/**
 * This class contains some information for a single Bible volume.
 * 
 * <p>Mapping of illustrations to various contents items.
 * (Map key -&gt object :: index of prefatory matter in an associated list
 *  -&gt list of illustrations for that prefatory matter)</p>
 * 
 * <p>Contents specific to a single volume. Prefatory matter, 
 * parascriptural item, bible books, misc contents, and associated
 * illustrations.</p>
 */
public class BibleVolume {
    // TODO does this actually work??
    private PrefatoryMatter prefatory;
    private ParascripturalItem pi;
    private BibleBooks books;
    
    private List<MiscContent> miscs;
    
    private final int volume;
    
    private final Map<Integer, List<Illustration>> other_prefaces_ills;
    private final Map<Integer, List<Illustration>> guyart_ills;
    private final Map<Integer, List<Illustration>> comestor_letter_ills;
    private final Map<Integer, List<Illustration>> comestor_ills;
    private final List<Illustration> catechisms_ills;
    private final List<Illustration> misc_ills;
    private final List<Illustration> MasterTOC_ills;
    
    private final Map<String, List<Illustration>> book_ills;
    
    public BibleVolume() {
        this.volume = 0;
        this.other_prefaces_ills = null;
        this.guyart_ills = null;
        this.comestor_letter_ills = null;
        this.comestor_ills = null;
        this.book_ills = null;
        this.catechisms_ills = null;
        this.misc_ills = null;
        this.MasterTOC_ills = null;
    }
    
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
        
        
        this.pi = bible.getTextualContent().parascripturalItem();
        if (pi.getVolume() != volume 
                || !pi.getLocVol().equals(String.valueOf(volume))) {
            this.pi = null;
        }
        
        this.miscs = new ArrayList<MiscContent> ();
        for (MiscContent misc : bible.getTextualContent().miscContents()) {
            if (misc.getVolume() == volume) {
                miscs.add(misc);
            }
        }
        
        IllustrationList ills = bible.getIllustrations();
        
        this.other_prefaces_ills = new HashMap<Integer, List<Illustration>> ();
        this.guyart_ills = new HashMap<Integer, List<Illustration>> ();
        this.comestor_letter_ills = new HashMap<Integer, List<Illustration>> ();
        this.comestor_ills = new HashMap<Integer, List<Illustration>> ();
        this.book_ills = new HashMap<String, List<Illustration>> ();
        this.catechisms_ills = new ArrayList<Illustration> ();
        this.misc_ills = new ArrayList<Illustration> ();
        this.MasterTOC_ills = setTOCIlls(ills);
        
        for (Illustration ill : ills) {
            if (ill.getVolume() != volume) {
                continue;
            }
            
            if (ill.getBook().equals("catechismsPrayersTreatise")) {
                catechisms_ills.add(ill);
            } else if (ill.getBook().equals("miscContents")) {
                misc_ills.add(ill);
            }
        }
        
        setIllustrationMaps(ills);
        setBookIlls(ills);
    }
    
    public int getVolume() {
        return volume;
    }
    
    public PrefatoryMatter getPrefatoryMatter() {
        return prefatory;
    }
    
    public BibleBooks getBibleBooks() {
        return books;
    }
    
    public ParascripturalItem getParascripturalItem() {
        return pi;
    }
    
    public List<MiscContent> miscContents() {
        return miscs;
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
    
    public List<Illustration> catechismsIlls() {
        return catechisms_ills;
    }
    
    public List<Illustration> miscContentIlls() {
        return misc_ills;
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
                        ill.getBook().trim())
                        && ill.getVolume() == volume)
                    ills.add(ill);
            }
            
            book_ills.put(title.getBookName(), ills);
        }
    }
    
    private boolean inOnlyThisItem(String folio, OtherPreface item) {
        
        folio = folio.toLowerCase();
        String start_page = item.getStartPage().toLowerCase();
        
        if (compareFolios(folio, start_page) < 0) {
            return false;
        }
        
        for (OtherPreface other : prefatory.otherPrefaces()) {
            // Ignore any items that occur before the item under test
            if (other.getStartPage() != null
                    && compareFolios(start_page, other.getStartPage()) <= 0
                    && compareFolios(folio, other.getStartPage()) > 0) {
                return false;
            }
        }
        
        for (OtherPreface other : prefatory.guyartList()) {
            if (other.getStartPage() != null
                    && compareFolios(start_page, other.getStartPage()) <= 0
                    && compareFolios(folio, other.getStartPage()) > 0) {
                return false;
            }
        }
        
        for (OtherPreface other : prefatory.comestorLetters()) {
            if (other.getStartPage() != null
                    && compareFolios(start_page, other.getStartPage()) <= 0
                    && compareFolios(folio, other.getStartPage()) > 0) {
                return false;
            }
        }
        
        for (OtherPreface other : prefatory.comestorList()) {
            if (other.getStartPage() != null
                    && compareFolios(start_page, other.getStartPage()) <= 0
                    && compareFolios(folio, other.getStartPage()) > 0) {
                return false;
            }
        }
        
        String TOC_start = prefatory.getMasterTableOfContents().getStartPage();
        if (TOC_start != null 
                && compareFolios(start_page, TOC_start) <= 0
                && compareFolios(folio, TOC_start) > 0) {
            return false;
        }
        
        return true;
    }
    
    private List<Illustration> inThisItem(IllustrationList all_ills, 
            OtherPreface other, String item) {
        
        List<Illustration> ills = new ArrayList<Illustration> ();
        
        for (Illustration ill : all_ills) {
            boolean in_this_preface = ill.getVolume() == volume
                    && (ill.getBook().toLowerCase().contains(item)
                    || (ill.getBook().toLowerCase().contains("preface")
                            && inOnlyThisItem(ill.getFolio(), other)));
            
            if (in_this_preface) {
                ills.add(ill);
            }
        }
        
        return ills;
    }
    
    /**
     * <p>Compare the position of two folios in a manuscript.</p>
     * 
     * @param f1
     * @param f2
     * @return
     *      An integer that is: <br>
     *      positive if <code>f1 &gt f2</code><br> 
     *      negative if <code>f1 &lt f2</code><br>
     *      0 if they are equal
     */
    public int compareFolios(String f1, String f2) {
        int page1 = 0;
        int page2 = 0;
        
        try {
            page1 = Integer.parseInt(f1);
            page2 = Integer.parseInt(f2);
            
            return page1 - page2;
        } catch (NumberFormatException e) {
            // At least one of the folios ends with a letter
            String end1 = f1.substring(f1.length() - 1);
            String end2 = f2.substring(f2.length() - 1);
            
            try {
                page1 = Integer.parseInt(f1.substring(0, f1.length() - 1));
                page2 = Integer.parseInt(f2.substring(0, f2.length() - 1));
            } catch (NumberFormatException ex) {
                return end1.compareToIgnoreCase(end2);
                //throw new RuntimeException("Invalid folio format: "
                //        + f1 + "/" + f2);
            }
            
            return (page1 - page2 == 0 
                    ? end1.compareToIgnoreCase(end2) : page1 - page2);
        }
    }
    
    private void setIllustrationMaps(IllustrationList ills) {
        for (int i = 0; i < prefatory.otherPrefaces().size(); i++) {
            other_prefaces_ills.put(i, 
                    inThisItem(ills, prefatory.otherPrefaces().get(i),
                            "other"));
        }
        
        for (int i = 0; i < prefatory.guyartList().size(); i++) {
            guyart_ills.put(i, 
                    inThisItem(ills, prefatory.guyartList().get(i),
                            "guyart"));
        }
        
        for (int i = 0; i < prefatory.comestorLetters().size(); i++) {
            comestor_letter_ills.put(i, 
                    inThisItem(ills, prefatory.comestorLetters().get(i),
                            "comestor's letter"));
        }
        
        for (int i = 0; i < prefatory.comestorList().size(); i++) {
            comestor_ills.put(i, 
                    inThisItem(ills, prefatory.comestorList().get(i),
                            "comestor"));
        }
    }
    
    private List<Illustration> setTOCIlls(IllustrationList ills) {
        OtherPreface other = new OtherPreface();
        other.setStartPage(prefatory.getMasterTableOfContents().getStartPage());
        
        return inThisItem(ills, other, "table of contents");
    }
    
}
