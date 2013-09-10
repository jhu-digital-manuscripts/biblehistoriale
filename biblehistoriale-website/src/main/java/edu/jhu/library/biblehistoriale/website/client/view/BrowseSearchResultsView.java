package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.query.QueryMatch;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;

public interface BrowseSearchResultsView extends IsWidget {
    
    public interface Presenter {
        
    }
    
    void setQueryResults(QueryResult result);
    
    QueryMatch getSelectedMatch();
    
    HandlerRegistration addSelectionChangeHandler(SelectionChangeEvent.Handler handler);
    
}
