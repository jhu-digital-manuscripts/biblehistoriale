package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class Volumes {
    
    public enum State {
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), 
        FRAGMENT("fragment"), UNBOUND("unbound"), UNKNOWN("unknown");
        
        private String value;
        
        private State(String value) {
            this.value = value;
        }
        
        public String value() {
            return value;
        }
    }
    
    private State previousState;
    private State presentState;
    
    private List<String> volumeNotes;
    
    public Volumes() {
        
    }
    
    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        // previousState cannot be FRAGMENT
        this.previousState = previousState == State.FRAGMENT 
                ? State.UNKNOWN : previousState;
    }

    public State getPresentState() {
        return presentState;
    }

    public void setPresentState(State presentState) {
        this.presentState = presentState;
    }

    public List<String> volumeNotes() {
        return volumeNotes;
    }

    public void setVolumeNotes(List<String> volumeNotes) {
        this.volumeNotes = volumeNotes;
    }
    
}
