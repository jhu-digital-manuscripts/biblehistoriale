package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Saves state information for the profile detail view. A bible id 
 * is saved. Associated with ProfileDetailActivity.
 */
public class ProfileDetailPlace extends Place {

    private String bible_id;
    
    public ProfileDetailPlace(String profile_id) {
        this.bible_id = profile_id;
    }
    
    public String id() {
        return bible_id;
    }
    
    public static class Tokenizer 
            implements PlaceTokenizer<ProfileDetailPlace> {

        @Override
        public ProfileDetailPlace getPlace(String token) {
            return new ProfileDetailPlace(token);
        }

        @Override
        public String getToken(ProfileDetailPlace place) {
            return place.id();
        }
        
    }
    
}
