package edu.jhu.library.biblehistoriale.website.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import edu.jhu.library.biblehistoriale.model.profile.Bible;

/**
 * A widget that displays the contents of a Bible Historiale
 * bible.
 */
public class BibleDisplay extends Composite {
    
    private final Bible bible;
    
    private final FlowPanel main;
    
    public BibleDisplay(Bible bible) {
        this.bible = bible;
        
        this.main = new FlowPanel();
        
        main.add(new Label(bible.getId()));
        main.add(new Label(this.bible.getClassification().getCoverTitle()));
        main.add(new Label(bible.getClassification().getCurrentRepository()));
        main.add(new Label(bible.getClassification().getCurrentShelfmark()));
        
        initWidget(main);
    }
    
}
