package edu.jhu.library.biblehistoriale.model.profile;

import java.util.Iterator;
import java.util.List;

/**
 * List of bibliography entries
 */
public class Bibliography implements Iterable<BiblioEntry> {

    private List<BiblioEntry> biblioEntries;
    
    public int size() {
        return biblioEntries.size();
    }
    
    public BiblioEntry biblioEntry(int index) {
        return biblioEntries.get(index);
    }

    public void setBiblioEntries(List<BiblioEntry> biblioEntries) {
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
