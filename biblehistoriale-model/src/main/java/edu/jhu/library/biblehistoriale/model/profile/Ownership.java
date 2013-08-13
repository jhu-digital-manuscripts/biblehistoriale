package edu.jhu.library.biblehistoriale.model.profile;

import java.util.Iterator;
import java.util.List;

public class Ownership implements Iterable<Owner> {
    
    private List<Owner> owners;
    
    public Ownership() {
        
    }
    
    public void setOwnership(List<Owner> owners) {
        this.owners = owners;
    }
    
    public Owner owner(int index) {
        return owners.get(index);
    }
    
    public int size() {
        return owners.size();
    }
    
    @Override
    public Iterator<Owner> iterator() {
        return new Iterator<Owner>() {
            int next = 0;

            @Override
            public boolean hasNext() {
                return next < size();
            }

            @Override
            public Owner next() {
                return owner(next++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    } 
    
}
