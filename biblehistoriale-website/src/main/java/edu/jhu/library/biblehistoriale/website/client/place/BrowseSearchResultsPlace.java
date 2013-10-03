package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOperation;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.TermField;

/**
 * <p>Contains state information for the browse search results view.
 * Associated with BrowseSearchResultsActivity.</p>
 * 
 * <p>A Query and QueryOptions are saved.</p>
 * 
 * @see Query
 * @see QueryOptions
 */
public class BrowseSearchResultsPlace extends Place {
    
    private Query query;
    private QueryOptions opts;
    
    public BrowseSearchResultsPlace(Query query, QueryOptions opts) {
        this.query = query;
        this.opts = opts;
    }
    
    public Query getQuery() {
        return query;
    }
    
    public QueryOptions getQueryOptions() {
        return opts;
    }
    
    /**
     * <p>Tokenizer for BrowseSearchResultsPlace. Contains methods
     * for turning search results state into a string, and turning a 
     * string token into a BrowseSearchResultsPlace.</p>
     */
    public static class Tokenizer 
            implements PlaceTokenizer<BrowseSearchResultsPlace> {

        private static final String SEARTH_DELIMITER_PATTERN = "\\|";
        private static final String VALUE_DELIMITER_PATTERN = ";";
        private static final String SEARCH_DELIMITER = "|";
        private static final String VALUE_DELIMITER = ";";
        
        @Override
        public BrowseSearchResultsPlace getPlace(String token) {
            // OPERATION;CATEGORY;search phrase;TYPE
            QueryOptions opts = new QueryOptions();
            
            Query query = buildQuery(token);
            
            return new BrowseSearchResultsPlace(query, opts);
        }
        
        public Query buildQuery(String q_str) {
            Query query = null;
            
            String[] queries = q_str.split(SEARTH_DELIMITER_PATTERN);
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

        @Override
        public String getToken(BrowseSearchResultsPlace place) {
            StringBuilder sb = new StringBuilder();
            
            Query query = place.getQuery();
            // TODO QueryOptions not really used yet...
            //QueryOptions opts = place.getQueryOptions();
            
            return query_string(query, QueryOperation.AND, sb);
        }

        private String query_string(Query query, QueryOperation op, StringBuilder sb) {
            
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
                    query_string(child, query.operation(), sb);
                }
            }
            
            return sb.toString();
        }
        
    }
    
}
