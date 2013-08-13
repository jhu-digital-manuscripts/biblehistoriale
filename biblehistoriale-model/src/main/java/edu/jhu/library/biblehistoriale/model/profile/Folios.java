package edu.jhu.library.biblehistoriale.model.profile;

import java.util.Iterator;
import java.util.List;

public class Folios implements Iterable<IndVolume> {
    
    private int totalFolios;
    
    private List<IndVolume> indVolumes;
    
    public Folios() {
        this.totalFolios = 0;
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

    public void setIndVolumes(List<IndVolume> indVolumes) {
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
