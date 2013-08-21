package edu.jhu.library.biblehistoriale.profile.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.profile.Bibliography;
import edu.jhu.library.biblehistoriale.model.profile.Classification;
import edu.jhu.library.biblehistoriale.model.profile.IllustrationList;
import edu.jhu.library.biblehistoriale.model.profile.PhysicalCharacteristics;
import edu.jhu.library.biblehistoriale.model.profile.ProvenPatronHistory;
import edu.jhu.library.biblehistoriale.model.profile.TextualContent;

public class ProfileBuilder {
    
    private static DocumentBuilder builder;
    
    private final static boolean ignore_comments = true;
    
    /**
     * Create a new DOM document from a file path.
     * 
     * A Path object can be created by using the Paths utility class. EX:
     * Path path = Paths.get("/a/path/string");
     * 
     * @param path
     * @return
     * @throws SAXException
     * @throws IOException
     */
    public static Document createDocument(Path path) 
            throws SAXException, IOException {
        
        if (builder == null) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(ignore_comments);
            
            try {
                builder = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                // TODO
            }
        }
        
        OpenOption opt = StandardOpenOption.READ;
        
        return builder.parse(Files.newInputStream(path, opt));
    }
    
    public static Bible buildProfile(Path path) {
        String[] tokens = path.toString().split("/");
        String filename = tokens[tokens.length - 1];
        
        try {
            return buildProfile(filename, createDocument(path));
        } catch (SAXException e) {
            
        } catch (IOException e) {
            
        }
        
        return null;
    }
    
    /**
     * Parse an XML MS profile and build a java Bible
     * 
     * @param file
     * @return
     */
    public static Bible buildProfile(String filename, Document doc) {
        Bible bible = new Bible();
        
        bible.setId(filename);
        
        NodeList bibles = doc.getElementsByTagName("bible");
        Node bible_node = bibles.item(0);
        
        bible.setScannedMss(
                BibleParser.getAttributeValue(bible_node, "scannedMss"));
        bible.setBibleHistorialeTranscription(
                BibleParser.getAttributeValue(bible_node,
                        "biblehistorialeTranscriptions"));
        
        NodeList bible_nodes = bible_node.getChildNodes();
        
        for (int i = 0; i < bible_nodes.getLength(); i++) {
            
            Node bible_child = bible_nodes.item(i);
            String child_name = bible_child.getNodeName();
            
            if (child_name.equals("physChar")) {
                PhysicalCharacteristics phys_char = 
                        BibleParser.parsePhysChar(bible_child);
                bible.setPhysChar(phys_char);
            } else if (child_name.equals("provenPatronHist")) {
                ProvenPatronHistory patron_hist = 
                        BibleParser.parsePatronHist(bible_child);
                bible.setProvenPatronHist(patron_hist);
            } else if (child_name.equals("illustrations")) {
                IllustrationList ill_list = 
                        BibleParser.parseIllustrations(bible_child);
                bible.setIllustrations(ill_list);
            } else if (child_name.equals("classification")) {
                Classification classification = 
                        BibleParser.parseClassification(bible_child);
                bible.setClassification(classification);
            } else if (child_name.equals("textualContents")) {
                TextualContent text_content = 
                        BibleParser.parseTextualContent(bible_child);
                bible.setTextualContent(text_content);
            } else if (child_name.equals("bibliography")) {
                Bibliography biblio = 
                        BibleParser.parseBibliography(bible_child);
                bible.setBibliography(biblio);
            } else {
                // TODO: throw exception, unsupported tag
            }
        }
        
        return bible;
    }
    
    
    
}
