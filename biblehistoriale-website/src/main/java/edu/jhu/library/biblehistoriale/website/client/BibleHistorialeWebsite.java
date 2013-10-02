package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.web.bindery.event.shared.EventBus;

import edu.jhu.library.biblehistoriale.website.client.mvp.AppActivityMapper;
import edu.jhu.library.biblehistoriale.website.client.mvp.AppPlaceHistoryMapper;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.view.impl.HeaderViewImpl;

/**
 * <p>Entry point classes define <code>onModuleLoad()</code>.</p>
 */
public class BibleHistorialeWebsite implements EntryPoint {
    public static final int HEADER_HEIGHT = 175;
	
    private Place default_place = new BrowseProfilesPlace();
    
    private ScrollPanel main_content = new ScrollPanel();
    private HeaderPresenter header;
    
    private final DockLayoutPanel main = new DockLayoutPanel(Unit.PX);
    
	public void onModuleLoad() {
	    
	    ClientFactory client_factory = GWT.create(ClientFactory.class);
	    EventBus event_bus = client_factory.eventBus();
	    PlaceController place_controller = client_factory.placeController();
	    
	    // Start ActivityManager for main widget with ActivityMapper
	    ActivityMapper activity_mapper = new AppActivityMapper(client_factory);
	    ActivityManager activity_manager =
	            new ActivityManager(activity_mapper, event_bus);
	    activity_manager.setDisplay(main_content);
	    
	    AppPlaceHistoryMapper history_mapper = 
	            GWT.create(AppPlaceHistoryMapper.class);
	    final PlaceHistoryHandler history_handler = 
	            new PlaceHistoryHandler(history_mapper);
	    history_handler.register(place_controller, event_bus, default_place);
	    
	    history_handler.handleCurrentHistory();
	    
	    // Set the base layout
        main.setStylePrimaryName("Main");
        main_content.addStyleName("Content");
        
        this.header = new HeaderPresenter(new HeaderViewImpl(), client_factory);
        
        main.addNorth(header, HEADER_HEIGHT);
        main.add(main_content);
        
        RootLayoutPanel.get().add(main);
	}
	
}
