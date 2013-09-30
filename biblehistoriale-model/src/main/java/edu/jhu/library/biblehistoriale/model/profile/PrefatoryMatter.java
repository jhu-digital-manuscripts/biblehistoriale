package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Prefatory matter, associated with the &ltprefatoryMatter&gt 
 * element of the bible schema.</p>
 * 
 * @see OtherPreface
 * @see Guyart
 * @see ComestorLetter
 * @see MasterTableOfContents
 */
public class PrefatoryMatter implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int volume;
    private String prefactoryNote;
    
    private ArrayList<OtherPreface> otherPrefaces;
    private ArrayList<Guyart> guyartList;
    private ArrayList<ComestorLetter> comestorLetters;
    private ArrayList<OtherPreface> comestorList;
    
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

    public ArrayList<OtherPreface> otherPrefaces() {
        return otherPrefaces;
    }

    public void setOtherPrefaces(ArrayList<OtherPreface> otherPrefaces) {
        this.otherPrefaces = otherPrefaces;
    }

    public ArrayList<Guyart> guyartList() {
        return guyartList;
    }

    public void setGuyartList(ArrayList<Guyart> guyartList) {
        this.guyartList = guyartList;
    }

    public ArrayList<ComestorLetter> comestorLetters() {
        return comestorLetters;
    }

    public void setComestorLetters(ArrayList<ComestorLetter> comestorLetters) {
        this.comestorLetters = comestorLetters;
    }

    public ArrayList<OtherPreface> comestorList() {
        return comestorList;
    }

    public void setComestorList(ArrayList<OtherPreface> comestorList) {
        this.comestorList = comestorList;
    }

    public MasterTableOfContents getMasterTableOfContents() {
        return masterTableOfContents;
    }

    public void setMasterTableOfContents(MasterTableOfContents masterTableOfContents) {
        this.masterTableOfContents = masterTableOfContents;
    }
    
}
