package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Incipit implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Accuracy {
        APPROXIMATE, ACTUAL
    }
    
    private Accuracy accuracy;
    private String text;
    
    public Accuracy getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(Accuracy accuracy) {
        this.accuracy = accuracy;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
}
