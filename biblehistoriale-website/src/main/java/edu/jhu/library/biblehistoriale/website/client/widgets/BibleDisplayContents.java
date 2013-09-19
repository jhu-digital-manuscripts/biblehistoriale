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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.Choice;
import edu.jhu.library.biblehistoriale.model.profile.ComestorLetter;
import edu.jhu.library.biblehistoriale.model.profile.Guyart;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.Incipit;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.MasterTableOfContents;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;
import edu.jhu.library.biblehistoriale.model.profile.Title;
import edu.jhu.library.biblehistoriale.model.profile.TitleIncipit;
import edu.jhu.library.biblehistoriale.website.client.Messages;

/**
 * Contains methods for displaying textual content of a Bible.
 */
public class BibleDisplayContents {
    
    private static final String BH = "BH";
    private static final String BXIII = "BXIII";
    private static final String RAOUL = "Raoul de Presles";
    
    private static final String MORALITES = "moralite";
    private static final String MULTIPLE = "multiple";
    private static final String MIXED = "mixed";
    
    private Bible bible;
    
    private Document doc;
    
    private final List<HandlerRegistration> handlers;
    private final List<DisclosurePanel> descriptions;
    private final List<DisclosurePanel> ills_incips;
    
    public BibleDisplayContents(Document doc) {
        this.doc = doc;
        
        this.handlers = new ArrayList<HandlerRegistration> ();
        this.descriptions = new ArrayList<DisclosurePanel> ();
        this.ills_incips = new ArrayList<DisclosurePanel> ();
    }
    
    /**
     * Remove all handlers from the DOM.
     */
    public void detachContent() {
        for (HandlerRegistration hr : handlers) {
            hr.removeHandler();
        }
    }
    
    private Map<String, Integer> findTextSources(List<BibleBooks> vols) {
        int total = 0;
        int gloss_total = 0;
        
        int[] versions = new int[4];
        int[] glosses = new int[5];
        
        for (BibleBooks bb : vols) {
            for (Title title : bb) {
                total++;
                gloss_total++;
                
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
                
                String gloss2 = title.getGlossType2();
                gloss_total++;
                if (gloss2.equals(MORALITES)) {
                    glosses[0]++;
                } else if (gloss2.equals(BH)) {
                    glosses[1]++;
                } else if (gloss2.equals(BXIII)) {
                    glosses[2]++;
                } else if (gloss2.equals(MIXED)) {
                    glosses[3]++;
                } else if (gloss2.equals(MULTIPLE)) {
                    glosses[4]++;
                } else {
                    gloss_total--;
                }
            }
        }

        Map<String, Integer> percents = new HashMap<String, Integer> ();
        percents.put(BH, (int) Math.round((double) versions[0] / total * 100));
        percents.put(BXIII, (int) Math.round((double) versions[1] / total * 100));
        percents.put(RAOUL, (int) Math.round((double) versions[2] / total * 100));
        percents.put("other", (int) Math.round((double) versions[3] / total * 100));
        
        percents.put(MORALITES + "gloss",
                (int) Math.round((double) glosses[0] / gloss_total * 100));
        percents.put(BH + "gloss", 
                (int) Math.round((double) glosses[1] / gloss_total * 100));
        percents.put(BXIII + "gloss", 
                (int) Math.round((double) glosses[2] / gloss_total * 100));
        percents.put(MIXED + "gloss", 
                (int) Math.round((double) glosses[3] / gloss_total * 100));
        percents.put(MULTIPLE + "gloss", 
                (int) Math.round((double) glosses[4] / gloss_total * 100));
        
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
            return null;
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
     * Returns a ScrollPanel containing the textual contents
     * of the Bible object
     * 
     * @param bible
     * @return
     */
    public ScrollPanel displayContentView(Bible bible) {
        this.bible = bible;
        
        StringBuilder sb = new StringBuilder();
        
        FlowPanel panel = new FlowPanel();
        ScrollPanel top = new ScrollPanel();
        top.add(panel);
        
        top.setWidth("100%");
        top.setHeight("100%");
        
        List<BibleBooks> volume = bible.getTextualContent().bibleBooks();
        
        // Title
        SimplePanel p = new SimplePanel();
        panel.add(p);
        
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
        
        panel.add(new HTML(Messages.INSTANCE.remarks()));
        
        p = new SimplePanel();
        panel.add(p);
        
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
        
        IllustrationList ills = bible.getIllustrations();
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
        
        List<BibleBooks> vols = bible.getTextualContent().bibleBooks();
        
        sb = new StringBuilder("Gloss types/sources include: ");
        
        for (BibleBooks bb : vols) {
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
                + (matchingTextAndGloss(vols)
                        ? "" : "do not always ") + "match. ");
        
        List<Title> mults = findTitlesWithMultiple(vols);
        
        for (Title title : mults) {
            sb.append(title.getTextVersion() + " " + title.getBookName()
                    + " includes other glosses. ");
        }
        
        div.appendChild(BibleDisplay.textNode(sb.toString()));
        div.appendChild(doc.createBRElement());
        
        FlowPanel options_panel = new FlowPanel();
        panel.add(options_panel);
        
        HTML desc_link = new HTML("<u>Expand/collapse all descriptions</u>");
        HTML incip_link = new HTML("<u>Expand/collapse all incipits and " +
                "illustrations</u>");
        
        desc_link.setStylePrimaryName("Expander");
        desc_link.addStyleName("Indent");
        incip_link.setStylePrimaryName("Expander");
        incip_link.addStyleName("Clickable");
        incip_link.addStyleName("Indent");
        
        options_panel.add(desc_link);
        options_panel.add(incip_link);
        
        handlers.add(desc_link.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for (DisclosurePanel p : descriptions) {
                    p.setOpen(!p.isOpen());
                }
            }
        }));
        
        handlers.add(incip_link.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for (DisclosurePanel p : ills_incips) {
                    p.setOpen(!p.isOpen());
                }
            }
        }));
        
        panel.add(displayByVolumes());
        
        return top;
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
    private FlowPanel displayByVolumes() {
        int num_vols = numVols();
        int[] ill_by_volume = countIllsInVols(bible.getIllustrations());
        
        FlowPanel tables = new FlowPanel();
        //List<FlexTable> table_list = new ArrayList<FlexTable> ();
        
        // Init tables and add the title row
        for (int i = 0; i < num_vols; i++) {
            int vol = i + 1;
            
            BibleVolume bible_volume = new BibleVolume(bible, vol);
            
            StringBuilder sb = new StringBuilder();
            FlexTable table = new FlexTable();
            table.setStylePrimaryName("OwnerTable");
            table.addStyleName("VolumeTable");
            
            //table_list.add(table);
            tables.add(table);
            
            SimplePanel p = new SimplePanel();
            table.setWidget(0, 0, p);
            
            Element div = doc.createDivElement();
            div.setClassName("VolumeTitle");
            BibleDisplay.appendChild(p, div);
            
            Element titlediv = doc.createDivElement();
            titlediv.setClassName("Title");
            div.appendChild(titlediv);
            
            titlediv.appendChild(BibleDisplay.textNode("Volume " + vol));
            div.appendChild(doc.createBRElement());
            
            ParascripturalItem pi = bible.getTextualContent().parascripturalItem();
            String[] titles = getFirstAndLastBooks(vol);
            
            if (hasPrefaceInVolume(i+1))
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
            
            sb = new StringBuilder("Folios: ");
            
            for (IndVolume ind : bible.getPhysChar().getFolios()) {
                if (ind.getVolume() == vol) {
                    sb.append(ind.getValue());
                }
            }
            
            try {
                sb.append("    Total illustrations: " + ill_by_volume[i]);
            } catch (IndexOutOfBoundsException e) {
                // TODO
            }
            
            div.appendChild(BibleDisplay.textNode(sb.toString()));
            
            HTML label = new HTML("Prefatory Matter");
            label.setStylePrimaryName("TableSubtitle");
            table.setWidget(1, 0, label);
            
            PrefatoryMatter prefatory = bible_volume.getPrefatoryMatter();
            
            if (prefatory != null) {
                String descr = prefatory.getPrefactoryNote() != null 
                        ?"<i>Description:</i> " + prefatory.getPrefactoryNote()
                                : "";
                table.setWidget(2, 0, new HTML(descr));
                
                FlowPanel fp = new FlowPanel();
                table.setWidget(3, 0, fp);
                
                for (int j = 0; j < prefatory.otherPrefaces().size(); j++) {
                    OtherPreface other = prefatory.otherPrefaces().get(j);
                    
                    p = new SimplePanel();
                    fp.add(p);
                    
                    div = doc.createDivElement();
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
                    
                    // TODO: details
                    subdiv.appendChild(BibleDisplay.span("Incipit(s): ", 
                            "ArticleTitle"));
                    subdiv.appendChild(BibleDisplay.textNode(other.getText() + " (" 
                            + other.getAccuracy().accuracy() + "). "));
                    
                    subdiv = doc.createDivElement();
                    subdiv.setClassName("ContentIlls");
                    div.appendChild(subdiv);
                    
                    subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
                    for (Illustration ill : bible_volume.getOtherPrefacesIlls(other)) {
                        subdiv.appendChild(BibleDisplay.textNode(
                                ill.getBook() + ", " + ill.getNumber()));
                    }
                    
                }

                for (int j = 0; j < prefatory.guyartList().size(); j++) {
                    
                    Guyart guyart = prefatory.guyartList().get(j);

                    p = new SimplePanel();
                    fp.add(p);
                    
                    div = doc.createDivElement();
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

                    // TODO: details
                    subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
                    subdiv.appendChild(BibleDisplay.textNode(guyart.getText() + " (" 
                            + guyart.getAccuracy().accuracy() + "). "));
                    
                    subdiv = doc.createDivElement();
                    subdiv.setClassName("ContentIlls");
                    div.appendChild(subdiv);
                    
                    subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
                    for (Illustration ill : bible_volume.getGuyartIlls(guyart)) {
                        subdiv.appendChild(BibleDisplay.textNode(
                                ill.getBook() + ", " + ill.getNumber()));
                    }
                }
                
                for (int j = 0; j < prefatory.comestorLetters().size(); j++) {
                    ComestorLetter cs = prefatory.comestorLetters().get(j);
                    
                    p = new SimplePanel();
                    fp.add(p);
                    
                    div = doc.createDivElement();
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
                    
                    // TODO: details
                    subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
                    
                    Element ul = doc.createULElement();
                    subdiv.appendChild(ul);
                    
                    for (Incipit inc : cs.incipits()) {
                        Element li = doc.createLIElement();
                        ul.appendChild(li);
                        
                        li.setInnerHTML(inc.getText() + " ("
                                + inc.getAccuracy().accuracy() + "). ");
                    }
                    
                    subdiv = doc.createDivElement();
                    subdiv.setClassName("ContentIlls");
                    div.appendChild(subdiv);
                    
                    subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
                    for (Illustration ill : bible_volume.getComestorLetterIlls(cs)) {
                        subdiv.appendChild(BibleDisplay.textNode(
                                ill.getBook() + ", " + ill.getNumber()));
                    }
                }
                
                for (int j = 0; j < prefatory.guyartList().size(); j++) {
                    
                    OtherPreface other = prefatory.comestorList().get(j);

                    p = new SimplePanel();
                    fp.add(p);
                    
                    div = doc.createDivElement();
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

                    // TODO: details
                    subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
                    subdiv.appendChild(BibleDisplay.textNode(other.getText() + " (" 
                            + other.getAccuracy().accuracy() + "). "));
                    
                    subdiv = doc.createDivElement();
                    subdiv.setClassName("ContentIlls");
                    div.appendChild(subdiv);
                    
                    subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
                    for (Illustration ill : bible_volume.getComestorIlls(other)) {
                        subdiv.appendChild(BibleDisplay.textNode(
                                ill.getBook() + ", " + ill.getNumber()));
                    }
                }
               
                MasterTableOfContents master = prefatory.getMasterTableOfContents();
                
                if (master != null) {
                    p = new SimplePanel();
                    fp.add(p);
                    
                    div = doc.createDivElement();
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

                    // TODO: details
                    subdiv.appendChild(BibleDisplay.textNode(master.getText() 
                            + (master.matchesContents() 
                            ? " (matches content). " : " (does not match content). ")));
                }
            }
            
            BibleBooks books = bible_volume.getBibleBooks();
            
            label = new HTML("Biblical books");
            label.setStylePrimaryName("TableSubtitle");
            table.setWidget(4, 0, label);
            
            FlowPanel fp = new FlowPanel();
            table.setWidget(5, 0, fp);
            
            for (int j = 0; j < books.size(); j++) {
                Title title = books.title(j);

                p = new SimplePanel();
                fp.add(p);
                
                div = doc.createDivElement();
                BibleDisplay.appendChild(p, div);
                
                sb = new StringBuilder((j + 1) + ". ");
                sb.append(title.getBookName() + " ");
                
                div.appendChild(BibleDisplay.span(sb.toString(), 
                        BibleDisplay.MINOR_SECTION));

                sb = new StringBuilder("(");
                sb.append(title.getTextVersion() + "). ");
                
                if (!BibleDisplay.isBlank(title.getStartPage()))
                    sb.append("Begins folio " + title.getStartPage() + ". ");
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

                // TODO: details
                subdiv.appendChild(BibleDisplay.span("Incipit(s): ", "ArticleTitle"));
                
                Element ul = doc.createULElement();
                subdiv.appendChild(ul);
                
                for (TitleIncipit inc : title) {
                    Element li = doc.createLIElement();
                    ul.appendChild(li);
                    
                    li.setInnerHTML("[" + inc.getTextType() + "] " 
                            + inc.getText() + " (" + inc.getAccuracy().accuracy() 
                            + "). ");
                }
                
                subdiv = doc.createDivElement();
                subdiv.setClassName("ContentIlls");
                div.appendChild(subdiv);
                
                subdiv.appendChild(BibleDisplay.span("Illustrations: ", "ArticleTitle"));
                
                ul = doc.createULElement();
                subdiv.appendChild(ul);
                
                for (Illustration ill : bible_volume.getTitleIlls(title)) {
                    Element li = doc.createLIElement();
                    ul.appendChild(li);
                    
                    li.setInnerHTML(ill.getNumber() + ". " + ill.getBook() + ", " 
                            + ill.getFolio() + ". Keywords: " + ill.getKeywords());
                }
            }
        }
        
        return tables;
    }
    
}
