package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>Information about the number of folios. Iterable over the individual
 * volumes of the bible.</p>
 * 
 * <p>Associated with the &ltfolios&gt element in the bible schema.</p>
 * 
 * @see IndVolume
 */
public class Folios implements Iterable<IndVolume>, Serializable {
    
    private static final long serialVersionUID = 1L;

    private int totalFolios;
    
    private ArrayList<IndVolume> indVolumes;
    
    public Folios() {
        this.totalFolios = 0;
        this.indVolumes = new ArrayList<IndVolume> ();
    }
    
    public int size() {
        return indVolumes.size();
    }
    
    public int getTotalFolios() {
        return totalFolios;
    }

    public void setTotalFolios(int totalFolios) {
        this.totalFolios = totalFolios;
    }

    public IndVolume indVolume(int index) {
        return indVolumes.get(index);
    }

    public void setIndVolumes(ArrayList<IndVolume> indVolumes) {
        this.indVolumes = indVolumes;
    }

    @Override
    public Iterator<IndVolume> iterator() {
        return new Iterator<IndVolume>() {
            int next = 0;

            @Override
            public boolean hasNext() {
                return next < size();
            }

            @Override
            public IndVolume next() {
                return indVolume(next++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
}
