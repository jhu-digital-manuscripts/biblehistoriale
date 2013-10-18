package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOperation;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.website.client.QueryUtils;

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
        private static final QueryUtils utils =
                QueryUtils.getInstance();
        
        @Override
        public BrowseSearchResultsPlace getPlace(String token) {
            // OPERATION;CATEGORY;search phrase;TYPE
            return new BrowseSearchResultsPlace(utils.buildQuery(token), 
                    utils.buildQueryOptions(token));
        }

        @Override
        public String getToken(BrowseSearchResultsPlace place) {
            // TODO QueryOptions not used...
            //QueryOptions opts = place.getQueryOptions();
            
            return utils.getQueryToken(place.getQuery(), 
                    QueryOperation.AND, new StringBuilder());
        }
        
    }
    
}
