package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.ProjectInfoPlace;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;

public class ProjectInfoActivity extends AbstractActivity 
        implements ProjectInfoView.Presenter {
    
    private ProjectInfoView view;
    //private ClientFactory client_factory;
    
    public ProjectInfoActivity(ProjectInfoPlace place, 
            ClientFactory client_factory) {
        //this.client_factory = client_factory;
        this.view = client_factory.projectInfoView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view.asWidget());
    }

}
