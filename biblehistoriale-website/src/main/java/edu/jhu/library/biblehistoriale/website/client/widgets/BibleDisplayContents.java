package edu.jhu.library.biblehistoriale.website.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.profile.Annotation;
import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.CatechismsPrayersTreatise;
import edu.jhu.library.biblehistoriale.model.profile.Choice;
import edu.jhu.library.biblehistoriale.model.profile.ComestorLetter;
import edu.jhu.library.biblehistoriale.model.profile.Guyart;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.Incipit;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.MasterTableOfContents;
import edu.jhu.library.biblehistoriale.model.profile.MasterTableOfContents.Detail;
import edu.jhu.library.biblehistoriale.model.profile.MiscContent;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;
import edu.jhu.library.biblehistoriale.model.profile.Title;
import edu.jhu.library.biblehistoriale.model.profile.TitleIncipit;
import edu.jhu.library.biblehistoriale.website.client.BibleVolume;
import edu.jhu.library.biblehistoriale.website.client.Messages;

/**
 * Contains methods for displaying textual content of a Bible.
 */
public class BibleDisplayContents {
    private static final SimpleHtmlSanitizer sanitizer =
            SimpleHtmlSanitizer.getInstance();
    
    private static final String BH = "BH";
    private static final String BXIII = "BXIII";
    private static final String RAOUL = "Raoul de Presles";
    
    private static final String MORALITES = "moralite";
    private static final String MULTIPLE = "multiple";
    private static final String MIXED = "mixed";
    
    private Bible bible;
    
    private Document doc;
    
    private final List<HandlerRegistration> handlers;
    private final List<DisclosurePanel> ills_incips;
    
    private int[] ill_by_volume;
    
    private boolean ills_incips_is_collapsed;
    
    public BibleDisplayContents(Document doc) {
        this.doc = doc;
        
        this.handlers = new ArrayList<HandlerRegistration> ();
        this.ills_incips = new ArrayList<DisclosurePanel> ();
        
        this.ills_incips_is_collapsed = true;
        
        this.ill_by_volume = null;
    }
    
    /**
     * Remove all handlers from the DOM.
     */
    public void detachContent() {
        for (HandlerRegistration hr : handlers) {
            hr.removeHandler();
        }
    }
    
    
    /**
     * Returns a ScrollPanel containing the textual contents
     * of the Bible object
     * 
     * @param bible
     * @return
     */
    public ScrollPanel displayContentView(Bible bible) {
        this.bible = bible;
        this.ill_by_volume = countIllsInVols(bible.getIllustrations());
        
        FlowPanel panel = new FlowPanel();
        ScrollPanel top = new ScrollPanel();
        top.add(panel);
        
        top.setWidth("100%");
        top.setHeight("100%");
        
        displayOverviewInfo(panel);
        //panel.add(displayByVolumes());
        displayByVolumes(panel);
        
        return top;
    }
    
    /**
     * Calculate the percentage of books from various text sources.
     * 
     * @param vols
     * @return
     */
    private Map<String, Integer> findTextSources(List<BibleBooks> vols) {
        int total = 0;
        int gloss_total = 0;
        int gloss2_total = 0;
        
        int[] versions = new int[4];
        int[] glosses = new int[4];
        int[] gloss2 = new int[5];
        
        for (BibleBooks bb : vols) {
            for (Title title : bb) {
                total++;
                gloss_total++;
                gloss2_total++;
                
                String version = title.getTextVersion();
                if (version.equals(BH)) {
                    versions[0]++;
                } else if (version.equals(BXIII)) {
                    versions[1]++;
                } else if (version.equals(RAOUL)) {
                    versions[2]++;
                } else {
                    versions[3]++;
                }
                
                String gloss = title.getGlossType();
                if (gloss.equals(MORALITES)) {
                    glosses[0]++;
                } else if (gloss.equals(BH)) {
                    glosses[1]++;
                } else if (gloss.equals(BXIII)) {
                    glosses[2]++;
                } else if (gloss.equals(MIXED)) {
                    glosses[3]++;
                } else {
                    gloss_total--;
                }
                
                String gloss2_str = title.getGlossType2();
                if (gloss2_str.equals(MORALITES)) {
                    gloss2[0]++;
                } else if (gloss2_str.equals(BH)) {
                    gloss2[1]++;
                } else if (gloss2_str.equals(BXIII)) {
                    gloss2[2]++;
                } else if (gloss2_str.equals(MIXED)) {
                    gloss2[3]++;
                } else if (gloss2_str.equals(MULTIPLE)) {
                    gloss2[4]++;
                }
            }
        }

        Map<String, Integer> percents = new HashMap<String, Integer> ();
        percents.put(BH, (int) Math.round((double) versions[0] / total * 100));
        percents.put(BXIII, (int) Math.round((double) versions[1] / total * 100));
        percents.put(RAOUL, (int) Math.round((double) versions[2] / total * 100));
        percents.put("other", (int) Math.round((double) versions[3] / total * 100));
        
        percents.put(MORALITES + "gloss",
                (int) Math.round((double) gloss2[0] / gloss2_total * 100));
        percents.put(BH + "gloss", 
                (int) Math.round((double) glosses[1] / gloss_total * 100));
        percents.put(BXIII + "gloss", 
                (int) Math.round((double) glosses[2] / gloss_total * 100));
        percents.put(MIXED + "gloss", 
                (int) Math.round((double) glosses[3] / gloss_total * 100));
        percents.put(MULTIPLE + "gloss", 
                (int) Math.round((double) gloss2[4] / gloss2_total * 100));
        
        return percents;
    }
    
    /**
     * Find the number of volumes.
     * 
     * @return
     */
    private int numVols() {
        int vols = 1;
        // Find the number of volumes in this Bible
        try {
            String state = 
                    bible.getPhysChar().getVolumes().getPresentState().value();
            
            vols = Integer.parseInt(state);
        } catch (NumberFormatException e) {
            return 0;
            // Not necessarily an error. state could be a non-number.
            // In this case, it is unknown.
        }
        
        return vols;
    }
    
    /**
     * Count the number of illustrations in each volume.
     * If the number of volumes is unknown, return NULL.
     * 
     * @param ills
     * @return
     */
    private int[] countIllsInVols(IllustrationList ills) {
        int vols = numVols();
        
        if (vols == 0) {
            return new int[0];
        }
        
        int[] num_ills = new int[vols];
        
        for (Illustration ill : ills) {
            for (int i = 0; i < num_ills.length; i++) {
                if (ill.getVolume() == (i + 1)) {
                    num_ills[i]++;
                }
            }
        }
        
        return num_ills;
    }
    
    /**
     * Does the textVersion field match the glossType field in
     * every title?
     * 
     * @param vols
     * @return
     */
    private boolean matchingTextAndGloss(List<BibleBooks> vols) {
        
        for (BibleBooks bb : vols) {
            for (Title title : bb) {
                if (!title.getTextVersion().equals(title.getGlossType()))
                    return false;
            }
        }
        
        return true;
    }
    
    /**
     * Find all titles with "multiple" under glossType2
     * 
     * @param vols
     * @return
     */
    private List<Title> findTitlesWithMultiple(List<BibleBooks> vols) {
        List<Title> mults = new ArrayList<Title> ();
        
        for (BibleBooks bb : vols) {
            for (Title title : bb) {
                if (title.getGlossType2().equals("multiple"))
                    mults.add(title);
            }
        }
        
        return mults;
    }
    
    /**
     * Display initial remarks and overview information.
     * 
     * @param container
     */
    private void displayOverviewInfo(FlowPanel container) {
        StringBuilder sb = new StringBuilder();

        List<BibleBooks> volume = bible.getTextualContent().bibleBooks();
        
        // Title
        SimplePanel p = new SimplePanel();
        container.add(p);
        
        Element div = doc.createDivElement();
        div.setClassName("Title");
        
        BibleDisplay.appendChild(p, div);
        
        if (!BibleDisplay.isBlank(bible.getClassification().getCurrentCity()))
            div.appendChild(BibleDisplay.textNode(
                    bible.getClassification().getCurrentCity() + ", "));
        
        AnchorElement anch = doc.createAnchorElement();
        
        String link_text = bible.getClassification().getCurrentRepository();
        String link = bible.getClassification().getRepositoryLink();
        
        if (!BibleDisplay.isBlank(link)) {
            anch.setHref(link);
            anch.appendChild(BibleDisplay.textNode(link_text));
            div.appendChild(anch);
        } else {
            div.appendChild(BibleDisplay.textNode(link_text));
        }
        
        if (!BibleDisplay.isBlank(bible.getClassification().getCurrentShelfmark()))
                div.appendChild(BibleDisplay.textNode(" " + 
                        bible.getClassification().getCurrentShelfmark()));
        div.appendChild(doc.createBRElement());
        div.appendChild(BibleDisplay.textNode("Contents View"));
        
        container.add(new HTML(Messages.INSTANCE.remarks()));
        
        p = new SimplePanel();
        container.add(p);
        
        div = doc.createDivElement();
        div.setClassName("Section");
        
        BibleDisplay.appendChild(p, div);
        
        Element titlediv = doc.createDivElement();
        titlediv.setClassName("SectionHeader");
        titlediv.appendChild(BibleDisplay.textNode(Messages.INSTANCE.overview()));
        
        div.appendChild(titlediv);
        
        sb.append("Includes biblical text sourced from: ");
        
        Map<String, Integer> sources = findTextSources(volume);
        if (sources.get(BH) > 0)
            sb.append(BH + " (in " + sources.get(BH) + "% of books). ");
        if (sources.get(BXIII) > 0)
            sb.append(BXIII + " (in " + sources.get(BXIII) + "% of books). ");
        if (sources.get(RAOUL) > 0)
            sb.append(RAOUL + " (in " + sources.get(RAOUL) + "% of books). ");
        if (sources.get("other") > 0)
            sb.append("other (in " + sources.get("other") + "% of books). ");
        
        div.appendChild(BibleDisplay.textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        sb = new StringBuilder("Glosses sourced from: ");
        
        if (sources.get(MORALITES+"gloss") > 0)
            sb.append(MORALITES + " (" + sources.get(MORALITES+"gloss") + "%). ");
        if (sources.get(BH+"gloss") > 0)
            sb.append(BH + " (" + sources.get(BH+"gloss") + "%). ");
        if (sources.get(BXIII+"gloss") > 0)
            sb.append(BXIII + " (" + sources.get(BXIII+"gloss") + "%). ");
        if (sources.get(MULTIPLE+"gloss") > 0)
            sb.append(MULTIPLE + " (" + sources.get(MULTIPLE+"gloss") + "%) ");
        if (sources.get(MIXED+"gloss") > 0)
            sb.append(MIXED + " (" + sources.get(MIXED+"gloss") + "%). ");
        
        div.appendChild(BibleDisplay.textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        sb = new StringBuilder("Classified as ");
        
        CatalogerClassification classif = bible.getClassification().getClassification();
        if (classif != null) {
            Berger berg = classif.getBergerClass();
            Sneddon sned = classif.getSneddonClass();
            
            if (berg.getCategory() != null)
                sb.append(berg.getCategory().category() + " ");
            if (berg.getBhcSubtype() != null)
                sb.append(berg.getBhcSubtype().subtype() + " ");
            if (berg.getCategory() != null || berg.getBhcSubtype() != null)
                sb.append("by Berger. ");
            
            if (sned.getSub1() != null)
                sb.append(sned.getSub1());
            if (sned.getSub2() != null)
                sb.append(sned.getSub2());
            if (sned.getSub3() != null)
                sb.append(sned.getSub3());
            if (sned.getSub1() != null || sned.getSub2() != null || sned.getSub3() != null)
                sb.append(" by Sneddon. ");
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        IllustrationList ills = bible.getIllustrations();
        if (ills != null) {
            int[] by_volume = countIllsInVols(ills);
            
            sb = new StringBuilder();
            sb.append(ills.size() + " illustrations. ");
            
            if (by_volume != null) {
                for (int i = 0; i < by_volume.length; i++) {
                    sb.append(by_volume[i] + " in volume " + (i + 1) + ". ");
                }
            }
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        if (volume != null) {
            sb = new StringBuilder("Gloss types/sources include: ");
            
            for (BibleBooks bb : volume) {
                for (Title title : bb) {
                    String type = title.getGlossType();
                    if (sb.indexOf(type) == -1) {
                        sb.append(type + ". ");
                    }
                }
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
            
            sb = new StringBuilder("Text and gloss " 
                    + (matchingTextAndGloss(volume)
                            ? "" : "do not always ") + "match. ");
            
            List<Title> mults = findTitlesWithMultiple(volume);
            
            for (Title title : mults) {
                sb.append(title.getTextVersion() + " " + title.getBookName()
                        + " includes other glosses. ");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        FlowPanel options_panel = new FlowPanel();
        container.add(options_panel);
        
        HTML details_link = new HTML("<u>Expand/collapse all details</u>");
        
        details_link.setStylePrimaryName("Clickable");
        details_link.addStyleName("Expander");
        details_link.addStyleName("Indent");
        
        options_panel.add(details_link);
        
        handlers.add(details_link.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ills_incips_is_collapsed = !ills_incips_is_collapsed;
                for (DisclosurePanel p : ills_incips) {
                    p.setOpen(!ills_incips_is_collapsed);
                }
            }
        }));
    }
    
    /**
     * Does the specified volume have any prefatory matter?
     * 
     * @param volume
     * @return
     */
    private boolean hasPrefaceInVolume(int volume) {
        for (PrefatoryMatter pm : 
            bible.getTextualContent().prefatoryMatters()) {
            if (pm.getVolume() == volume) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Find the names of the first and last books in the
     * specified bible volume
     * 
     * @param volume
     * @return
     */
    private String[] getFirstAndLastBooks(int volume) {
        String[] titles = new String[2];
        
        for (BibleBooks bb : bible.getTextualContent().bibleBooks()) {
            if (bb.getVolume() == volume) {
                titles[0] = bb.title(0).getBookName();
                titles[1] = bb.title(bb.size() - 1).getBookName();
                return titles;
            }
        }
        
        return null;
    }
    
    /**
     * Displays the prefatory matter and bible books.
     * 
     * @return
     */
    private void displayByVolumes(FlowPanel panel) {
        int num_vols = numVols();
        
        // Init tables and add the title row
        for (int i = 0; i < num_vols; i++) {
            int vol = i + 1;

            BibleVolume bible_volume = new BibleVolume(bible, vol);
            
            FlexTable table = new FlexTable();
            table.setStylePrimaryName("OwnerTable");
            table.addStyleName("VolumeTable");
            
            panel.add(table);
            
            SimplePanel p = new SimplePanel();
            table.setWidget(0, 0, p);
            
            // Descriptions
            displayVolumeDescription(bible_volume, p);
            
            HTML label = new HTML("Prefatory Matter");
            label.setStylePrimaryName("SectionHeader");
            table.setWidget(1, 0, label);
            
            // Prefatory matter
            if (bible_volume.getPrefatoryMatter() != null) {
                displayPrefatoryMatter(bible_volume, table);
            }
            
            label = new HTML("Biblical books");
            label.setStylePrimaryName("SectionHeader");
            table.setWidget(4, 0, label);
            
            FlowPanel fp = new FlowPanel();
            table.setWidget(5, 0, fp);
            
            // Bible books
            displayBibleBooks(bible_volume, fp);
            
            table.setWidget(6, 0, displayOtherContents(bible_volume));
        }
    }
    
    /**
     * Display a brief description of the specified volume.
     * 
     * @param bible_volume
     * @param p
     *          the container panel.
     */
    private void displayVolumeDescription(BibleVolume bible_volume, Panel p) {
        StringBuilder sb = new StringBuilder();
        
        int vol = bible_volume.getVolume();
        
        Element div = doc.createDivElement();
        div.setClassName("VolumeTitle");
        BibleDisplay.appendChild(p, div);
        
        Element titlediv = doc.createDivElement();
        titlediv.setClassName("Title");
        div.appendChild(titlediv);
        
        titlediv.appendChild(BibleDisplay.textNode("Volume " + vol));
        div.appendChild(doc.createBRElement());

        ParascripturalItem pi = bible.getTextualContent().parascripturalItem();
        if (pi != null) {
            String[] titles = getFirstAndLastBooks(vol);
            
            if (hasPrefaceInVolume(vol))
                sb.append("Preface(s). ");
            if (titles != null)
                sb.append(titles[0] + " - " + titles[1] + ". ");
            if (pi.getLitanyPresence() == Choice.Y)
                sb.append("Litany. ");
            if (pi.getCanticlePresence() == Choice.Y)
                sb.append("Canticles. ");
            if (pi.catechismPrayersTreatises().size() > 0
                    || bible.getTextualContent().miscContents().size() > 0) {
                sb.append("Other materials. ");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            div.appendChild(doc.createBRElement());
        }
        
        if (bible.getPhysChar().getFolios().size() > 0) {
            sb = new StringBuilder("Folios: ");
            
            for (IndVolume ind : bible.getPhysChar().getFolios()) {
                if (ind != null && ind.getVolume() == vol) {
                    sb.append(ind.getValue());
                }
            }
        }
        
        try {
            sb.append("    Total illustrations: " + ill_by_volume[vol - 1]);
        } catch (IndexOutOfBoundsException e) {
            // do nothing
        }
        
        div.appendChild(BibleDisplay.textNode(sb.toString()));
        
    }
    
    /**
     * Display the prefatory matter for the given bible volume. This includes
     * Guyart, Comestor, Comestor's Letter, other prefaces, and master
     * table of contents.
     * 
     * @param bible_volume
     * @param table
     *          Container that is used to display the information on the web page.
     */
    private void displayPrefatoryMatter(BibleVolume bible_volume, FlexTable table) {
        StringBuilder sb = new StringBuilder();
        PrefatoryMatter prefatory = bible_volume.getPrefatoryMatter();
        
        String descr = prefatory.getPrefactoryNote() != null 
                ?"<i>Description:</i> " + prefatory.getPrefactoryNote()
                        : "";
        
        table.setWidget(2, 0, new HTML(sanitizer.sanitize(descr)));
        
        FlowPanel fp = new FlowPanel();
        table.setWidget(3, 0, fp);

        /**
         * Other Preface
         */
        for (int j = 0; j < prefatory.otherPrefaces().size(); j++) {
            OtherPreface other = prefatory.otherPrefaces().get(j);
            
            if (other == null) {
                continue;
            }
            
            SimplePanel p = new SimplePanel();
            fp.add(p);
            
            Element div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            div.appendChild(BibleDisplay.span("Preface ", 
                    BibleDisplay.MINOR_SECTION));
            
            sb = new StringBuilder("(Guyart's). ");
            
            if (!BibleDisplay.isBlank(other.getStartPage()))
                sb.append("Begins folio " + other.getStartPage() + ". ");
            if (bible_volume.getOtherPrefacesIlls(other) != null) {
                sb.append("No. of illustrations: "
                        + bible_volume.getOtherPrefacesIlls(other).size());
            } else {
                sb.append("No. of illustrations: 0");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            
            DisclosurePanel disclose = new DisclosurePanel();
            fp.add(disclose);
            ills_incips.add(disclose);
            
            Label expand = new Label("[+ Expand details]");
            expand.setStylePrimaryName("Expander");
            disclose.setHeader(expand);
            
            p = new SimplePanel();
            disclose.setContent(p);
            
            div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            Element subdiv = doc.createDivElement();
            subdiv.setClassName("Incipit");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Incipit(s): ", 
                    "ArticleTitle"));
            
            sb = new StringBuilder();
            if (!BibleDisplay.isBlank(other.getText()))
                sb.append(other.getText() + " (");
            if (other.getAccuracy() != null)
                sb.append(other.getAccuracy().accuracy() + "). ");
            subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
            
            subdiv = doc.createDivElement();
            subdiv.setClassName("ContentIlls");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
            subdiv.appendChild(doc.createBRElement());
            
            for (Illustration ill : bible_volume.getOtherPrefacesIlls(other)) {
                if (ill == null) {
                    continue;
                }
                sb = new StringBuilder();
                
                if (ill.getNumber() > 0)
                    sb.append(ill.getNumber() + ", ");
                if (!BibleDisplay.isBlank(ill.getFolio()))
                    sb.append("Fol. " + ill.getFolio() + ". ");
                if (!BibleDisplay.isBlank(ill.getKeywords()))
                    sb.append(ill.getKeywords());
                
                subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
                subdiv.appendChild(doc.createBRElement());
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    // TODO: any way to do thumbnails?
                    // Would have to either change URLs in profiles
                    // Or extract <img> element from url response.....
                    
                    //Image img = new Image(ill.getUrl());
                    //img.setWidth(THUMB_WIDTH + "px");
                    
                    AnchorElement anch = doc.createAnchorElement();
                    
                    //anch.setInnerHTML(img.getElement().getString());
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    subdiv.appendChild(anch);
                }
            }
            
        }

        /**
         * Guyart
         */
        for (int j = 0; j < prefatory.guyartList().size(); j++) {
            Guyart guyart = prefatory.guyartList().get(j);

            if (guyart == null) {
                continue;
            }
            
            SimplePanel p = new SimplePanel();
            fp.add(p);
            
            Element div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            div.appendChild(BibleDisplay.span("Preface ", 
                    BibleDisplay.MINOR_SECTION));

            sb = new StringBuilder("(Guyart's). ");
            
            if (!BibleDisplay.isBlank(guyart.getStartPage()))
                sb.append("Begins folio " + guyart.getStartPage() + ". ");
            if (bible_volume.getOtherPrefacesIlls(guyart) != null) {
                sb.append("No. of illustrations: "
                        + bible_volume.getOtherPrefacesIlls(guyart).size());
            } else {
                sb.append("No. of illustrations: 0");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            
            DisclosurePanel disclose = new DisclosurePanel();
            fp.add(disclose);
            ills_incips.add(disclose);
            
            Label expand = new Label("[+ Expand details]");
            expand.setStylePrimaryName("Expander");
            disclose.setHeader(expand);
            
            p = new SimplePanel();
            disclose.setContent(p);
            
            div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            Element subdiv = doc.createDivElement();
            subdiv.setClassName("Incipit");
            div.appendChild(subdiv);

            subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
            
            sb = new StringBuilder();
            if (!BibleDisplay.isBlank(guyart.getText()))
                sb.append(guyart.getText() + " (");
            if (guyart.getAccuracy() != null)
                sb.append(guyart.getAccuracy().accuracy() + "). ");
            subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
            
            subdiv = doc.createDivElement();
            subdiv.setClassName("ContentIlls");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
            subdiv.appendChild(doc.createBRElement());
            
            for (Illustration ill : bible_volume.getGuyartIlls(guyart)) {
                if (ill == null) {
                    continue;
                }
                sb = new StringBuilder();
                
                if (ill.getNumber() > 0)
                    sb.append(ill.getNumber() + ", ");
                if (!BibleDisplay.isBlank(ill.getFolio()))
                    sb.append("Fol. " + ill.getFolio() + ". ");
                if (!BibleDisplay.isBlank(ill.getKeywords()))
                    sb.append(ill.getKeywords());
                
                subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
                subdiv.appendChild(doc.createBRElement());
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    AnchorElement anch = doc.createAnchorElement();
                    
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    subdiv.appendChild(anch);
                }
            }
            
            if (!BibleDisplay.isBlank(guyart.getTranscriptionUrl())) {
                AnchorElement anch = doc.createAnchorElement();
                
                anch.setInnerHTML("View transcription of similar text");
                anch.setHref(guyart.getTranscriptionUrl());
                
                div.appendChild(anch);
            }
        }

        /**
         * Comestor letter
         */
        for (int j = 0; j < prefatory.comestorLetters().size(); j++) {
            ComestorLetter cs = prefatory.comestorLetters().get(j);
            
            if (cs == null) {
                continue;
            }
            
            SimplePanel p = new SimplePanel();
            fp.add(p);
            
            Element div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            div.appendChild(BibleDisplay.span("Preface ", 
                    BibleDisplay.MINOR_SECTION));
            
            sb = new StringBuilder("(Comestor's letter). ");
            
            if (!BibleDisplay.isBlank(cs.getStartPage()))
                sb.append("Begins folio " + cs.getStartPage() + ". ");
            if (bible_volume.getOtherPrefacesIlls(cs) != null) {
                sb.append("No. of illustrations: "
                        + bible_volume.getOtherPrefacesIlls(cs).size());
            } else {
                sb.append("No. of illustrations: 0");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            
            DisclosurePanel disclose = new DisclosurePanel();
            fp.add(disclose);
            ills_incips.add(disclose);
            
            Label expand = new Label("[+ Expand details]");
            expand.setStylePrimaryName("Expander");
            disclose.setHeader(expand);
            
            p = new SimplePanel();
            disclose.setContent(p);
            
            div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            Element subdiv = doc.createDivElement();
            subdiv.setClassName("Incipit");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
            
            Element ul = doc.createULElement();
            subdiv.appendChild(ul);
            
            for (Incipit inc : cs.incipits()) {
                if (inc == null) {
                    continue;
                }
                Element li = doc.createLIElement();
                ul.appendChild(li);
                
                sb = new StringBuilder();
                if (!BibleDisplay.isBlank(inc.getText()))
                    sb.append(inc.getText() + " (");
                if (inc.getAccuracy() != null)
                    sb.append(inc.getAccuracy().accuracy() + "). ");
                
                li.setInnerSafeHtml(sanitizer.sanitize(sb.toString()));
            }
            
            subdiv = doc.createDivElement();
            subdiv.setClassName("ContentIlls");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
            subdiv.appendChild(doc.createBRElement());
            
            for (Illustration ill : bible_volume.getComestorLetterIlls(cs)) {
                if (ill == null) {
                    continue;
                }
                
                sb = new StringBuilder();
                
                if (ill.getNumber() > 0)
                    sb.append(ill.getNumber() + ", ");
                if (!BibleDisplay.isBlank(ill.getFolio()))
                    sb.append("Fol. " + ill.getFolio() + ". ");
                if (!BibleDisplay.isBlank(ill.getKeywords()))
                    sb.append(ill.getKeywords());
                
                subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
                subdiv.appendChild(doc.createBRElement());
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    AnchorElement anch = doc.createAnchorElement();
                    
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    subdiv.appendChild(anch);
                }
            }
            
            if (!BibleDisplay.isBlank(cs.getTranscriptionUrl())) {
                AnchorElement anch = doc.createAnchorElement();
                
                anch.setInnerHTML("View transcription of similar text");
                anch.setHref(cs.getTranscriptionUrl());
                
                div.appendChild(anch);
            }
        }

        /**
         * Comestor
         */
        for (int j = 0; j < prefatory.guyartList().size(); j++) {
            OtherPreface other = prefatory.comestorList().get(j);
            
            if (other == null) {
                continue;
            }

            SimplePanel p = new SimplePanel();
            fp.add(p);
            
            Element div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            div.appendChild(BibleDisplay.span("Preface ", 
                    BibleDisplay.MINOR_SECTION));

            sb = new StringBuilder("(Comestor's preface). ");
            
            if (!BibleDisplay.isBlank(other.getStartPage()))
                sb.append("Begins folio " + other.getStartPage() + ". ");
            if (bible_volume.getOtherPrefacesIlls(other) != null) {
                sb.append("No. of illustrations: "
                        + bible_volume.getOtherPrefacesIlls(other).size());
            } else {
                sb.append("No. of illustrations: 0");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            
            DisclosurePanel disclose = new DisclosurePanel();
            fp.add(disclose);
            ills_incips.add(disclose);
            
            Label expand = new Label("[+ Expand details]");
            expand.setStylePrimaryName("Expander");
            disclose.setHeader(expand);
            
            p = new SimplePanel();
            disclose.setContent(p);
            
            div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            Element subdiv = doc.createDivElement();
            subdiv.setClassName("Incipit");
            div.appendChild(subdiv);

            subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
            
            sb = new StringBuilder();
            if (!BibleDisplay.isBlank(other.getText()))
                sb.append(other.getText() + " (");
            if (other.getAccuracy() != null)
                sb.append(other.getAccuracy().accuracy() + "). ");
            subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
            
            subdiv = doc.createDivElement();
            subdiv.setClassName("ContentIlls");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
            subdiv.appendChild(doc.createBRElement());
            
            for (Illustration ill : bible_volume.getComestorIlls(other)) {
                if (ill == null) {
                    continue;
                }
                
                subdiv.appendChild(BibleDisplay.textNode(
                        ill.getNumber() + ", Fol. " + ill.getFolio()
                        + ", " + ill.getKeywords()));
                
                subdiv.appendChild(doc.createBRElement());
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    AnchorElement anch = doc.createAnchorElement();
                    
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    subdiv.appendChild(anch);
                }
            }
            
            if (!BibleDisplay.isBlank(other.getTranscriptionUrl())) {
                AnchorElement anch = doc.createAnchorElement();
                
                anch.setInnerHTML("View transcription of similar text");
                anch.setHref(other.getTranscriptionUrl());
                
                div.appendChild(anch);
            }
        }
       
        MasterTableOfContents master = prefatory.getMasterTableOfContents();

        if (master != null) {
            SimplePanel p = new SimplePanel();
            fp.add(p);
            
            Element div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
     
            div.appendChild(BibleDisplay.span("Master table of contents ", 
                    BibleDisplay.MINOR_SECTION));

            sb = new StringBuilder("(detail: "
                    + (master.getTableDetail() == null
                    ? " " : master.getTableDetail().detail()) + "). ");
            
            if (!BibleDisplay.isBlank(master.getStartPage()))
                sb.append("Begins folio " + master.getStartPage() + ". ");
   
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            
            DisclosurePanel disclose = new DisclosurePanel();
            fp.add(disclose);
            ills_incips.add(disclose);
        
            Label expand = new Label("[+ Expand details]");
            expand.setStylePrimaryName("Expander");
            disclose.setHeader(expand);
            
            p = new SimplePanel();
            disclose.setContent(p);
            
            div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            Element subdiv = doc.createDivElement();
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span(
                    "Descriptions: ", "ArticleTitle"));
            
            sb = new StringBuilder();
            if (master.getTableDetail() == Detail.BOOK)
                sb.append("Detailed by book. ");
            else if (master.getTableDetail() == Detail.CHAPTER)
                sb.append("Detailed by chapter. ");
            else if (master.getTableDetail() == Detail.MIXED)
                sb.append("Mixed level of detail (some contents listed by book "
                        + "only, others divided by chapter). ");

            sb.append(master.matchesContents() 
                    ? " (appears to match content). " 
                            : " (does not match content). ");
            subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
            subdiv.appendChild(doc.createBRElement());
            
            subdiv.appendChild(BibleDisplay.textNode(master.getText()));
        }
        
    }
    
    private List<Annotation> getAnnotationsForTitle(Title title) {
        List<Annotation> anns = new ArrayList<Annotation> ();
        
        for (Annotation ann : bible.getProvenPatronHist().annotations()) {
            if (ann.getBook().equals(title.getBookName()))
                anns.add(ann);
        }
        
        return anns;
    }
    
    /**
     * Display information about the bible books in this volume.
     * 
     * @param bible_volume
     * @param fp
     *          The container used to display the information.
     */
    private void displayBibleBooks(BibleVolume bible_volume, FlowPanel fp) {
        StringBuilder sb = new StringBuilder();
        BibleBooks books = bible_volume.getBibleBooks();
        
        for (int j = 0; j < books.size(); j++) {
            Title title = books.title(j);
            
            if (title == null) {
                continue;
            }

            SimplePanel p = new SimplePanel();
            fp.add(p);
            
            Element div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            sb = new StringBuilder((j + 1) + ". ");
            sb.append(title.getBookName() + " ");
            
            div.appendChild(BibleDisplay.span(sb.toString(), 
                    BibleDisplay.MINOR_SECTION));

            sb = new StringBuilder("(");
            sb.append(title.getTextVersion() + "). ");
            
            if (!BibleDisplay.isBlank(title.getStartPage()))
                sb.append("Begins folio " + title.getStartPage() + ". ");
            if (!BibleDisplay.isBlank(title.getGlossType())
                    || !BibleDisplay.isBlank(title.getGlossType2()))
                sb.append("Gloss source(s): ");
            if (!BibleDisplay.isBlank(title.getGlossType()))
                sb.append(title.getGlossType() + ". ");
            if (!BibleDisplay.isBlank(title.getGlossType2()))
                sb.append(title.getGlossType2() + ". ");
            if (bible_volume.getTitleIlls(title) != null) {
                sb.append("No. of illustrations: "
                        + bible_volume.getTitleIlls(title).size());
            } else {
                sb.append("No. of illustrations: 0");
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));

            DisclosurePanel disclose = new DisclosurePanel();
            fp.add(disclose);
            ills_incips.add(disclose);
            
            Label expand = new Label("[+ Expand details]");
            expand.setStylePrimaryName("Expander");
            disclose.setHeader(expand);
            
            p = new SimplePanel();
            disclose.setContent(p);
            
            div = doc.createDivElement();
            BibleDisplay.appendChild(p, div);
            
            Element subdiv = doc.createDivElement();
            subdiv.setClassName("Incipit");
            div.appendChild(subdiv);

            subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
            
            Element ul = doc.createULElement();
            subdiv.appendChild(ul);

            for (TitleIncipit inc : title) {
                if (inc == null) {
                    continue;
                }
                Element li = doc.createLIElement();
                ul.appendChild(li);
                
                sb = new StringBuilder();
                if (!BibleDisplay.isBlank(inc.getText()))
                    sb.append(inc.getText() + " (");
                if (inc.getAccuracy() != null)
                    sb.append(inc.getAccuracy().accuracy() + "). ");
                
                li.setInnerSafeHtml(sanitizer.sanitize(sb.toString()));
            }
            
            subdiv = doc.createDivElement();
            subdiv.setClassName("ContentIlls");
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
            
            ul = doc.createULElement();
            subdiv.appendChild(ul);

            for (Illustration ill : bible_volume.getTitleIlls(title)) {
                if (ill == null) {
                    continue;
                }
                Element li = doc.createLIElement();
                ul.appendChild(li);
                
                sb = new StringBuilder();
                
                if (ill.getNumber() > 0)
                    sb.append(ill.getNumber() + ", ");
                if (!BibleDisplay.isBlank(ill.getFolio()))
                    sb.append("Fol. " + ill.getFolio() + ". ");
                if (!BibleDisplay.isBlank(ill.getKeywords()))
                    sb.append(ill.getKeywords());
                
                li.appendChild(BibleDisplay.textNode(sb.toString() + " "));
                
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    AnchorElement anch = doc.createAnchorElement();
                    
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    li.appendChild(anch);
                }
            }
            
            // Annotations
            List<Annotation> anns = getAnnotationsForTitle(title);

            if (anns != null && anns.size() > 0) {
                subdiv = doc.createDivElement();
                subdiv.setClassName("Floater");
                div.appendChild(subdiv);
                
                subdiv.appendChild(BibleDisplay.span("Annotation(s): ", "ArticleTitle"));
                ul = doc.createULElement();
                subdiv.appendChild(ul);
                
                for (Annotation ann : anns) {
                    if (ann == null) {
                        continue;
                    }
                    Element li = doc.createLIElement();
                    ul.appendChild(li);
                    
                    sb = new StringBuilder();
                    
                    if (!BibleDisplay.isBlank(ann.getFolio()))
                        sb.append("Fol. " + ann.getFolio() + ". ");
                    if (!BibleDisplay.isBlank(ann.getText()))
                        sb.append("\"" + ann.getText() + "\" ");
                    if (!BibleDisplay.isBlank(ann.getName()))
                        sb.append(ann.getName());
                    
                    li.appendChild(BibleDisplay.textNode(sb.toString()));
                    li.appendChild(doc.createBRElement());
                    
                    li.appendChild(BibleDisplay.textNode("Refers to: "
                            + ann.getTextReferenced()));
                }
            }

            if (!BibleDisplay.isBlank(title.getTranscriptionUrl())) {
                AnchorElement anch = doc.createAnchorElement();
                
                anch.setInnerHTML("View transcription of similar text");
                anch.setHref(title.getTranscriptionUrl());
                
                div.appendChild(anch);
            }
        }
    }
    
    /**
     * Display parascriptural and other contents.
     * 
     * @param bible_volume
     * @return
     */
    private FlowPanel displayOtherContents(BibleVolume bible_volume) {
        int volume = bible_volume.getVolume();
        
        FlowPanel cont = new FlowPanel();
        
        ParascripturalItem pi = bible_volume.getParascripturalItem();
        List<MiscContent> misc = bible_volume.miscContents();
        
        if (pi == null && (misc == null || misc.size() == 0)) {
            return cont;
        }
        
        Label title = new Label(Messages.INSTANCE.otherContents());
        title.setStylePrimaryName("SectionHeader");
        cont.add(title);
        
        SimplePanel p = new SimplePanel();
        cont.add(p);
        
        Element div = doc.createDivElement();
        BibleDisplay.appendChild(p, div);
        
        // Litany
        if (pi.getLitanyPresence() == Choice.Y 
                && pi.getLocVol().equals(String.valueOf(volume))) {
            Element subdiv = doc.createDivElement();
            div.appendChild(subdiv);
            subdiv.appendChild(BibleDisplay.span("Litany", "TableSubtitle"));
            
            StringBuilder sb = new StringBuilder();
            
            if (pi.getSneddonId() != null)
                sb.append(" (Sneddon " + pi.getSneddonId().getMessage() + ") ");
            if (pi.getForm() != null)
                sb.append(pi.getForm().formString() + ", ");
            if (!BibleDisplay.isBlank(pi.getPlaceOfOriginUse()))
                sb.append(pi.getPlaceOfOriginUse() + " origin/use. ");
            if (!BibleDisplay.isBlank(pi.getLocStart()))
                sb.append("Begins folio " + pi.getLocStart() + ". ");
            subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
        }
        
        // Canticles
        if (pi.getCanticlePresence() == Choice.Y
                && pi.getVolume() == volume) {
            Element subdiv = doc.createDivElement();
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Canticles", "TableSubtitle"));
            
            StringBuilder sb = new StringBuilder();
            
            if (!BibleDisplay.isBlank(pi.getCanticleType()))
                sb.append(" (" + pi.getCanticleType() + "), ");
            if (!BibleDisplay.isBlank(pi.getCanticleStartFolio()))
                sb.append("begins folio " + pi.getCanticleStartFolio() + ". ");
            
            subdiv.appendChild(BibleDisplay.textNode(sb.toString()));
        }
        
        // Catechisms Prayers Treatises
        List<CatechismsPrayersTreatise> catechisms = pi.catechismPrayersTreatises();
        if (catechisms != null && catechisms.size() > 0){
            Element subdiv = doc.createDivElement();
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Other Religious Texts ", "TableSubtitle"));
            
            Element sdiv = doc.createDivElement();
            subdiv.appendChild(sdiv);
            
            List<Illustration> cills = bible_volume.catechismsIlls();
            if (cills.size() > 0)
                sdiv.setClassName("ContentIlls");
            
            Element ul = doc.createULElement();
            sdiv.appendChild(ul);
            
            for (CatechismsPrayersTreatise ct : catechisms) {
                if (ct.getPresence() == Choice.Y && ct.getVolume() == volume) {
                    Element li = doc.createLIElement();
                    ul.appendChild(li);
                    
                    if (!BibleDisplay.isBlank(ct.getStartFolio())) {
                        li.appendChild(BibleDisplay.textNode(" Begins folio " 
                                + ct.getStartFolio() + ". "));
                        li.appendChild(doc.createBRElement());
                    }
                    if (ct.getDescriptionsFirstLines().size() > 0) {
                        Element desc_list = doc.createULElement();
                        li.appendChild(desc_list);
                        
                        for (String line : ct.getDescriptionsFirstLines()) {
                            Element item = doc.createLIElement();
                            desc_list.appendChild(item);
                            
                            item.appendChild(BibleDisplay.textNode(line));
                        }
                    }
                }
            }
            
            sdiv = doc.createDivElement();
            sdiv.setClassName("ContentIlls");
            subdiv.appendChild(sdiv);
            
            ul = doc.createULElement();
            sdiv.appendChild(ul);
            
            for (Illustration ill : cills) {
                if (ill == null) {
                    continue;
                }
                Element li = doc.createLIElement();
                ul.appendChild(li);
                
                StringBuilder sb = new StringBuilder();
                
                if (ill.getNumber() > 0)
                    sb.append(ill.getNumber() + ", ");
                if (!BibleDisplay.isBlank(ill.getFolio()))
                    sb.append("Fol. " + ill.getFolio() + ". ");
                if (!BibleDisplay.isBlank(ill.getKeywords()))
                    sb.append(ill.getKeywords());
                
                li.appendChild(BibleDisplay.textNode(sb.toString() + " "));
                
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    AnchorElement anch = doc.createAnchorElement();
                    
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    li.appendChild(anch);
                }
            }
        }
        
        // Misc Contents
        if (misc != null && misc.size() > 0) {
            Element subdiv = doc.createDivElement();
            div.appendChild(subdiv);
            
            subdiv.appendChild(BibleDisplay.span("Miscellaneous", "TableSubtitle"));
            
            Element sdiv = doc.createDivElement();
            subdiv.appendChild(sdiv);
            
            List<Illustration> mills = bible_volume.miscContentIlls();
            if (mills.size() > 0)
                sdiv.setClassName("ContentIlls");
            
            Element ul = doc.createULElement();
            sdiv.appendChild(ul);
            
            for (MiscContent mi : misc) {
                if (mi.getVolume() == volume) {
                    Element li = doc.createLIElement();
                    ul.appendChild(li);
                    
                    if (!BibleDisplay.isBlank(mi.getStartFolio())) {
                        li.appendChild(BibleDisplay.textNode(" Begins folio " 
                                + mi.getStartFolio() + ". "));
                        li.appendChild(doc.createBRElement());
                    }
                    if (!BibleDisplay.isBlank(mi.getDescription())) {
                        li.appendChild(BibleDisplay.textNode(mi.getDescription()));
                    }
                }
            }
            
            sdiv = doc.createDivElement();
            sdiv.setClassName("ContentIlls");
            subdiv.appendChild(sdiv);
            
            ul = doc.createULElement();
            sdiv.appendChild(ul);
            
            for (Illustration ill : mills) {
                if (ill == null) {
                    continue;
                }
                Element li = doc.createLIElement();
                ul.appendChild(li);
                
                StringBuilder sb = new StringBuilder();
                
                if (ill.getNumber() > 0)
                    sb.append(ill.getNumber() + ", ");
                if (!BibleDisplay.isBlank(ill.getFolio()))
                    sb.append("Fol. " + ill.getFolio() + ". ");
                if (!BibleDisplay.isBlank(ill.getKeywords()))
                    sb.append(ill.getKeywords());
                
                li.appendChild(BibleDisplay.textNode(sb.toString() + " "));
                
                if (ill.getUrl() != null && !ill.getUrl().equals("")) {
                    AnchorElement anch = doc.createAnchorElement();
                    
                    anch.setInnerHTML("[View image]");
                    anch.setHref(ill.getUrl());
                    li.appendChild(anch);
                }
            }
        }
        
        return cont;
    } 
}
