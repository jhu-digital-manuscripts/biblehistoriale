package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.List;

public class ProvenPatronHistory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Production production;
    private Personalization personalization;
    
    private List<Ownership> ownerships;
    private List<Annotation> annotations;
    private List<String> provenanceNote;
    
    public ProvenPatronHistory() {
        
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

    public List<Ownership> ownerships() {
        return ownerships;
    }

    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    public List<Annotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<String> provenanceNote() {
        return provenanceNote;
    }

    public void setProvenanceNote(List<String> provenanceNote) {
        this.provenanceNote = provenanceNote;
    }
    
}
