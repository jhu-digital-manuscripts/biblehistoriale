package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>The physical characteristics of a bible, including dimensions, 
 * gloss headings, quires, etc.</p>
 * 
 * <p>Associated with the &ltphysChar&gt element of the bible schema.</p>
 * 
 * @see Volumes
 * @see Folios
 * @see PageLayout
 * @see Materials
 * @see Dimensions
 * @see QuireStructure
 */
public class PhysicalCharacteristics implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Volumes volumes;
    private Folios folios;
    private PageLayout pageLayout;
    private Materials materials;
    private String rubricNotes;
    private String physicaNotes;
    private String pageLayoutNotes;
    
    private ArrayList<Dimensions> dimensions;
    private ArrayList<QuireStructure> quireStructs;
    private ArrayList<String> glossHeadings;
    private ArrayList<String> underlinings;
    
    public PhysicalCharacteristics() {
        this.volumes = new Volumes();
        this.folios = new Folios();
        this.pageLayout = new PageLayout();
        this.materials = new Materials();
        
        this.dimensions = new ArrayList<Dimensions>();
        this.quireStructs = new ArrayList<QuireStructure> ();
        this.glossHeadings = new ArrayList<String> ();
        this.underlinings = new ArrayList<String> ();
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

    public ArrayList<Dimensions> dimensions() {
        return dimensions;
    }

    public void setDimensions(ArrayList<Dimensions> dimensions) {
        this.dimensions = dimensions;
    }

    public ArrayList<QuireStructure> quireStructs() {
        return quireStructs;
    }

    public void setQuireStructs(ArrayList<QuireStructure> quireStructs) {
        this.quireStructs = quireStructs;
    }

    public ArrayList<String> glossHeadings() {
        return glossHeadings;
    }

    public void setGlossHeadings(ArrayList<String> glossHeadings) {
        this.glossHeadings = glossHeadings;
    }

    public ArrayList<String> underlinings() {
        return underlinings;
    }

    public void setUnderlinings(ArrayList<String> underlinings) {
        this.underlinings = underlinings;
    }

    public String getPageLayoutNotes() {
        return pageLayoutNotes;
    }

    public void setPageLayoutNotes(String pageLayoutNotes) {
        this.pageLayoutNotes = pageLayoutNotes;
    }
    
}
