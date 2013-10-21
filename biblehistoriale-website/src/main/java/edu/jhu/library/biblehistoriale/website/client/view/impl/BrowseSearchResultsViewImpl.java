package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
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
    private final int PAGE_SIZE = 10;
    
    private final ScrollPanel top;
    private final FlowPanel main;
    private final FlowPanel query_message_panel;
    
    private final HTML loading_message;
    private final HTML noresults_message;
    private final HTML failed_message;
    private final HTML query_label;
    
    private final CellList<QueryMatch> cell_list;
    private final SingleSelectionModel<QueryMatch> selection_model;
    
    private final SimplePager pager;
    private final ListDataProvider<QueryMatch> data_provider;
    
    public BrowseSearchResultsViewImpl() {
        CellListResources cell_res = GWT.create(CellListResources.class);
        
        this.top = new ScrollPanel();
        this.main = new FlowPanel();
        this.query_message_panel = new FlowPanel();
        
        this.noresults_message = new HTML(Messages.INSTANCE.noResultsFound());
        this.loading_message = new HTML(Messages.INSTANCE.searchLoading());
        this.failed_message = new HTML(Messages.INSTANCE.searchFailed());
        this.query_label = new HTML();
        
        this.cell_list = new CellList<QueryMatch> (new QueryMatchCell(), cell_res);
        cell_list.setPageSize(PAGE_SIZE);
        
        this.selection_model = new SingleSelectionModel<QueryMatch> ();
        cell_list.setSelectionModel(selection_model);
        
        this.data_provider = new ListDataProvider<QueryMatch>();
        this.pager = new SimplePager();
        
        pager.setDisplay(cell_list);
        data_provider.addDataDisplay(cell_list);
        
        main.add(query_message_panel);
        main.add(loading_message);
        
        top.add(main);
        top.setSize("100%", "100%");
        
        SimpleHtmlSanitizer sanitizer = SimpleHtmlSanitizer.getInstance();
        query_label.setHTML(sanitizer.sanitize("<b>Query:</b> "));
        query_message_panel.add(query_label);
        
        initWidget(top);
    }

    @Override
    public void setQueryResults(QueryResult result) {
        main.remove(loading_message);
        
        if (result == null || result.matches() == null) {
            main.add(failed_message);
            return;
        }
        
        if (result.getTotal() == 0) {
            main.add(noresults_message);
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

    @Override
    public void setQueryMessage(String query) {
        query_message_panel.add(new Label(query));
    }
    
}
