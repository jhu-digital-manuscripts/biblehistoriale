package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;

public interface ClientFactory {
    
    EventBus eventBus();
    
    PlaceController placeController();
    
    // Put views here
    ProjectInfoView projectInfoView();
    
    BrowseProfilesView browseProfilesView();
    
    ContactUsView contactUsView();
    
    ProfileDetailView profileDetailView();
    
}
