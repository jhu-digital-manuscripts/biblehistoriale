package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

import edu.jhu.library.biblehistoriale.website.client.mvp.AppActivityMapper;
import edu.jhu.library.biblehistoriale.website.client.mvp.AppPlaceHistoryMapper;
import edu.jhu.library.biblehistoriale.website.client.place.HomePlace;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BibleHistorialeWebsite implements EntryPoint {
	
    private Place default_place = new HomePlace();
    private SimplePanel container = new SimplePanel();
    
	public void onModuleLoad() {
	    
	    ClientFactory client_factory = GWT.create(ClientFactory.class);
	    EventBus event_bus = client_factory.eventBus();
	    PlaceController place_controller = client_factory.placeController();
	    
	    // Start ActivityManager for main widget with ActivityMapper
	    ActivityMapper activity_mapper = new AppActivityMapper(client_factory);
	    ActivityManager activity_manager =
	            new ActivityManager(activity_mapper, event_bus);
	    activity_manager.setDisplay(container);
	    
	    AppPlaceHistoryMapper history_mapper = 
	            GWT.create(AppPlaceHistoryMapper.class);
	    PlaceHistoryHandler history_handler = 
	            new PlaceHistoryHandler(history_mapper);
	    history_handler.register(place_controller, event_bus, default_place);
	    
	    // TODO: use a different container widget, not Root!
	    RootPanel.get().add(container);
	    history_handler.handleCurrentHistory();
	    
	}
}
