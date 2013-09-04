package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;

public class ProfileDetailActivity extends AbstractActivity implements ProfileDetailView.Presenter {
    
    private ProfileDetailView view;
    
    public ProfileDetailActivity(ProfileDetailPlace place, ClientFactory client_factory) {
        this.view = client_factory.profileDetailView();
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

}
