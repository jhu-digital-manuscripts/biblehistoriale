package edu.jhu.library.biblehistoriale.model.query;

public enum TermField {
    TITLE("title"),
    PEOPLE("people"),
    TEXT("text"),
    NOTES("notes"),
    ILLUSTRATIONS("illustrations"),
    PHYS_CHAR("physical characteristics"),
    PATRON_HIST("provenance/patron history"),
    CLASSIFICATION("classification"),
    TEXTUAL_CONTENT("textual content"),
    BIBLIOGRAPHY("bibliography");
    
    private String term;
    
    private TermField(String term) {
        this.term = term;
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
