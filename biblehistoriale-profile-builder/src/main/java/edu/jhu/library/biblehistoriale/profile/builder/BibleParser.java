package edu.jhu.library.biblehistoriale.profile.builder;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.jhu.library.biblehistoriale.model.ProfileElements;
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
            String val = getAttributeValue(node, attribute);
            
            if (val == null || val.equals("")) {
                return -1;
            }
            
            return Integer.parseInt(
                    getAttributeValue(node, attribute));
        } catch (NumberFormatException e) {
            throw new ProfileBuilderException(e);
        }
    }
    
    public static int getIntegerElement(Node node) throws ProfileBuilderException {
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                String val = node.getTextContent();
                
                if (val == null || val.equals("")) {
                    return -1;
                }
                
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
            if (name(node).equals(ProfileElements.BIBAUTHOR)) {
                authors.add(getElementText(node));
            } else if (name(node).equals(ProfileElements.ARTICLETITLE)) {
                entry.setArticleTitle(getElementText(node));
            } else if (name(node).equals(ProfileElements.BOOKORJOURNALTITLE)) {
                entry.setBookOrJournalTitle(getElementText(node));
            } else if (name(node).equals(ProfileElements.PUBLICATIONINFO)) {
                entry.setPublicationInfo(getElementText(node));
            } else if (name(node).equals(ProfileElements.ARTICLELINK)) {
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
            
            if (name(child).equals(ProfileElements.PREFATORYMATTER)) {
                PrefatoryMatter prefact = prefatoryMatter(child);
                prefactories.add(prefact);
            } else if (name(child).equals(ProfileElements.BIBLEBOOKS)) {
                BibleBooks book = bibleBook(child);
                books.add(book);
            } else if (name(child).equals(ProfileElements.PARASCRIPTURALITEMS)) {
                ParascripturalItem item = parascripturalItem(child);
                content.setParascripturalItem(item);
            } else if (name(child).equals(ProfileElements.MISCCONTENTS)) {
                MiscContent misc = miscContent(child);
                miscs.add(misc);
            } else if (name(child).equals(ProfileElements.NOTES)) {
                notes.add(getElementText(child));
            }
            
            child = child.getNextSibling();
            
        }
        
        content.setVolume(getIntegerAttribute(bible_child, ProfileElements.VOLUME));
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
            if (name(node).equals(ProfileElements.DESCRIPTION)) {
                misc.setDescription(getElementText(node));
            }
            node = node.getNextSibling();
        }
        
        misc.setVolume(getIntegerAttribute(child, ProfileElements.VOLUME));
        misc.setStartFolio(getAttributeValue(child, ProfileElements.STARTFOLIO));
        misc.setEndFolio(getAttributeValue(child, ProfileElements.ENDFOLIO));
        
        return misc;
    }

    private static ParascripturalItem parascripturalItem(Node child) 
            throws ProfileBuilderException {
        ParascripturalItem item = new ParascripturalItem();
        
        List<CatechismsPrayersTreatise> treatises = 
                new ArrayList<CatechismsPrayersTreatise> ();
        
        Node node = child.getFirstChild();
        while (node != null) {
            if (name(node).equals(ProfileElements.LITANY)) {
                Node lit_node = node.getFirstChild();
                while (lit_node != null) {
                    if (name(lit_node).equals(ProfileElements.PLACEOFORIGINUSE)) {
                        item.setPlaceOfOriginUse(
                                getElementText(lit_node));
                    }
                    lit_node = lit_node.getNextSibling();
                }
                
                item.setForm(getAttributeValue(node, ProfileElements.FORM));
                item.setLocVol(getAttributeValue(node, ProfileElements.LOCVOL));
                item.setLocStart(getAttributeValue(node, ProfileElements.LOCSTART));
                item.setLocEnd(getAttributeValue(node, ProfileElements.LOCEND));
                item.setLitanyPresence(
                        getAttributeValue(node, ProfileElements.PRESENCE));
                item.setSneddonId(getAttributeValue(node, ProfileElements.SNEDDONID));
            } else if (name(node).equals(ProfileElements.CANTICLES)) {
                Node can_node = node.getFirstChild();
                while (can_node != null) {
                    if (name(can_node).equals(ProfileElements.CANTICLETYPE)) {
                        item.setCanticleType(
                                getElementText(can_node));
                    }
                    can_node = can_node.getNextSibling();
                }
                
                item.setCanticleEndFolio(
                        getAttributeValue(node, ProfileElements.ENDFOLIO));
                item.setCanticleStartFolio(
                        getAttributeValue(node, ProfileElements.STARTFOLIO));
                item.setVolume(getIntegerAttribute(node, ProfileElements.VOLUME));
                item.setCanticlePresence(
                        getAttributeValue(node, ProfileElements.PRESENCE));
            } else if (name(node).equals(ProfileElements.ADDEDPROLOGUE)) {
                item.setJeanDeBlois(getAttributeValue(node, ProfileElements.JEANDEBLOIS));
                item.setJerome(getAttributeValue(node, ProfileElements.JEROME));
            } else if (name(node).equals(ProfileElements.CATECHISMSPRAYERSTREATISES)) {
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
            if (name(child).equals(ProfileElements.DESCRIPTIONSFIRSTLINES)) {
                first_lines.add(getElementText(child));
            }
            child = child.getNextSibling();
        }
        
        treatise.setDescriptionsFirstLines(first_lines);
        treatise.setPresence(getAttributeValue(node, ProfileElements.PRESENCE));
        treatise.setStartFolio(getAttributeValue(node, ProfileElements.STARTFOLIO));
        treatise.setEndFolio(getAttributeValue(node, ProfileElements.ENDFOLIO));
        treatise.setVolume(getIntegerAttribute(node, ProfileElements.VOLUME));
        
        return treatise;
    }

    private static BibleBooks bibleBook(Node child) throws ProfileBuilderException {
        BibleBooks books = new BibleBooks();
        
        List<Title> titles = new ArrayList<Title> ();
        
        Node node = child.getFirstChild();
        while (node != null) {
            if (name(node).equals(ProfileElements.TITLE)) {
                titles.add(title(node));
            }
            node = node.getNextSibling();
        }
        
        books.setTitles(titles);
        books.setVolume(getIntegerAttribute(child, ProfileElements.VOLUME));
        
        return books;
    }

    private static Title title(Node node) {
        Title title = new Title();
        
        List<TitleIncipit> incipits = new ArrayList<TitleIncipit> ();
        
        Node child = node.getFirstChild();
        while (child != null) {
            
            if (name(child).equals(ProfileElements.INCIPIT)) {
                TitleIncipit inc = title.new TitleIncipit();
                
                inc.setAccuracy(getAttributeValue(child, ProfileElements.ACCURACY));
                inc.setTextType(getAttributeValue(child, ProfileElements.TEXTTYPE));
                inc.setText(getElementText(child));
                
                incipits.add(inc);
            } else if (name(child).equals(ProfileElements.EDITIONS)) {
                title.setEditions(getElementText(child));
            } else if (name(child).equals(ProfileElements.BOOKNOTE)) {
                title.setBookNote(getElementText(child));
            }
            
            child = child.getNextSibling();
        }
        
        title.setIncipit(incipits);
        title.setBookName(getAttributeValue(node, ProfileElements.BOOKNAME));
        title.setHasChapterNames(getAttributeValue(node, ProfileElements.CHAPTERNAMES));
        title.setStartPage(getAttributeValue(node, ProfileElements.STARTPAGE));
        title.setGlossType(getAttributeValue(node, ProfileElements.GLOSSTYPE));
        title.setGlossType2(getAttributeValue(node, ProfileElements.GLOSSTYPE2));
        title.setEndPage(getAttributeValue(node, ProfileElements.ENDPAGE));
        title.setHasTableOfContents(
                getAttributeValue(node, ProfileElements.TABLEOFCONTENTS).equals("y"));
        title.setTextVersion(getAttributeValue(node, ProfileElements.TEXTVERSION));
        
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
            
            if (name(node).equals(ProfileElements.OTHERPREFACE)) {
                OtherPreface other = new OtherPreface();
                
                other.setText(getElementText(node));
                other.setAccuracy(getAttributeValue(node, ProfileElements.ACCURACY));
                other.setStartPage(getAttributeValue(node, ProfileElements.STARTPAGE));
                
                others.add(other);
            } else if (name(node).equals(ProfileElements.GUYART)) {
                guyarts.add(guyart(node));
            } else if (name(node).equals(ProfileElements.COMESTORLETTER)) {
                letters.add(comestorLetter(node));
            } else if (name(node).equals(ProfileElements.MASTERTABLEOFCONTENTS)) {
                MasterTableOfContents master = new MasterTableOfContents();
                
                master.setStartPage(getAttributeValue(node, ProfileElements.STARTPAGE));
                master.setTableDetail(
                        getAttributeValue(node, ProfileElements.TABLEDETAIL));
                master.setMatchesContents(
                        getAttributeValue(node, ProfileElements.MATCHESCONTENTS)
                        .equals("y"));
                master.setText(getElementText(node));
                
                matter.setMasterTableOfContents(master);
            } else if (name(node).equals(ProfileElements.COMESTOR)) {
                OtherPreface other = new OtherPreface();
                
                other.setText(getElementText(node));
                other.setAccuracy(getAttributeValue(node, ProfileElements.ACCURACY));
                other.setStartPage(getAttributeValue(node, ProfileElements.STARTPAGE));
                
                comestors.add(other);
            } else if (name(node).equals(ProfileElements.PREFATORYNOTE)) {
                matter.setPrefactoryNote(getElementText(node));
            }
            
            node = node.getNextSibling();
        }
        
        matter.setVolume(getIntegerAttribute(child, ProfileElements.VOLUME));
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
            if (name(child).equals(ProfileElements.INCIPIT)) {
                incipits.add(incipit(child));
            }
            child = child.getNextSibling();
        }
        
        letter.setIncipits(incipits);
        letter.setStartPage(getAttributeValue(node, ProfileElements.STARTPAGE));
        
        return letter;
    }

    private static Guyart guyart(Node node) {
        Guyart guy = new Guyart();
        
        Node child = node.getFirstChild();
        while (child != null) {
            if (name(child).equals(ProfileElements.INCIPIT)) {
                guy.setIncipit(incipit(child));
            }
            child = child.getNextSibling();
        }
        
        guy.setStartPage(getAttributeValue(node, ProfileElements.STARTPAGE));
        guy.setContainsGuyartName(
                getAttributeValue(node, ProfileElements.CONTAINSGUYARTNAME)
                .equals("y"));
        
        return guy;
    }

    private static Incipit incipit(Node node) {
        Incipit incipit = new Incipit();
        
        incipit.setAccuracy(getAttributeValue(node, ProfileElements.ACCURACY));
        incipit.setText(getElementText(node));
        
        return incipit;
    }

    public static Classification parseClassification(Node bible_child) 
            throws ProfileBuilderException {
        Classification classification = new Classification();
        
        NodeList children = bible_child.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals(ProfileElements.SHELFMARKS)) {
                List<String> former_shelfmarks = new ArrayList<String> ();
                
                NodeList nodes = child.getChildNodes();
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    
                    if (name(node).equals(ProfileElements.CURRENTCITY)) {
                        classification.setCurrentCity(
                                getElementText(node));
                    } else if (name(node).equals(ProfileElements.CURRENTREPOSITORY)) {
                        classification.setCurrentRepository(
                                getElementText(node));
                    } else if (name(node).equals(ProfileElements.CURRENTSHELFMARK)) {
                        classification.setCurrentShelfmark(
                                getElementText(node));
                    } else if (name(node).equals(ProfileElements.FORMERSHELFMARK)) {
                        former_shelfmarks.add(
                                getElementText(node));
                    } else if (name(node).equals(ProfileElements.REPOSITORYLINK)) {
                        classification.setRepositoryLink(
                                getElementText(node));
                    }
                }
                
                classification.setFormerShelfmarks(former_shelfmarks);
            } else if (name(child).equals(ProfileElements.TITLES)) {
                NodeList nodes = child.getChildNodes();
                
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    
                    if (name(node).equals(ProfileElements.COVERTITLE)) {
                        classification.setCoverTitle(
                                getElementText(node));
                    } else if (name(node).equals(ProfileElements.RUBRICTITLE)) {
                        classification.setRubricTitle(
                                getElementText(node));
                    }
                }
            } else if (name(child).equals(ProfileElements.BOOKTYPE)) {
                BookType type = new BookType();
                
                type.setType(getAttributeValue(child, ProfileElements.COLLECTIONTYPE));
                type.setTech(getAttributeValue(child, ProfileElements.TECHNOLOGY));
                
                classification.setBookType(type);
            } else if (name(child).equals(ProfileElements.CATALOGERCLASSIFICATIONS)) {
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
            
            if (name(node).equals(ProfileElements.BERGERCLASS)) {
                Berger berger = new Berger();
                
                berger.setCategory(getAttributeValue(node, ProfileElements.CATEGORY));
                berger.setBhcSubtype(
                        getAttributeValue(node, ProfileElements.BHCSUBTYPE));
                
                cc.setBergerClass(berger);
            } else if (name(node).equals(ProfileElements.SNEDDONCLASS)) {
                cc.setSneddonClass(sneddon(node));
            } else if (name(node).equals(ProfileElements.FOURNIECATALOG)) {
                NodeList fournies = node.getChildNodes();
                
                for (int j = 0; j < fournies.getLength(); j++) {
                    Node fournie = fournies.item(j);
                    
                    if (name(fournie).equals(ProfileElements.FOURNIENUMBER)) {
                        cc.setFournieNumber(
                                getElementText(fournie));
                    } else if (name(fournie).equals(ProfileElements.FOURNIELINK)) {
                        cc.setFournieLink(
                                getElementText(fournie));
                    }
                }
            } else if (name(node).equals(ProfileElements.SECUNDOFOLIO)) {
                SecundoFolio secundo = new SecundoFolio();
                
                secundo.setValue(getElementText(node));
                secundo.setVolume(getIntegerAttribute(node, ProfileElements.VOLUME));
                
                secundos.add(secundo);
            } else if (name(node).equals(ProfileElements.CLASSIFICATIONNOTES)) {
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
            
            if (name(child).equals(ProfileElements.SIGLUM)) {
                sneddon.setSiglum(getElementText(child));
            } else if (name(child).equals(ProfileElements.ENTRY)) {
                sneddon.setEntry(getElementText(child));
            }
        }
        
        sneddon.setCategory(getAttributeValue(node, ProfileElements.CATEGORY));
        sneddon.setSub1(getAttributeValue(node, ProfileElements.SUBCATEGORY1));
        sneddon.setSub2(getAttributeValue(node, ProfileElements.SUBCATEGORY2));
        sneddon.setSub3(getAttributeValue(node, ProfileElements.SUBCATEGORY3));
        
        return sneddon;
    }

    public static IllustrationList parseIllustrations(Node bible_child) 
            throws ProfileBuilderException {
        IllustrationList ills = new IllustrationList();
        
        NodeList children = bible_child.getChildNodes();
        
        List<Illustration> illustrations = new ArrayList<Illustration> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals(ProfileElements.DECORATIONSUMMARY)) {
                ills.setDecorationSummary(decorationSummary(child));
            } else if (name(child).equals(ProfileElements.ILLUSTRATIONLIST)) {
                NodeList nodes = child.getChildNodes();
                for (int j = 0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    
                    if (name(node).equals(ProfileElements.ILLUSTRATION)) {
                        Illustration ill = illustration(node);
                        illustrations.add(ill);
                    }
                }
                ills.setIllustrations(illustrations);
            } else if (name(child).equals(ProfileElements.ILLUSTRATIONNOTE)) {
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
            
            if (name(child).equals(ProfileElements.KEYWORDS)) {
                ill.setKeywords(getElementText(child));
            } else if (name(child).equals(ProfileElements.LINK)) {
                ill.setUrl(getElementText(child));
            }
        }
        
        ill.setBook(getAttributeValue(node, ProfileElements.BOOK));
        ill.setFolio(getAttributeValue(node, ProfileElements.FOLIO));
        ill.setNumber(getIntegerAttribute(node, ProfileElements.NUMBER));
        ill.setVolume(getIntegerAttribute(node, ProfileElements.VOLUME));
        
        return ill;
    }

    private static DecorationSummary decorationSummary(Node child) 
            throws ProfileBuilderException {
        DecorationSummary summary = new DecorationSummary();
        
        NodeList nodes = child.getChildNodes();
        
        List<String> workshops = new ArrayList<String> ();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals(ProfileElements.ARTISTWORKSHOP)) {
                workshops.add(getElementText(node));
            }
        }
        
        summary.setArtistWorkshops(workshops);
        summary.setBasDePage(getAttributeValue(child, ProfileElements.BASDEPAGE));
        summary.setDecoratedInitials(
                getAttributeValue(child, ProfileElements.DECORATEDINITIALS));
        summary.setFoliateBorder(
                getAttributeValue(child, ProfileElements.FOLIATEBORDER));
        summary.setIllStyle(getAttributeValue(child, ProfileElements.ILLSTYLE));
        summary.setLargeIlls(getIntegerAttribute(child, ProfileElements.LARGEILLS));
        summary.setNumber(getIntegerAttribute(child, ProfileElements.NUMBER));
        
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
            
            if (name(child).equals(ProfileElements.PRODUCTION)) {
                hist.setProduction(production(child));
            } else if (name(child).equals(ProfileElements.OWNERSHIP)) {
                owners.add(ownership(child));
            } else if (name(child).equals(ProfileElements.PERSONALIZATION)) {
                hist.setPersonalization(personalization(child));
            } else if (name(child).equals(ProfileElements.ANNOTATION)) {
                annotations.add(annotation(child));
            } else if (name(child).equals(ProfileElements.PROVENANCENOTE)) {
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
            
            if (name(node).equals(ProfileElements.NAME)) {
                ann.setName(getElementText(node));
            } else if (name(node).equals(ProfileElements.DATE)) {
                ann.setDate(getElementText(node));
            } else if (name(node).equals(ProfileElements.TEXTREFERENCED)) {
                ann.setTextReferenced(getElementText(node));
            } else if (name(node).equals(ProfileElements.TEXT)) {
                ann.setText(getElementText(node));
            }
        }
        
        ann.setBook(getAttributeValue(child, ProfileElements.BOOK));
        ann.setVolume(getIntegerAttribute(child, ProfileElements.VOLUME));
        ann.setFolio(getAttributeValue(child, ProfileElements.FOLIO));
        ann.setType(getAttributeValue(child, ProfileElements.TYPE));
        
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
            
            if (name(node).equals(ProfileElements.SIGNATURE)) {
                sigs.add(signature(node));
            } else if (name(node).equals(ProfileElements.DEDICATION)) {
                dedications.add(getElementText(node));
            } else if (name(node).equals(ProfileElements.LEGALINSCRIPTIONS)) {
                legals.add(personalizationItem(node));
            } else if (name(node).equals(ProfileElements.PATRONPORTRAIT)) {
                patrons.add(personalizationItem(node));
            } else if (name(node).equals(ProfileElements.PATRONARMS)) {
                arms.add(personalizationItem(node));
            } else if (name(node).equals(ProfileElements.COLOPHON)) {
                colophons.add(personalizationItem(node));
            } else if (name(node).equals(ProfileElements.PURCHASEPRICE)) {
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
            
            if (name(node).equals(ProfileElements.NAME)) {
                sig.setName(getElementText(node));
            } else if (name(node).equals(ProfileElements.TEXT)) {
                sig.setText(getElementText(node));
            }
        }
        
        return sig;
    }

    private static PersonalizationItem personalizationItem(Node node) 
            throws ProfileBuilderException {
        PersonalizationItem item = new PersonalizationItem();
        
        item.setValue(getElementText(node));
        item.setFolio(getAttributeValue(node, ProfileElements.FOLIO));
        item.setVolume(getIntegerAttribute(node, ProfileElements.VOLUME));
        
        return item;
    }

    private static Ownership ownership(Node child) throws ProfileBuilderException {
        Ownership ownership = new Ownership();
        
        NodeList nodes = child.getChildNodes();
        
        List<Owner> owners = new ArrayList<Owner>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals(ProfileElements.OWNER)) {
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
            
            if (name(child).equals(ProfileElements.OWNERNAME)) {
                owner.setOwnerName(getElementText(child));
            } else if (name(child).equals(ProfileElements.OWNERSHIPDATE)) {
                owner.setOwnershipDate(getElementText(child));
                owner.setOwnershipEndDate(getIntegerAttribute(
                        child, ProfileElements.ENDYEAR));
                owner.setOwnershipStartDate(getIntegerAttribute(
                        child, ProfileElements.STARTYEAR));
            } else if (name(child).equals(ProfileElements.OWNERPLACE)) {
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
            
            if (name(node).equals(ProfileElements.PRODDATE)) {
                prod.setProdDate(getElementText(node));
                prod.setProdEndDate(getIntegerAttribute(
                        node, ProfileElements.ENDYEAR));
                prod.setProdStartDate(getIntegerAttribute(
                        node, ProfileElements.STARTYEAR));
            } else if (name(node).equals(ProfileElements.PRODLOC)) {
                prod.setProdLoc(getElementText(node));
            } else if (name(node).equals(ProfileElements.CONTRIBUTORS)) {
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
            } else if (name(node).equals(ProfileElements.PRODUCTIONNOTE)) {
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
            
            if (name(child).equals(ProfileElements.VOLUMES)) {
                phys_char.setVolumes(volumes(child));
            } else if (name(child).equals(ProfileElements.DIMENSIONS)) {
                dims.add(dimensions(child));
            } else if (name(child).equals(ProfileElements.FOLIOS)) {
                phys_char.setFolios(folios(child));
            } else if (name(child).equals(ProfileElements.QUIRESTRUCT)) {
                structs.add(quireStruct(child));
            } else if (name(child).equals(ProfileElements.PAGELAYOUT)) {
                phys_char.setPageLayout(pageLayout(child));
            } else if (name(child).equals(ProfileElements.RUBRICNOTE)) {
                phys_char.setRubricNotes(getElementText(child));
            } else if (name(child).equals(ProfileElements.PAGELAYOUTNOTE)) {
                phys_char.setPageLayoutNotes(getElementText(child));
            } else if (name(child).equals(ProfileElements.GLOSSHEADINGS)) {
                glosses.add(getElementText(child));
            } else if (name(child).equals(ProfileElements.UNDERLINING)) {
                underlinings.add(getElementText(child));
            } else if (name(child).equals(ProfileElements.MATERIALS)) {
                phys_char.setMaterials(materials(child));
            } else if (name(child).equals(ProfileElements.PHYSICALNOTES)) {
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
            
            if (name(node).equals(ProfileElements.SUPPORT)) {
                mats.setSupport(getAttributeValue(node, ProfileElements.MATERIAL));
            } else if (name(node).equals(ProfileElements.BINDING)) {
                NodeList bindings = node.getChildNodes();
                
                for (int j = 0; j < bindings.getLength(); j++) {
                    Node bind = bindings.item(j);
                    
                    if (name(bind).equals(ProfileElements.BINDMATERIAL)) {
                        mats.setBindMaterial(getElementText(bind));
                    } else if (name(bind).equals(ProfileElements.BINDDATE)) {
                        mats.setBindDate(bind.getTextContent());
                        mats.setBindDateStartYear(
                                getIntegerAttribute(bind, ProfileElements.STARTYEAR));
                        mats.setBindDateEndYear(
                                getIntegerAttribute(bind, ProfileElements.ENDYEAR));
                    }
                }
            }
        }
        
        return mats;
    }

    private static PageLayout pageLayout(Node child) {
        PageLayout layout = new PageLayout();
        
        layout.setColumns(getAttributeValue(
                child, ProfileElements.COLUMNS));
        layout.setGlossPlace(getAttributeValue(
                child, ProfileElements.GLOSSPLACE));
        layout.setRunningHeads(getAttributeValue(
                child, ProfileElements.RUNNINGHEADS));
        layout.setChapterNumbers(getAttributeValue(
                child, ProfileElements.CHAPTERNUMBERS));
        layout.setSmallLetterHist(getAttributeValue(
                child, ProfileElements.SMALLLETTERSHIST));
        layout.setCatchphrases(getAttributeValue(
                child, ProfileElements.CATCHPHRASES));
        
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
            
            if (name(node).equals(ProfileElements.QUIRETOTAL)) {
                total.add(getIntegerAttribute(node, ProfileElements.NUMBER));
            } else if (name(node).equals(ProfileElements.TYPICALQUIRE)) {
                typical.add(getIntegerAttribute(node, ProfileElements.NUMBER));
            } else if (name(node).equals(ProfileElements.FULLQUIRESTRUCT)) {
                full.add(node.getTextContent());
            } else if (name(node).equals(ProfileElements.QUIRENOTE)) {
                note.add(node.getTextContent());
            }
        }
        
        struct.setQuireTotal(total);
        struct.setTypicalQuires(typical);
        struct.setFullQuireStructs(full);
        struct.setQuireNotes(note);
        struct.setVolume(getIntegerAttribute(child, ProfileElements.VOLUME));
        
        return struct;
    }

    private static Folios folios(Node child) throws ProfileBuilderException {
        Folios folios = new Folios();
        
        NodeList nodes = child.getChildNodes();
        
        List<IndVolume> ind_vols = new ArrayList<IndVolume>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals(ProfileElements.INDVOLUMES)) {
                IndVolume ind = new IndVolume();
                
                ind.setVolume(getIntegerAttribute(node, "n"));
                ind.setValue(node.getTextContent());
                
                ind_vols.add(ind);
            } else if (name(node).equals(ProfileElements.TOTALFOLIOS)) {
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
            
            if (name(node).equals(ProfileElements.PAGE)) {
                dims.setPage(node.getTextContent());
            } else if (name(node).equals(ProfileElements.TEXTBLOCK)) {
                dims.setTextBlock(node.getTextContent());
            }
        }
        
        dims.setUnits(getAttributeValue(child, ProfileElements.UNITS));
        dims.setVolume(getIntegerAttribute(child, ProfileElements.VOLUME));
        
        return dims;
    }

    private static Volumes volumes(Node child) {
        Volumes volumes = new Volumes();
        
        NodeList children = child.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            
            if (name(node).equals(ProfileElements.VOLUMENOTE)) {
                volumes.setVolumeNotes(node.getTextContent());
            }
        }
        
        volumes.setPresentState(Volumes.State.getState(
                getAttributeValue(child, ProfileElements.PRESENTSTATE)));
        volumes.setPreviousState(Volumes.State.getState(
                getAttributeValue(child, ProfileElements.PREVIOUSSTATE)));
        
        return volumes;
    }

    
    
}
