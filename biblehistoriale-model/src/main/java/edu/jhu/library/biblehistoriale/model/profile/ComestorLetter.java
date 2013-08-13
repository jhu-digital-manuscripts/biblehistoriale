package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class ComestorLetter {
    
    private String startPage;
    private List<Incipit> incipits;
    
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
