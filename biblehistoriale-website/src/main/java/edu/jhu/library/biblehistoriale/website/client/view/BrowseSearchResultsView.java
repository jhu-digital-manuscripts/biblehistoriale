package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

public interface BrowseSearchResultsView extends IsWidget {
    
    public interface Presenter {
        
    }
    
    void setLabelText(String text);
    
}
