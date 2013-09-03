package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import edu.jhu.library.biblehistoriale.website.client.view.HomeView;

public class HomeViewImpl extends Composite implements HomeView {

    private FlowPanel main;
    
    public HomeViewImpl() {
        main = new FlowPanel();
        
        main.add(new Label("This will be the home page"));
        
        initWidget(main);
    }
    
}
