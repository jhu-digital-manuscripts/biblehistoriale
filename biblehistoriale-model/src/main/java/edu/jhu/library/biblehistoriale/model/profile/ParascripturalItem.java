package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParascripturalItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum LitanyForm {
        PROSE, VERSE, MIXED, UNKNOWN, NA
    }
    
    public enum SneddonId {
        ONE, TWO, THREE, OTHER, UNKNOWN, NA
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
    
    private List<CatechismsPrayersTreatise> catechismPrayersTreatises;

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
        if (form.equals("prose")) {
            this.form = LitanyForm.PROSE;
        } else if (form.equals("verse")) {
            this.form = LitanyForm.VERSE;
        } else if (form.equals("mixed")) {
            this.form = LitanyForm.MIXED;
        } else if (form.equals("unknown")) {
            this.form = LitanyForm.UNKNOWN;
        } else if (form.equals("n/a")) {
            this.form = LitanyForm.NA;
        } else {
            this.form = null;
        }
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
        if (sneddonId.equals("1")) {
            this.sneddonId = SneddonId.ONE;
        } else if (sneddonId.equals("2")) {
            this.sneddonId = SneddonId.TWO;
        } else if (sneddonId.equals("3")) {
            this.sneddonId = SneddonId.THREE;
        } else if (sneddonId.equals("other")) {
            this.sneddonId = SneddonId.OTHER;
        } else if (sneddonId.equals("unknown")) {
            this.sneddonId = SneddonId.UNKNOWN;
        } else if (sneddonId.equals("n/a")) {
            this.sneddonId = SneddonId.NA;
        } else {
            this.sneddonId = null;
        }
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
            List<CatechismsPrayersTreatise> catechismPrayersTreatises) {
        this.catechismPrayersTreatises = catechismPrayersTreatises;
    }
    
}
