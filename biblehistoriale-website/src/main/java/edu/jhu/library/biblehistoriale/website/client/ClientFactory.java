package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import edu.jhu.library.biblehistoriale.website.client.view.HomeView;

public interface ClientFactory {
    
    EventBus eventBus();
    
    PlaceController placeController();
    
    // Put views here
    HomeView homeView();
    
}
