package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextualContent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<PrefatoryMatter> prefatoryMatters;
    private ArrayList<BibleBooks> bibleBooks;
    private ArrayList<MiscContent> miscContents;
    private ArrayList<String> notes;
    
    private ParascripturalItem parascripturalItem;
    
    private int volume;
    
    public TextualContent() {
        this.parascripturalItem = new ParascripturalItem();
        
        this.prefatoryMatters = new ArrayList<PrefatoryMatter> ();
        this.bibleBooks = new ArrayList<BibleBooks> ();
        this.miscContents = new ArrayList<MiscContent> ();
        this.notes = new ArrayList<String> ();
    }

    public ArrayList<PrefatoryMatter> prefatoryMatters() {
        return prefatoryMatters;
    }

    public void setPrefactoryMatters(ArrayList<PrefatoryMatter> prefatoryMatters) {
        this.prefatoryMatters = prefatoryMatters;
    }

    public ArrayList<BibleBooks> bibleBooks() {
        return bibleBooks;
    }

    public void setBibleBooks(ArrayList<BibleBooks> bibleBooks) {
        this.bibleBooks = bibleBooks;
    }

    public ArrayList<MiscContent> miscContents() {
        return miscContents;
    }

    public void setMiscContents(ArrayList<MiscContent> miscContents) {
        this.miscContents = miscContents;
    }

    public ArrayList<String> notes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
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
