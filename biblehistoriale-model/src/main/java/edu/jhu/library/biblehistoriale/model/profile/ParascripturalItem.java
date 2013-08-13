package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class ParascripturalItem {
    
    public enum LitanyForm {
        PROSE, VERSE, MIXED, UNKNOWN, NA
    }
    
    public enum SneddonId {
        ONE, TWO, THREE, OTHER, UNKNOWN, NA
    }
    
    public enum AddedChoice {
        Y, N, PARTIAL, UNKNOWN
    }
    
    private int volume;
    
    public ParascripturalItem() {
        this.volume = 0;
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

    public void setForm(LitanyForm form) {
        this.form = form;
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

    public void setLitanyPresence(Choice litanyPresence) {
        this.litanyPresence = litanyPresence;
    }

    public SneddonId getSneddonId() {
        return sneddonId;
    }

    public void setSneddonId(SneddonId sneddonId) {
        this.sneddonId = sneddonId;
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

    public void setCanticlePresence(Choice canticlePresence) {
        this.canticlePresence = canticlePresence;
    }

    public AddedChoice getJeanDeBlois() {
        return jeanDeBlois;
    }

    public void setJeanDeBlois(AddedChoice jeanDeBlois) {
        this.jeanDeBlois = jeanDeBlois;
    }

    public AddedChoice getJerome() {
        return jerome;
    }

    public void setJerome(AddedChoice jerome) {
        this.jerome = jerome;
    }

    public List<CatechismsPrayersTreatise> catechismPrayersTreatises() {
        return catechismPrayersTreatises;
    }

    public void setCatechismPrayersTreatises(
            List<CatechismsPrayersTreatise> catechismPrayersTreatises) {
        this.catechismPrayersTreatises = catechismPrayersTreatises;
    }
    
}
