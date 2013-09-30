package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Information about the production of a bible.</p>
 * 
 * <p>Associated with the &ltproduction&gt element of the bible schema.</p>
 * 
 * @see Contribtor
 */
public class Production implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String prodDate;
    private String prodLoc;
    private String prodNotes;
    
    private int prodStartDate;
    private int prodEndDate;
    
    private ArrayList<Contributor> contributors;
    
    public Production() {
        this.contributors = new ArrayList<Contributor> ();
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

    public ArrayList<Contributor> contributors() {
        return contributors;
    }

    public void setContributors(ArrayList<Contributor> contributors) {
        this.contributors = contributors;
    }
    
}
