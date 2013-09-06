package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ownership implements Iterable<Owner>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Owner> owners;
    
    public Ownership() {
        this.owners = new ArrayList<Owner> ();
    }
    
    public void setOwnership(ArrayList<Owner> owners) {
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
