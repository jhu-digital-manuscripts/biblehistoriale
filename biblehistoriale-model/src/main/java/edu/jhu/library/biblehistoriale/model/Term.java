package edu.jhu.library.biblehistoriale.model;

/**
 * A term matches a value against a field. The interpretation of the value
 * depends on the type of the term.
 */
public class Term {
    private final TermField field;
    private final String value;
    private final TermType type;

    public Term(TermField field, String value, TermType type) {
        this.field = field;
        this.value = value;
        this.type = type;
    }

    public Term(TermField field, String value) {
        this(field, value, TermType.PHRASE);
    }

    public TermField getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public TermType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Term [field=" + field + ", value=" + value + ", type=" + type
                + "]";
    }
    
    
}
