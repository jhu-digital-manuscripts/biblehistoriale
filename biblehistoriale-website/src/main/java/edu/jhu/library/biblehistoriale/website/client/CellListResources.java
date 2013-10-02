package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellList;

/**
 * Resources for the Search Results cell list in 
 * BrowseSearchResults.
 */
public interface CellListResources extends CellList.Resources {
    
    public static final CellListResources INSTANCE = 
            GWT.create(CellListResources.class);
    
    @Source("BHCellListStyle.css")
    CellList.Style cellListStyle();
    
}
