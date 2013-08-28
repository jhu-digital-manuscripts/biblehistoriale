package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import edu.jhu.library.biblehistoriale.website.client.view.HomeView;
import edu.jhu.library.biblehistoriale.website.client.view.impl.HomeViewImpl;

public class ClientFactoryImpl implements ClientFactory {

    private static EventBus event_bus = new SimpleEventBus();
    private static PlaceController place_controller =
            new PlaceController(event_bus);
    
    private static HomeView home_view = new HomeViewImpl();
    
    public EventBus eventBus() {
        return event_bus;
    }

    public PlaceController placeController() {
        return place_controller;
    }

    public HomeView homeView() {
        return home_view;
    }

}
