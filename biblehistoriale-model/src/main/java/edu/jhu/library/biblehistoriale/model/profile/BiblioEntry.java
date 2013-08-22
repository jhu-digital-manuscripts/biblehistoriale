package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BiblioEntry implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String articleTitle;
    private String bookOrJournalTitle;
    private String publicationInfo;
    
    private List<String> bibAuthors;
    private List<String> articleLinks;
    
    public BiblioEntry() {
        this.bibAuthors = new ArrayList<String> ();
        this.articleLinks = new ArrayList<String> ();
    }
    
    public String getArticleTitle() {
        return articleTitle;
    }
    
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
    
    public String getBookOrJournalTitle() {
        return bookOrJournalTitle;
    }
    
    public void setBookOrJournalTitle(String bookOrJournalTitle) {
        this.bookOrJournalTitle = bookOrJournalTitle;
    }
    
    public String getPublicationInfo() {
        return publicationInfo;
    }
    
    public void setPublicationInfo(String publicationInfo) {
        this.publicationInfo = publicationInfo;
    }
    
    public List<String> bibAuthors() {
        return bibAuthors;
    }
    
    public void setBibAuthors(List<String> bibAuthors) {
        this.bibAuthors = bibAuthors;
    }
    
    public List<String> articleLinks() {
        return articleLinks;
    }
    
    public void setArticleLinks(List<String> articleLinks) {
        this.articleLinks = articleLinks;
    }
    
}
