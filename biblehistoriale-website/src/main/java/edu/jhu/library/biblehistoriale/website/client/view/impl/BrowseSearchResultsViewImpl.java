package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.query.QueryMatch;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;
import edu.jhu.library.biblehistoriale.website.client.widgets.QueryMatchCell;

public class BrowseSearchResultsViewImpl extends Composite 
        implements BrowseSearchResultsView {
    
    private final FlowPanel main;
    
    private final CellList<QueryMatch> cell_list;
    private final SingleSelectionModel<QueryMatch> selection_model;
    
    public BrowseSearchResultsViewImpl() {
        this.main = new FlowPanel();
        
        this.cell_list = new CellList<QueryMatch> (new QueryMatchCell());
        cell_list.setPageSize(10);
        
        this.selection_model = new SingleSelectionModel<QueryMatch> ();
        cell_list.setSelectionModel(selection_model);
        
        main.add(cell_list);
        
        initWidget(main);
    }

    @Override
    public void setQueryResults(QueryResult result) {
        cell_list.setRowCount((int) result.getTotal(), true);
        cell_list.setRowData(result.matches());
    }

    @Override
    public QueryMatch getSelectedMatch() {
        return selection_model.getSelectedObject();
    }

    @Override
    public HandlerRegistration addSelectionChangeHandler(Handler handler) {
        return cell_list.getSelectionModel()
                .addSelectionChangeHandler(handler);
    }
    
}
