package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class OtherPreface extends Incipit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String startPage;

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((startPage == null) ? 0 : startPage.hashCode());
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
        OtherPreface other = (OtherPreface) obj;
        if (startPage == null) {
            if (other.startPage != null)
                return false;
        } else if (!startPage.equals(other.startPage))
            return false;
        return true;
    }
    
}
