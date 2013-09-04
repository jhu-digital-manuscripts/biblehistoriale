package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;
import edu.jhu.library.biblehistoriale.website.client.view.impl.BrowseProfilesViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ContactUsViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ProfileDetailViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ProjectInfoViewImpl;

public class ClientFactoryImpl implements ClientFactory {

    private static EventBus event_bus = new SimpleEventBus();
    private static PlaceController place_controller =
            new PlaceController(event_bus);
    
    @Override
    public EventBus eventBus() {
        return event_bus;
    }

    @Override
    public PlaceController placeController() {
        return place_controller;
    }
    
    @Override
    public ProjectInfoView projectInfoView() {
        return new ProjectInfoViewImpl();
    }

    @Override
    public BrowseProfilesView browseProfilesView() {
        return new BrowseProfilesViewImpl();
    }

    @Override
    public ContactUsView contactUsView() {
        return new ContactUsViewImpl();
    }

    @Override
    public ProfileDetailView profileDetailView() {
        return new ProfileDetailViewImpl();
    }

}
