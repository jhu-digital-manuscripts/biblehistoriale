package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Title implements Serializable, Iterable<TitleIncipit> {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<TitleIncipit> incipits;
    
    private String editions;
    private String textVersion;
    private String glossType;
    private String glossType2;
    private String startPage;
    private String endPage;
    private String bookName;
    private String bookNote;
    
    private Choice hasChapterNames;
    private boolean hasTableOfContents;
    
    public Title() {
        this.hasTableOfContents = false;
        
        this.incipits = new ArrayList<TitleIncipit> ();
    }
    
    public int size() {
        return incipits.size();
    }
    
    public TitleIncipit incipit(int index) {
        return incipits.get(index);
    }
    
    public void setIncipit(ArrayList<TitleIncipit> incipits) {
        this.incipits = incipits;
    }
    
    public String getEditions() {
        return editions;
    }
    
    public void setEditions(String editions) {
        this.editions = editions;
    }
    
    public String getTextVersion() {
        return textVersion;
    }
    
    public void setTextVersion(String textVersion) {
        this.textVersion = textVersion;
    }
    
    public String getGlossType() {
        return glossType;
    }
    
    public void setGlossType(String glossType) {
        this.glossType = glossType;
    }
    
    public String getGlossType2() {
        return glossType2;
    }
    
    public void setGlossType2(String glossType2) {
        this.glossType2 = glossType2;
    }
    
    public String getStartPage() {
        return startPage;
    }
    
    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }
    
    public String getEndPage() {
        return endPage;
    }
    
    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getBookNote() {
        return bookNote;
    }
    
    public void setBookNote(String bookNote) {
        this.bookNote = bookNote;
    }
    
    public Choice hasChapterNames() {
        return hasChapterNames;
    }
    
    public void setHasChapterNames(String hasChapterNames) {
        this.hasChapterNames = Choice.getChoice(hasChapterNames);
    }
    
    public boolean hasTableOfContents() {
        return hasTableOfContents;
    }
    
    public void setHasTableOfContents(boolean hasTableOfContents) {
        this.hasTableOfContents = hasTableOfContents;
    }

    @Override
    public Iterator<TitleIncipit> iterator() {
        return new Iterator<TitleIncipit> () {
            int next = 0;
            
            @Override
            public boolean hasNext() {
                return next < size();
            }

            @Override
            public TitleIncipit next() {
                return incipit(next++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
    }
    
}
