package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BibleBooks implements Iterable<Title>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int volume;
    private List<Title> titles;
    
    public BibleBooks() {
        this.volume = 0;
        this.titles = new ArrayList<Title> ();
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    public int size() {
        return titles.size();
    }
    
    public Title title(int index) {
        return titles.get(index);
    }
    
    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    @Override
    public Iterator<Title> iterator() {
        return new Iterator<Title> () {
            int next = 0;
            
            @Override
            public boolean hasNext() {
                return next < size();
            }

            @Override
            public Title next() {
                return title(next++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
    }
    
}
