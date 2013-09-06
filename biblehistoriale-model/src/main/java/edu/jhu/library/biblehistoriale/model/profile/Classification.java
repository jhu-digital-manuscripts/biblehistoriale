package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classification implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String currentCity;
    private String currentRepository;
    private String currentShelfmark;
    private ArrayList<String> formerShelfmarks;
    private String repositoryLink;
    
    private String coverTitle;
    private String rubricTitle;
    
    private BookType bookType;
    private CatalogerClassification classification;
    
    public Classification() {
        this.bookType = new BookType();
        this.classification = new CatalogerClassification();
        
        this.formerShelfmarks = new ArrayList<String> ();
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentRepository() {
        return currentRepository;
    }

    public void setCurrentRepository(String currentRepository) {
        this.currentRepository = currentRepository;
    }

    public String getCurrentShelfmark() {
        return currentShelfmark;
    }

    public void setCurrentShelfmark(String currentShelfmark) {
        this.currentShelfmark = currentShelfmark;
    }

    public ArrayList<String> formerShelfmarks() {
        return formerShelfmarks;
    }

    public void setFormerShelfmarks(ArrayList<String> formerShelfmarks) {
        this.formerShelfmarks = formerShelfmarks;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

    public String getCoverTitle() {
        return coverTitle;
    }

    public void setCoverTitle(String coverTitle) {
        this.coverTitle = coverTitle;
    }

    public String getRubricTitle() {
        return rubricTitle;
    }

    public void setRubricTitle(String rubricTitle) {
        this.rubricTitle = rubricTitle;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public CatalogerClassification getClassification() {
        return classification;
    }

    public void setClassification(CatalogerClassification classification) {
        this.classification = classification;
    }
    
}
