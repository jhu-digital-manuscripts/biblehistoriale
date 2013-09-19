package edu.jhu.library.biblehistoriale.website.shared;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;

public enum BrowseCriteria {
    
    REPOSITORY("Repository") {
         public String[] getPropertyFromBible(Bible bible) {
             return new String[] { 
                     bible.getClassification().getCurrentRepository() 
                     };
         }
    }, 
    PRODDATE("Production Date") {
        public String[] getPropertyFromBible(Bible bible) {
            return new String[] {
                    bible.getProvenPatronHist().getProduction().getProdDate()
                    };
        }
    }, 
    PRODLOC("Production Location") {
        public String[] getPropertyFromBible(Bible bible) {
            return new String[] {
                    bible.getProvenPatronHist().getProduction().getProdLoc()
                    };
        }
    }, 
    OWNERNAME("Owner Name") {
        public String[] getPropertyFromBible(Bible bible) {
            
            List<String> names = new ArrayList<String> ();
            
            for (Ownership ownership : bible.getProvenPatronHist().ownerships()) {
                for (Owner owner : ownership) {
                    names.add(owner.getOwnerName());
                }
            }
            
            return names.toArray(new String[0]);
        }
    }, 
    CLASSIFICATION("Classification") {
        public String[] getPropertyFromBible(Bible bible) {
            // TODO
            return new String[0];
        }
    };
    
    /**
     * 
     */
    private String criteria;
    
    private BrowseCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    public String message() {
        return criteria;
    }
    
    public abstract String[] getPropertyFromBible(Bible bible);
    
}
