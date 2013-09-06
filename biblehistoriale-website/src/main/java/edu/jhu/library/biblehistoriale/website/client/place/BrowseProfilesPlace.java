package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import edu.jhu.library.biblehistoriale.website.client.BrowseCriteria;

public class BrowseProfilesPlace extends Place {
    
    // TODO: determine state information and tokenization
    private final BrowseCriteria criteria;
    
    public BrowseProfilesPlace(BrowseCriteria criteria) {
        this.criteria = criteria;
    }
    
    public BrowseCriteria criteria() {
        return criteria;
    }
    
    public static class Tokenizer 
            implements PlaceTokenizer<BrowseProfilesPlace> {

        @Override
        public BrowseProfilesPlace getPlace(String token) {
            return new BrowseProfilesPlace(
                    BrowseCriteria.valueOf(token));
        }

        @Override
        public String getToken(BrowseProfilesPlace place) {
            return place.criteria().toString();
        }
        
    }
    
}
