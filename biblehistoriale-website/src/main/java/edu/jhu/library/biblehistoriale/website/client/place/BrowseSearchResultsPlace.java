package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.TermField;

public class BrowseSearchResultsPlace extends Place {
    
    private Query query;
    private QueryOptions opts;
    
    public BrowseSearchResultsPlace(Query query, QueryOptions opts) {
        this.query = query;
        this.opts = opts;
    }
    
    public BrowseSearchResultsPlace() {
        this(new Query(TermField.ALL, "Brussels"), new QueryOptions());
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
            return new BrowseSearchResultsPlace();
        }

        @Override
        public String getToken(BrowseSearchResultsPlace place) {
            return "";
        }
        
    }
    
}
