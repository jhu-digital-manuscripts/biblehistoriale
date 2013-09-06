package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.jhu.library.biblehistoriale.website.client.view.ConstructAdvancedQueryView;

public class ConstructAdvancedQueryViewImpl extends Composite
        implements ConstructAdvancedQueryView {
    
    private final FlowPanel main;
    
    public ConstructAdvancedQueryViewImpl() {
        this.main = new FlowPanel();
        
        initWidget(main);
    }
    
}
