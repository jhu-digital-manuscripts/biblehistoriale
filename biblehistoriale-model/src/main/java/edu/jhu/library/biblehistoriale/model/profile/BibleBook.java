package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class BibleBook {
    
    private int volume;
    private List<Title> titles;
    
    public BibleBook() {
        this.volume = 0;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    public List<Title> titles() {
        return titles;
    }
    
    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
    
}
