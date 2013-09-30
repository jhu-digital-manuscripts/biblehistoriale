package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Associated with the &ltcatalogerClassification&gt element in bible schema.
 * </p>
 * 
 * <p>Contains Berger class, Sneddon class, Fournie catalog, secundo folios,
 *  and notes</p>
 *  
 *  @see Berger
 *  @see Sneddon
 *  @see SecundoFolio
 */
public class CatalogerClassification implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Berger bergerClass;
    private Sneddon sneddonClass;
    
    private String fournieNumber;
    private String fournieLink;
    private String classificationNote;
    
    private ArrayList<SecundoFolio> secundoFolios;
    
    public CatalogerClassification() {
        this.bergerClass = new Berger();
        this.sneddonClass = new Sneddon();
        
        this.secundoFolios = new ArrayList<SecundoFolio> ();
    }

    public Berger getBergerClass() {
        return bergerClass;
    }

    public void setBergerClass(Berger bergerClass) {
        this.bergerClass = bergerClass;
    }

    public Sneddon getSneddonClass() {
        return sneddonClass;
    }

    public void setSneddonClass(Sneddon sneddonClass) {
        this.sneddonClass = sneddonClass;
    }

    public String getFournieNumber() {
        return fournieNumber;
    }

    public void setFournieNumber(String fournieNumber) {
        this.fournieNumber = fournieNumber;
    }

    public String getFournieLink() {
        return fournieLink;
    }

    public void setFournieLink(String fournieLink) {
        this.fournieLink = fournieLink;
    }

    public String getClassificationNote() {
        return classificationNote;
    }

    public void setClassificationNote(String classificationNote) {
        this.classificationNote = classificationNote;
    }

    public ArrayList<SecundoFolio> secundoFolios() {
        return secundoFolios;
    }

    public void setSecundoFolios(ArrayList<SecundoFolio> secundoFolios) {
        this.secundoFolios = secundoFolios;
    }
    
}
