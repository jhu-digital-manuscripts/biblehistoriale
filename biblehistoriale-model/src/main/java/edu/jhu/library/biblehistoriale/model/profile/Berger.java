package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Berger implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Category {
        BXIII("BXIII"), BH("BH"), BHC("BHC");
        
        private String cat;
        
        private Category(String cat) {
            this.cat = cat;
        }
        
        public String category() {
            return cat;
        }
        
        public static Category getCategory(String cat) {
            for (Category c : Category.values()) {
                if (c.cat.equals(cat)) {
                    return c;
                }
            }
            return null;
        }
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
        
        public static BhcSubtype getSubtype(String subtype) {
            for (BhcSubtype s : BhcSubtype.values()) {
                if (s.subtype.equals(subtype)) {
                    return s;
                }
            }
            return null;
        }
    }
    
    private Category category;
    private BhcSubtype bhcSubtype;
    
    public Berger() {
        
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Category.getCategory(category);
    }

    public BhcSubtype getBhcSubtype() {
        return bhcSubtype;
    }

    public void setBhcSubtype(String bhcSubtype) {
        this.bhcSubtype = BhcSubtype.getSubtype(bhcSubtype);
    }
    
}
