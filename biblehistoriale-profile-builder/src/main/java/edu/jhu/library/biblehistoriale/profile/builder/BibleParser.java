package edu.jhu.library.biblehistoriale.profile.builder;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.jhu.library.biblehistoriale.model.profile.*;

public class BibleParser {
    
    public static String getAttributeValue(Node node, String attribute) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return ((Element) node).getAttribute(attribute);
        }
        
        return null;
    }
    
    private static int getIntegerAttribute(Node node, String attribute) {
        
        try {
            return Integer.parseInt(
                    getAttributeValue(node, attribute));
        } catch (NumberFormatException e) {
            // TODO
        }
        
        return -1;
    }
    
    private static int getIntegerElement(Node node) {
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                return Integer.parseInt(node.getTextContent());
            } catch (NumberFormatException e) {
                // TODO
            }
        }
        
        return -1;
    }
    
    private static String getElementText(Node node) {
        return node.getTextContent();
    }
    
    private static String name(Node node) {
        return node.getNodeName();
    }
    
    public static Bibliography parseBibliography(Node bible_child) {
        // TODO Auto-generated method stub
        return null;
    }

    public static TextualContent parseTextualContent(Node bible_child) {
        // TODO Auto-generated method stub
        return null;
    }

    public static Classification parseClassification(Node bible_child) {
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
                CatalogerClassification cc = catalogerClassification(child);
                classification.setClassification(cc);
            }
        }
        
        return classification;
    }

    private static CatalogerClassification catalogerClassification(Node child) {
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
                Sneddon sneddon = sneddon(node);
                cc.setSneddonClass(sneddon);
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

    public static IllustrationList parseIllustrations(Node bible_child) {
        IllustrationList ills = new IllustrationList();
        
        NodeList children = bible_child.getChildNodes();
        
        List<Illustration> illustrations = new ArrayList<Illustration> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("decorationSummary")) {
                DecorationSummary summary = decorationSummary(child);
                ills.setDecorationSummary(summary);
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

    private static Illustration illustration(Node node) {
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

    private static DecorationSummary decorationSummary(Node child) {
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

    public static ProvenPatronHistory parsePatronHist(Node bible_child) {
        ProvenPatronHistory hist = new ProvenPatronHistory();
        
        NodeList children = bible_child.getChildNodes();
        
        List<Ownership> owners = new ArrayList<Ownership> ();
        List<Annotation> annotations = new ArrayList<Annotation> ();
        List<String> provenances = new ArrayList<String> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            
            if (name(child).equals("production")) {
                Production prod = production(child);
                hist.setProduction(prod);
            } else if (name(child).equals("ownership")) {
                Ownership owns = ownership(child);
                owners.add(owns);
            } else if (name(child).equals("personalization")) {
                Personalization person = personalization(child);
                hist.setPersonalization(person);
            } else if (name(child).equals("annotation")) {
                Annotation ann = annotation(child);
                annotations.add(ann);
            } else if (name(child).equals("provenanceNote")) {
                provenances.add(getElementText(child));
            }
        }
        
        hist.setOwnerships(owners);
        hist.setAnnotations(annotations);
        hist.setProvenanceNote(provenances);
        
        return hist;
    }

    private static Annotation annotation(Node child) {
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

    private static Personalization personalization(Node child) {
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
                Signature sig = signature(node);
                sigs.add(sig);
            } else if (name(node).equals("dedication")) {
                dedications.add(getElementText(node));
            } else if (name(node).equals("legalInscriptions")) {
                PersonalizationItem item = personalizationItem(node);
                legals.add(item);
            } else if (name(node).equals("patronPortrait")) {
                PersonalizationItem item = personalizationItem(node);
                patrons.add(item);
            } else if (name(node).equals("patronArms")) {
                PersonalizationItem item = personalizationItem(node);
                arms.add(item);
            } else if (name(node).equals("colophon")) {
                PersonalizationItem item = personalizationItem(node);
                colophons.add(item);
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
    
    private static Signature signature(Node child) {
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

    private static PersonalizationItem personalizationItem(Node node) {
        PersonalizationItem item = new PersonalizationItem();
        
        item.setValue(getElementText(node));
        item.setFolio(getAttributeValue(node, "folio"));
        item.setVolume(getIntegerAttribute(node, "volume"));
        
        return item;
    }

    private static Ownership ownership(Node child) {
        Ownership ownership = new Ownership();
        
        NodeList nodes = child.getChildNodes();
        
        List<Owner> owners = new ArrayList<Owner>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (name(node).equals("owner")) {
                Owner owner = owner(node);
                owners.add(owner);
            }
        }
        
        ownership.setOwnership(owners);
        
        return ownership;
    }

    private static Owner owner(Node node) {
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

    private static Production production(Node child) {
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

    public static PhysicalCharacteristics parsePhysChar(Node node) {
        PhysicalCharacteristics phys_char = new PhysicalCharacteristics();
        
        NodeList children = node.getChildNodes();
        
        List<Dimensions> dims = new ArrayList<Dimensions>();
        List<QuireStructure> structs = new ArrayList<QuireStructure>();
        List<String> glosses = new ArrayList<String> ();
        List<String> underlinings = new ArrayList<String> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            //String child_name = child.getNodeName();
            
            if (name(child).equals("volumes")) {
                Volumes volumes = volumes(child);
                phys_char.setVolumes(volumes);
            } else if (name(child).equals("dimensions")) {
                dims.add(dimensions(child));
            } else if (name(child).equals("folios")) {
                Folios folios = folios(child);
                phys_char.setFolios(folios);
            } else if (name(child).equals("quireStruct")) {
                QuireStructure struct = quireStruct(child);
                structs.add(struct);
            } else if (name(child).equals("pageLayout")) {
                PageLayout layout = pageLayout(child);
                phys_char.setPageLayout(layout);
            } else if (name(child).equals("rubricNote")) {
                phys_char.setRubricNotes(getElementText(child));
            } else if (name(child).equals("pageLayoutNote")) {
                phys_char.setPageLayoutNotes(getElementText(child));
            } else if (name(child).equals("glossHeadings")) {
                glosses.add(getElementText(child));
            } else if (name(child).equals("underlining")) {
                underlinings.add(getElementText(child));
            } else if (name(child).equals("materials")) {
                Materials mats = materials(child);
                phys_char.setMaterials(mats);
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

    private static Materials materials(Node child) {
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

    private static QuireStructure quireStruct(Node child) {
        QuireStructure struct = new QuireStructure();
        
        NodeList nodes = child.getChildNodes();
        
        List<Integer> total = new ArrayList<Integer>();
        List<Integer> typical = new ArrayList<Integer>();
        List<String> full = new ArrayList<String>();
        List<String> note = new ArrayList<String>();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            //String name = node.getNodeName();
            
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

    private static Folios folios(Node child) {
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

    private static Dimensions dimensions(Node child) {
        Dimensions dims = new Dimensions();
        
        NodeList nodes = child.getChildNodes();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            //String node_name = node.getNodeName();
            
            if (name(node).equals("page")) {
                dims.setPage(node.getTextContent());
            } else if (name(node).equals("textBlock")) {
                dims.setTextBlock(node.getTextContent());
            } // TODO throw exception if unsupported tag found
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
