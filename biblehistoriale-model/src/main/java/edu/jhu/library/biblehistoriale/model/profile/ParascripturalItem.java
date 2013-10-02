package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Associated with the &ltparascripturalItems&gt element
 * of the bible schema. Includes the litany, canticles, and any added prologues</p>
 */
public class ParascripturalItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum LitanyForm {
        PROSE("prose"), VERSE("verse"), MIXED("mixed"),
        UNKNOWN("unknown"), NA("n/a");
        
        private String form;
        
        private LitanyForm(String form) {
            this.form = form;
        }
        
        public String formString() {
            return form;
        }
        
        public static LitanyForm getLitanyForm(String form) {
            for (LitanyForm lf : LitanyForm.values()) {
                if (lf.formString().equals(form))
                    return lf;
            }
            return null;
        }
    }
    
    public enum SneddonId {
        ONE("1"), TWO("2"), THREE("3"), 
        OTHER("other"), UNKNOWN("unknown"), NA("n/a");
        
        private String message;
        
        private SneddonId(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        public static SneddonId getSneddonId(String message) {
            for (SneddonId si : SneddonId.values()) {
                if (si.getMessage().equals(message))
                    return si;
            }
            return null;
        }
    }
    
    public enum AddedChoice {
        Y("y"), N("n"), PARTIAL("partial"), UNKNOWN("unknown");
        
        private String choice;
        
        private AddedChoice(String choice) {
            this.choice = choice;
        }
        
        public String choice() {
            return choice;
        }
        
        public static AddedChoice getChoice(String choice) {
            for (AddedChoice c : AddedChoice.values()) {
                if (c.choice.equals(choice)) {
                    return c;
                }
            }
            return null;
        }
    }
    
    private int volume;
    
    public ParascripturalItem() {
        this.volume = 0;
        
        this.catechismPrayersTreatises = new ArrayList<CatechismsPrayersTreatise> ();
    }
    
    // Litany
    private String placeOfOriginUse;
    private LitanyForm form;
    private String locVol;
    private String locStart;
    private String locEnd;
    private Choice litanyPresence;
    private SneddonId sneddonId;
    
    // Canticles
    private String canticleType;
    private String canticleStartFolio;
    private String canticleEndFolio;
    private Choice canticlePresence;
    
    // Added prologue
    private AddedChoice jeanDeBlois;
    private AddedChoice jerome;
    
    private ArrayList<CatechismsPrayersTreatise> catechismPrayersTreatises;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getPlaceOfOriginUse() {
        return placeOfOriginUse;
    }

    public void setPlaceOfOriginUse(String placeOfOriginUse) {
        this.placeOfOriginUse = placeOfOriginUse;
    }

    public LitanyForm getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = LitanyForm.getLitanyForm(form);
    }

    public String getLocVol() {
        return locVol;
    }

    public void setLocVol(String locVol) {
        this.locVol = locVol;
    }

    public String getLocStart() {
        return locStart;
    }

    public void setLocStart(String locStart) {
        this.locStart = locStart;
    }

    public String getLocEnd() {
        return locEnd;
    }

    public void setLocEnd(String locEnd) {
        this.locEnd = locEnd;
    }

    public Choice getLitanyPresence() {
        return litanyPresence;
    }

    public void setLitanyPresence(String litanyPresence) {
        this.litanyPresence = Choice.getChoice(litanyPresence);
    }

    public SneddonId getSneddonId() {
        return sneddonId;
    }

    public void setSneddonId(String sneddonId) {
        this.sneddonId = SneddonId.getSneddonId(sneddonId);
    }

    public String getCanticleType() {
        return canticleType;
    }

    public void setCanticleType(String canticleType) {
        this.canticleType = canticleType;
    }

    public String getCanticleStartFolio() {
        return canticleStartFolio;
    }

    public void setCanticleStartFolio(String canticleStartFolio) {
        this.canticleStartFolio = canticleStartFolio;
    }

    public String getCanticleEndFolio() {
        return canticleEndFolio;
    }

    public void setCanticleEndFolio(String canticleEndFolio) {
        this.canticleEndFolio = canticleEndFolio;
    }

    public Choice getCanticlePresence() {
        return canticlePresence;
    }

    public void setCanticlePresence(String canticlePresence) {
        this.canticlePresence = Choice.getChoice(canticlePresence);
    }

    public AddedChoice getJeanDeBlois() {
        return jeanDeBlois;
    }

    public void setJeanDeBlois(String jeanDeBlois) {
        this.jeanDeBlois = AddedChoice.getChoice(jeanDeBlois);
    }

    public AddedChoice getJerome() {
        return jerome;
    }

    public void setJerome(String jerome) {
        this.jerome = AddedChoice.getChoice(jerome);
    }

    public List<CatechismsPrayersTreatise> catechismPrayersTreatises() {
        return catechismPrayersTreatises;
    }

    public void setCatechismPrayersTreatises(
            ArrayList<CatechismsPrayersTreatise> catechismPrayersTreatises) {
        this.catechismPrayersTreatises = catechismPrayersTreatises;
    }
    
}
