package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class Contributor implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum ContributorType {
        ARTIST("artist"), SCRIBE("scribe"), PRINTER("printer"), EDITOR("editor");
        
        private String value;
        
        private ContributorType(String value) {
            this.value = value;
        }
        
        public String value() {
            return value;
        }
        
        public static ContributorType getContributor(String value) {
            for (ContributorType c : ContributorType.values()) {
                if (c.value.equals(value)) {
                    return c;
                }
            }
            return null;
        }
    }
    
    private ContributorType type;
    private String value;
    
    public Contributor() {
        
    }

    public ContributorType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ContributorType.getContributor(type);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
