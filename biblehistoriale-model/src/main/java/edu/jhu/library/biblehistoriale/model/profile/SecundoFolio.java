package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class SecundoFolio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int volume;
    private String value;
    
    public SecundoFolio() {
        this.volume = 0;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
