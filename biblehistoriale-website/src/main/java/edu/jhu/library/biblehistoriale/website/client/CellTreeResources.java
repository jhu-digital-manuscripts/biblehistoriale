package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.user.cellview.client.CellTree;

/**
 * Resources for the browse profiles by criteria cell tree in
 * BrowseProfiles.
 */
public interface CellTreeResources extends CellTree.Resources {
    
    @Source("BHCellTreeStyle.css")
    CellTree.Style cellTreeStyle();
    
}
