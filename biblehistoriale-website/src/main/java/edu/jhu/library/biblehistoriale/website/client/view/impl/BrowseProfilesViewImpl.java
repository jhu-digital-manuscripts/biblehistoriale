package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;

public class BrowseProfilesViewImpl extends Composite 
        implements BrowseProfilesView {
    
    private final FlowPanel main;
    
    public BrowseProfilesViewImpl() {
        this.main = new FlowPanel();
        
        main.add(new HTML("Here, the user will be able to browser the MS profiles, " +
        		"choose filtering criteria, and select a profile for detailed viewing."));
        
        initWidget(main);
    }
    
    // TODO: investigate CellTree and other Cell widgets to see which would be appropriate
    
}
