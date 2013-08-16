package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Sneddon implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Category {
        BXIII, BH, BHC, MIXED, OTHER, NOTPROVIDED
    }
    
    public enum SubcategoryOne {
        PB, BM, GB, PROL, PROLTRACES, MIXED, OTHER
    }
    
    public enum SubcategoryTwo {
        A, B, MIXED, OTHER
    }
    
    public enum SubcategoryThree {
        PREFACE, NOPREFACE, OTHER
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

        if (category.equals("BXIII")) {
            this.category = Category.BXIII;
        } else if (category.equals("BH")) {
            this.category = Category.BH;
        } else if (category.equals("BHC")) {
            this.category = Category.BHC;
        } else if (category.equals("mixed")) {
            this.category = Category.MIXED;
        } else if (category.equals("other")) {
            this.category = Category.OTHER;
        } else if (category.equals("not provided")) {
            this.category = Category.NOTPROVIDED;
        } else {
            this.category = null;
        }
        
    }

    public SubcategoryOne getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {

        if (sub1.equals("PB")) {
            this.sub1 = SubcategoryOne.PB;
        } else if (sub1.equals("BM")) {
            this.sub1 = SubcategoryOne.BM;
        } else if (sub1.equals("GB")) {
            this.sub1 = SubcategoryOne.GB;
        } else if (sub1.equals("Prol.")) {
            this.sub1 = SubcategoryOne.PROL;
        } else if (sub1.equals("Prol. traces")) {
            this.sub1 = SubcategoryOne.PROLTRACES;
        } else if (sub1.equals("mixed")) {
            this.sub1 = SubcategoryOne.MIXED;
        } else if (sub1.equals("other")) {
            this.sub1 = SubcategoryOne.OTHER;
        } else {
            this.sub1 = null;
        }
        
    }

    public SubcategoryTwo getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        
        if (sub2.equals("a")) {
            this.sub2 = SubcategoryTwo.A;
        } else if (sub2.equals("b")) {
            this.sub2 = SubcategoryTwo.B;
        } else if (sub2.equals("mixed")) {
            this.sub2 = SubcategoryTwo.MIXED;
        } else if (sub2.equals("other")) {
            this.sub2 = SubcategoryTwo.OTHER;
        } else {
            this.sub2 = null;
        }
        
    }

    public SubcategoryThree getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        if (sub3.equals("prefaces")) {
            this.sub3 = SubcategoryThree.PREFACE;
        } else if (sub3.equals("no prefaces")) {
            this.sub3 = SubcategoryThree.NOPREFACE;
        } else if (sub3.equals("other")) {
            this.sub3 = SubcategoryThree.OTHER;
        } else {
            this.sub3 = null;
        }
    }
    
}
