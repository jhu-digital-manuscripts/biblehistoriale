package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;

public class ComestorLetter extends OtherPreface implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Incipit> incipits;
    
    public ComestorLetter() {
        this.incipits = new ArrayList<Incipit> ();
    }
    
    @Override
    public void setText(String incipit) {
        throw new UnsupportedOperationException(
                "Cannot set a single piece of text for ComestorLetter.");
    }
    
    /**
     * Returns the all entries in the list of incipits
     */
    @Override
    public String getText() {
        return incipits.toString();
    }
    
    public ArrayList<Incipit> incipits() {
        return incipits;
    }
    
    public void setIncipits(ArrayList<Incipit> incipits) {
        this.incipits = incipits;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((incipits == null) ? 0 : incipits.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComestorLetter other = (ComestorLetter) obj;
        if (incipits == null) {
            if (other.incipits != null)
                return false;
        } else if (!incipits.equals(other.incipits))
            return false;
        return true;
    }
    
}
