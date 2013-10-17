package edu.jhu.library.biblehistoriale.website.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.profile.Annotation;
import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.Choice;
import edu.jhu.library.biblehistoriale.model.profile.Contributor;
import edu.jhu.library.biblehistoriale.model.profile.DecorationSummary;
import edu.jhu.library.biblehistoriale.model.profile.Dimensions;
import edu.jhu.library.biblehistoriale.model.profile.Folios;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
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
    
    interface ImagesCallback {
        void onImagesRecieved(Map<String, Image> imgs);
    }
    
    public static final String MINOR_SECTION = "MinorSection";
    private static final SimpleHtmlSanitizer sanitizer;
    private static final Image loading;
    private static final Image none;
    
    private static Map<String, Map<String, Image>> img_cache;
    private static final int CACHE_MAX_SIZE;
    
    static {
        sanitizer = SimpleHtmlSanitizer.getInstance();
        
        loading = new Image("images/loading.gif");
        loading.addStyleName("ProfileImage");
        none = new Image("images/placeholder.png");
        none.addStyleName("ProfileImage");
        
        img_cache = new HashMap<String, Map<String, Image>> ();
        CACHE_MAX_SIZE = 10;
    }
    
    private final Bible bible;
    
    private final TabLayoutPanel main;
    private final SimplePanel profile_panel;
    private final SimplePanel content_panel;
    
    public final static Document doc = Document.get();
    
    private final List<HandlerRegistration> handlers;
    
    private final BibleDisplayContents cont;
    private int pending_images;
    
    private Element img_container;
    
    public BibleDisplay(final Bible bible) {
        this.handlers = new ArrayList<HandlerRegistration> ();
        
        this.bible = bible;
        this.cont = new BibleDisplayContents(doc);
        
        this.main = new TabLayoutPanel(2, Unit.EM);
        main.setStylePrimaryName("ProfileTabs");
        
        this.profile_panel = new SimplePanel();
        this.content_panel = new SimplePanel();
        this.img_container = doc.createDivElement();
        
        main.add(profile_panel, "Profile View");
        main.add(content_panel, "Contents View");
        
        main.selectTab(0);
        
        // Display profile information
        try {
            displayProfileView();
        } catch (Exception e) {
            profile_panel.add(new Label(Messages.INSTANCE.failedToDisplayProfile()));
        }
        
        try {
            content_panel.add(cont.displayContentView(bible));
        } catch (Exception e) {
            content_panel.add(
                    new Label(Messages.INSTANCE.failedToDisplayContent()));
        }
        
        main.setWidth((Window.getClientWidth() - 22) + "px");
        main.setHeight(
                (Window.getClientHeight() - BibleHistorialeWebsite.HEADER_HEIGHT - 12)+"px");

        initWidget(main);
        
        // Find and display images.
        if (img_cache.containsKey(bible.getId())) {
            Map<String, Image> imgs = img_cache.get(bible.getId());
            
            cont.setImages(imgs);
            displayProfileImage(imgs);
            
        } else {
            findResolvableImages(new ImagesCallback() {
                @Override
                public void onImagesRecieved(Map<String, Image> img_map) {
                    
                    if (img_cache.size() > CACHE_MAX_SIZE) {
                        img_cache.clear();
                    }
                    
                    img_cache.put(bible.getId(), img_map);
                    
                    cont.setImages(img_map);
                    displayProfileImage(img_map);
                }
            });
        }
        
        // When this view is detached, detach all event handlers.
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
    
    /**
     * Selects an image to display then adds it to the webpage.
     * 
     * @param imgs
     */
    private void displayProfileImage(Map<String, Image> imgs) {
        
        if (imgs == null || imgs.size() == 0) {
            img_container.setInnerHTML(none.getElement().getString());
            return;
        }
        
        for (Illustration ill : bible.getIllustrations()) {
            if (!isBlank(ill.getUrl())) {
                if (ill.getUrl() == null) {
                    continue;
                }
                
                Image img = new Image(ill.getUrl());
                
                img.setStyleName("ProfileImage");
                img_container.setInnerHTML(img.getElement().getString());
                return;
            }
        }
        
    }
    
    /**
     * <p>Go through all illustrations to find which ones have resolvable
     * URLs. If an illustration has a URL associated with it, the Content-Type
     * of the URL is checked.</p>
     * 
     * @param cb
     */
    private void findResolvableImages(final ImagesCallback cb) {
        final Map<String, Image> imgs = new HashMap<String, Image> ();
        pending_images = 0;
        
        final FlowPanel container = new FlowPanel();
        container.setVisible(false);
        RootPanel.get().add(container);
        
        IllustrationList ills = bible.getIllustrations();
        
        for (final Illustration ill : ills) {
            if (!isBlank(ill.getUrl())) {
                pending_images++;
                
                final Image img = new Image(ill.getUrl());
                container.add(img);
                
                handlers.add(img.addLoadHandler(new LoadHandler() {
                    @Override
                    public void onLoad(LoadEvent event) {
                        pending_images--;
                        container.remove(img);

                        img.setVisible(true);
                        imgs.put(ill.getUrl(), img);
                        
                        if (pending_images <= 0) {
                            cb.onImagesRecieved(imgs);
                        }
                    }
                }));
                
                handlers.add(img.addErrorHandler(new ErrorHandler() {
                    @Override
                    public void onError(ErrorEvent event) {
                        pending_images--;
                        container.remove(img);
                        
                        if (pending_images <= 0) {
                            cb.onImagesRecieved(imgs);
                        }
                    }
                }));
            }
        }
        
        if (pending_images == 0) {
            cb.onImagesRecieved(null);
        }
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
    
    public static Element span(String text, String domclass) {
        Element span = doc.createSpanElement();
        
        if (domclass != null) {
            span.setClassName(domclass);
        }
        
        if (text != null) {
            span.setInnerSafeHtml(
                    sanitizer.sanitize(text.replaceAll("\\s+", " ")));
        }
        
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
        
        profile_panel.add(top);
        
        // Title
        
        SimplePanel p = new SimplePanel();
        panel.add(p);
        
        Element div = doc.createDivElement();
        div.setClassName("Title");
        
        appendChild(p, div);
        
        Element subdiv = doc.createDivElement();
        div.appendChild(subdiv);
        
        subdiv.appendChild(img_container);
        img_container.setInnerHTML(loading.getElement().getString());

        subdiv = doc.createDivElement();
        div.appendChild(subdiv);
        
        if (!isBlank(bible.getClassification().getCurrentCity()))
            subdiv.appendChild(textNode(bible.getClassification().getCurrentCity() + ", "));
        
        AnchorElement anch = doc.createAnchorElement();
        
        String link_text = bible.getClassification().getCurrentRepository();
        String link = bible.getClassification().getRepositoryLink();
        
        if (!isBlank(link)) {
            anch.setHref(link);
            anch.setTarget("_blank");
            anch.appendChild(doc.createTextNode(link_text));
            subdiv.appendChild(anch);
        } else {
            subdiv.appendChild(doc.createTextNode(link_text));
        }

        if (!isBlank(bible.getClassification().getCurrentShelfmark()))
            subdiv.appendChild(textNode(" " + 
                        bible.getClassification().getCurrentShelfmark()));
        subdiv.appendChild(doc.createBRElement());
        subdiv.appendChild(textNode(Messages.INSTANCE.manuscriptProfile()));
        
        subdiv = doc.createDivElement();
        subdiv.setClassName("TitleMinor");
        
        div.appendChild(subdiv);
        //appendChild(p, div);

        subdiv.appendChild(span(Messages.INSTANCE.format(), MINOR_SECTION));
        if (bible.getClassification().getBookType().getTech() != null)
            subdiv.appendChild(
                    textNode(bible.getClassification().getBookType()
                                    .getTech().technology()));
        subdiv.appendChild(doc.createBRElement());
        subdiv.appendChild(span(Messages.INSTANCE.prodDate(), MINOR_SECTION));
        if (!isBlank(bible.getProvenPatronHist().getProduction().getProdDate()))
            subdiv.appendChild(
                    textNode(bible.getProvenPatronHist().getProduction().getProdDate()));
        subdiv.appendChild(doc.createBRElement());
        
        anch = doc.createAnchorElement();
        link = bible.getScannedMss();

        if (link != null && !link.equals("")) {
            anch.setHref(link);
            anch.setTarget("_blank");
            anch.setInnerText(link);
            
            Element aspan = span("[", "MsLink");
            aspan.appendChild(anch);
            aspan.appendChild(textNode("]"));
            
            subdiv.appendChild(aspan);
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
        
        if (bible.getPhysChar().dimensions().size() > 0) {
            div.appendChild(span(Messages.INSTANCE.dims(), MINOR_SECTION));
        }

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
        div.appendChild(textNode(""+ folios.getTotalFolios()));
        
        if (folios.size() > 0)
            div.appendChild(textNode(" ("));
            
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

            if (sum != null) {
                sb.append(ills.size() + " illustrations. ");
                if (sum.getIllStyle() != null)
                    sb.append("(" + sum.getIllStyle().value() + ") ");
                
                if (sum.getLargeIlls() > 0) {
                    sb.append("Includes " + sum.getLargeIlls()
                            + " large presentation scenes. ");
                }
                
                if (sum.getFoliateBorder() == Choice.Y)
                    sb.append("Includes foliate borders. ");
                if (sum.getBasDePage() == Choice.Y)
                    sb.append("Includes bas-de-page scenes. ");
                if (sum.getDecoratedInitials() == Choice.Y)
                    sb.append("Includes decorated initials. ");
            }
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
        
        sb = new StringBuilder();
        for (QuireStructure qs : bible.getPhysChar().quireStructs()) {
            
            if (qs.getVolume() > 0)
                sb.append("Vol. " + qs.getVolume() + " has ");
            if (qs.quireTotal() != null && qs.quireTotal().size() > 0)
                sb.append(qs.quireTotal() + " quires. ");
            if (qs.typicalQuires() != null && qs.typicalQuires().size() > 0)
                sb.append("Most of which contain " + qs.typicalQuires() + " folios");
            if (qs.fullQuireStructs() != null && qs.fullQuireStructs().size() > 0)
                sb.append(": " + qs.fullQuireStructs() + ". ");
            
            if (qs.quireNotes() != null && qs.quireNotes().size() > 0)
                sb.append(qs.quireNotes().get(0)
                        + (qs.quireNotes().get(0).trim().endsWith(".")
                                ? "" : "."));
        }
        
        if (!isBlank(sb.toString())) {
            div.appendChild(span(Messages.INSTANCE.quireStruct(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        div.appendChild(span(Messages.INSTANCE.mats(), MINOR_SECTION));

        Materials mats = bible.getPhysChar().getMaterials();
        sb = new StringBuilder();
        
        sb.append(mats.getSupport().support() + ". ");
        
        if (!isBlank(mats.getBindMaterial()) || (mats.getBindDateStartYear() != 0 
                && mats.getBindDateEndYear() != 0)) {
            sb.append("Binding ");
        }
        
        if (mats.getBindDateStartYear() != 0 
                && mats.getBindDateEndYear() != 0)
            sb.append("(" + mats.getBindDateStartYear() + " - "
                    + mats.getBindDateEndYear() + ") ");
            
        if (!isBlank(mats.getBindMaterial()))
            sb.append("made of " + mats.getBindMaterial() + ". ");
        
        div.appendChild(textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
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
        
        if (!isBlank(sb.toString())) {
            div.appendChild(span(Messages.INSTANCE.rubrics(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        if (!isBlank(bible.getPhysChar().getPageLayoutNotes())
                || !isBlank(bible.getPhysChar().getPhysicaNotes())
                || !isBlank(bible.getIllustrations().getIllustrationNote())) {
            div.appendChild(span(Messages.INSTANCE.additionalNotes(), MINOR_SECTION));
        }

        if (!isBlank(bible.getPhysChar().getPageLayoutNotes())) {
            div.appendChild(textNode(bible.getPhysChar().getPageLayoutNotes()));
            div.appendChild(doc.createBRElement());
        }
        if (!isBlank(bible.getPhysChar().getPhysicaNotes())) {
            div.appendChild(textNode(bible.getPhysChar().getPhysicaNotes()));
            div.appendChild(doc.createBRElement());
        }
        if (!isBlank(bible.getIllustrations().getIllustrationNote())) 
            div.appendChild(textNode(bible.getIllustrations().getIllustrationNote()));
        
        
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
            
            if (!isBlank(prod.getProdLoc()))
                span.addClassName("Indent");
            
            div.appendChild(span);
            div.appendChild(textNode(prod.getProdDate()));
        }
        
        // Table view of owners
        List<Ownership> ownerships = bible.getProvenPatronHist().ownerships();
        
        Element tablediv = doc.createDivElement();
        tablediv.setClassName("OwnerTable");
        
        Element table = doc.createTableElement();
        Element tr = doc.createTRElement();
        Element td = doc.createTDElement();
        
        if (ownerships.size() > 0) {
            div.appendChild(tablediv);
            tablediv.appendChild(table);
            
            table.appendChild(tr);
            
            td.appendChild(span(Messages.INSTANCE.ownedBy(), MINOR_SECTION));
            tr.appendChild(td);
            
            td = doc.createTDElement();
            td.appendChild(span(Messages.INSTANCE.datesAndLoc(), MINOR_SECTION));
            tr.appendChild(td);
        }
        
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
        
        sb = new StringBuilder();
        
        List<Contributor> conts = 
                bible.getProvenPatronHist().getProduction().contributors();
        
        for (Contributor cont : conts) {
            if (!isBlank(cont.getValue()))
                sb.append(cont.getValue());
            if (cont.getType() != null) 
                sb.append(" (" + cont.getType().value() + "); ");
        }
        
        if (!isBlank(sb.toString())) {
            div.appendChild(doc.createBRElement());
            div.appendChild(span(Messages.INSTANCE.contrib(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
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
        
        if (!isBlank(sb.toString()))
            div.appendChild(textNode(sb.toString()));
        else
            div.appendChild(textNode("none"));
        div.appendChild(doc.createBRElement());
        
        // expandable detail
        disclose = new DisclosurePanel();
        panel.add(disclose);
        
        expand = new Label("[+ expand for full text of personalized features]");
        expand.setStylePrimaryName("Expander");
        
        disclose.setHeader(expand);
        
        details = new SimplePanel();
        disclose.setContent(details);
        
        div = doc.createDivElement();
        appendChild(details, div);
        
        sb = new StringBuilder();
        for (PersonalizationItem col : per.colophons()) {
            sb.append(col.getValue() + " ");
            
            if (!isBlank(col.getFolio()) || col.getVolume() > 0)
                sb.append("(" + 
                        (col.getVolume() >= 0 ? "vol. " + col.getVolume() + ", " : "")
                        + (!isBlank(col.getFolio()) ? col.getFolio() : "")
                        + ") ");
        }
        
        if (!isBlank(sb.toString())) {
            div.appendChild(span(Messages.INSTANCE.colophons(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        if (per.signatures().size() > 0 || per.dedications().size() > 0
                || per.legalInscriptions().size() > 0) {
            div.appendChild(span(Messages.INSTANCE.signatureTitle(), MINOR_SECTION));
        }
        
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
        
        // Add a list of annotations
        
        List<Annotation> anns = bible.getProvenPatronHist().annotations();
        
        if (anns != null && anns.size() > 0) {
            div.appendChild(span("Annotations:", MINOR_SECTION));
        }
        
        ol = doc.createOLElement();
        div.appendChild(ol);
        
        for (Annotation ann : anns) {
            li = doc.createLIElement();
            ol.appendChild(li);
            
            sb = new StringBuilder();
            if (ann.getVolume() > 0)
                sb.append("Vol. " + ann.getVolume() + ", ");
            if (!isBlank(ann.getFolio()))
                sb.append("fol. " + ann.getFolio() + ". ");
            if (!isBlank(ann.getBook()))
                sb.append(ann.getBook() + ". ");
            
            li.appendChild(textNode(sb.toString()));
            
            if (!isBlank(ann.getText()))
                li.appendChild(span(ann.getText()
                        + (ann.getText().trim().endsWith(".")
                                ? "" : ". "),
                        "Monospace"));
            if (!isBlank(ann.getTextReferenced())) {
                li.appendChild(textNode("In reference to "));
                li.appendChild(span(ann.getTextReferenced()
                        + (ann.getTextReferenced().trim().endsWith(". ")
                                ? "" : ". "),
                        "Monospace"));
            }
            
            sb = new StringBuilder();
            if (!isBlank(ann.getName()))
                sb.append(ann.getName() + ", ");
            if (!isBlank(ann.getDate()))
                sb.append(ann.getDate());
            
            li.appendChild(textNode(sb.toString()));
        }
        
        String price = bible.getProvenPatronHist().getPersonalization().getPurchasePrice();
        if (!isBlank(price)) {
            div.appendChild(span("Purchase Price: ", MINOR_SECTION));
            div.appendChild(textNode(price));
            div.appendChild(doc.createBRElement());
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
        
        if (!isBlank(notes.toString()) 
                || !isBlank(prod.getProdNotes())
                || bible.getProvenPatronHist().provenanceNote().size() > 0) {
            div.appendChild(span(Messages.INSTANCE.notes(), MINOR_SECTION));
        }
        
        if (!isBlank(notes.toString())) {
            div.appendChild(textNode(notes.toString()));
            div.appendChild(doc.createBRElement());
        }
        if (!isBlank(prod.getProdNotes())) {
            div.appendChild(textNode(prod.getProdNotes()));
            div.appendChild(doc.createBRElement());
        }
        
        for (String str : bible.getProvenPatronHist().provenanceNote()) {
            div.appendChild(textNode(str));
            div.appendChild(doc.createBRElement());
        }
        
        
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
        
        if (!isBlank(classif.getClassificationNote()))
            sb.append(". " + classif.getClassificationNote() 
                    + (classif.getClassificationNote().trim().endsWith(".")
                            ? " " : ". "));
        
        if (!isBlank(sb.toString()) || !isBlank(classif.getFournieNumber())) {
            div.appendChild(span(Messages.INSTANCE.catalogClassif(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
        }
            
        
        if (!isBlank(classif.getFournieNumber())) {
            div.appendChild(textNode((isBlank(bible.getShortName()) 
                    ? "This manuscript" : bible.getShortName())
                    + " is entry number(s) "));
            
            if (!isBlank(classif.getFournieLink())) {
                anch = doc.createAnchorElement();
                anch.setHref(classif.getFournieLink());
                anch.setTarget("_blank");
                
                anch.setInnerSafeHtml(sanitizer.sanitize(classif.getFournieNumber() 
                        + " in Eleanor Fournie's " + "online catalog."));
                div.appendChild(anch);
            } else {
                div.appendChild(textNode(classif.getFournieNumber() + 
                        " in Eleanor Fournie's online catalog."));
            }
        }
        
        div.appendChild(doc.createBRElement());
        
        sb = new StringBuilder();
        for (SecundoFolio sec : classif.secundoFolios()) {
            if (sec.getVolume() > 0)
                sb.append("Vol. " + sec.getVolume() 
                        + (!isBlank(sec.getValue()) ? ": " : " "));
            if (!isBlank(sec.getValue())) 
                sb.append(sec.getValue() + ". ");
        }
        
        if (!isBlank(sb.toString())) {
            div.appendChild(span(Messages.INSTANCE.secundo(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        TextualContent textcont = bible.getTextualContent();

        sb = new StringBuilder();
        notes = new StringBuilder();
        int tables_of_contents = 0;

        for (PrefatoryMatter mat : textcont.prefatoryMatters()) {
            if (!isBlank(mat.getMasterTableOfContents().getText())) {
                tables_of_contents++;
                
                if (mat.getVolume() > 0) {
                    sb.append("Included in vol. " + mat.getVolume() + " ");
                    notes.append("<i>Vol. " + mat.getVolume() + "</i> ");
                }
                
                notes.append("table: " + mat.getMasterTableOfContents().getText() + " ");
                
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
        
        disclose = new DisclosurePanel();
        if (tables_of_contents > 0) {
            div.appendChild(span(Messages.INSTANCE.tableOfContents(), MINOR_SECTION));
            div.appendChild(textNode(sb.toString()));
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
        
        
        
        div.setInnerSafeHtml(sanitizer.sanitize(notes.toString()));
        
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
                if (sb.toString().trim().endsWith("."))
                    sb.append(". ");
            }
            
            li.appendChild(textNode(sb.toString()));
            
            if (!isBlank(bib.getArticleTitle()))
                li.appendChild(span(bib.getArticleTitle()
                        + (bib.getArticleTitle().trim().endsWith(".")
                                ? "" : ". "),
                        "ArticleTitle"));
            
            if (!isBlank(bib.getBookOrJournalTitle()))
                li.appendChild(textNode(bib.getBookOrJournalTitle() + " "));
            
            if (!isBlank(bib.getPublicationInfo()))
                li.appendChild(textNode(bib.getPublicationInfo()
                        + (bib.getPublicationInfo().trim().endsWith(".")
                                ? "" : ". ")));
            
            for (String st : bib.articleLinks()) {
                anch = doc.createAnchorElement();
                anch.setHref(st);
                anch.setTarget("_blank");
                
                anch.setInnerSafeHtml(sanitizer.sanitize(st));
                li.appendChild(anch);
                li.appendChild(textNode("; "));
            }
            
            ul.appendChild(li);
        } 
    }   
}
