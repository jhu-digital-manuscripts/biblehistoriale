package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.List;

public class TextualContent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<PrefactoryMatter> prefactoryMatters;
    private List<BibleBook> bibleBooks;
    private List<MiscContent> miscContents;
    private List<String> notes;
    
    private ParascripturalItem parascripturalItem;

    public List<PrefactoryMatter> prefactoryMatters() {
        return prefactoryMatters;
    }

    public void setPrefactoryMatters(List<PrefactoryMatter> prefactoryMatters) {
        this.prefactoryMatters = prefactoryMatters;
    }

    public List<BibleBook> bibleBooks() {
        return bibleBooks;
    }

    public void setBibleBooks(List<BibleBook> bibleBooks) {
        this.bibleBooks = bibleBooks;
    }

    public List<MiscContent> miscContents() {
        return miscContents;
    }

    public void setMiscContents(List<MiscContent> miscContents) {
        this.miscContents = miscContents;
    }

    public List<String> notes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public ParascripturalItem parascripturalItem() {
        return parascripturalItem;
    }

    public void setParascripturalItem(ParascripturalItem parascripturalItem) {
        this.parascripturalItem = parascripturalItem;
    }
    
}
