package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Incipit implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Accuracy {
        APPROXIMATE("approximate"), ACTUAL("actual");
        
        private String accuracy;
        
        private Accuracy(String accuracy) {
            this.accuracy = accuracy;
        }
        
        public String accuracy() {
            return accuracy;
        }
    }
    
    private Accuracy accuracy;
    private String text;
    
    public Accuracy getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(String accuracy) {
        if (accuracy.equals("actual")) {
            this.accuracy = Accuracy.ACTUAL;
        } else if (accuracy.equals("approximate")) {
            this.accuracy = Accuracy.APPROXIMATE;
        } else {
            this.accuracy = null;
        }
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Incipit other = (Incipit) obj;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }
    
}
