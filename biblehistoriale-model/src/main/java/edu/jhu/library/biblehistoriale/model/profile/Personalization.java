package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class Personalization {
    
    private String purchasePrice;
    
    private List<Signature> signatures;
    private List<String> dedications;
    private List<PersonalizationItem> legalInscriptions;
    private List<PersonalizationItem> patronPortraits;
    private List<PersonalizationItem> patronArms;
    private List<PersonalizationItem> colophons;
    
    public Personalization() {
        
    }
    
    public String getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    public List<Signature> signatures() {
        return signatures;
    }
    
    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }
    
    public List<String> dedications() {
        return dedications;
    }
    
    public void setDedications(List<String> dedications) {
        this.dedications = dedications;
    }
    
    public List<PersonalizationItem> legalInscriptions() {
        return legalInscriptions;
    }
    
    public void setLegalInscriptions(List<PersonalizationItem> legalInscriptions) {
        this.legalInscriptions = legalInscriptions;
    }
    
    public List<PersonalizationItem> patronPortraits() {
        return patronPortraits;
    }
    
    public void setPatronPortraits(List<PersonalizationItem> patronPortraits) {
        this.patronPortraits = patronPortraits;
    }
    
    public List<PersonalizationItem> patronArms() {
        return patronArms;
    }
    
    public void setPatronArms(List<PersonalizationItem> patronArms) {
        this.patronArms = patronArms;
    }
    
    public List<PersonalizationItem> colophons() {
        return colophons;
    }
    
    public void setColophons(List<PersonalizationItem> colophons) {
        this.colophons = colophons;
    }
    
}
