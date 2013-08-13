package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class QuireStructure {
    
    private int quireTotal;
    private int volume;
    
    private List<Integer> typicalQuires;
    private List<String> fullQuireStructs;
    private List<String> quireNotes;
    
    public QuireStructure() {
        this.quireTotal = 0;
        this.volume = 0;
    }

    public int getQuireTotal() {
        return quireTotal;
    }

    public void setQuireTotal(int quireTotal) {
        this.quireTotal = quireTotal;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public List<Integer> typicalQuires() {
        return typicalQuires;
    }

    public void setTypicalQuires(List<Integer> typicalQuires) {
        this.typicalQuires = typicalQuires;
    }

    public List<String> fullQuireStructs() {
        return fullQuireStructs;
    }

    public void setFullQuireStructs(List<String> fullQuireStructs) {
        this.fullQuireStructs = fullQuireStructs;
    }

    public List<String> quireNotes() {
        return quireNotes;
    }

    public void setQuireNotes(List<String> quireNotes) {
        this.quireNotes = quireNotes;
    }
    
}
