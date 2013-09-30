package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>The physical dimensions of the bible and pages and text blocks.</p>
 * 
 * <p>Associated with the &ltdimensions&gt element in the bible schema.</p>
 */
public class Dimensions implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String page;
    private String textBlock;
    private String units;
    
    private int volume;

    public Dimensions() {
        this.volume = 0;
    }
    
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTextBlock() {
        return textBlock;
    }

    public void setTextBlock(String textBlock) {
        this.textBlock = textBlock;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    public boolean hasPageAndTextBlock() {
        return page != null && textBlock != null;
    }
    
}
