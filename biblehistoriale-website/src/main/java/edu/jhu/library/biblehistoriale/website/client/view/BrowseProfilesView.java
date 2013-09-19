package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * UI interface. This view allows users to browse through 
 * MS profiles according to criteria
 */
public interface BrowseProfilesView extends IsWidget {
    
    public interface Presenter {
        
        // TODO setProfiles(...)
        
    }
    
    HandlerRegistration addClickHandlerToProfileLink(ClickHandler handler);
    
    HandlerRegistration addClickHandlerToVatLink(ClickHandler handler);

}
