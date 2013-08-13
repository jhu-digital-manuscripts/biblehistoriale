package edu.jhu.library.biblehistoriale.model;

/**
 * A term matches a value against a field. The interpretation of the value
 * depends on the type of the term.
 */
public class Term {
    private final String field;
    private final String value;
    private final TermType type;

    public Term(String field, String value, TermType type) {
        this.field = field;
        this.value = value;
        this.type = type;
    }

    public Term(String field, String value) {
        this(field, value, TermType.PHRASE);
    }

    public String getField() {
        return field;
    }

    public String getLiteral() {
        return value;
    }

    public TermType getType() {
        return type;
    }
}
