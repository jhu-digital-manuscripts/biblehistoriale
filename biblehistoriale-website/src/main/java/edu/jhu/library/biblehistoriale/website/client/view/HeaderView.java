package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * UI interface for the website header. Includes a basic search and
 * links to project info place, contact info place, advanced search place
 */
public interface HeaderView extends IsWidget {
    
    interface Presenter extends IsWidget {
        
        void goTo(Place place);
        
        void resize(int width, int height);
        
    }
    
    HandlerRegistration addInfoLinkClickHandler(ClickHandler handler);
    
    HandlerRegistration addContactInfoClickHandler(ClickHandler handler);
    
    HandlerRegistration addAdvancedSearchClickHandler(ClickHandler handler);
    
    HandlerRegistration addSearchClickHandler(ClickHandler handler);
    
    HandlerRegistration addSearchKeyPressHandler(KeyPressHandler handler);
    
    String searchBarValue();
    
    void resize(int width, int height);
    
}
