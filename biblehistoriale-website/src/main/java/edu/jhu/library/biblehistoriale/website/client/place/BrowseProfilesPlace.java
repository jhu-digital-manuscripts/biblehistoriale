package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BrowseProfilesPlace extends Place {
    
    public static class Tokenizer 
            implements PlaceTokenizer<BrowseProfilesPlace> {

        @Override
        public BrowseProfilesPlace getPlace(String token) {
            return new BrowseProfilesPlace();
        }

        @Override
        public String getToken(BrowseProfilesPlace place) {
            return "";
        }
        
    }
    
}
