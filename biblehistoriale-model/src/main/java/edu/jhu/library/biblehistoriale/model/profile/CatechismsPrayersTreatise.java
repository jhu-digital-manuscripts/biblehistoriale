package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatechismsPrayersTreatise implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int volume;
    
    private String startFolio;
    private String endFolio;
    
    private Choice presence;
    
    private ArrayList<String> descriptionsFirstLines;

    public CatechismsPrayersTreatise() {
        this.volume = 0;
    }
    
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
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

    public Choice getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = Choice.getChoice(presence);
    }

    public List<String> getDescriptionsFirstLines() {
        return descriptionsFirstLines;
    }

    public void setDescriptionsFirstLines(ArrayList<String> descriptionsFirstLines) {
        this.descriptionsFirstLines = descriptionsFirstLines;
    }
    
}
