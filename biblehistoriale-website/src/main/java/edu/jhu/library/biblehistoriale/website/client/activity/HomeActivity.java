package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.HomePlace;
import edu.jhu.library.biblehistoriale.website.client.view.HomeView;

public class HomeActivity extends AbstractActivity 
        implements HomeView.Presenter {
    
    private ClientFactory client_factory;
    
    public HomeActivity(HomePlace place, ClientFactory client_factory) {
        this.client_factory = client_factory;
    }

    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        HomeView home_view = client_factory.homeView();
        home_view.setPresenter(this);
        panel.setWidget(home_view.asWidget());
    }

    /**
     * Navigate to a new Place in the browser.
     * 
     * @param place
     */
    public void goTo(Place place) {
        client_factory.placeController().goTo(place);
    }
    
}
