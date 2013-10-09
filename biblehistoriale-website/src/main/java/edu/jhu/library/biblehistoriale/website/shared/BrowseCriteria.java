package edu.jhu.library.biblehistoriale.website.shared;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.Contributor;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;

public enum BrowseCriteria {
    
    REPOSITORY("Repository") {
        
        @Override
         public String[] getPropertyFromBible(Bible bible) {
             String repo = bible.getClassification().getCurrentRepository();
             
             if (repo == null || repo.equals("")) {
                 return new String[0];
             }
             
             return new String[] { repo };
         }
    }, 
    PRODDATE("Production Date") {
        
        @Override
        public String[] getPropertyFromBible(Bible bible) {
            String prod = bible.getProvenPatronHist().getProduction().getProdDate();
            
            if (prod == null || prod.equals("")) {
                return new String[0];
            }
            
            return new String[] { prod };
        }
    }, 
    PRODLOC("Production Location") {
        
        @Override
        public String[] getPropertyFromBible(Bible bible) {
            String prod = bible.getProvenPatronHist().getProduction().getProdLoc();
            
            if (prod == null || prod.equals("")) {
                return new String[0];
            }
            
            return new String[] { prod };
        }
    }, 
    OWNERNAME("Owner Name") {
        
        @Override
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
    CONTRIBUTORS("Contributors") {

        @Override
        public String[] getPropertyFromBible(Bible bible) {
            
            ArrayList<Contributor> contributors = 
                    bible.getProvenPatronHist().getProduction().contributors();
            
            if (contributors == null) {
                return new String[0];
            }
            
            ArrayList<String> cont = new ArrayList<String> ();
            
            for (Contributor c : contributors) {
                
                if (c.getType() != null && c.getValue() != null) {
                    cont.add(c.getValue() + " (" + c.getType().value() + ")");
                }
                
            }
            
            return cont.toArray(new String[0]);
        }
    },
    CLASSIFICATION("Classification") {
        
        @Override
        public String[] getPropertyFromBible(Bible bible) {
            CatalogerClassification cl = bible.getClassification().getClassification();
            
            if (cl == null) {
                return new String[0];
            }
            
            ArrayList<String> classif = new ArrayList<String> ();
            
            Berger berg = cl.getBergerClass();
            Sneddon sned = cl.getSneddonClass();
            
            if (berg != null) {
                StringBuilder berg_str = new StringBuilder();
                
                berg_str.append("Berger class: ");
                if (berg.getCategory() != null)
                    berg_str.append(berg.getCategory().category());
                if (berg.getBhcSubtype() != null) 
                    berg_str.append(berg.getBhcSubtype().subtype());
                
                classif.add(berg_str.toString());
            }
            
            if (sned != null) {
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
                
                classif.add(sned_str.toString());
            }
            
            return classif.toArray(new String[0]);
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
