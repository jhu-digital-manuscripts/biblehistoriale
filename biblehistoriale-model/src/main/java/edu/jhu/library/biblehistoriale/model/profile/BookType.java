package edu.jhu.library.biblehistoriale.model.profile;

public class BookType {
    
    public enum CollectionType {
        BIBLE("bible"), OLDTESTAMENT("old testament"),
        NEWTESTAMENT("new testament"), GOSPELS("gospels"), 
        SINGLEBOOK("single book"), MISC("miscellany"), OTHER("other"),
        UNKNOWN("unknown");
        
        private String type;
        
        private CollectionType(String type) {
            this.type = type;
        }
        
        public String type() {
            return type;
        }
    }
    
    public enum Technology {
        MANUSCRIPT("manuscript"), PRINT("print"), UNKNOWN("unknown");
        
        private String tech;
        
        private Technology(String tech) {
            this.tech = tech;
        }
        
        public String technology() {
            return tech;
        }
    }
    
    private CollectionType type;
    private Technology tech;
    
    public BookType() {
        
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        this.type = type;
    }

    public Technology getTech() {
        return tech;
    }

    public void setTech(Technology tech) {
        this.tech = tech;
    }
    
}
