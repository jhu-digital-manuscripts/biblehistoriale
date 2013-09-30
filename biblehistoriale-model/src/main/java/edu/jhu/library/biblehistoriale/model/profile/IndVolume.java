package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>Information about an individual volume, describing the number of folios.</p>
 * 
 * <p>Associated with the &ltindVolume&gt element in the bible schema.</p>
 */
public class IndVolume implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int volume;
    private String value;
    
    public IndVolume() {
        
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