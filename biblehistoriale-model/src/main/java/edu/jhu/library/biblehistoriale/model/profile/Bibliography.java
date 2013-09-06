package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * List of bibliography entries
 */
public class Bibliography implements Iterable<BiblioEntry>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<BiblioEntry> biblioEntries;
    
    public Bibliography() {
        this.biblioEntries = new ArrayList<BiblioEntry> ();
    }
    
    public int size() {
        return biblioEntries.size();
    }
    
    public BiblioEntry biblioEntry(int index) {
        return biblioEntries.get(index);
    }

    public void setBiblioEntries(ArrayList<BiblioEntry> biblioEntries) {
        this.biblioEntries = biblioEntries;
    }

    @Override
    public Iterator<BiblioEntry> iterator() {
        return new Iterator<BiblioEntry>() {
            int next = 0;

            @Override
            public boolean hasNext() {
                return next < size();
            }

            @Override
            public BiblioEntry next() {
                return biblioEntry(next++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    
    
}
