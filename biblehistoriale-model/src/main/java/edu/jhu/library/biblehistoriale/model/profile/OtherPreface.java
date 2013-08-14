package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class OtherPreface extends Incipit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String startPage;

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }
    
}
