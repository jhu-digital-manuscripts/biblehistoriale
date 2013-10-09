package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import edu.jhu.library.biblehistoriale.website.client.Messages;
import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;

public class ContactUsViewImpl extends Composite implements ContactUsView {
    
    private final ScrollPanel top;
    private final FlowPanel main;
    
    public ContactUsViewImpl() {
        String email = Messages.INSTANCE.email();
        
        this.top = new ScrollPanel();
        this.main = new FlowPanel();
        
        main.add(new HTML(Messages.INSTANCE.contactDescription()));
        main.add(new Anchor(email, "mailto:" + email));
        
        top.add(main);
        top.setSize("100%", "100%");
        
        initWidget(top);
    }
    
}
