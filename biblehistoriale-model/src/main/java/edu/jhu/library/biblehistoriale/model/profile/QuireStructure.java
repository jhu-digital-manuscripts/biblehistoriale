package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>The quire structure of a bible.</p>
 * 
 * <p>Associated with the &ltquireStruct&gt element of the bible schema.</p>
 */
public class QuireStructure implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int volume;
    
    private ArrayList<Integer> quireTotal;
    private ArrayList<Integer> typicalQuires;
    private ArrayList<String> fullQuireStructs;
    private ArrayList<String> quireNotes;
    
    public QuireStructure() {
        this.volume = 0;
        
        this.quireTotal = new ArrayList<Integer>();
        this.typicalQuires = new ArrayList<Integer>();
        this.fullQuireStructs = new ArrayList<String>();
        this.quireNotes = new ArrayList<String>();
    }

    public ArrayList<Integer> quireTotal() {
        return quireTotal;
    }

    public void setQuireTotal(ArrayList<Integer> quireTotal) {
        this.quireTotal = quireTotal;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public ArrayList<Integer> typicalQuires() {
        return typicalQuires;
    }

    public void setTypicalQuires(ArrayList<Integer> typicalQuires) {
        this.typicalQuires = typicalQuires;
    }

    public ArrayList<String> fullQuireStructs() {
        return fullQuireStructs;
    }

    public void setFullQuireStructs(ArrayList<String> fullQuireStructs) {
        this.fullQuireStructs = fullQuireStructs;
    }

    public ArrayList<String> quireNotes() {
        return quireNotes;
    }

    public void setQuireNotes(ArrayList<String> quireNotes) {
        this.quireNotes = quireNotes;
    }
    
}
