package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ConstructAdvancedQueryPlace extends Place {
    
    // TODO: determine tokenization of advanced query!
    
    public static class Tokenizer 
            implements PlaceTokenizer<ConstructAdvancedQueryPlace> {

        @Override
        public ConstructAdvancedQueryPlace getPlace(String token) {
            // TODO Auto-generated method stub
            return new ConstructAdvancedQueryPlace();
        }

        @Override
        public String getToken(ConstructAdvancedQueryPlace place) {
            // TODO Auto-generated method stub
            return "";
        }
        
    }
    
}
