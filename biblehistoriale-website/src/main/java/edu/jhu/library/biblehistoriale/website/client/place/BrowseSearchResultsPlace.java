package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;

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
    
    public static class Tokenizer 
            implements PlaceTokenizer<BrowseSearchResultsPlace> {

        @Override
        public BrowseSearchResultsPlace getPlace(String token) {
            // TODO transform a token into a Query in the case of bookmark
            Query query = null;
            QueryOptions opts = null;
            
            return new BrowseSearchResultsPlace(query, opts);
        }

        @Override
        public String getToken(BrowseSearchResultsPlace place) {
            // TODO: tokenize a query
            return "";
        }
        
    }
    
}
