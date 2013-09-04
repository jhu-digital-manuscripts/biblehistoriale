package edu.jhu.library.biblehistoriale.model.query;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A query is a tree whose leaves are terms and inner nodes are logical
 * operations.
 */
public class Query implements Serializable {
    private static final long serialVersionUID = 1L;

    private Query[] children;
    private QueryOperation op;
    private Term term;

    public Query() {
        this(null);
    }

    public Query(Term term) {
        this.children = null;
        this.term = term;
        this.op = null;
    }

    public Query(TermField term_field, String term_value) {
        this(new Term(term_field, term_value));
    }

    public Query(QueryOperation op, Query... children) {
        this.op = op;
        this.children = children;
        this.term = null;
    }

    public Query[] children() {
        return children;
    }

    public boolean isTerm() {
        return term != null;
    }

    public boolean isOperation() {
        return op != null && children != null;
    }

    public QueryOperation operation() {
        return op;
    }

    public Term Term() {
        return term;
    }

    public String toString() {
        return term != null ? term.toString() : "{" + op.name()
                + Arrays.toString(children) + "}";
    }
}
