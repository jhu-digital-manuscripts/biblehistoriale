package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ContactUsPlace extends Place {
    
    public static class Tokenizer 
            implements PlaceTokenizer<ContactUsPlace> {

        @Override
        public ContactUsPlace getPlace(String token) {
            return new ContactUsPlace();
        }

        @Override
        public String getToken(ContactUsPlace place) {
            return "";
        }
        
    }
    
}
