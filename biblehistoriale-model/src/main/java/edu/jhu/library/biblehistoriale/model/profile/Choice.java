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
    
    public static Choice getChoice(String choice) {
        for (Choice c : Choice.values()) {
            if (c.value.equals(choice)) {
                return c;
            }
        }
        return null;
    }
    
}
