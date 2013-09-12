package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class PageLayout implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public enum Column {
        ONE("1"), TWO("2"), THREE("3"), UNKNOWN("unknown");
        
        private String col;
        
        private Column(String col) {
            this.col = col;
        }
        
        public String column() {
            return col;
        }
        
        public static Column getColumn(String col) {
            for (Column c : Column.values()) {
                if (c.col.equals(col)) {
                    return c;
                }
            }
            return null;
        }
    }
    
    public enum ConsistentChoice {
        Y("y"), N("n"), INCONSISTENT("inconsistent");
        
        private String choice;
        
        private ConsistentChoice(String choice) {
            this.choice = choice;
        }
        
        public String choice() {
            return choice;
        }
        
        public static ConsistentChoice getChoice(String choice) {
            for (ConsistentChoice c : ConsistentChoice.values()) {
                if (c.choice.equals(choice)) {
                    return c;
                }
            }
            
            return null;
        }
    }
    
    public enum Place {
        INTEXT("in-text", "body of the text"), 
        MARGINS("margins", "margins"), 
        MIXED("mixed", "body of the text and margins"), 
        UNKNOWN("unknown", "");
        
        private String place;
        private String message;
        
        private Place(String place, String message) {
            this.place = place;
            this.message = message;
        }
        
        public String message() {
            return message;
        }
        
        public String place() {
            return place;
        }
        
        public static Place getPlace(String place) {
            for (Place p : Place.values()) {
                if (p.place.equals(place)) {
                    return p;
                }
            }
            
            return null;
        }
    }
    
    private Column columns;
    private Place glossPlace;
    private Choice runningHeads;
    private ConsistentChoice chapterNumbers;
    private ConsistentChoice smallLetterHist;
    private Choice catchphrases;
    
    public PageLayout() {
        
    }

    public Column getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = Column.getColumn(columns);
    }

    public Place getGlossPlace() {
        return glossPlace;
    }

    public void setGlossPlace(String glossPlace) {
        this.glossPlace = Place.getPlace(glossPlace);
    }

    public Choice getRunningHeads() {
        return runningHeads;
    }

    public void setRunningHeads(String runningHeads) {
        this.runningHeads = Choice.getChoice(runningHeads);
    }

    public ConsistentChoice getChapterNumbers() {
        return chapterNumbers;
    }

    public void setChapterNumbers(String chapterNumbers) {
        this.chapterNumbers = ConsistentChoice.getChoice(chapterNumbers);
    }

    public ConsistentChoice getSmallLetterHist() {
        return smallLetterHist;
    }

    public void setSmallLetterHist(String smallLetterHist) {
        this.smallLetterHist = ConsistentChoice.getChoice(smallLetterHist);
    }

    public Choice getCatchphrases() {
        return catchphrases;
    }

    public void setCatchphrases(String catchphrases) {
        this.catchphrases = Choice.getChoice(catchphrases);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
