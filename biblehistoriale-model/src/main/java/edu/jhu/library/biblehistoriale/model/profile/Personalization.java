package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Any personalizations done to a bible by an owner.</p>
 * 
 * <p>Associated with the &ltpersonalization&gt element of the bible schema.</p>
 * 
 * @see Signature
 * @see PersonalizationItem
 */
public class Personalization implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String purchasePrice;
    
    private ArrayList<Signature> signatures;
    private ArrayList<String> dedications;
    private ArrayList<PersonalizationItem> legalInscriptions;
    private ArrayList<PersonalizationItem> patronPortraits;
    private ArrayList<PersonalizationItem> patronArms;
    private ArrayList<PersonalizationItem> colophons;
    
    public Personalization() {
        this.signatures = new ArrayList<Signature> ();
        this.dedications = new ArrayList<String> ();
        this.legalInscriptions = new ArrayList<PersonalizationItem> ();
        this.patronPortraits = new ArrayList<PersonalizationItem> ();
        this.patronArms = new ArrayList<PersonalizationItem> ();
        this.colophons = new ArrayList<PersonalizationItem> ();
    }
    
    public String getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    public ArrayList<Signature> signatures() {
        return signatures;
    }
    
    public void setSignatures(ArrayList<Signature> signatures) {
        this.signatures = signatures;
    }
    
    public ArrayList<String> dedications() {
        return dedications;
    }
    
    public void setDedications(ArrayList<String> dedications) {
        this.dedications = dedications;
    }
    
    public ArrayList<PersonalizationItem> legalInscriptions() {
        return legalInscriptions;
    }
    
    public void setLegalInscriptions(ArrayList<PersonalizationItem> legalInscriptions) {
        this.legalInscriptions = legalInscriptions;
    }
    
    public ArrayList<PersonalizationItem> patronPortraits() {
        return patronPortraits;
    }
    
    public void setPatronPortraits(ArrayList<PersonalizationItem> patronPortraits) {
        this.patronPortraits = patronPortraits;
    }
    
    public ArrayList<PersonalizationItem> patronArms() {
        return patronArms;
    }
    
    public void setPatronArms(ArrayList<PersonalizationItem> patronArms) {
        this.patronArms = patronArms;
    }
    
    public ArrayList<PersonalizationItem> colophons() {
        return colophons;
    }
    
    public void setColophons(ArrayList<PersonalizationItem> colophons) {
        this.colophons = colophons;
    }
    
}
