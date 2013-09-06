package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

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
