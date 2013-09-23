package edu.jhu.library.biblehistoriale.website.shared;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;

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
            CatalogerClassification cl = bible.getClassification().getClassification();
            
            Berger berg = cl.getBergerClass();
            Sneddon sned = cl.getSneddonClass();
            
            StringBuilder berg_str = new StringBuilder();
            berg_str.append("Berger class: ");
            if (berg.getCategory() != null)
                berg_str.append(berg.getCategory().category());
            if (berg.getBhcSubtype() != null) 
                berg_str.append(berg.getBhcSubtype().subtype());
            
            StringBuilder sned_str = new StringBuilder();
            sned_str.append("Sneddon class: ");
            if (sned.getCategory() != null)
                sned_str.append(sned.getCategory());
            if (sned.getSub1() != null) 
                sned_str.append(sned.getSub1());
            if (sned.getSub2() != null)
                sned_str.append(sned.getSub2());
            if (sned.getSub3() != null)
                sned_str.append(sned.getSub3());
            
            return new String[] { 
                    berg_str.toString(), sned_str.toString()
            };
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
