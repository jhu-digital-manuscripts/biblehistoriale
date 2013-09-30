package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>Base class of some of the prefatory matters. Holds the start page, one
 * incipit, and the URL to any transcriptions of a preface.</p>
 * 
 * <p>Directly associated with the &ltotherPreface&gt element in the bible schema.</p>
 * 
 * @see Incipit
 */
public class OtherPreface extends Incipit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String startPage;
    private String transcription_url;

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getTranscriptionUrl() {
        return transcription_url;
    }

    public void setTranscriptionUrl(String transcription_url) {
        this.transcription_url = transcription_url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((startPage == null) ? 0 : startPage.hashCode());
        result = prime
                * result
                + ((transcription_url == null) ? 0 : transcription_url
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        OtherPreface other = (OtherPreface) obj;
        if (startPage == null) {
            if (other.startPage != null)
                return false;
        } else if (!startPage.equals(other.startPage))
            return false;
        if (transcription_url == null) {
            if (other.transcription_url != null)
                return false;
        } else if (!transcription_url.equals(other.transcription_url))
            return false;
        return true;
    }
    
}
