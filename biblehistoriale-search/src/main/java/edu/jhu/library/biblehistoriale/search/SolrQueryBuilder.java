package edu.jhu.library.biblehistoriale.search;

import java.util.Map;

import edu.jhu.library.biblehistoriale.model.Query;
import edu.jhu.library.biblehistoriale.model.QueryOperation;
import edu.jhu.library.biblehistoriale.model.Term;
import edu.jhu.library.biblehistoriale.model.TermField;
import edu.jhu.library.biblehistoriale.model.TermType;

public class SolrQueryBuilder {
    // Term field -> list of solr fields
    private final Map<TermField, String[]> field_map;

    public SolrQueryBuilder(Map<TermField, String[]> field_map) {
        this.field_map = field_map;
    }

    public String buildQuery(Query query) {
        StringBuilder result = new StringBuilder();
        build(query, result);
        return result.toString();
    }

    private void build(Query query, StringBuilder sb) {
        sb.append("(");

        if (query.isOperation()) {

            boolean first = true;

            for (Query kid : query.children()) {
                if (first) {
                    first = false;
                } else {
                    if (query.operation() == QueryOperation.AND) {
                        sb.append(" AND ");
                    }
                }

                build(kid, sb);
            }
        } else if (query.isTerm()) {
            build(query.Term(), sb);
        }

        sb.append(")");
    }

    private void build(Term term, StringBuilder sb) {
        String[] solr_fields = field_map.get(term.getField());

        if (solr_fields == null) {
            throw new RuntimeException("Unhandled field " + term.getField());
        }

        CharSequence solr_term = to_solr_query(term);

        boolean first = true;

        for (String solr_field : solr_fields) {
            if (first) {
                first = false;
            } else {
                sb.append(" OR ");
            }

            sb.append(solr_field + ":");
            sb.append(solr_term);
        }
    }

    private CharSequence to_solr_query(Term term) {
        if (term.getType() == TermType.PHRASE) {
            return create_literal_query(term.getValue());
        } else {
            throw new RuntimeException("Unhandled term type");
        }
    }

    /**
     * Return lucene query which exactly matches a string.
     */
    private static CharSequence create_literal_query(String s) {
        StringBuilder sb = new StringBuilder(s.length());

        sb.append('\"');

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '&' || c == '|' || c == '(' || c == ')' || c == '}'
                    || c == '{' || c == '[' || c == ']' || c == ':' || c == '^'
                    || c == '!' || c == '\"' || c == '+' || c == '-'
                    || c == '~' || c == '*' || c == '?' || c == '\\') {
                sb.append('\\');
            }

            sb.append(c);
        }

        sb.append('\"');

        return sb;
    }

}
