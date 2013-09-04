package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;

public class BrowseProfilesActivity extends AbstractActivity 
        implements BrowseProfilesView.Presenter {

    private BrowseProfilesView view;
    
    public BrowseProfilesActivity(BrowseProfilesPlace place, 
            ClientFactory client_factory) {
        this.view = client_factory.browseProfilesView();
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

}
