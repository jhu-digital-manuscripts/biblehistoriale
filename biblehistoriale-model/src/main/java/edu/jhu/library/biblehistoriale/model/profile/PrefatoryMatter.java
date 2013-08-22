package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrefatoryMatter implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int volume;
    private String prefactoryNote;
    
    private List<OtherPreface> otherPrefaces;
    private List<Guyart> guyartList;
    private List<ComestorLetter> comestorLetters;
    private List<OtherPreface> comestorList;
    
    private MasterTableOfContents masterTableOfContents;
    
    public PrefatoryMatter() {
        this.volume = 0;
        this.masterTableOfContents = new MasterTableOfContents();
        
        this.otherPrefaces = new ArrayList<OtherPreface> ();
        this.guyartList = new ArrayList<Guyart> ();
        this.comestorLetters = new ArrayList<ComestorLetter> ();
        this.comestorList = new ArrayList<OtherPreface> ();
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getPrefactoryNote() {
        return prefactoryNote;
    }

    public void setPrefactoryNote(String prefactoryNote) {
        this.prefactoryNote = prefactoryNote;
    }

    public List<OtherPreface> otherPrefaces() {
        return otherPrefaces;
    }

    public void setOtherPrefaces(List<OtherPreface> otherPrefaces) {
        this.otherPrefaces = otherPrefaces;
    }

    public List<Guyart> guyartList() {
        return guyartList;
    }

    public void setGuyartList(List<Guyart> guyartList) {
        this.guyartList = guyartList;
    }

    public List<ComestorLetter> comestorLetters() {
        return comestorLetters;
    }

    public void setComestorLetters(List<ComestorLetter> comestorLetters) {
        this.comestorLetters = comestorLetters;
    }

    public List<OtherPreface> comestorList() {
        return comestorList;
    }

    public void setComestorList(List<OtherPreface> comestorList) {
        this.comestorList = comestorList;
    }

    public MasterTableOfContents getMasterTableOfContents() {
        return masterTableOfContents;
    }

    public void setMasterTableOfContents(MasterTableOfContents masterTableOfContents) {
        this.masterTableOfContents = masterTableOfContents;
    }
    
}
