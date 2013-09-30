package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>Associated with the &ltsignature&gt element of the bible schema.</p>
 */
public class Signature extends PersonalizationItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String text;
    
    public Signature() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
