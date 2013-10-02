package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Saves any state information for the construct advanced
 * query view. Associated with ConstructAdvancedQueryActivity.
 */
public class ConstructAdvancedQueryPlace extends Place {
    
    public static class Tokenizer 
            implements PlaceTokenizer<ConstructAdvancedQueryPlace> {

        @Override
        public ConstructAdvancedQueryPlace getPlace(String token) {
            return new ConstructAdvancedQueryPlace();
        }

        @Override
        public String getToken(ConstructAdvancedQueryPlace place) {
            return "";
        }
        
    }
    
}
