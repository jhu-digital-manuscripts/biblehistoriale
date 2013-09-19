package edu.jhu.library.biblehistoriale.website.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.Choice;
import edu.jhu.library.biblehistoriale.model.profile.Contributor;
import edu.jhu.library.biblehistoriale.model.profile.DecorationSummary;
import edu.jhu.library.biblehistoriale.model.profile.Dimensions;
import edu.jhu.library.biblehistoriale.model.profile.Folios;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.Materials;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;
import edu.jhu.library.biblehistoriale.model.profile.PageLayout;
import edu.jhu.library.biblehistoriale.model.profile.Personalization;
import edu.jhu.library.biblehistoriale.model.profile.PersonalizationItem;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Production;
import edu.jhu.library.biblehistoriale.model.profile.QuireStructure;
import edu.jhu.library.biblehistoriale.model.profile.SecundoFolio;
import edu.jhu.library.biblehistoriale.model.profile.Signature;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;
import edu.jhu.library.biblehistoriale.model.profile.TextualContent;
import edu.jhu.library.biblehistoriale.website.client.BibleHistorialeWebsite;
import edu.jhu.library.biblehistoriale.website.client.Messages;

/**
 * A widget that displays the contents of a Bible Historiale
 * bible.
 */
public class BibleDisplay extends Composite {
    public static final String MINOR_SECTION = "MinorSection";
    
    private final Bible bible;
    
    private final TabLayoutPanel main;
    private final SimplePanel profile_panel;
    private final SimplePanel content_panel;
    
    public final static Document doc = Document.get();
    
    private final List<HandlerRegistration> handlers;
    
    private final BibleDisplayContents cont;
    private boolean content_set_up;
    
    public BibleDisplay(final Bible bible) {
        this.handlers = new ArrayList<HandlerRegistration> ();
        
        this.bible = bible;
        this.cont = new BibleDisplayContents(doc);
        
        this.main = new TabLayoutPanel(1.5, Unit.EM);
        
        this.profile_panel = new SimplePanel();
        this.content_panel = new SimplePanel();
        
        main.add(profile_panel, "Profile View");
        main.add(content_panel, "Contents View");
        content_set_up = false;
        
        main.selectTab(0);
        displayProfileView();
        
        // TODO: resize
        main.setWidth((Window.getClientWidth() - 22) + "px");
        main.setHeight(
                (Window.getClientHeight() - BibleHistorialeWebsite.HEADER_HEIGHT - 12)+"px");
        
        initWidget(main);
        
        handlers.add(main.addSelectionHandler(new SelectionHandler<Integer> () {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem().intValue() == 1 
                        && !content_set_up) {
                    content_panel.add(cont.displayContentView(bible));
                    content_set_up = true;
                }
            }
        }));
        
        this.addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                if (!event.isAttached()) {
                    for (HandlerRegistration hr : handlers) {
                        hr.removeHandler();
                    }
                    cont.detachContent();
                }
            }
        });
    }
    
    public void resize(int width, int height) {
        main.setWidth((width - 5) + "px");
        main.setHeight((height - BibleHistorialeWebsite.HEADER_HEIGHT)
                + "px");
    }
    
    /**
     * Does this property contain a non-null, non-blank value?
     * 
     * @param val
     * @return
     */
    public static boolean isBlank(String val) {
        return (val == null || val.equals(""));
    }
    
    /**
     * Append a child Element onto a GWT UIObject,
     * such as a Panel
     * 
     * @param parent
     * @param child
     */
    public static void appendChild(UIObject parent, Element child) {
        parent.getElement().appendChild(child);
    }
    
    public static Node textNode(String text) {
        return doc.createTextNode(text);
    }
    
    public static Element paragraph(String text, String domclass) {
        Element para = doc.createPElement();
        
        if (domclass != null) {
            para.setClassName(domclass);
        }
        
        para.setInnerHTML(text);
        
        return para;
    }
    
    public static Element span(String text, String domclass) {
        Element span = doc.createSpanElement();
        
        if (domclass != null) {
            span.setClassName(domclass);
        }
        
        span.setInnerHTML(text.replaceAll("\\s+", " "));
        
        return span;
    }

    /**
     * 
     */
    private void displayProfileView() {
        StringBuilder sb = new StringBuilder();
        StringBuilder notes = new StringBuilder();
        
        FlowPanel panel = new FlowPanel();
        ScrollPanel top = new ScrollPanel();
        top.add(panel);
        
        top.setWidth("100%");
        top.setHeight("100%");
        
        //panel.setWidth((Window.getClientWidth() - 75) + "px");
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
        div.appendChild(textNode(Messages.INSTANCE.manuscriptProfile()));
        
        div = doc.createDivElement();
        div.setClassName("TitleMinor");
        
        appendChild(p, div);
        
        div.appendChild(span(Messages.INSTANCE.format(), MINOR_SECTION));
        div.appendChild(
                textNode(bible.getClassification().getBookType().getTech().technology()));
        div.appendChild(doc.createBRElement());
        div.appendChild(span(Messages.INSTANCE.prodDate(), MINOR_SECTION));
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
        
        titlediv.appendChild(textNode(Messages.INSTANCE.physChar()));
        
        div.appendChild(span(Messages.INSTANCE.vols(), MINOR_SECTION));
        div.appendChild(textNode(
                bible.getPhysChar().getVolumes().getPresentState().value()
                + " of "
                + bible.getPhysChar().getVolumes().getPreviousState().value()));
        div.appendChild(doc.createBRElement());
        div.appendChild(span(Messages.INSTANCE.dims(), MINOR_SECTION));
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
     
        Folios folios = bible.getPhysChar().getFolios();
        div.appendChild(span(Messages.INSTANCE.folios(), MINOR_SECTION));
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
        div.appendChild(span(Messages.INSTANCE.pageLayout(), MINOR_SECTION));
       
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
        
        div.appendChild(span(Messages.INSTANCE.ills(), MINOR_SECTION));
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
  
        // Expandable details
        DisclosurePanel disclose = new DisclosurePanel();
        panel.add(disclose);
        
        Label expand = new Label("[+ expand for additional details]");
        expand.setStylePrimaryName("Expander");
        
        disclose.setHeader(expand);
        SimplePanel details = new SimplePanel();
        disclose.setContent(details);
        
        div = doc.createDivElement();
        appendChild(details, div);
        
        div.appendChild(span(Messages.INSTANCE.quireStruct(), MINOR_SECTION));
        
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
        
        div.appendChild(span(Messages.INSTANCE.mats(), MINOR_SECTION));
        
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
        
        div.appendChild(span(Messages.INSTANCE.rubrics(), MINOR_SECTION));
        
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
        
        div.appendChild(span(Messages.INSTANCE.additionalNotes(), MINOR_SECTION));
        
        if (!isBlank(bible.getPhysChar().getPageLayoutNotes()))
            div.appendChild(paragraph(bible.getPhysChar().getPageLayoutNotes(), null));
        if (!isBlank(bible.getPhysChar().getPhysicaNotes()))
            div.appendChild(paragraph(bible.getPhysChar().getPhysicaNotes(), null));
        if (!isBlank(bible.getIllustrations().getIllustrationNote())) 
            div.appendChild(paragraph(bible.getIllustrations().getIllustrationNote(), null));
        
        
        // Provenance, ownership, etc
        
        p = new SimplePanel();
        panel.add(p);
        
        div = doc.createDivElement();
        div.setClassName("Section");
        
        appendChild(p, div);
        
        titlediv = doc.createDivElement();
        titlediv.setClassName("SectionHeader");
        
        div.appendChild(titlediv);
        
        titlediv.appendChild(textNode(Messages.INSTANCE.provenanceTitle()));
        
        Production prod = bible.getProvenPatronHist().getProduction();
        if (!isBlank(prod.getProdLoc())) {
            div.appendChild(span("Made in: ", MINOR_SECTION));
            div.appendChild(textNode(prod.getProdLoc()));
        }
        
        if (!isBlank(prod.getProdDate())) {
            Element span = span(Messages.INSTANCE.date(), MINOR_SECTION);
            span.addClassName("Indent");
            
            div.appendChild(span);
            div.appendChild(textNode(prod.getProdDate()));
        }
        
        // Table view of owners
        
        Element tablediv = doc.createDivElement();
        tablediv.setClassName("OwnerTable");
        div.appendChild(tablediv);
        
        Element table = doc.createTableElement();
        tablediv.appendChild(table);
        
        Element tr = doc.createTRElement();
        table.appendChild(tr);
        
        Element td = doc.createTDElement();
        td.appendChild(span(Messages.INSTANCE.ownedBy(), MINOR_SECTION));
        tr.appendChild(td);
        
        td = doc.createTDElement();
        td.appendChild(span(Messages.INSTANCE.datesAndLoc(), MINOR_SECTION));
        tr.appendChild(td);
        
        List<Ownership> ownerships = bible.getProvenPatronHist().ownerships();
        
        for (Ownership ownership : ownerships) {
            for (Owner owner : ownership) {
                tr = doc.createTRElement();
                table.appendChild(tr);
                
                td = doc.createTDElement();
                tr.appendChild(td);
                if (!isBlank(owner.getOwnerName()))
                    td.appendChild(textNode(owner.getOwnerName()));
                
                td = doc.createTDElement();
                tr.appendChild(td);
                
                sb = new StringBuilder();
                
                if (!isBlank(owner.getOwnershipDate()))
                    sb.append(owner.getOwnershipDate() + ". ");
                
                for (String st : owner.getOwnerPlace()) {
                    sb.append(st + ". ");
                }
                td.appendChild(textNode(sb.toString()));
                
            }
        }
        
        div.appendChild(span(Messages.INSTANCE.contrib(), MINOR_SECTION));
        
        sb = new StringBuilder();
        
        List<Contributor> conts = 
                bible.getProvenPatronHist().getProduction().contributors();
        
        for (Contributor cont : conts) {
            if (!isBlank(cont.getValue()))
                sb.append(cont.getValue());
            if (cont.getType() != null) 
                sb.append(" (" + cont.getType().value() + "); ");
        }
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span(Messages.INSTANCE.personalizedFeatures(), MINOR_SECTION));
        
        Personalization per = bible.getProvenPatronHist().getPersonalization();
        sb = new StringBuilder();
        notes = new StringBuilder();
        
        if (per.colophons().size() > 0)
            sb.append("Colophon" + (per.colophons().size() > 1 ? "s. " : ". "));
        if (per.patronArms().size() > 0) {
            sb.append("Heraldic device" + (per.patronArms().size() > 1 ? "s. " : ". "));
            for (PersonalizationItem it : per.patronArms()) {
                notes.append("Heraldic devices appear in vol. " + 
                        it.getVolume() + ", fol. " + it.getFolio() + ". ");
            }
        }
        if (per.signatures().size() > 0) 
            sb.append("Signature" + (per.signatures().size() > 1 ? "s. " : ". "));
        if (per.patronPortraits().size() > 0) {
            sb.append("Patron portrait" + (per.patronPortraits().size() > 1 ? "s. " : ". "));
            for (PersonalizationItem it : per.patronPortraits()) {
                notes.append("A patron portrait appears in vol. " + it.getVolume()
                        + ", fol. " + it.getFolio() + ". ");
            }
        }
        if (per.legalInscriptions().size() > 0)
            sb.append("Inscription" + (per.legalInscriptions().size() > 1 ? "s. " : ". "));
        if (per.dedications().size() > 0)
            sb.append("Dedication" + (per.dedications().size() > 1 ? "s. " : ". "));
        if (!isBlank(per.getPurchasePrice()))
            sb.append("Purchase price. ");
        if (bible.getProvenPatronHist().annotations().size() > 0)
            sb.append("Annotation");
        
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        // expandable detail
        disclose = new DisclosurePanel();
        panel.add(disclose);
        
        expand = new Label("[+ expand for full text of colophons, inscriptions," +
                " and annotations");
        expand.setStylePrimaryName("Expander");
        
        disclose.setHeader(expand);
        
        details = new SimplePanel();
        disclose.setContent(details);
        
        div = doc.createDivElement();
        appendChild(details, div);
        
        div.appendChild(span(Messages.INSTANCE.colophons(), MINOR_SECTION));
        
        sb = new StringBuilder();
        for (PersonalizationItem col : per.colophons()) {
            sb.append(col.getValue() + " ");
            
            if (!isBlank(col.getFolio()) || col.getVolume() > 0)
                sb.append("(" + 
                        (col.getVolume() >= 0 ? "vol. " + col.getVolume() + ", " : "")
                        + (!isBlank(col.getFolio()) ? col.getFolio() : "")
                        + ") ");
        }
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span(Messages.INSTANCE.signatureTitle(), MINOR_SECTION));
        
        Element ol = doc.createOLElement();
        div.appendChild(ol);
        
        Element li = null;
        
        for (Signature sig : per.signatures()) {
            sb = new StringBuilder();
            li = doc.createLIElement();
            
            if (!isBlank(sig.getText()))
                sb.append("\"" + sig.getText() + "\" ");
            if (sig.getVolume() > 0) 
                sb.append("vol. " + sig.getVolume() + " ");
            if (!isBlank(sig.getFolio()))
                sb.append(sig.getFolio() + ", ");
            if (!isBlank(sig.getName()))
                sb.append(sig.getName());
            
            li.appendChild(textNode(sb.toString()));
            ol.appendChild(li);
        }
        
        for (String st : per.dedications()) {
            li = doc.createLIElement();
            if (!isBlank(st)) {
                li.appendChild(textNode(st));
                ol.appendChild(li);
            }
        }
        
        for (PersonalizationItem it : per.legalInscriptions()) {
            sb = new StringBuilder();
            li = doc.createLIElement();
            
            if (!isBlank(it.getValue()))
                sb.append("\"" + it.getValue() + "\" ");
            if (it.getVolume() > 0)
                sb.append("vol. " + it.getVolume() + " ");
            if (!isBlank(it.getFolio()))
                sb.append(it.getFolio());
            
            li.appendChild(textNode(sb.toString()));
            ol.appendChild(li);
        }
        
        
        // Expandable detail
        disclose = new DisclosurePanel();
        panel.add(disclose);
        
        expand = new Label("[+ expand for additional details]");
        expand.setStylePrimaryName("Expander");
        disclose.setHeader(expand);
        
        details = new SimplePanel();
        disclose.setContent(details);
        
        div = doc.createDivElement();
        appendChild(details, div);
        
        div.appendChild(span(Messages.INSTANCE.notes(), MINOR_SECTION));
        
        div.appendChild(paragraph(notes.toString(), null));
        
        if (!isBlank(prod.getProdNotes()))
            div.appendChild(paragraph(prod.getProdNotes(), null));
        
        notes = new StringBuilder();
        for (String str : bible.getProvenPatronHist().provenanceNote()) {
            notes.append(str);
        }
        
        if (bible.getProvenPatronHist().provenanceNote().size() > 0)
            div.appendChild(paragraph(notes.toString(), null));
        
        
        // Classification and contents summary
        
        p = new SimplePanel();
        panel.add(p);
        
        div = doc.createDivElement();
        div.setClassName("Section");
        appendChild(p, div);
        
        titlediv = doc.createDivElement();
        titlediv.setClassName("SectionHeader");
        titlediv.appendChild(textNode(Messages.INSTANCE.classificationTitle()));
        div.appendChild(titlediv);
        
        div.appendChild(span(Messages.INSTANCE.catalogClassif(), MINOR_SECTION));
        
        sb = new StringBuilder();

        CatalogerClassification classif = bible.getClassification().getClassification();
        Berger berg = classif.getBergerClass();
        Sneddon sned = classif.getSneddonClass();
        
        if (berg.getCategory() != null && berg.getBhcSubtype() != null) 
            sb.append("Samuel Berger classifies this manuscript as a "
                    + berg.getCategory().category() + ", subtype "
                    + berg.getBhcSubtype().subtype() + ". ");
        if (sned.getCategory() != null)
            sb.append("In Clive Sneddon's classification, it is "
                    + sned.getCategory().toString() + ", ");
        if (sned.getSub1() != null)
            sb.append(sned.getSub1());
        if (sned.getSub2() != null)
            sb.append(sned.getSub2());
        if (sned.getSub3() != null)
            sb.append(sned.getSub3());
        if (sned.getSub1() != null || sned.getSub2() != null || sned.getSub3() != null)
            sb.append(" subtype, ");
        if (!isBlank(sned.getSiglum()))
            sb.append("siglum " + sned.getSiglum() + ", ");
        if (!isBlank(sned.getEntry()))
            sb.append("entry " + sned.getEntry());
        sb.append(". ");
        
        if (!isBlank(classif.getClassificationNote()))
            sb.append(classif.getClassificationNote() 
                    + (classif.getClassificationNote().trim().endsWith(".")
                            ? " " : ". "));
        
        if (!isBlank(classif.getFournieNumber())) {
            sb.append((isBlank(bible.getShortName()) 
                    ? "This manuscript" : bible.getShortName())
                    + " is entry number(s) ");
            div.appendChild(textNode(sb.toString()));
            
            if (!isBlank(classif.getFournieLink())) {
                anch = doc.createAnchorElement();
                anch.setHref(classif.getFournieLink());
                anch.setInnerHTML(classif.getFournieNumber() + " in Eleanor Fournie's " +
                		"online catalog.");
                div.appendChild(anch);
            } else {
                div.appendChild(textNode(classif.getFournieNumber() + 
                        " in Eleanor Fournie's online catalog."));
            }
            
        } else {
            div.appendChild(textNode(sb.toString()));
        }
        
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span(Messages.INSTANCE.secundo(), MINOR_SECTION));
        
        sb = new StringBuilder();
        for (SecundoFolio sec : classif.secundoFolios()) {
            if (sec.getVolume() > 0)
                sb.append("Vol. " + sec.getVolume() 
                        + (!isBlank(sec.getValue()) ? ": " : " "));
            if (!isBlank(sec.getValue())) 
                sb.append(sec.getValue() + ". ");
        }
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        div.appendChild(span(Messages.INSTANCE.tableOfContents(), MINOR_SECTION));
        
        TextualContent textcont = bible.getTextualContent();
        
        sb = new StringBuilder();
        notes = new StringBuilder();
        int tables_of_contents = 0;
        
        for (PrefatoryMatter mat : textcont.prefatoryMatters()) {
            if (!isBlank(mat.getMasterTableOfContents().getText())) {
                tables_of_contents++;
                
                if (mat.getVolume() > 0) {
                    sb.append("Included in vol. " + mat.getVolume() + " ");
                    notes.append("Vol. " + mat.getVolume() + " ");
                }
                
                notes.append("table: " + mat.getMasterTableOfContents().getText()
                        + "</br>");
                
                if (mat.getMasterTableOfContents().getTableDetail() != null)
                    sb.append("with " + 
                            mat.getMasterTableOfContents().getTableDetail().detail()
                            + " level of detail. ");
                if (mat.getMasterTableOfContents().matchesContents())
                    sb.append(" Matches (or approximately matches) content. ");
                else
                    sb.append(" Does not match content. ");
            }
        }
        div.appendChild(textNode(sb.toString()));
        
        
        disclose = new DisclosurePanel();
        if (tables_of_contents > 0) {
            panel.add(disclose);
        }
        
        expand = new Label("[+ expand for table" 
                + (tables_of_contents > 1 ? "s" : "") +" of contents]");
        expand.setStylePrimaryName("Expander");
        disclose.setHeader(expand);
        
        details = new SimplePanel();
        disclose.setContent(details);
        
        div = doc.createDivElement();
        appendChild(details, div);
        
        div.setInnerHTML(notes.toString());
        
        p = new SimplePanel();
        panel.add(p);
        
        HTML toContentsView = new HTML(Messages.INSTANCE.seeContentsView());
        toContentsView.setStylePrimaryName("Clickable");
        
        handlers.add(toContentsView.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                main.selectTab(1);
            }
        }));
        
        p.add(toContentsView);
        
        
        // Bibliography stuffs
        
        p = new SimplePanel();
        panel.add(p);
        
        div = doc.createDivElement();
        div.setClassName("Section");
        appendChild(p, div);
        
        titlediv = doc.createDivElement();
        titlediv.setClassName("SectionHeader");
        titlediv.setInnerHTML(Messages.INSTANCE.biblioTitle());
        div.appendChild(titlediv);
        
        Element ul = doc.createULElement();
        if (bible.getBibliography().size() > 0)
            div.appendChild(ul);
        
        for (BiblioEntry bib : bible.getBibliography()) {
            li = doc.createLIElement();
            
            sb = new StringBuilder();
            for (int i = 0; i < bib.bibAuthors().size(); i++) {
                sb.append(bib.bibAuthors().get(i));
                if (i != bib.bibAuthors().size() - 1)
                    sb.append(", ");
            }
            li.appendChild(textNode(sb.toString()));
            
            if (!isBlank(bib.getArticleTitle()))
                li.appendChild(span(bib.getArticleTitle(), "ArticleTitle"));
            
            if (!isBlank(bib.getBookOrJournalTitle()))
                li.appendChild(textNode(bib.getBookOrJournalTitle()));
            
            if (!isBlank(bib.getPublicationInfo()))
                li.appendChild(textNode(bib.getPublicationInfo()));
            
            for (String st : bib.articleLinks()) {
                anch = doc.createAnchorElement();
                anch.setHref(st);
                anch.setInnerHTML(st);
                li.appendChild(anch);
                li.appendChild(textNode("; "));
            }
            
            ul.appendChild(li);
        } 
    }   
}
