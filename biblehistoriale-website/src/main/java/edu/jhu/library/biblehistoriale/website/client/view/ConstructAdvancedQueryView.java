package edu.jhu.library.biblehistoriale.website.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public interface ConstructAdvancedQueryView extends IsWidget {
    
    public interface Presenter {
        
    }
    
    /**
     * Add a new row to the advanced query builder
     */
    void addQueryRow();
    
    /**
     * Get the query operation of a row, AND or OR
     * 
     * @param row
     * @return
     */
    String getOperation(int row);
    
    /**
     * Get the TermField of a row (the search category)
     * 
     * @param row
     * @return
     */
    String getField(int row);
    
    /**
     * Get the text entered by the user. 
     * 
     * @param row
     * @return
     */
    String getSearchTerm(int row);
    
    int getRowCount();
    
    HandlerRegistration addSearchClickHandler(ClickHandler handler);
    
    HandlerRegistration addFieldClickHandler(ClickHandler handler);
    
}
