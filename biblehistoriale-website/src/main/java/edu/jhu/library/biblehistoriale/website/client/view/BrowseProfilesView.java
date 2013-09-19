package edu.jhu.library.biblehistoriale.website.client.view;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.shared.BrowseCriteria;

/**
 * UI interface. This view allows users to browse through 
 * MS profiles according to criteria
 */
public interface BrowseProfilesView extends IsWidget {
    
    public interface Presenter {
        
        // TODO setProfiles(...)
        
    }
    
    List<Label> displayByCriteria(HashMap<BrowseCriteria, HashMap<String, String[]>> crit);

}
