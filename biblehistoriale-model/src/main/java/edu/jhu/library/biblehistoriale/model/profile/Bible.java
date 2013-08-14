package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * An Ms profile, representing one bible. Also the root in the XML schema.
 */
public class Bible implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private PhysicalCharacteristics physChar;
    private ProvenPatronHistory provenPatronHist;
    private IllustrationList illustrations;
    private Classification classification;
    private TextualContent textualContent;
    private Bibliography bibliography;
    
    private String scannedMss;                          // URL
    private String bibleHistorialeTranscription;        // URL
    
    public Bible() {
        
    }
    
    public void setPhysChar(PhysicalCharacteristics physChar) {
        this.physChar = physChar;
    }

    public void setProvenPatronHist(ProvenPatronHistory provenPatronHist) {
        this.provenPatronHist = provenPatronHist;
    }

    public void setIllustrations(IllustrationList illustrations) {
        this.illustrations = illustrations;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public void setTextualContent(TextualContent textualContent) {
        this.textualContent = textualContent;
    }

    public void setBibliography(Bibliography bibliography) {
        this.bibliography = bibliography;
    }

    public void setScannedMss(String scannedMss) {
        this.scannedMss = scannedMss;
    }

    public void setBibleHistorialeTranscription(String bibleHistorialeTranscription) {
        this.bibleHistorialeTranscription = bibleHistorialeTranscription;
    }
    
    public PhysicalCharacteristics getPhysChar() {
        return physChar;
    }
    
    public ProvenPatronHistory getProvenPatronHist() {
        return provenPatronHist;
    }
    
    public IllustrationList getIllustrations() {
        return illustrations;
    }
    
    public Classification getClassification() {
        return classification;
    }
    
    public TextualContent getTextualContent() {
        return textualContent;
    }
    
    public Bibliography getBibliography() {
        return bibliography;
    }
    
    public boolean hasScannedMss() {
        if (scannedMss != null) {
            return true;
        }
        
        return false;
    }
    
    public boolean hasBibleHistorialeTranscriptions() {
        if (bibleHistorialeTranscription != null) {
            return true;
        }
        
        return false;
    }
    
    public String getScannedMss() {
        return scannedMss;
    }
    
    public String getBibleHistorialeTranscription() {
        return bibleHistorialeTranscription;
    }
    
}
