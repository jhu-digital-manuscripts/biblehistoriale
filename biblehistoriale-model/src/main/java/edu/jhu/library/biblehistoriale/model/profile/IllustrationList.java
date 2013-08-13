package edu.jhu.library.biblehistoriale.model.profile;

import java.util.Iterator;
import java.util.List;

public class IllustrationList implements Iterable<Illustration> {
    
    private DecorationSummary decorationSummary;
    private String illustrationNote;
    
    private List<Illustration> illustrations;
    
    public IllustrationList() {
        
    }
    
    public void setDecorationSummary(DecorationSummary decorationSummary) {
        this.decorationSummary = decorationSummary;
    }
    
    public void setIllustrationNote(String illustrationNote) {
        this.illustrationNote = illustrationNote;
    }
    
    public void setIllustrations(List<Illustration> illustrations) {
        this.illustrations = illustrations;
    }
    
    public DecorationSummary getDecorationSummary() {
        return decorationSummary;
    }
    
    public Illustration illustration(int index) {
        return illustrations.get(index);
    }
    
    public int size() {
        return illustrations.size();
    }
    
    public String getIllustrationNote() {
        return illustrationNote;
    }

    @Override
    public Iterator<Illustration> iterator() {
        return new Iterator<Illustration>() {
            int next = 0;

            @Override
            public boolean hasNext() {
                return next < size();
            }

            @Override
            public Illustration next() {
                return illustration(next++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
}
