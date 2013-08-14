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

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubcategoryOne getSub1() {
        return sub1;
    }

    public void setSub1(SubcategoryOne sub1) {
        this.sub1 = sub1;
    }

    public SubcategoryTwo getSub2() {
        return sub2;
    }

    public void setSub2(SubcategoryTwo sub2) {
        this.sub2 = sub2;
    }

    public SubcategoryThree getSub3() {
        return sub3;
    }

    public void setSub3(SubcategoryThree sub3) {
        this.sub3 = sub3;
    }
    
}
