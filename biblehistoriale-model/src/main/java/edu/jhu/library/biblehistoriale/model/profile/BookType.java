package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class BookType implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum CollectionType {
        BIBLE("Bible"), OLDTESTAMENT("Old Testament"),
        NEWTESTAMENT("New Testament"), GOSPELS("Gospels"), 
        SINGLEBOOK("Single Book"), 
        MISC("Miscellany with biblical extracts"), 
        OTHER("Other"),
        UNKNOWN("Unknown");
        
        private String type;
        
        private CollectionType(String type) {
            this.type = type;
        }
        
        public String type() {
            return type;
        }
        
        public static CollectionType getType(String type) {
            for (CollectionType t : CollectionType.values()) {
                if (t.type.equals(type)) {
                    return t;
                }
            }
            return null;
        }
    }
    
    public enum Technology {
        MANUSCRIPT("manuscript"), PRINT("print"), UNKNOWN("Unknown");
        
        private String tech;
        
        private Technology(String tech) {
            this.tech = tech;
        }
        
        public String technology() {
            return tech;
        }
        
        public static Technology getTechnology(String tech) {
            for (Technology t : Technology.values()) {
                if (t.tech.equals(tech)) {
                    return t;
                }
            }
            return null;
        }
    }
    
    private CollectionType type;
    private Technology tech;
    
    public BookType() {
        
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = CollectionType.getType(type);
    }

    public Technology getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = Technology.getTechnology(tech);
    }
    
}
