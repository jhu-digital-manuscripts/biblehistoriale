package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class MiscContent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String description;
    private String startFolio;
    private String endFolio;
    
    private int volume;

    public MiscContent() {
        this.volume = 0;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartFolio() {
        return startFolio;
    }

    public void setStartFolio(String startFolio) {
        this.startFolio = startFolio;
    }

    public String getEndFolio() {
        return endFolio;
    }

    public void setEndFolio(String endFolio) {
        this.endFolio = endFolio;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    
}
