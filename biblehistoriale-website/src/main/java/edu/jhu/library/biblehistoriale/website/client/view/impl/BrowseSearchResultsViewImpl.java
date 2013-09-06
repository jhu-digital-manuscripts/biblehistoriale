package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;

public class BrowseSearchResultsViewImpl extends Composite 
        implements BrowseSearchResultsView {
    
    private final FlowPanel main;
    
    private final Label label;
    
    public BrowseSearchResultsViewImpl() {
        this.main = new FlowPanel();
        
        this.label = new Label("Search results go here!");
        
        initWidget(main);
    }

    @Override
    public void setLabelText(String text) {
        label.setText(text);
    }
    
}
