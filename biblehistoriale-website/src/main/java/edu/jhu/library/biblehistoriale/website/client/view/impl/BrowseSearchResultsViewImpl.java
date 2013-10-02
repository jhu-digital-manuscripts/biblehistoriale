package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.query.QueryMatch;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.website.client.CellListResources;
import edu.jhu.library.biblehistoriale.website.client.Messages;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;
import edu.jhu.library.biblehistoriale.website.client.widgets.QueryMatchCell;

public class BrowseSearchResultsViewImpl extends Composite 
        implements BrowseSearchResultsView {
    
    private final FlowPanel main;
    
    private final HTML failure_message;
    
    private final CellList<QueryMatch> cell_list;
    private final SingleSelectionModel<QueryMatch> selection_model;
    
    private final SimplePager pager;
    private final ListDataProvider<QueryMatch> data_provider;
    
    public BrowseSearchResultsViewImpl() {
        CellListResources cell_res = GWT.create(CellListResources.class);
        
        this.main = new FlowPanel();
        
        this.failure_message = new HTML(Messages.INSTANCE.noResultsFound());
        
        this.cell_list = new CellList<QueryMatch> (new QueryMatchCell(), cell_res);
        cell_list.setPageSize(2);
        
        this.selection_model = new SingleSelectionModel<QueryMatch> ();
        cell_list.setSelectionModel(selection_model);
        
        this.data_provider = new ListDataProvider<QueryMatch>();
        this.pager = new SimplePager();
        
        pager.setDisplay(cell_list);
        data_provider.addDataDisplay(cell_list);
        
        initWidget(main);
    }

    @Override
    public void setQueryResults(QueryResult result) {
        if (result == null) {
            main.add(failure_message);
            return;
        }
        
        main.add(pager);
        main.add(cell_list);
        
        cell_list.setRowCount((int) result.getTotal(), true);
        data_provider.setList(result.matches());
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
