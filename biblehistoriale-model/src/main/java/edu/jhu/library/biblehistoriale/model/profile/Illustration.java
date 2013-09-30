package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>A single illustration in the bible. An illustration has a book with 
 * which it is associated, a folio that it lives on, the volume that it can
 *  be found in, and keywords describing it. It can also include a URL to 
 *  an image of the illustration.</p>
 * 
 * <p>Associated with the &ltillustration&gt element in the bible schema.</p>
 */
public class Illustration implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String keywords;
    private String url;
    private String book;
    private String folio;
    
    private int number;
    private int volume;
    
    public Illustration() {
        this.number = 0;
        this.volume = 0;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getBook() {
        return book;
    }
    
    public void setBook(String book) {
        this.book = book;
    }
    
    public String getFolio() {
        return folio;
    }
    
    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
}
