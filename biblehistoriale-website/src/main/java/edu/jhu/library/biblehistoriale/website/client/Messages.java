package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface Messages extends Constants {
    public static final Messages INSTANCE = GWT.create(Messages.class);
    
    public String addField();
    
    public String search();
    
    public String info();
    
    public String browse();
    
    public String contactUs();
    
    public String advancedSearch();
    
    public String loading();
    
    public String manuscriptProfile();
    
    public String format();
    
    public String prodDate();
    
    public String physChar();
    
    public String vols();
    
    public String vol();
    
    public String dims();
    
    public String folios();
    
    public String pageLayout();
    
    public String catchphrases();
    
    public String ills();
    
    public String quireStruct();
    
    public String mats();
    
    public String rubrics();
    
    public String additionalNotes();
    
    public String provenanceTitle();
    
    public String date();
    
    public String ownedBy();
    
    public String datesAndLoc();
    
    public String contrib();
    
    public String personalizedFeatures();
    
    public String colophons();
    
    public String signatureTitle();
    
    public String notes();
    
    public String classificationTitle();
    
    public String catalogClassif();
    
    public String secundo();
    
    public String tableOfContents();
    
    public String seeContentsView();
    
    public String biblioTitle();
    
    
    public String remarks();
    
    public String overview();

}
