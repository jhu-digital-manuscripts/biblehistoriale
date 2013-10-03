package edu.jhu.library.biblehistoriale.website.client;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOperation;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.TermField;

public class QueryUtils {
    
    private static final QueryUtils INSTANCE = 
            new QueryUtils();
    
    private static final String SEARCH_DELIMITER_PATTERN = "\\|";
    private static final String VALUE_DELIMITER_PATTERN = ";";
    private static final String SEARCH_DELIMITER = "|";
    private static final String VALUE_DELIMITER = ";";
    
    public static QueryUtils getInstance() {
        return INSTANCE;
    }
    
    public String searchDelimiterPattern() {
        return SEARCH_DELIMITER_PATTERN;
    }
    
    public String valueDelimiterPattern() {
        return VALUE_DELIMITER_PATTERN;
    }
    
    public String searchDelimiter() {
        return SEARCH_DELIMITER;
    }
    
    public String valueDelimiter() {
        return VALUE_DELIMITER;
    }
    
    /**
     * Build a QueryOptions object form a query token.
     * 
     * @param token
     * @return
     */
    public QueryOptions buildQueryOptions(String token) {
        return new QueryOptions();
    }
    
    /**
     * Build a Query object from a query token.
     * 
     * @param token
     * @return
     */
    public Query buildQuery(String token) {
        Query query = null;
        
        String[] queries = token.split(SEARCH_DELIMITER_PATTERN);
        for (int i = queries.length - 1; i >= 0; i--) {
            String[] vals = queries[i].split(VALUE_DELIMITER_PATTERN);
            
            if (vals.length != 4) {
                continue;
            }
            
            QueryOperation op = QueryOperation.valueOf(vals[0]);
            TermField field = TermField.valueOf(vals[1]);
            String term = vals[2];
            
            if (query == null) {
                query = new Query(field, term);
                continue;
            }
            
            Query sub_q = new Query(field, term);
            
            query = new Query(op, sub_q, query);
        }
        
        return query;
    }
    
    /**
     * Get the query token from a Query object.
     * 
     * @param query
     * @param op
     *          The top level query operation.
     * @param sb
     * @return
     */
    public String getQueryToken(Query query, QueryOperation op, StringBuilder sb) {
        
        if (query.isTerm()) {
            
            sb.append(op.toString()
                    + VALUE_DELIMITER
                    + query.Term().getField() 
                    + VALUE_DELIMITER
                    + query.Term().getValue()
                    + VALUE_DELIMITER
                    + query.Term().getType().toString()
                    + SEARCH_DELIMITER);
            
        } else if (query.isOperation()) {
            for (Query child : query.children()) {
                getQueryToken(child, query.operation(), sb);
            }
        }
        
        return sb.toString();
    }
    
}
