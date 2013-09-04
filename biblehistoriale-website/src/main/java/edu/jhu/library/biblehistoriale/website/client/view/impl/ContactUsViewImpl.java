package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;

public class ContactUsViewImpl extends Composite implements ContactUsView {
    
    private final FlowPanel main;
    
    public ContactUsViewImpl() {
        this.main = new FlowPanel();
        
        main.add(new HTML("This page will contain contact information" +
        		" and will provide a link to an email address"));
        
        initWidget(main);
    }
    
}
