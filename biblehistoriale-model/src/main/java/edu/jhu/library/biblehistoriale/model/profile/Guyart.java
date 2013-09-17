package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Guyart extends OtherPreface implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private boolean containsGuyartName;
    
    public Guyart() {
        this.containsGuyartName = false;
    }
    
    public boolean containsGuyartName() {
        return containsGuyartName;
    }
    
    public void setContainsGuyartName(boolean containsGuyartName) {
        this.containsGuyartName = containsGuyartName;
    }
    
    public void setIncipit(Incipit incipit) {
        setText(incipit.getText());
        setAccuracy(incipit.getAccuracy().accuracy());
    }
    
    public Incipit getIncipit() {
        Incipit inc = new Incipit();
        
        inc.setAccuracy(getAccuracy().accuracy());
        inc.setText(getText());
        
        return inc;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (containsGuyartName ? 1231 : 1237);
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
        Guyart other = (Guyart) obj;
        if (containsGuyartName != other.containsGuyartName)
            return false;
        return true;
    }
    
}
