package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class PageLayout implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String columns;
    private String glossPlace;
    private String runningHeads;
    private String chapterNumbers;
    private String smallLetterHist;
    private String catchphrases;
    
    public PageLayout() {
        
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getGlossPlace() {
        return glossPlace;
    }

    public void setGlossPlace(String glossPlace) {
        this.glossPlace = glossPlace;
    }

    public String getRunningHeads() {
        return runningHeads;
    }

    public void setRunningHeads(String runningHeads) {
        this.runningHeads = runningHeads;
    }

    public String getChapterNumbers() {
        return chapterNumbers;
    }

    public void setChapterNumbers(String chapterNumbers) {
        this.chapterNumbers = chapterNumbers;
    }

    public String getSmallLetterHist() {
        return smallLetterHist;
    }

    public void setSmallLetterHist(String smallLetterHist) {
        this.smallLetterHist = smallLetterHist;
    }

    public String getCatchphrases() {
        return catchphrases;
    }

    public void setCatchphrases(String catchphrases) {
        this.catchphrases = catchphrases;
    }
    
}
