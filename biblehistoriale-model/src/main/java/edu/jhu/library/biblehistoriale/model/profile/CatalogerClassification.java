package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class CatalogerClassification {
    
    private Berger bergerClass;
    private Sneddon sneddonClass;
    
    private String fournieNumber;
    private String fournieLink;
    private String classificationNote;
    
    private List<SecundoFolio> secundoFolios;
    
    public CatalogerClassification() {
        
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

    public List<SecundoFolio> secundoFolios() {
        return secundoFolios;
    }

    public void setSecundoFolios(List<SecundoFolio> secundoFolios) {
        this.secundoFolios = secundoFolios;
    }
    
}
