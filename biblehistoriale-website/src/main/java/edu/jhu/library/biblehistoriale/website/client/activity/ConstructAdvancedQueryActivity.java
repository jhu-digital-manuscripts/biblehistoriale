package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.ConstructAdvancedQueryPlace;
import edu.jhu.library.biblehistoriale.website.client.view.ConstructAdvancedQueryView;

public class ConstructAdvancedQueryActivity extends AbstractActivity
        implements ConstructAdvancedQueryView.Presenter {

    private ConstructAdvancedQueryView view;
    
    public ConstructAdvancedQueryActivity(ConstructAdvancedQueryPlace place,
            ClientFactory client_factory) {
        this.view = client_factory.constructAdvancedQueryView();
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

}
