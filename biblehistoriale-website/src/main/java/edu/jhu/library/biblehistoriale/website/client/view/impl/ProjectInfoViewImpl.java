package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import edu.jhu.library.biblehistoriale.website.client.Messages;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;

public class ProjectInfoViewImpl extends Composite implements ProjectInfoView {
    
    private final FlowPanel main;
    
    private final HTML content;
    
    // TODO: Grab contents from .properties file
    public ProjectInfoViewImpl() {
        this.main = new FlowPanel();
        this.content = new HTML(Messages.INSTANCE.projectInfo());
        
        main.add(content);
        
        initWidget(main);
    }
    
}
