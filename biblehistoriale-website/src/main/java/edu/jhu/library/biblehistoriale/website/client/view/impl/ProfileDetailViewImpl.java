package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;
import edu.jhu.library.biblehistoriale.website.client.widgets.BibleDisplay;

public class ProfileDetailViewImpl extends Composite implements ProfileDetailView {
    
    private final FlowPanel main;
    
    private final Label loading;
    
    public ProfileDetailViewImpl() {
        this.main = new FlowPanel();
        
        this.loading = new Label("Loading profile...");
        main.add(loading);
        
        initWidget(main);
    }

    @Override
    public void display(Bible bible) {
        main.remove(loading);
        main.add(new BibleDisplay(bible));
    }
    
}
