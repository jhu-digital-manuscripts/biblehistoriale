package edu.jhu.library.biblehistoriale.model.profile;

public class Signature extends PersonalizationItem {
    
    private String name;
    private String text;
    
    public Signature() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
