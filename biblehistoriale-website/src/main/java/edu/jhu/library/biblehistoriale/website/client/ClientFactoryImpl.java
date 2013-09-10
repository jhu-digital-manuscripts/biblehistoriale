package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeService;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;
import edu.jhu.library.biblehistoriale.website.client.view.ConstructAdvancedQueryView;
import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;
import edu.jhu.library.biblehistoriale.website.client.view.impl.BrowseProfilesViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.BrowseSearchResultsViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ConstructAdvancedQueryViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ContactUsViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ProfileDetailViewImpl;
import edu.jhu.library.biblehistoriale.website.client.view.impl.ProjectInfoViewImpl;

public class ClientFactoryImpl implements ClientFactory {

    private static EventBus event_bus = new SimpleEventBus();
    private static PlaceController place_controller =
            new PlaceController(event_bus);
    private static BibleHistorialeServiceAsync service = 
            GWT.create(BibleHistorialeService.class);
    
    @Override
    public EventBus eventBus() {
        return event_bus;
    }

    @Override
    public PlaceController placeController() {
        return place_controller;
    }
    
    @Override
    public BibleHistorialeServiceAsync service() {
        return service;
    }
    
    @Override
    public ProjectInfoView newProjectInfoView() {
        return new ProjectInfoViewImpl();
    }

    @Override
    public BrowseProfilesView newBrowseProfilesView() {
        return new BrowseProfilesViewImpl();
    }

    @Override
    public ContactUsView newContactUsView() {
        return new ContactUsViewImpl();
    }

    @Override
    public ProfileDetailView newProfileDetailView() {
        return new ProfileDetailViewImpl();
    }

    @Override
    public ConstructAdvancedQueryView newConstructAdvancedQueryView() {
        return new ConstructAdvancedQueryViewImpl();
    }

    @Override
    public BrowseSearchResultsView newBrowseSearchResultsView() {
        return new BrowseSearchResultsViewImpl();
    }
    
}
