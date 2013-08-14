package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class MasterTableOfContents implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Detail {
        BOOK, CHAPTER, MIXED, OTHER
    }
    
    private String startPage;
    private String text;
    
    private boolean matchesContents;

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean matchesContents() {
        return matchesContents;
    }

    public void setMatchesContents(boolean matchesContents) {
        this.matchesContents = matchesContents;
    }
    
}
