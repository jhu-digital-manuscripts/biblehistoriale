package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Title implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public class TitleIncipit extends Incipit implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String textType;

        public String getTextType() {
            return textType;
        }

        public void setTextType(String textType) {
            this.textType = textType;
        }
        
    }
    
    private TitleIncipit incipit;
    
    private String editions;
    private String textVersion;
    private String glossType;
    private String glossType2;
    private String startPage;
    private String bookName;
    private String bookNote;
    
    private boolean hasChapterNames;
    private boolean hasTableOfContents;
    
    public Title() {
        this.hasChapterNames = false;
        this.hasTableOfContents = false;
    }
    
    public TitleIncipit getIncipit() {
        return incipit;
    }
    
    public void setIncipit(TitleIncipit incipit) {
        this.incipit = incipit;
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
    
    public boolean hasChapterNames() {
        return hasChapterNames;
    }
    
    public void setHasChapterNames(boolean hasChapterNames) {
        this.hasChapterNames = hasChapterNames;
    }
    
    public boolean hasTableOfContents() {
        return hasTableOfContents;
    }
    
    public void setHasTableOfContents(boolean hasTableOfContents) {
        this.hasTableOfContents = hasTableOfContents;
    }
    
}
