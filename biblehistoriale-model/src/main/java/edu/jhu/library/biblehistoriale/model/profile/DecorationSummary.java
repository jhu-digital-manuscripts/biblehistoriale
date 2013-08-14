package edu.jhu.library.biblehistoriale.model.profile;

import java.util.List;

public class DecorationSummary {
    
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
        
    }
    
    private Choice basDePage;
    private Choice decoratedInitials;
    private Choice foliateBorder;
    private IllustrationStyle illStyle;
    
    private int largeIlls;
    private int number;
    
    private List<String> artistWorkshops;
    
    public DecorationSummary() {
        this.largeIlls = 0;
        this.number = 0;
    }

    public Choice getBasDePage() {
        return basDePage;
    }

    public void setBasDePage(Choice basDePage) {
        this.basDePage = basDePage;
    }

    public Choice getDecoratedInitials() {
        return decoratedInitials;
    }

    public void setDecoratedInitials(Choice decoratedInitials) {
        this.decoratedInitials = decoratedInitials;
    }

    public Choice getFoliateBorder() {
        return foliateBorder;
    }

    public void setFoliateBorder(Choice foliateBorder) {
        this.foliateBorder = foliateBorder;
    }

    public IllustrationStyle getIllStyle() {
        return illStyle;
    }

    public void setIllStyle(IllustrationStyle illStyle) {
        this.illStyle = illStyle;
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

    public List<String> getArtistWorkshops() {
        return artistWorkshops;
    }

    public void setArtistWorkshops(List<String> artistWorkshops) {
        this.artistWorkshops = artistWorkshops;
    }
    
}
