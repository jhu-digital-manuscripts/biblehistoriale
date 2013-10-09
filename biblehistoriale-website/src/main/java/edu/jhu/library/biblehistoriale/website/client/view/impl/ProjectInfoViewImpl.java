package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import edu.jhu.library.biblehistoriale.website.client.ProjectInfoConstants;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;

public class ProjectInfoViewImpl extends Composite implements ProjectInfoView {
    
    private final ScrollPanel main;
    
    private final HTML content;
    
    public ProjectInfoViewImpl() {
        
        this.main = new ScrollPanel();
        this.content = new HTML(
                ProjectInfoConstants.INSTANCE.projectInfo());
        
        main.add(content);
        main.setSize("100%", "100%");
        
        initWidget(main);
    }
    
}
