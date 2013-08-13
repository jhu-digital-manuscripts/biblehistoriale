package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class Owner {
    
    private String ownerName;
    private String ownershipDate;
    
    private int ownershipStartDate;
    private int ownershipEndDate;
    
    private List<String> ownerPlace;
    
    public Owner() {
        this.ownershipStartDate = -1;
        this.ownershipEndDate = -1;
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

    public void setOwnerPlace(List<String> ownerPlace) {
        this.ownerPlace = ownerPlace;
    }
    
}
