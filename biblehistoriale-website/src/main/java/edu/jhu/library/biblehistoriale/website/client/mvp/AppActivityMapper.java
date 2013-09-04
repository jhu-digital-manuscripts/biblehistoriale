package edu.jhu.library.biblehistoriale.website.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.activity.BrowseProfilesActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.HomeActivity;
import edu.jhu.library.biblehistoriale.website.client.activity.ProjectInfoActivity;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.HomePlace;
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
        if (place instanceof HomePlace) {
            return new HomeActivity((HomePlace)place, client_factory);
        } else if (place instanceof BrowseProfilesPlace) {
            return new BrowseProfilesActivity(
                    (BrowseProfilesPlace) place, client_factory);
        } else if (place instanceof ProjectInfoPlace) {
            return new ProjectInfoActivity(
                    (ProjectInfoPlace) place, client_factory);
        }
        
        return null;
    }
    
    
    
}
