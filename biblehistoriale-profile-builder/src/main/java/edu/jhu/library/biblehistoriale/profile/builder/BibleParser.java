package edu.jhu.library.biblehistoriale.profile.builder;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.jhu.library.biblehistoriale.model.profile.Annotation;
import edu.jhu.library.biblehistoriale.model.profile.Berger;
import edu.jhu.library.biblehistoriale.model.profile.BibleBooks;
import edu.jhu.library.biblehistoriale.model.profile.BiblioEntry;
import edu.jhu.library.biblehistoriale.model.profile.Bibliography;
import edu.jhu.library.biblehistoriale.model.profile.BookType;
import edu.jhu.library.biblehistoriale.model.profile.CatalogerClassification;
import edu.jhu.library.biblehistoriale.model.profile.CatechismsPrayersTreatise;
import edu.jhu.library.biblehistoriale.model.profile.Classification;
import edu.jhu.library.biblehistoriale.model.profile.ComestorLetter;
import edu.jhu.library.biblehistoriale.model.profile.Contributor;
import edu.jhu.library.biblehistoriale.model.profile.DecorationSummary;
import edu.jhu.library.biblehistoriale.model.profile.Dimensions;
import edu.jhu.library.biblehistoriale.model.profile.Folios;
import edu.jhu.library.biblehistoriale.model.profile.Guyart;
import edu.jhu.library.biblehistoriale.model.profile.Illustration;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.Incipit;
import edu.jhu.library.biblehistoriale.model.profile.IndVolume;
import edu.jhu.library.biblehistoriale.model.profile.MasterTableOfContents;
import edu.jhu.library.biblehistoriale.model.profile.Materials;
import edu.jhu.library.biblehistoriale.model.profile.MiscContent;
import edu.jhu.library.biblehistoriale.model.profile.OtherPreface;
import edu.jhu.library.biblehistoriale.model.profile.Owner;
import edu.jhu.library.biblehistoriale.model.profile.Ownership;
import edu.jhu.library.biblehistoriale.model.profile.PageLayout;
import edu.jhu.library.biblehistoriale.model.profile.ParascripturalItem;
import edu.jhu.library.biblehistoriale.model.profile.Personalization;
import edu.jhu.library.biblehistoriale.model.profile.PersonalizationItem;
import edu.jhu.library.biblehistoriale.model.profile.PhysicalCharacteristics;
import edu.jhu.library.biblehistoriale.model.profile.PrefatoryMatter;
import edu.jhu.library.biblehistoriale.model.profile.Production;
import edu.jhu.library.biblehistoriale.model.profile.ProvenPatronHistory;
import edu.jhu.library.biblehistoriale.model.profile.QuireStructure;
import edu.jhu.library.biblehistoriale.model.profile.SecundoFolio;
import edu.jhu.library.biblehistoriale.model.profile.Signature;
import edu.jhu.library.biblehistoriale.model.profile.Sneddon;
import edu.jhu.library.biblehistoriale.model.profile.TextualContent;
import edu.jhu.library.biblehistoriale.model.profile.Title;
import edu.jhu.library.biblehistoriale.model.profile.Title.TitleIncipit;
import edu.jhu.library.biblehistoriale.model.profile.Volumes;

public class BibleParser {
    
    public static String getAttributeValue(Node node, String attribute) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return ((Element) node).getAttribute(attribute);
        }
        
        return null;
    }
    
    public static int getIntegerAttribute(Node node, String attribute) 
            throws ProfileBuilderException {
        
        try {
            return Integer.parseInt(
                    getAttributeValue(node, attribute));
        } catch (NumberFormatException e) {
            throw new ProfileBuilderException(e);
        }
    }
    
    public static int getIntegerElement(Node node) throws ProfileBuilderException {
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                return Integer.parseInt(node.getTextContent());
            } catch (NumberFormatException e) {
                throw new ProfileBuilderException(e);
            }
        }
        
        return -1;
    }
    
    public static String getElementText(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return node.getTextContent();
        }
        return null;
    }
    
    public static String name(Node node) {
        return node.getNodeName();
    }
    
    public static Bibliography parseBibliography(Node bible_child) {
        Bibliography bib = new Bibliography();
        
        List<BiblioEntry> bib_entries = new ArrayList<BiblioEntry> ();
        
        Node child = bible_child.getFirstChild();
        while (child != null) {
            if (name(child).equals("biblioEntry")) {
                bib_entries.add(biblioEntry(child));
            }
            child = child.getNextSibling();
        }
        
        bib.setBiblioEntries(bib_entries);
        
        return bib;
    }

    private static BiblioEntry biblioEntry(Node child) {
        BiblioEntry entry = new BiblioEntry();
        
        List<String> authors = new ArrayList<String> ();
        List<String> links = new ArrayList<String> ();
        
        Node node = child.getFirstChild();
        while (node != null) {
            if (name(node).equals("bibAuthor")) {
                authors.add(getElementText(node));
            } else if (name(node).equals("articleTitle")) {
                entry.setArticleTitle(getElementText(node));
            } else if (name(node).equals("bookOrJournalTitle")) {
                entry.setBookOrJournalTitle(getElementText(node));
            } else if (name(node).equals("publicationInfo")) {
                entry.setPublicationInfo(getElementText(node));
            } else if (name(node).equals("articleLink")) {
                links.add(getElementText(node));
            }
            
            node = node.getNextSibling();
        }
        
        entry.setBibAuthors(authors);
        entry.setArticleLinks(links);
        
        return entry;
    }

    public static TextualContent parseTextualContent(Node bible_child) 
            throws ProfileBuilderException {
        TextualContent content = new TextualContent();
        
        List<PrefatoryMatter> prefactories =
                new ArrayList<PrefatoryMatter> ();
        List<BibleBooks> books = new ArrayList<BibleBooks> ();
        List<MiscContent> miscs = new ArrayList<MiscContent> ();
        List<String> notes = new ArrayList<String> ();
        
        Node child = bible_child.getFirstChild();
        while (child != null) {
            
            if (name(child).equals("prefatoryMatter")) {
                PrefatoryMatter prefact = prefatoryMatter(child);
                prefactories.add(prefact);
            } else if (name(child).equals("bibleBooks")) {
                BibleBooks book = bibleBook(child);
                books.add(book);
            } else if (name(child).equals("parascripturalItems")) {
                ParascripturalItem item = parascripturalItem(child);
                content.setParascripturalItem(item);
            } else if (name(child).equals("miscContents")) {
                MiscContent misc = miscContent(child);
                miscs.add(misc);
            } else if (name(child).equals("notes")) {
                notes.add(getElementText(child));
            }
            
            child = child.getNextSibling();
            
        }
        
        content.setVolume(getIntegerAttribute(bible_child, "volume"));
        content.setPrefactoryMatters(prefactories);
        content.setBibleBooks(books);
        content.setMiscContents(miscs);
        content.setNotes(notes);
        
        return content;
    }

    private static MiscContent miscContent(Node child) throws ProfileBuilderException {
        MiscContent misc = new MiscContent();
        
        Node node = child.getFirstChild();
        while (node != null) {
            if (name(node).equals("description")) {
                misc.setDescription(getElementText(node));
            }
            node = node.getNextSibling();
        }
        
        misc.setVolume(getIntegerAttribute(child, "volume"));
        misc.setStartFolio(getAttributeValue(child, "startFolio"));
        misc.setEndFolio(getAttributeValue(child, "endFolio"));
        
        return misc;
    }

    private static ParascripturalItem parascripturalItem(Node child) 
            throws ProfileBuilderException {
        ParascripturalItem item = new ParascripturalItem();
        
        List<CatechismsPrayersTreatise> treatises = 
                new ArrayList<CatechismsPrayersTreatise> ();
        
        Node node = child.getFirstChild();
        while (node != null) {
            if (name(node).equals("litany")) {
                Node lit_node = node.getFirstChild();
                while (lit_node != null) {
                    if (name(lit_node).equals("placeOfOriginUse")) {
                        item.setPlaceOfOriginUse(
                                getElementText(lit_node));
                    }
                    lit_node = lit_node.getNextSibling();
                }
                
                item.setForm(getAttributeValue(node, "form"));
                item.setLocVol(getAttributeValue(node, "locVol"));
                item.setLocStart(getAttributeValue(node, "locStart"));
                item.setLocEnd(getAttributeValue(node, "locEnd"));
                item.setLitanyPresence(
                        getAttributeValue(node, "presence"));
                item.setSneddonId(getAttributeValue(node, "sneddonID"));
            } else if (name(node).equals("canticles")) {
                Node can_node = node.getFirstChild();
                while (can_node != null) {
                    if (name(can_node).equals("canticleType")) {
                        item.setCanticleType(
                                getElementText(can_node));
                    }
                    can_node = can_node.getNextSibling();
                }
                
                item.setCanticleEndFolio(
                        getAttributeValue(node, "endFolio"));
                item.setCanticleStartFolio(
                        getAttributeValue(node, "startFolio"));
                item.setVolume(getIntegerAttribute(node, "volume"));
                item.setCanticlePresence(
                        getAttributeValue(node, "presence"));
            } else if (name(node).equals("addedPrologue")) {
                item.setJeanDeBlois(getAttributeValue(node, "jeanDeBlois"));
                item.setJerome(getAttributeValue(node, "jerome"));
            } else if (name(node).equals("catechismsPrayersTreatises")) {
                treatises.add(treatise(node));
            }
            
            node = node.getNextSibling();
        }
        
        item.setCatechismPrayersTreatises(treatises);
        
        return item;
    }

    private static CatechismsPrayersTreatise treatise(Node node) 
            throws ProfileBuilderException {
        CatechismsPrayersTreatise treatise = 
                new CatechismsPrayersTreatise();
        
        List<String> first_lines = new ArrayList<String> ();
        
        Node child = node.getFirstChild();
        while (child != null) {
            if (name(child).equals("descriptionsFirstLines")) {
                first_lines.add(getElementText(child));
            }
            child = child.getNextSibling();
        }
        
        treatise.setDescriptionsFirstLines(first_lines);
        treatise.setPresence(getAttributeValue(node, "presence"));
        treatise.setStartFolio(getAttributeValue(node, "startFolio"));
        treatise.setEndFolio(getAttributeValue(node, "endFolio"));
        treatise.setVolume(getIntegerAttribute(node, "volume"));
        
        return treatise;
    }

    private static BibleBooks bibleBook(Node child) throws ProfileBuilderException {
        BibleBooks books = new BibleBooks();
        
        List<Title> titles = new ArrayList<Title> ();
        
        Node node = child.getFirstChild();
        while (node != null) {
            if (name(node).equals("title")) {
                titles.add(title(node));
            }
            node = node.getNextSibling();
        }
        
        books.setTitles(titles);
        books.setVolume(getIntegerAttribute(child, "volume"));
        
        return books;
    }

    private static Title title(Node node) {
        Title title = new Title();
        
        List<TitleIncipit> incipits = new ArrayList<TitleIncipit> ();
        
        Node child = node.getFirstChild();
        while (child != null) {
            
            if (name(child).equals("incipit")) {
                TitleIncipit inc = title.new TitleIncipit();
                
                inc.setAccuracy(getAttributeValue(child, "accuracy"));
                inc.setTextType(getAttributeValue(child, "textType"));
                inc.setText(getElementText(child));
                
                incipits.add(inc);
            } else if (name(child).equals("editions")) {
                title.setEditions(getElementText(child));
            } else if (name(child).equals("bookNote")) {
                title.setBookNote(getElementText(child));
            }
            
            child = child.getNextSibling();
        }
        
        title.setIncipit(incipits);
        title.setBookName(getAttributeValue(node, "bookName"));
        title.setHasChapterNames(getAttributeValue(node, "chapterNames"));
        title.setStartPage(getAttributeValue(node, "startPage"));
        title.setGlossType(getAttributeValue(node, "glossType"));
        title.setGlossType2(getAttributeValue(node, "glossType2"));
        title.setEndPage(getAttributeValue(node, "endPage"));
        title.setHasTableOfContents(
                getAttributeValue(node, "tableOfContents").equals("y"));
        title.setTextVersion(getAttributeValue(node, "textVersion"));
        
        return title;
    }

    private static PrefatoryMatter prefatoryMatter(Node child) 
            throws ProfileBuilderException {
        PrefatoryMatter matter = new PrefatoryMatter();
        
        List<OtherPreface> others = new ArrayList<OtherPreface> ();
        List<Guyart> guyarts = new ArrayList<Guyart> ();
        List<ComestorLetter> letters = new ArrayList<ComestorLetter> ();
        List<OtherPreface> comestors = new ArrayList<OtherPreface> ();
        
        Node node = child.getFirstChild();
        while (node != null) {
            
            if (name(node).equals("otherPreface")) {
                OtherPreface other = new OtherPreface();
                
                other.setText(getElementText(node));
                other.setAccuracy(getAttributeValue(node, "accuracy"));
                other.setStartPage(getAttributeValue(node, "startPage"));
                
                others.add(other);
            } else if (name(node).equals("guyart")) {
                guyarts.add(guyart(node));
            } else if (name(node).equals("comestorLetter")) {
                letters.add(comestorLetter(node));
            } else if (name(node).equals("MasterTableOfContents")) {
                MasterTableOfContents master = new MasterTableOfContents();
                
                master.setStartPage(getAttributeValue(node, "startPage"));
                master.setTableDetail(
                        getAttributeValue(node, "tableDetail"));
                master.setMatchesContents(
                        getAttributeValue(node, "matchesContents")
                        .equals("y"));
                master.setText(getElementText(node));
                
                matter.setMasterTableOfContents(master);
            } else if (name(node).equals("comestor")) {
                OtherPreface other = new OtherPreface();
                
                other.setText(getElementText(node));
                other.setAccuracy(getAttributeValue(node, "accuracy"));
                other.setStartPage(getAttributeValue(node, "startPage"));
                
                comestors.add(other);
            } else if (name(node).equals("prefatoryNote")) {
                matter.setPrefactoryNote(getElementText(node));
            }
            
            node = node.getNextSibling();
        }
        
        matter.setVolume(getIntegerAttribute(child, "volume"));
        matter.setOtherPrefaces(others);
        matter.setGuyartList(guyarts);
        matter.setComestorLetters(letters);
        matter.setComestorList(comestors);
        
        return matter;
    }

    private static ComestorLetter comestorLetter(Node node) {
        ComestorLetter letter = new ComestorLetter();
        
        List<Incipit> incipits = new ArrayList<Incipit> ();
        
        Node child = node.getFirstChild();
        while (child != null) {
            if (name(child).equals("incipit")) {
                incipits.add(incipit(child));
            }
            child = child.getNextSibling();
        }
        
        letter.setIncipits(incipits);
        letter.setStartPage(getAttributeValue(node, "startPage"));
        
        return letter;
    }

    private static Guyart guyart(Node node) {
        Guyart guy = new Guyart();
        
        Node child = node.getFirstChild();
        while (child != null) {
            if (name(child).equals("incipit")) {
                guy.setIncipit(incipit(child));
            }
            child = child.getNextSibling();
        }
        
        guy.setStartPage(getAttributeValue(node, "startPage"));
        guy.setContainsGuyartName(
                getAttributeValue(node, "containsGuyartName")
                .equals("y"));
        
        return guy;
    }

    private static Incipit incipit(Node node) {
        Incipit incipit = new Incipit();
        
        incipit.setAccuracy(getAttributeValue(node, "accuracy"));
        incipit.setText(getElementText(node));
        
        return incipit;
    }

    public static Classification parseClassification(Node bible_child) 
            throws ProfileBuilderException {
        Classification classification = new Classification();
        
        NodeList children = bible_child.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("shelfmarks")) {
                List<String> former_shelfmarks = new ArrayList<String> ();
                
                NodeList nodes = child.getChildNodes();
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    
                    if (name(node).equals("currentCity")) {
                        classification.setCurrentCity(
                                getElementText(node));
                    } else if (name(node).equals("currentRepository")) {
                        classification.setCurrentRepository(
                                getElementText(node));
                    } else if (name(node).equals("currentShelfmark")) {
                        classification.setCurrentShelfmark(
                                getElementText(node));
                    } else if (name(node).equals("formerShelfmark")) {
                        former_shelfmarks.add(
                                getElementText(node));
                    } else if (name(node).equals("repositoryLink")) {
                        classification.setRepositoryLink(
                                getElementText(node));
                    }
                }
                
                classification.setFormerShelfmarks(former_shelfmarks);
            } else if (name(child).equals("titles")) {
                NodeList nodes = child.getChildNodes();
                
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    
                    if (name(node).equals("coverTitle")) {
                        classification.setCoverTitle(
                                getElementText(node));
                    } else if (name(node).equals("rubricTitle")) {
                        classification.setRubricTitle(
                                getElementText(node));
                    }
                }
            } else if (name(child).equals("bookType")) {
                BookType type = new BookType();
                
                type.setType(getAttributeValue(child, "collectionType"));
                type.setTech(getAttributeValue(child, "technology"));
                
                classification.setBookType(type);
            } else if (name(child).equals("catalogerClassifications")) {
                classification.setClassification(
                        catalogerClassification(child));
            }
        }
        
        return classification;
    }

    private static CatalogerClassification catalogerClassification(Node child) 
            throws ProfileBuilderException {
        CatalogerClassification cc = new CatalogerClassification();
        
        NodeList nodes = child.getChildNodes();
        
        List<SecundoFolio> secundos = new ArrayList<SecundoFolio>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("bergerClass")) {
                Berger berger = new Berger();
                
                berger.setCategory(getAttributeValue(node, "category"));
                berger.setBhcSubtype(
                        getAttributeValue(node, "bhcSubtype"));
                
                cc.setBergerClass(berger);
            } else if (name(node).equals("sneddonClass")) {
                cc.setSneddonClass(sneddon(node));
            } else if (name(node).equals("fournieCatalog")) {
                NodeList fournies = node.getChildNodes();
                
                for (int j = 0; j < fournies.getLength(); j++) {
                    Node fournie = fournies.item(j);
                    
                    if (name(fournie).equals("fournieNumber")) {
                        cc.setFournieNumber(
                                getElementText(fournie));
                    } else if (name(fournie).equals("fournieLink")) {
                        cc.setFournieLink(
                                getElementText(fournie));
                    }
                }
            } else if (name(node).equals("secundoFolio")) {
                SecundoFolio secundo = new SecundoFolio();
                
                secundo.setValue(getElementText(node));
                secundo.setVolume(getIntegerAttribute(node, "volume"));
                
                secundos.add(secundo);
            } else if (name(node).equals("classificationNotes")) {
                cc.setClassificationNote(getElementText(node));
            }
        }
        
        cc.setSecundoFolios(secundos);
        
        return cc;
    }

    private static Sneddon sneddon(Node node) {
        Sneddon sneddon = new Sneddon();
        
        NodeList children = node.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("siglum")) {
                sneddon.setSiglum(getElementText(child));
            } else if (name(child).equals("entry")) {
                sneddon.setEntry(getElementText(child));
            }
        }
        
        sneddon.setCategory(getAttributeValue(node, "category"));
        sneddon.setSub1(getAttributeValue(node, "subcategory1"));
        sneddon.setSub2(getAttributeValue(node, "subcategory2"));
        sneddon.setSub3(getAttributeValue(node, "subcategory3"));
        
        return sneddon;
    }

    public static IllustrationList parseIllustrations(Node bible_child) 
            throws ProfileBuilderException {
        IllustrationList ills = new IllustrationList();
        
        NodeList children = bible_child.getChildNodes();
        
        List<Illustration> illustrations = new ArrayList<Illustration> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("decorationSummary")) {
                ills.setDecorationSummary(decorationSummary(child));
            } else if (name(child).equals("illustrationList")) {
                NodeList nodes = child.getChildNodes();
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    
                    if (name(node).equals("illustration")) {
                        Illustration ill = illustration(node);
                        illustrations.add(ill);
                    }
                }
                ills.setIllustrations(illustrations);
            } else if (name(child).equals("illustrationNote")) {
                ills.setIllustrationNote(getElementText(child));
            }
        }
        
        return ills;
    }

    private static Illustration illustration(Node node) throws ProfileBuilderException {
        Illustration ill = new Illustration();
        
        NodeList children = node.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("keywords")) {
                ill.setKeywords(getElementText(child));
            } else if (name(child).equals("link")) {
                ill.setUrl(getElementText(child));
            }
        }
        
        ill.setBook(getAttributeValue(node, "book"));
        ill.setFolio(getAttributeValue(node, "folio"));
        ill.setNumber(getIntegerAttribute(node, "number"));
        ill.setVolume(getIntegerAttribute(node, "volume"));
        
        return ill;
    }

    private static DecorationSummary decorationSummary(Node child) 
            throws ProfileBuilderException {
        DecorationSummary summary = new DecorationSummary();
        
        NodeList nodes = child.getChildNodes();
        
        List<String> workshops = new ArrayList<String> ();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("artistWorkshop")) {
                workshops.add(getElementText(node));
            }
        }
        
        summary.setArtistWorkshops(workshops);
        summary.setBasDePage(getAttributeValue(child, "basDePage"));
        summary.setDecoratedInitials(
                getAttributeValue(child, "decoratedInitials"));
        summary.setFoliateBorder(
                getAttributeValue(child, "foliateBorder"));
        summary.setIllStyle(getAttributeValue(child, "illStyle"));
        summary.setLargeIlls(getIntegerAttribute(child, "largeIlls"));
        summary.setNumber(getIntegerAttribute(child, "number"));
        
        return summary;
    }

    public static ProvenPatronHistory parsePatronHist(Node bible_child) 
            throws ProfileBuilderException {
        ProvenPatronHistory hist = new ProvenPatronHistory();
        
        NodeList children = bible_child.getChildNodes();
        
        List<Ownership> owners = new ArrayList<Ownership> ();
        List<Annotation> annotations = new ArrayList<Annotation> ();
        List<String> provenances = new ArrayList<String> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("production")) {
                hist.setProduction(production(child));
            } else if (name(child).equals("ownership")) {
                owners.add(ownership(child));
            } else if (name(child).equals("personalization")) {
                hist.setPersonalization(personalization(child));
            } else if (name(child).equals("annotation")) {
                annotations.add(annotation(child));
            } else if (name(child).equals("provenanceNote")) {
                provenances.add(getElementText(child));
            }
        }
        
        hist.setOwnerships(owners);
        hist.setAnnotations(annotations);
        hist.setProvenanceNote(provenances);
        
        return hist;
    }

    private static Annotation annotation(Node child) throws ProfileBuilderException {
        Annotation ann = new Annotation();
        
        NodeList nodes = child.getChildNodes();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("name")) {
                ann.setName(getElementText(node));
            } else if (name(node).equals("date")) {
                ann.setDate(getElementText(node));
            } else if (name(node).equals("textReferenced")) {
                ann.setTextReferenced(getElementText(node));
            } else if (name(node).equals("text")) {
                ann.setText(getElementText(node));
            }
        }
        
        ann.setBook(getAttributeValue(child, "book"));
        ann.setVolume(getIntegerAttribute(child, "volume"));
        ann.setFolio(getAttributeValue(child, "folio"));
        ann.setType(getAttributeValue(child, "type"));
        
        return ann;
    }

    private static Personalization personalization(Node child) 
            throws ProfileBuilderException {
        Personalization person = new Personalization();
        
        NodeList nodes = child.getChildNodes();
        
        List<Signature> sigs = new ArrayList<Signature> ();
        List<String> dedications = new ArrayList<String> ();
        List<PersonalizationItem> legals = 
                new ArrayList<PersonalizationItem> ();
        List<PersonalizationItem> patrons =
                new ArrayList<PersonalizationItem> ();
        List<PersonalizationItem> arms = 
                new ArrayList<PersonalizationItem> ();
        List<PersonalizationItem> colophons = 
                new ArrayList<PersonalizationItem> ();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            //String name = node.getNodeName();
            
            if (name(node).equals("signature")) {
                sigs.add(signature(node));
            } else if (name(node).equals("dedication")) {
                dedications.add(getElementText(node));
            } else if (name(node).equals("legalInscriptions")) {
                legals.add(personalizationItem(node));
            } else if (name(node).equals("patronPortrait")) {
                patrons.add(personalizationItem(node));
            } else if (name(node).equals("patronArms")) {
                arms.add(personalizationItem(node));
            } else if (name(node).equals("colophon")) {
                colophons.add(personalizationItem(node));
            } else if (name(node).equals("purchasePrice")) {
                person.setPurchasePrice(getElementText(node));
            }
        }
        
        person.setSignatures(sigs);
        person.setDedications(dedications);
        person.setLegalInscriptions(legals);
        person.setPatronPortraits(patrons);
        person.setPatronArms(arms);
        person.setColophons(colophons);
        
        return person;
    }
    
    private static Signature signature(Node child) throws ProfileBuilderException {
        Signature sig = new Signature();
        
        PersonalizationItem item = personalizationItem(child);
        sig.setValue(item.getValue());
        sig.setVolume(item.getVolume());
        sig.setFolio(item.getFolio());
        
        NodeList nodes = child.getChildNodes();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("name")) {
                sig.setName(getElementText(node));
            } else if (name(node).equals("text")) {
                sig.setText(getElementText(node));
            }
        }
        
        return sig;
    }

    private static PersonalizationItem personalizationItem(Node node) 
            throws ProfileBuilderException {
        PersonalizationItem item = new PersonalizationItem();
        
        item.setValue(getElementText(node));
        item.setFolio(getAttributeValue(node, "folio"));
        item.setVolume(getIntegerAttribute(node, "volume"));
        
        return item;
    }

    private static Ownership ownership(Node child) throws ProfileBuilderException {
        Ownership ownership = new Ownership();
        
        NodeList nodes = child.getChildNodes();
        
        List<Owner> owners = new ArrayList<Owner>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("owner")) {
                owners.add(owner(node));
            }
        }
        
        ownership.setOwnership(owners);
        
        return ownership;
    }

    private static Owner owner(Node node) throws ProfileBuilderException {
        Owner owner = new Owner();
        
        NodeList nodes = node.getChildNodes();
        
        List<String> places = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            
            if (name(child).equals("ownerName")) {
                owner.setOwnerName(getElementText(child));
            } else if (name(child).equals("ownershipDate")) {
                owner.setOwnershipDate(getElementText(child));
                owner.setOwnershipEndDate(getIntegerAttribute(
                        child, "endYear"));
                owner.setOwnershipStartDate(getIntegerAttribute(
                        child, "startYear"));
            } else if (name(child).equals("ownerPlace")) {
                places.add(getElementText(child));
            }
        }
        
        owner.setOwnerPlace(places);
        
        return owner;
    }

    private static Production production(Node child) throws ProfileBuilderException {
        Production prod = new Production();
        
        NodeList nodes = child.getChildNodes();
        
        List<Contributor> contributors = new ArrayList<Contributor>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("prodDate")) {
                prod.setProdDate(getElementText(node));
                prod.setProdEndDate(getIntegerAttribute(
                        node, "endYear"));
                prod.setProdStartDate(getIntegerAttribute(
                        node, "startYear"));
            } else if (name(node).equals("prodLoc")) {
                prod.setProdLoc(getElementText(node));
            } else if (name(node).equals("contributors")) {
                NodeList cont_list = node.getChildNodes();
                for (int j = 0; j < cont_list.getLength(); j++) {
                    Node cont_node = cont_list.item(j);
                    
                    if (cont_node.getNodeType() == Node.ELEMENT_NODE) {
                        Contributor cont = new Contributor();
                        cont.setType(name(cont_node));
                        cont.setValue(getElementText(cont_node));
                        
                        contributors.add(cont); 
                    }
                    
                }
                prod.setContributors(contributors);
            } else if (name(node).equals("productionNote")) {
                prod.setProdNotes(getElementText(node));
            }
        }
        
        return prod;
    }

    public static PhysicalCharacteristics parsePhysChar(Node node) 
               throws ProfileBuilderException {
        PhysicalCharacteristics phys_char = new PhysicalCharacteristics();
        
        NodeList children = node.getChildNodes();
        
        List<Dimensions> dims = new ArrayList<Dimensions>();
        List<QuireStructure> structs = new ArrayList<QuireStructure>();
        List<String> glosses = new ArrayList<String> ();
        List<String> underlinings = new ArrayList<String> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("volumes")) {
                phys_char.setVolumes(volumes(child));
            } else if (name(child).equals("dimensions")) {
                dims.add(dimensions(child));
            } else if (name(child).equals("folios")) {
                phys_char.setFolios(folios(child));
            } else if (name(child).equals("quireStruct")) {
                structs.add(quireStruct(child));
            } else if (name(child).equals("pageLayout")) {
                phys_char.setPageLayout(pageLayout(child));
            } else if (name(child).equals("rubricNote")) {
                phys_char.setRubricNotes(getElementText(child));
            } else if (name(child).equals("pageLayoutNote")) {
                phys_char.setPageLayoutNotes(getElementText(child));
            } else if (name(child).equals("glossHeadings")) {
                glosses.add(getElementText(child));
            } else if (name(child).equals("underlining")) {
                underlinings.add(getElementText(child));
            } else if (name(child).equals("materials")) {
                phys_char.setMaterials(materials(child));
            } else if (name(child).equals("physicalNotes")) {
                phys_char.setPhysicaNotes(getElementText(child));
            }
        }
        
        phys_char.setDimensions(dims);
        phys_char.setQuireStructs(structs);
        phys_char.setGlossHeadings(glosses);
        phys_char.setUnderlinings(underlinings);
        
        return phys_char;
    }

    private static Materials materials(Node child) throws ProfileBuilderException {
        Materials mats = new Materials();
        
        NodeList nodes = child.getChildNodes();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("support")) {
                mats.setSupport(getAttributeValue(node, "material"));
            } else if (name(node).equals("binding")) {
                NodeList bindings = node.getChildNodes();
                
                for (int j = 0; j < bindings.getLength(); j++) {
                    Node bind = bindings.item(j);
                    
                    if (name(bind).equals("bindMaterial")) {
                        mats.setBindMaterial(getElementText(bind));
                    } else if (name(bind).equals("bindDate")) {
                        mats.setBindDate(bind.getTextContent());
                        mats.setBindDateStartYear(
                                getIntegerAttribute(bind, "startYear"));
                        mats.setBindDateEndYear(
                                getIntegerAttribute(bind, "endYear"));
                    }
                }
            }
        }
        
        return mats;
    }

    private static PageLayout pageLayout(Node child) {
        PageLayout layout = new PageLayout();
        
        layout.setColumns(getAttributeValue(
                child, "columns"));
        layout.setGlossPlace(getAttributeValue(
                child, "glossPlace"));
        layout.setRunningHeads(getAttributeValue(
                child, "runningHeads"));
        layout.setChapterNumbers(getAttributeValue(
                child, "chapterNumbers"));
        layout.setSmallLetterHist(getAttributeValue(
                child, "smallLettersHist"));
        layout.setCatchphrases(getAttributeValue(
                child, "catchphrases"));
        
        return layout;
    }

    private static QuireStructure quireStruct(Node child) throws ProfileBuilderException {
        QuireStructure struct = new QuireStructure();
        
        NodeList nodes = child.getChildNodes();
        
        List<Integer> total = new ArrayList<Integer>();
        List<Integer> typical = new ArrayList<Integer>();
        List<String> full = new ArrayList<String>();
        List<String> note = new ArrayList<String>();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("quireTotal")) {
                total.add(getIntegerAttribute(node, "number"));
            } else if (name(node).equals("typicalQuire")) {
                typical.add(getIntegerAttribute(node, "number"));
            } else if (name(node).equals("fullQuireStruct")) {
                full.add(node.getTextContent());
            } else if (name(node).equals("quireNote")) {
                note.add(node.getTextContent());
            }
        }
        
        struct.setQuireTotal(total);
        struct.setTypicalQuires(typical);
        struct.setFullQuireStructs(full);
        struct.setQuireNotes(note);
        struct.setVolume(getIntegerAttribute(child, "volume"));
        
        return struct;
    }

    private static Folios folios(Node child) throws ProfileBuilderException {
        Folios folios = new Folios();
        
        NodeList nodes = child.getChildNodes();
        
        List<IndVolume> ind_vols = new ArrayList<IndVolume>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("indVolumes")) {
                IndVolume ind = new IndVolume();
                
                ind.setVolume(getIntegerAttribute(node, "n"));
                ind.setValue(node.getTextContent());
                
                ind_vols.add(ind);
            } else if (name(node).equals("totalFolios")) {
                folios.setTotalFolios(getIntegerElement(node));
            }
        }
        
        folios.setIndVolumes(ind_vols);
        
        return folios;
    }

    private static Dimensions dimensions(Node child) throws ProfileBuilderException {
        Dimensions dims = new Dimensions();
        
        NodeList nodes = child.getChildNodes();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("page")) {
                dims.setPage(node.getTextContent());
            } else if (name(node).equals("textBlock")) {
                dims.setTextBlock(node.getTextContent());
            }
        }
        
        dims.setUnits(getAttributeValue(child, "units"));
        dims.setVolume(getIntegerAttribute(child, "volume"));
        
        return dims;
    }

    private static Volumes volumes(Node child) {
        Volumes volumes = new Volumes();
        
        NodeList children = child.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            
            if (name(node).equals("volumeNote")) {
                volumes.setVolumeNotes(node.getTextContent());
            }
        }
        
        volumes.setPresentState(Volumes.State.getState(
                getAttributeValue(child, "presentState")));
        volumes.setPreviousState(Volumes.State.getState(
                getAttributeValue(child, "previousState")));
        
        return volumes;
    }

    
    
}
