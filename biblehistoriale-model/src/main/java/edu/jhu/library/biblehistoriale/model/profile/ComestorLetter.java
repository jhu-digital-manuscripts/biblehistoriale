package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComestorLetter implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String startPage;
    private List<Incipit> incipits;
    
    public ComestorLetter() {
        this.incipits = new ArrayList<Incipit> ();
    }
    
    public String getStartPage() {
        return startPage;
    }
    
    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }
    
    public List<Incipit> incipits() {
        return incipits;
    }
    
    public void setIncipits(List<Incipit> incipits) {
        this.incipits = incipits;
    }
    
}
