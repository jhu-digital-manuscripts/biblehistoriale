package edu.jhu.library.biblehistoriale.model.query;

import java.io.Serializable;

/**
 * A term matches a value against a field. The interpretation of the value
 * depends on the type of the term.
 */
public class Term implements Serializable {
    private static final long serialVersionUID = 1L;

    private TermField field;
    private String value;
    private TermType type;

    public Term() {
        this(null, null, null);
    }
    
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
