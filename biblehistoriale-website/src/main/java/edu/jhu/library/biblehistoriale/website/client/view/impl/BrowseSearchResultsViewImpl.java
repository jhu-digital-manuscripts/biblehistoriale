package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;

public class BrowseSearchResultsViewImpl extends Composite 
        implements BrowseSearchResultsView {
    
    private final FlowPanel main;
    
    private final HTML label;
    
    public BrowseSearchResultsViewImpl() {
        this.main = new FlowPanel();
        
        this.label = new HTML("Search results go here!");
        
        main.add(label);
        
        initWidget(main);
    }

    @Override
    public void setLabelText(String text) {
        label.setText(text);
    }
    
}
