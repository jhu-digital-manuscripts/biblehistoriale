package edu.jhu.library.biblehistoriale.model.profile;

public class Contributor {
    
    public enum ContributorType {
        ARTIST("artist"), SCRIBE("scribe");
        
        private String value;
        
        private ContributorType(String value) {
            this.value = value;
        }
        
        public String value() {
            return value;
        }
    }
    
    private ContributorType type;
    private String value;
    
    public Contributor() {
        
    }

    public ContributorType getType() {
        return type;
    }

    public void setType(ContributorType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
