package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>Associated with the &ltsneddonClass&gt element of the bible schema.</p>
 */
public class Sneddon implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Category {
        BXIII("BXIII"), BH("BH"), BHC("BHC"), MIXED("mixed"),
        OTHER("other"), NOTPROVIDED("not provided");
        
        private String category;
        
        private Category(String category) {
            this.category = category;
        }
        
        public String category() {
            return category;
        }
        
        public static Category getCategory(String category) {
            for (Category c : Category.values()) {
                if (c.category.equals(category)) {
                    return c;
                }
            }
            return null;
        }
    }
    
    public enum SubcategoryOne {
        PB("PB"), BM("BM"), GB("GB"), PROL("Prol."),
        PROLTRACES("Prol. traces"), MIXED("mixed"), OTHER("other");
        
        private String category;
        
        private SubcategoryOne(String category) {
            this.category = category;
        }
        
        public String category() {
            return category;
        }
        
        public static SubcategoryOne getSubcategoryOne(String category) {
            for (SubcategoryOne s : SubcategoryOne.values()) {
                if (s.category.equals(category)) {
                    return s;
                }
            }
            return null;
        }
    }
    
    public enum SubcategoryTwo {
        A("a"), B("b"), MIXED("mixed"), OTHER("other");
        
        private String category;
        
        private SubcategoryTwo(String category) {
            this.category = category;
        }
        
        public String category() {
            return category;
        }
        
        public static SubcategoryTwo getSubcategoryTwo(String category) {
            for (SubcategoryTwo s : SubcategoryTwo.values()) {
                if (s.category.equals(category)) {
                    return s;
                }
            }
            return null;
        }
    }
    
    public enum SubcategoryThree {
        PREFACE("prefaces"), NOPREFACE("no prefaces"), OTHER("other");
        
        private String category;
        
        private SubcategoryThree(String category) {
            this.category = category;
        }
        
        public String category() {
            return category;
        }
        
        public static SubcategoryThree getSubcategoryThree(String category) {
            for (SubcategoryThree s : SubcategoryThree.values()) {
                if (s.category.equals(category)) {
                    return s;
                }
            }
            return null;
        }
    }
    
    private String siglum;
    private String entry;
    
    private Category category;
    private SubcategoryOne sub1;
    private SubcategoryTwo sub2;
    private SubcategoryThree sub3;
    
    public Sneddon() {
        
    }

    public String getSiglum() {
        return siglum;
    }

    public void setSiglum(String siglum) {
        this.siglum = siglum;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Category.getCategory(category);
    }

    public SubcategoryOne getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = SubcategoryOne.getSubcategoryOne(sub1);
    }

    public SubcategoryTwo getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = SubcategoryTwo.getSubcategoryTwo(sub2);
    }

    public SubcategoryThree getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = SubcategoryThree.getSubcategoryThree(sub3);
    }
    
}
