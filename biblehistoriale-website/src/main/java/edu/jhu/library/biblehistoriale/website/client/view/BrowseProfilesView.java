package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.shared.CriteriaNode;

/**
 * UI interface. This view allows users to browse through 
 * MS profiles according to criteria
 */
public interface BrowseProfilesView extends IsWidget {
    
    public interface Presenter {
        
    }
    
    CriteriaNode getSelectedNode();
    
    void displayByCriteria(CriteriaNode node);
    
    HandlerRegistration addSelectionChangeHandler(SelectionChangeEvent.Handler handler);
}
