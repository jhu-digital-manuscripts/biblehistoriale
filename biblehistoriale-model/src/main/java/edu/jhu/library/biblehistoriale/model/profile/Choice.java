package edu.jhu.library.biblehistoriale.model.profile;

public enum Choice {
    Y("y"), N("n"), UNKNOWN("unknown");
    
    final String value;
    
    private Choice(String value) {
        this.value = value;
    }
    
    public String value() {
        return value;
    }
    
}
