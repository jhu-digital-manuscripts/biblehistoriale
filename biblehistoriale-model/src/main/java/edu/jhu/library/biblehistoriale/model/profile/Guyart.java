package edu.jhu.library.biblehistoriale.model.profile;

public class Guyart {
    
    private Incipit incipit;
    private String startPage;
    private boolean containsGuyartName;
    
    public Guyart() {
        this.containsGuyartName = false;
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
