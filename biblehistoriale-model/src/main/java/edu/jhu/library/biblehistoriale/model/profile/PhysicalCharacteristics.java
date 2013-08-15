package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.List;

public class PhysicalCharacteristics implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Volumes volumes;
    private Folios folios;
    private PageLayout pageLayout;
    private Materials materials;
    private String rubricNotes;
    private String physicaNotes;
    private String pageLayoutNotes;
    
    private List<Dimensions> dimensions;
    private List<QuireStructure> quireStructs;
    private List<String> glossHeadings;
    private List<String> underlinings;
    
    public PhysicalCharacteristics() {
        
    }

    public Volumes getVolumes() {
        return volumes;
    }

    public void setVolumes(Volumes volumes) {
        this.volumes = volumes;
    }

    public Folios getFolios() {
        return folios;
    }

    public void setFolios(Folios folios) {
        this.folios = folios;
    }

    public PageLayout getPageLayout() {
        return pageLayout;
    }

    public void setPageLayout(PageLayout pageLayout) {
        this.pageLayout = pageLayout;
    }

    public Materials getMaterials() {
        return materials;
    }

    public void setMaterials(Materials materials) {
        this.materials = materials;
    }

    public String getRubricNotes() {
        return rubricNotes;
    }

    public void setRubricNotes(String rubricNotes) {
        this.rubricNotes = rubricNotes;
    }

    public String getPhysicaNotes() {
        return physicaNotes;
    }

    public void setPhysicaNotes(String physicaNotes) {
        this.physicaNotes = physicaNotes;
    }

    public List<Dimensions> dimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimensions> dimensions) {
        this.dimensions = dimensions;
    }

    public List<QuireStructure> quireStructs() {
        return quireStructs;
    }

    public void setQuireStructs(List<QuireStructure> quireStructs) {
        this.quireStructs = quireStructs;
    }

    public List<String> glossHeadings() {
        return glossHeadings;
    }

    public void setGlossHeadings(List<String> glossHeadings) {
        this.glossHeadings = glossHeadings;
    }

    public List<String> underlinings() {
        return underlinings;
    }

    public void setUnderlinings(List<String> underlinings) {
        this.underlinings = underlinings;
    }

    public String getPageLayoutNotes() {
        return pageLayoutNotes;
    }

    public void setPageLayoutNotes(String pageLayoutNotes) {
        this.pageLayoutNotes = pageLayoutNotes;
    }
    
}
