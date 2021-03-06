package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;
import edu.jhu.library.biblehistoriale.website.client.view.ConstructAdvancedQueryView;
import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;
import edu.jhu.library.biblehistoriale.website.client.view.ProjectInfoView;

public interface ClientFactory {
    
    EventBus eventBus();
    
    PlaceController placeController();
    
    BibleHistorialeServiceAsync service();
    
    ProjectInfoView newProjectInfoView();
    
    BrowseProfilesView newBrowseProfilesView();
    
    ContactUsView newContactUsView();
    
    ProfileDetailView newProfileDetailView();
    
    ConstructAdvancedQueryView newConstructAdvancedQueryView();
    
    BrowseSearchResultsView newBrowseSearchResultsView();
    
}
