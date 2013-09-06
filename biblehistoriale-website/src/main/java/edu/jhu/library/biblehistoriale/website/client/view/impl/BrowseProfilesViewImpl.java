package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;

public class BrowseProfilesViewImpl extends Composite 
        implements BrowseProfilesView {
    
    private final FlowPanel main;
    
    private final Label profile_link;
    
    public BrowseProfilesViewImpl() {
        this.main = new FlowPanel();
        
        this.profile_link = new Label("Profile view of Brussels KBR9001-2");
        
        main.add(new HTML("Here, the user will be able to browser the MS profiles, " +
        		"choose filtering criteria, and select a profile for detailed viewing."));
        main.add(profile_link);
        
        initWidget(main);
    }

    @Override
    public HandlerRegistration addClickHandlerToProfileLink(ClickHandler handler) {
        return profile_link.addClickHandler(handler);
    }
    
    // TODO: investigate CellTree and other Cell widgets to see which would be appropriate
    
}
