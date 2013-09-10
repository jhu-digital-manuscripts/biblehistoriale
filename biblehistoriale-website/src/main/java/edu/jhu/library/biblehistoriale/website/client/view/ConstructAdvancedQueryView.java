package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public interface ConstructAdvancedQueryView extends IsWidget {
    
    public interface Presenter {
        
    }
    
    void addQueryRow();
    
    String getOperation(int row);
    
    String getField(int row);
    
    String getSearchTerm(int row);
    
    int getRowCount();
    
    HandlerRegistration addSearchClickHandler(ClickHandler handler);
    
    HandlerRegistration addFieldClickHandler(ClickHandler handler);
    
}
