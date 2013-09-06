package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Owner implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String ownerName;
    private String ownershipDate;
    
    private int ownershipStartDate;
    private int ownershipEndDate;
    
    private ArrayList<String> ownerPlace;
    
    public Owner() {
        this.ownershipStartDate = -1;
        this.ownershipEndDate = -1;
        
        this.ownerPlace = new ArrayList<String> ();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnershipDate() {
        return ownershipDate;
    }

    public void setOwnershipDate(String ownershipDate) {
        this.ownershipDate = ownershipDate;
    }

    public int getOwnershipStartDate() {
        return ownershipStartDate;
    }

    public void setOwnershipStartDate(int ownershipStartDate) {
        this.ownershipStartDate = ownershipStartDate;
    }

    public int getOwnershipEndDate() {
        return ownershipEndDate;
    }

    public void setOwnershipEndDate(int ownershipEndDate) {
        this.ownershipEndDate = ownershipEndDate;
    }

    public List<String> getOwnerPlace() {
        return ownerPlace;
    }

    public void setOwnerPlace(ArrayList<String> ownerPlace) {
        this.ownerPlace = ownerPlace;
    }
    
}
