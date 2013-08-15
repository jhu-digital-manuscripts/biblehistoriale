package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.List;

public class Volumes implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum State {
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), 
        FRAGMENT("fragment"), UNBOUND("unbound"), UNKNOWN("unknown");
        
        private String value;
        
        private State(String value) {
            this.value = value;
        }
        
        public static State getState(String str) {
            for (State state : State.values()) {
                if (state.value.equals(str)) {
                    return state;
                }
            }
            
            return null;
        }
        
        public String value() {
            return value;
        }
    }
    
    private State previousState;
    private State presentState;
    
    private String volumeNotes;
    
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

    public String volumeNotes() {
        return volumeNotes;
    }

    public void setVolumeNotes(String volumeNotes) {
        this.volumeNotes = volumeNotes;
    }
    
}
