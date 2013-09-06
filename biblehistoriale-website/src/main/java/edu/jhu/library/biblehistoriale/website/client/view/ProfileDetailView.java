package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.user.client.ui.IsWidget;

import edu.jhu.library.biblehistoriale.model.profile.Bible;

/**
 * View of the detailed information of an MS profile
 */
public interface ProfileDetailView extends IsWidget {
    
    public interface Presenter {
        
    }
    
    void display(Bible bible);
    
}
