package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Guyart implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Incipit incipit;
    private String startPage;
    private boolean containsGuyartName;
    
    public Guyart() {
        this.containsGuyartName = false;
        this.incipit = new Incipit();
    }
    
    public Incipit getIncipit() {
        return incipit;
    }
    
    public void setIncipit(Incipit incipit) {
        this.incipit = incipit;
    }
    
    public String getStartPage() {
        return startPage;
    }
    
    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }
    
    public boolean containsGuyartName() {
        return containsGuyartName;
    }
    
    public void setContainsGuyartName(boolean containsGuyartName) {
        this.containsGuyartName = containsGuyartName;
    }
    
}
