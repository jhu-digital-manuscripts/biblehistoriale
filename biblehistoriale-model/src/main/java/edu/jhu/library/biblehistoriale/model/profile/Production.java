package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.List;

public class Production implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String prodDate;
    private String prodLoc;
    private String prodNotes;
    
    private int prodStartDate;
    private int prodEndDate;
    
    private List<Contributor> contributors;
    
    public Production() {
        
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getProdLoc() {
        return prodLoc;
    }

    public void setProdLoc(String prodLoc) {
        this.prodLoc = prodLoc;
    }

    public String getProdNotes() {
        return prodNotes;
    }

    public void setProdNotes(String prodNotes) {
        this.prodNotes = prodNotes;
    }

    public int getProdStartDate() {
        return prodStartDate;
    }

    public void setProdStartDate(int prodStartDate) {
        this.prodStartDate = prodStartDate;
    }

    public int getProdEndDate() {
        return prodEndDate;
    }

    public void setProdEndDate(int prodEndDate) {
        this.prodEndDate = prodEndDate;
    }

    public List<Contributor> contributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }
    
}
