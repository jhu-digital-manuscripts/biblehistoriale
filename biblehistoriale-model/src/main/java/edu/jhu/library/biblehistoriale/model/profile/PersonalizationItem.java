package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>An item of personalization by an owner of a bible. This can include 
 * legal inscriptions, patron arms, patron protraits, colophons.</p>
 * 
 * <p>Associated with the &ltlegalInscriptions&gt, &ltpatronPortrait&gt, 
 * &ltpatronArms&gt, and &ltcolophon&gt elements of the bible schema.</p>
 */
public class PersonalizationItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected String folio;
    protected String value;
    protected int volume;
    
    public PersonalizationItem() {
        this.volume = 0;
    }
    
    public String getFolio() {
        return folio;
    }
    
    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
}
