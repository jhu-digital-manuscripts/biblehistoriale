package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileDetailPlace extends Place {
    
    public static class Tokenizer 
            implements PlaceTokenizer<ProfileDetailPlace> {

        @Override
        public ProfileDetailPlace getPlace(String token) {
            // TODO
            return new ProfileDetailPlace();
        }

        @Override
        public String getToken(ProfileDetailPlace place) {
            // TODO
            return "";
        }
        
    }
    
}
