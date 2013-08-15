package edu.jhu.library.biblehistoriale.profile.builder;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.jhu.library.biblehistoriale.model.profile.*;

public class BibleParser {
    
    public static String getAttributeFromNode(Node node, String attribute) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return ((Element) node).getAttribute(attribute);
        }
        
        return null;
    }
    
    private static int getIntegerAttribute(Node node, String attribute) {
        
        try {
            return Integer.parseInt(
                    getAttributeFromNode(node, attribute));
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
    
    public static Bibliography parseBibliography(Node bible_child) {
        // TODO Auto-generated method stub
        return null;
    }

    public static TextualContent parseTextualContent(Node bible_child) {
        // TODO Auto-generated method stub
        return null;
    }

    public static Classification parseClassification(Node bible_child) {
        // TODO Auto-generated method stub
        return null;
    }

    public static IllustrationList parseIllustrations(Node bible_child) {
        return null;
    }

    public static ProvenPatronHistory parsePatronHist(Node bible_child) {
        ProvenPatronHistory hist = new ProvenPatronHistory();
        
        NodeList children = bible_child.getChildNodes();
        
        List<Ownership> owners = new ArrayList<Ownership> ();
        List<Annotation> annotations = new ArrayList<Annotation> ();
        List<String> provenances = new ArrayList<String> ();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            String name = child.getNodeName();
            
            if (name.equals("production")) {
                Production prod = production(child);
                hist.setProduction(prod);
            } else if (name.equals("ownership")) {
                Ownership owns = ownership(child);
                owners.add(owns);
            } else if (name.equals("personalization")) {
                Personalization person = personalization(child);
                hist.setPersonalization(person);
            } else if (name.equals("annotation")) {
                Annotation ann = annotation(child);
                annotations.add(ann);
            } else if (name.equals("provenanceNote")) {
                provenances.add(getElementText(child));
            }
        }
        
        hist.setOwnerships(owners);
        hist.setAnnotations(annotations);
        hist.setProvenanceNote(provenances);
        
        return hist;
    }

    private static Ownership ownership(Node child) {
        Ownership ownership = new Ownership();
        
        NodeList nodes = child.getChildNodes();
        
        List<Owner> owners = new ArrayList<Owner>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            
            if (node.getNodeName().equals("owner")) {
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
            
            if (child.getNodeName().equals("ownerName")) {
                owner.setOwnerName(getElementText(child));
            } else if (child.getNodeName().equals("ownershipDate")) {
                owner.setOwnershipDate(getElementText(child));
                owner.setOwnershipEndDate(getIntegerAttribute(
                        child, "endYear"));
                owner.setOwnershipStartDate(getIntegerAttribute(
                        child, "startYear"));
            } else if (child.getNodeName().equals("ownerPlace")) {
                places.add(getElementText(child));
            }
        }
        
        return owner;
    }

    private static Production production(Node child) {
        Production prod = new Production();
        
        NodeList nodes = child.getChildNodes();
        
        List<Contributor> contributors = new ArrayList<Contributor>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            String name = node.getNodeName();
            
            if (name.equals("prodDate")) {
                prod.setProdDate(getElementText(node));
                prod.setProdEndDate(getIntegerAttribute(
                        node, "endYear"));
                prod.setProdStartDate(getIntegerAttribute(
                        node, "startYear"));
            } else if (name.equals("prodLoc")) {
                prod.setProdLoc(getElementText(node));
            } else if (name.equals("contributors")) {
                NodeList cont_list = node.getChildNodes();
                for (int j = 0; j < cont_list.getLength(); j++) {
                    Node cont_node = cont_list.item(j);
                    
                    Contributor cont = new Contributor();
                    cont.setType(cont_node.getNodeName());
                    cont.setValue(cont_node.getTextContent());
                    
                    contributors.add(cont);
                }
            } else if (name.equals("productionNote")) {
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
            String child_name = child.getNodeName();
            
            if (child_name.equals("volumes")) {
                Volumes volumes = volumes(child);
                phys_char.setVolumes(volumes);
            } else if (child_name.equals("dimensions")) {
                dims.add(dimensions(child));
            } else if (child_name.equals("folios")) {
                Folios folios = folios(child);
                phys_char.setFolios(folios);
            } else if (child_name.equals("quireStruct")) {
                QuireStructure struct = quireStruct(child);
                structs.add(struct);
            } else if (child_name.equals("pageLayout")) {
                PageLayout layout = pageLayout(child);
                phys_char.setPageLayout(layout);
            } else if (child_name.equals("rubricNote")) {
                phys_char.setRubricNotes(getElementText(child));
            } else if (child_name.equals("pageLayoutNote")) {
                phys_char.setPageLayoutNotes(getElementText(child));
            } else if (child_name.equals("glossHeadings")) {
                glosses.add(getElementText(child));
            } else if (child_name.equals("underlining")) {
                underlinings.add(getElementText(child));
            } else if (child_name.equals("materials")) {
                Materials mats = materials(child);
                phys_char.setMaterials(mats);
            } else if (child_name.equals("physicalNotes")) {
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
            
            if (node.getNodeName().equals("support")) {
                mats.setSupport(getAttributeFromNode(node, "material"));
            } else if (node.getNodeName().equals("binding")) {
                NodeList bindings = node.getChildNodes();
                
                for (int j = 0; j < bindings.getLength(); j++) {
                    Node bind = bindings.item(j);
                    
                    if (bind.getNodeName().equals("bindMaterial")) {
                        mats.setBindMaterial(getElementText(bind));
                    } else if (bind.getNodeName().equals("bindDate")) {
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
        
        layout.setColumns(getAttributeFromNode(
                child, "columns"));
        layout.setGlossPlace(getAttributeFromNode(
                child, "glossPlace"));
        layout.setRunningHeads(getAttributeFromNode(
                child, "runningHeads"));
        layout.setChapterNumbers(getAttributeFromNode(
                child, "chapterNumbers"));
        layout.setSmallLetterHist(getAttributeFromNode(
                child, "smallLettersHist"));
        layout.setCatchphrases(getAttributeFromNode(
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
            String name = node.getNodeName();
            
            if (name.equals("quireTotal")) {
                total.add(getIntegerAttribute(node, "number"));
            } else if (name.equals("typicalQuire")) {
                typical.add(getIntegerAttribute(node, "number"));
            } else if (name.equals("fullQuireStruct")) {
                full.add(node.getTextContent());
            } else if (name.equals("quireNote")) {
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
            
            if (node.getNodeName().equals("indVolumes")) {
                IndVolume ind = new IndVolume();
                
                ind.setVolume(getIntegerAttribute(node, "n"));
                ind.setValue(node.getTextContent());
                
                ind_vols.add(ind);
            } else if (node.getNodeName().equals("totalFolios")) {
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
            String node_name = node.getNodeName();
            
            if (node_name.equals("page")) {
                dims.setPage(node.getTextContent());
            } else if (node_name.equals("textBlock")) {
                dims.setTextBlock(node.getTextContent());
            } // TODO throw exception if unsupported tag found
        }
        
        dims.setUnits(getAttributeFromNode(child, "units"));
        dims.setVolume(getIntegerAttribute(child, "volume"));
        
        return dims;
    }

    private static Volumes volumes(Node child) {
        Volumes volumes = new Volumes();
        
        NodeList children = child.getChildNodes();
        
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            
            if (node.getNodeName().equals("volumeNote")) {
                volumes.setVolumeNotes(node.getTextContent());
            }
        }
        
        volumes.setPresentState(Volumes.State.getState(
                getAttributeFromNode(child, "presentState")));
        volumes.setPreviousState(Volumes.State.getState(
                getAttributeFromNode(child, "previousState")));
        
        return volumes;
    }

    
    
}
