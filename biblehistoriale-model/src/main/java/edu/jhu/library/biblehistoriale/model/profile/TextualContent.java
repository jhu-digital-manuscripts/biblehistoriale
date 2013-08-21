package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.List;

public class TextualContent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<PrefatoryMatter> prefatoryMatters;
    private List<BibleBooks> bibleBooks;
    private List<MiscContent> miscContents;
    private List<String> notes;
    
    private ParascripturalItem parascripturalItem;
    
    private int volume;

    public List<PrefatoryMatter> prefatoryMatters() {
        return prefatoryMatters;
    }

    public void setPrefactoryMatters(List<PrefatoryMatter> prefatoryMatters) {
        this.prefatoryMatters = prefatoryMatters;
    }

    public List<BibleBooks> bibleBooks() {
        return bibleBooks;
    }

    public void setBibleBooks(List<BibleBooks> bibleBooks) {
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
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
}
