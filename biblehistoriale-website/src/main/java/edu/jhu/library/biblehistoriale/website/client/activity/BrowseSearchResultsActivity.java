package edu.jhu.library.biblehistoriale.website.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryMatch;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseSearchResultsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;

/**
 * Controls the browse search results view. Associated with 
 * BrowseSearchResultsPlace.
 * 
 * @see BrowseSearchResultsView
 * @see BrowseSearchResultsPlace
 */
public class BrowseSearchResultsActivity extends AbstractActivity 
        implements BrowseSearchResultsView.Presenter {

    private final BrowseSearchResultsView view;
    private final Query query;
    private final QueryOptions opts;
    
    private final PlaceController place_controller;
    private final BibleHistorialeServiceAsync service;
    
    private final List<HandlerRegistration> handlers;
    
    public BrowseSearchResultsActivity(BrowseSearchResultsPlace place,
            ClientFactory client_factory) {
        this.view = client_factory.newBrowseSearchResultsView();
        this.query = place.getQuery();
        this.opts = place.getQueryOptions();
        
        this.place_controller = client_factory.placeController();
        this.service = client_factory.service();
        
        this.handlers = new ArrayList<HandlerRegistration> ();
        
        bind();
        
        BrowseSearchResultsPlace.Tokenizer tokenizer = 
                new BrowseSearchResultsPlace.Tokenizer();
        String q_str = tokenizer.getToken(place);
        view.setQueryMessage(q_str.substring(0, q_str.length() - 1));
        
        service.search(query, opts, new AsyncCallback<QueryResult>() {

            @Override
            public void onFailure(Throwable caught) {
                view.setQueryResults(null);
            }

            @Override
            public void onSuccess(QueryResult result) {
                view.setQueryResults(result);
            }
            
        });
    }
    
    private void bind() {
        handlers.add(view.addSelectionChangeHandler(
                new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                QueryMatch selected = view.getSelectedMatch();
                
                if (selected == null) {
                    return;
                }
                
                place_controller.goTo(
                        new ProfileDetailPlace(selected.getId()));
            }
        }));
        
        view.asWidget().addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                if (!event.isAttached()) {
                    for (HandlerRegistration hr : handlers) {
                        hr.removeHandler();
                    }
                }
            }
        });
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }
}
