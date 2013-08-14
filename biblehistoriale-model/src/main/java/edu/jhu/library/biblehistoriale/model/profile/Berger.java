package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Berger implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Category {
        BXIII, BH, BHC
    }
    
    public enum BhcSubtype {
        PB("PB"), BM("BM"), GB("GB"), GBDB("GB-DB"),
        PROL("Prol."), PROLFULL("Prol. full");
        
        private String subtype;
        
        private BhcSubtype(String subtype) {
            this.subtype = subtype;
        }
        
        public String subtype() {
            return subtype;
        }
    }
    
    private Category category;
    private BhcSubtype bhcSubtype;
    
    public Berger() {
        
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BhcSubtype getBhcSubtype() {
        return bhcSubtype;
    }

    public void setBhcSubtype(BhcSubtype bhcSubtype) {
        this.bhcSubtype = bhcSubtype;
    }
    
}
