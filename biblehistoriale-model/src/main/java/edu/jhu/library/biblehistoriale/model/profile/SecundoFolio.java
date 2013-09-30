package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>Whatever the heck a secundo folio is. I think it has to 
 * do with old cataloging...</p>
 * 
 * <p>Associated with the &ltsecundoFolio&gt element in the bible schema.</p>
 */
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
