package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import edu.jhu.library.biblehistoriale.website.client.License;
import edu.jhu.library.biblehistoriale.website.client.ProjectInfoConstants;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;

public class ProjectInfoViewImpl extends Composite implements ProjectInfoView {
    
    private final ScrollPanel top;
    private final FlowPanel main;
    
    private final HTML content;
    private final HTML license;
    
    public ProjectInfoViewImpl() {
        
        this.top = new ScrollPanel();
        this.main = new FlowPanel();
        this.content = new HTML(
                ProjectInfoConstants.INSTANCE.projectInfo());
        this.license = new HTML(
                License.INSTANCE.license());
        
        top.add(main);
        top.setSize("100%", "100%");
        
        main.add(content);
        main.add(license);
        
        initWidget(top);
    }
    
}
