package edu.jhu.library.biblehistoriale.website.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.activity.BrowseProfilesActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.BrowseSearchResultsActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.ConstructAdvancedQueryActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.ContactUsActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.ProfileDetailActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.ProjectInfoActivity;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseSearchResultsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ConstructAdvancedQueryPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ContactUsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProjectInfoPlace;

public class AppActivityMapper implements ActivityMapper {

    private ClientFactory client_factory;
    
    public AppActivityMapper(ClientFactory client_factory) {
        super();
        this.client_factory = client_factory;
    }
    
    /**
     * Map each Place to its corresponding Activity.
     * 
     * @param place
     */
    public Activity getActivity(Place place) {
        
        // TODO Add mappings of places to activities here. 
        if (place instanceof BrowseProfilesPlace) {
            return new BrowseProfilesActivity(
                    (BrowseProfilesPlace) place, client_factory);
        } else if (place instanceof ProjectInfoPlace) {
            return new ProjectInfoActivity(
                    (ProjectInfoPlace) place, client_factory);
        } else if (place instanceof ContactUsPlace) {
            return new ContactUsActivity(
                    (ContactUsPlace) place, client_factory);
        } else if (place instanceof ProfileDetailPlace) {
            return new ProfileDetailActivity(
                    (ProfileDetailPlace) place, client_factory);
        } else if (place instanceof ConstructAdvancedQueryPlace) {
            return new ConstructAdvancedQueryActivity(
                    (ConstructAdvancedQueryPlace) place, client_factory);
        } else if (place instanceof BrowseSearchResultsPlace) {
            return new BrowseSearchResultsActivity(
                    (BrowseSearchResultsPlace) place, client_factory);
        }
        
        return null;
    }
    
    
    
}
