package edu.jhu.library.biblehistoriale.website.client.widgets;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.Choice;
import edu.jhu.library.biblehistoriale.model.profile.DecorationSummary;
import edu.jhu.library.biblehistoriale.model.profile.Dimensions;
import edu.jhu.library.biblehistoriale.model.profile.Folios;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.Materials;
import edu.jhu.library.biblehistoriale.model.profile.PageLayout;
import edu.jhu.library.biblehistoriale.model.profile.QuireStructure;
import edu.jhu.library.biblehistoriale.website.client.BibleHistorialeWebsite;

/**
 * A widget that displays the contents of a Bible Historiale
 * bible.
 */
public class BibleDisplay extends Composite {
    
    private final Bible bible;
    
    private final TabLayoutPanel main;
    private final SimplePanel profile_panel;
    private final SimplePanel content_panel;
    
    private final Document doc;
    
    private final HandlerRegistration handler;
    private boolean content_set_up;
    
    public BibleDisplay(Bible bible) {
        this.bible = bible;
        this.doc = Document.get();
        
        this.main = new TabLayoutPanel(1.5, Unit.EM);
        
        this.profile_panel = new SimplePanel();
        this.content_panel = new SimplePanel();
        
        main.add(profile_panel, "Profile View");
        main.add(content_panel, "Contents View");
        content_set_up = false;
        
        main.selectTab(0);
        displayProfileView();
        
        // TODO: resize
        main.setWidth((Window.getClientWidth() - 40) + "px");
        main.setHeight(
                (Window.getClientHeight() - BibleHistorialeWebsite.HEADER_HEIGHT) + "px");
        
        initWidget(main);
        
        this.handler = main.addSelectionHandler(new SelectionHandler<Integer> () {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem().intValue() == 1 
                        && !content_set_up) {
                    displayContentView();
                }
            }
        });
        
        this.addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                if (!event.isAttached()) {
                    handler.removeHandler();
                }
            }
        });
    }
    
    private boolean isBlank(String val) {
        return (val == null || val.equals(""));
    }
    
    private void appendChild(UIObject parent, Element child) {
        parent.getElement().appendChild(child);
    }
    
    private void appendChild(UIObject parent, Node child) {
        parent.getElement().appendChild(child);
    }
    
    private Node textNode(String text) {
        return doc.createTextNode(text);
    }
    
    private Element paragraph(String text, String domclass) {
        Element para = doc.createPElement();
        
        if (domclass != null) {
            para.setClassName(domclass);
        }
        
        para.setInnerHTML(text);
        
        return para;
    }
    
    private Element span(String text, String domclass) {
        Element span = doc.createSpanElement();
        
        if (domclass != null) {
            span.setClassName(domclass);
        }
        
        span.setInnerHTML(text.replaceAll("\\s+", " "));
        
        return span;
    }
    
    private void displayContentView() {
        
        content_set_up = true;
    }

    private void displayProfileView() {
        StringBuilder sb = new StringBuilder();
        
        FlowPanel panel = new FlowPanel();
        ScrollPanel top = new ScrollPanel();
        top.add(panel);
        
        top.setWidth("100%");
        top.setHeight("100%");
        
        panel.setWidth((Window.getClientWidth() - 75) + "px");
        profile_panel.add(top);
        
        // Title
        
        SimplePanel p = new SimplePanel();
        panel.add(p);
        
        Element div = doc.createDivElement();
        div.setClassName("Title");
        
        appendChild(p, div);
        
        if (!isBlank(bible.getClassification().getCurrentCity()))
            div.appendChild(textNode(bible.getClassification().getCurrentCity() + ", "));
        
        AnchorElement anch = doc.createAnchorElement();
        
        String link_text = bible.getClassification().getCurrentRepository();
        String link = bible.getClassification().getRepositoryLink();
        
        if (!isBlank(link)) {
            anch.setHref(link);
            anch.appendChild(doc.createTextNode(link_text));
            div.appendChild(anch);
        } else {
            div.appendChild(doc.createTextNode(link_text));
        }
        
        if (!isBlank(bible.getClassification().getCurrentShelfmark()))
                div.appendChild(textNode(" " + 
                        bible.getClassification().getCurrentShelfmark()));
        div.appendChild(doc.createBRElement());
        div.appendChild(textNode("Manuscript Profile"));
        
        div = doc.createDivElement();
        div.setClassName("TitleMinor");
        
        appendChild(p, div);
        
        div.appendChild(span("Format: ", "MinorSection"));
        div.appendChild(
                textNode(bible.getClassification().getBookType().getTech().technology()));
        div.appendChild(doc.createBRElement());
        div.appendChild(span("Date of Production: ", "MinorSection"));
        if (!isBlank(bible.getProvenPatronHist().getProduction().getProdDate()))
            div.appendChild(
                    textNode(bible.getProvenPatronHist().getProduction().getProdDate()));
        div.appendChild(doc.createBRElement());
        
        anch = doc.createAnchorElement();
        link = bible.getScannedMss();
        
        if (link != null && !link.equals("")) {
            anch.setHref(link);
            anch.setInnerText(link);
            
            Element aspan = span("[", "MsLink");
            aspan.appendChild(anch);
            aspan.appendChild(textNode("]"));
            
            div.appendChild(aspan);
        }
      
        
        // Physical Characteristics
        
        div = doc.createDivElement();
        div.setClassName("Section");
        
        appendChild(p, div);
        
        Element titlediv = doc.createDivElement();
        titlediv.setClassName("SectionHeader");
        
        div.appendChild(titlediv);
        
        titlediv.appendChild(textNode("Physical Characteristics"));
        
        Element contdiv = doc.createDivElement();
        //contdiv.addClassName("");
        div.appendChild(contdiv);
        
        div.appendChild(span("Volumes: ", "MinorSection"));
        div.appendChild(textNode(
                bible.getPhysChar().getVolumes().getPresentState().value()
                + " of "
                + bible.getPhysChar().getVolumes().getPreviousState().value()));
        div.appendChild(doc.createBRElement());
        div.appendChild(span("Dimensions (page): ", "MinorSection"));Window.alert("Displaying dimensions...");
        for (Dimensions dim : bible.getPhysChar().dimensions()) {
            sb = new StringBuilder();
            
            sb.append("vol. " + dim.getVolume() + ": ");
            if (!isBlank(dim.getPage()))
                sb.append(dim.getPage() + " ");
            if (!isBlank(dim.getUnits())) 
                sb.append(dim.getUnits() + "; ");
            
            div.appendChild(textNode(sb.toString()));
        }
        div.appendChild(doc.createBRElement());
Window.alert("Displaying folios...");        
        Folios folios = bible.getPhysChar().getFolios();
        div.appendChild(span("Folios: ", "MinorSection"));
        div.appendChild(textNode(""+ folios.getTotalFolios() + " ("));
        for (int i = 0; i < folios.size(); i++) {
            IndVolume ind = folios.indVolume(i);
            
            if (!isBlank(ind.getValue()))
                div.appendChild(textNode(ind.getValue()
                        + " in vol. " + ind.getVolume()));
            
            if (i != folios.size() - 1) {
                div.appendChild(textNode(" and "));
            }
        }
        div.appendChild(textNode(")"));
        
        div.appendChild(doc.createBRElement());
        div.appendChild(span("Page Layout: ", "MinorSection"));
Window.alert("Displaying page layout...");        
        PageLayout pl = bible.getPhysChar().getPageLayout();
        sb = new StringBuilder(pl.getColumns().column() + " columns");
        
        switch (pl.getRunningHeads()) {
        case Y: 
            sb.append(", with running heads");
            break;
        case N: 
            sb.append(", with no running heads");
            break;
        }
        
        switch (pl.getGlossPlace()) {
        case UNKNOWN:
            sb.append(". ");
            break;
        default:
            sb.append(", glosses occuring in the " 
                    + pl.getGlossPlace().message() + ". ");
            break;
        }
        
        if (pl.getChapterNumbers() != null) {
            switch (pl.getChapterNumbers()) {
            case Y:
                sb.append(" Chapter numbers present. ");
                break;
            case INCONSISTENT:
                sb.append(" Chapter numbers inconsistent. ");
                break;
            }
        }
        
        if (pl.getSmallLetterHist() != null) {
            switch (pl.getSmallLetterHist()) {
            case Y:
                sb.append("Decorated initials present");
                break;
            case INCONSISTENT:
                sb.append("Decorated initials inconsistent. ");
                break;
            }
        }
        
        if (pl.getCatchphrases() != null) {
            switch (pl.getCatchphrases()) {
            case Y:
                sb.append("Catchphrases present. ");
                break;
            }
        }
        
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span("Illustration: ", "MinorSection"));
        IllustrationList ills = bible.getIllustrations();
        
        sb = new StringBuilder();
        
        if (ills != null) {
            DecorationSummary sum = ills.getDecorationSummary();
            sb.append(ills.size() + " illustrations (" 
                    + sum.getIllStyle().value() + ")");
            if (sum.getLargeIlls() > 0) {
                sb.append(", including " + sum.getLargeIlls()
                        + " large presentation scenes. ");
            }
            
            if (sum.getFoliateBorder() == Choice.Y)
                sb.append("Includes foliate borders. ");
            if (sum.getBasDePage() == Choice.Y)
                sb.append("Includes bas-de-page scenes. ");
            if (sum.getDecoratedInitials() == Choice.Y)
                sb.append("Includes decorated initials. ");
            
            sb.append("See contents view for full list of illustrations," +
            		" broken down by book.");
            
            div.appendChild(textNode(sb.toString()));
        }
Window.alert("Displaying expandable details...");        
        // Expandable details
        DisclosurePanel disclose = new DisclosurePanel();
        panel.add(disclose);
        
        disclose.setHeader(new Label("[+ expand for additional details]"));
        SimplePanel details = new SimplePanel();
        disclose.setContent(details);
        
        div = doc.createDivElement();
        appendChild(details, div);
        
        div.appendChild(span("Quire structure: ", "MinorSection"));
        
        sb = new StringBuilder();
        for (QuireStructure qs : bible.getPhysChar().quireStructs()) {
            sb.append("Volume " + qs.getVolume()
                    + " has " + qs.quireTotal() + " quires, most of which contain "
                    + qs.typicalQuires().get(0) + " folios: "
                    + qs.fullQuireStructs().get(0) + ". ");
            
            if (qs.quireNotes().size() > 0)
                sb.append(qs.quireNotes().get(0) + ". ");
        }
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span("Materials: ", "MinorSection"));
        
        Materials mats = bible.getPhysChar().getMaterials();
        sb = new StringBuilder();
        
        sb.append(mats.getSupport().support() + ". Binding ");
        if (mats.getBindDateStartYear() != 0 
                && mats.getBindDateEndYear() != 0)
            sb.append("(" + mats.getBindDateStartYear() + " - "
                    + mats.getBindDateEndYear() + ") ");
            
        if (!isBlank(mats.getBindMaterial()))
            sb.append("made of " + mats.getBindMaterial() + ". ");
        
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span("Rubrics and underlining: ", "MinorSection"));
        
        sb = new StringBuilder();
        
        if (!isBlank(bible.getPhysChar().getRubricNotes()))
            sb.append(bible.getPhysChar().getRubricNotes() + 
                    (bible.getPhysChar().getRubricNotes().trim().endsWith(".")
                    ? "" : "."));
        if (bible.getPhysChar().glossHeadings().size() != 0) {
            sb.append(" Gloss headings include ");
            for (String st : bible.getPhysChar().glossHeadings()) {
                sb.append(st + " ");
            }
            sb.append(". ");
        }
        
        if (bible.getPhysChar().underlinings().size() != 0) {
            for (String st : bible.getPhysChar().underlinings()) {
                sb.append(st + ". ");
            }
        }
        
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span("Additional Notes: ", "MinorSection"));
        
        if (!isBlank(bible.getPhysChar().getPageLayoutNotes()))
            div.appendChild(paragraph(bible.getPhysChar().getPageLayoutNotes(), null));
        if (!isBlank(bible.getPhysChar().getPhysicaNotes()))
            div.appendChild(paragraph(bible.getPhysChar().getPhysicaNotes(), null));
        if (!isBlank(bible.getIllustrations().getIllustrationNote())) 
            div.appendChild(paragraph(bible.getIllustrations().getIllustrationNote(), null));
        
        
    }
    
    
    
}
