package edu.jhu.library.biblehistoriale.model.profile;

public class Incipit {
    
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
