package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecorationSummary implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum IllustrationStyle {
        
        ILLUMINATION("illumination"), GRISAILLE("grisaille"),
        UNKNOWN("unknown");
        
        final String value;
        
        private IllustrationStyle(String value) {
            this.value = value;
        }
        
        public String value() {
            return value;
        }
        
        public static IllustrationStyle getStyle(String value) {
            for (IllustrationStyle s : IllustrationStyle.values()) {
                if (s.value.equals(value)) {
                    return s;
                }
            }
            return null;
        }
        
    }
    
    private Choice basDePage;
    private Choice decoratedInitials;
    private Choice foliateBorder;
    private IllustrationStyle illStyle;
    
    private int largeIlls;
    private int number;
    
    private ArrayList<String> artistWorkshops;
    
    public DecorationSummary() {
        this.largeIlls = 0;
        this.number = 0;
        
        this.artistWorkshops = new ArrayList<String> ();
    }

    public Choice getBasDePage() {
        return basDePage;
    }

    public void setBasDePage(String basDePage) {
        this.basDePage = Choice.getChoice(basDePage);
    }

    public Choice getDecoratedInitials() {
        return decoratedInitials;
    }

    public void setDecoratedInitials(String decoratedInitials) {
        this.decoratedInitials = Choice.getChoice(decoratedInitials);
    }

    public Choice getFoliateBorder() {
        return foliateBorder;
    }

    public void setFoliateBorder(String foliateBorder) {
        this.foliateBorder = Choice.getChoice(foliateBorder);
    }

    public IllustrationStyle getIllStyle() {
        return illStyle;
    }

    public void setIllStyle(String illStyle) {
        this.illStyle = IllustrationStyle.getStyle(illStyle);
    }

    public int getLargeIlls() {
        return largeIlls;
    }

    public void setLargeIlls(int largeIlls) {
        this.largeIlls = largeIlls;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getArtistWorkshops() {
        return artistWorkshops;
    }

    public void setArtistWorkshops(ArrayList<String> artistWorkshops) {
        this.artistWorkshops = artistWorkshops;
    }
    
}
