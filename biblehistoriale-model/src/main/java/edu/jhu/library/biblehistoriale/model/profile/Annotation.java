package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * Associated with the &ltannotation&gt element in bible schema.
 */
public class Annotation implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum AnnotationType {
        MARKEDPASSAGE("marked passage"), PROSECOMMENT("prose comment"),
        OTHER("other");
        
        private String value;
        
        private AnnotationType(String value) {
            this.value = value;
        }
        
        public String value() {
            return value;
        }
        
        public static AnnotationType getType(String type) {
            for (AnnotationType t : AnnotationType.values()) {
                if (t.value.equals(type)) {
                    return t;
                }
            }
            return null;
        }
    }
    
    private String book;
    private String folio;
    private String name;
    private String date;
    private String textReferenced;
    private String text;
    
    private AnnotationType type;
    
    private int volume;
    
    public Annotation() {
        this.volume = 0;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTextReferenced() {
        return textReferenced;
    }

    public void setTextReferenced(String textReferenced) {
        this.textReferenced = textReferenced;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnnotationType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = AnnotationType.getType(type);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    
}
