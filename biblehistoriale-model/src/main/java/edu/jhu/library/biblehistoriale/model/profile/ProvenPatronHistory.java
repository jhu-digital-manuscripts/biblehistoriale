package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProvenPatronHistory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Production production;
    private Personalization personalization;
    
    private ArrayList<Ownership> ownerships;
    private ArrayList<Annotation> annotations;
    private ArrayList<String> provenanceNote;
    
    public ProvenPatronHistory() {
        this.production = new Production();
        this.personalization = new Personalization();
        
        this.ownerships = new ArrayList<Ownership> ();
        this.annotations = new ArrayList<Annotation> ();
        this.provenanceNote = new ArrayList<String> ();
    }

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public Personalization getPersonalization() {
        return personalization;
    }

    public void setPersonalization(Personalization personalization) {
        this.personalization = personalization;
    }

    public ArrayList<Ownership> ownerships() {
        return ownerships;
    }

    public void setOwnerships(ArrayList<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    public ArrayList<Annotation> annotations() {
        return annotations;
    }

    public void setAnnotations(ArrayList<Annotation> annotations) {
        this.annotations = annotations;
    }

    public ArrayList<String> provenanceNote() {
        return provenanceNote;
    }

    public void setProvenanceNote(ArrayList<String> provenanceNote) {
        this.provenanceNote = provenanceNote;
    }
    
}
