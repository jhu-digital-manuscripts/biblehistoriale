package edu.jhu.library.biblehistoriale.website.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProjectInfoPlace extends Place {
    
    public static class Tokenizer implements PlaceTokenizer<ProjectInfoPlace> {

        @Override
        public ProjectInfoPlace getPlace(String token) {
            return new ProjectInfoPlace();
        }

        @Override
        public String getToken(ProjectInfoPlace place) {
            return "";
        }
        
    }
    
}
