package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>An incipit with the added field: <code>textType</code></p>
 * 
 * <p>Associated with the &ltincipit&gt element as defined under
 * &lttitle&gt in the bible schema.</p>
 */
public class TitleIncipit extends Incipit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String textType;

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }
    
}
