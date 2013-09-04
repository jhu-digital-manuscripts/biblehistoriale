package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;

public class ProfileDetailViewImpl extends Composite implements ProfileDetailView {
    
    private final FlowPanel main;
    
    public ProfileDetailViewImpl() {
        this.main = new FlowPanel();
        
        main.add(new HTML("This will have the full detailed view of the MS profile"));
        
        initWidget(main);
    }
    
}
