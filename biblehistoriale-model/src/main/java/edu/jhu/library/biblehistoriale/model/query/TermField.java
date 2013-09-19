package edu.jhu.library.biblehistoriale.model.query;

public enum TermField {
    ALL("all"),
    TITLE("title/shelfmark"),
    PEOPLE("people"),
    PLACES("places"),
    TEXT("text"),
    ILLUSTRATIONS("illustrations"),
    PHYS_CHAR("physical characteristics"),
    CLASSIFICATION("classification"),
    BIBLIOGRAPHY("bibliography");
    
    private String term;
    
    private TermField(String term) {
        this.term = term;
    }
    
    public String term() {
        return term;
    }
    
    public static TermField getTermField(String term) {
        for (TermField f : TermField.values()) {
            if (f.term.equals(term)) {
                return f;
            }
        }
        return null;
    }
}
