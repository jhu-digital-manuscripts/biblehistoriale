package edu.jhu.library.biblehistoriale.website.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
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

    private static final Map<String, QueryResult> search_cache =
            new HashMap<String, QueryResult> ();
    private static final int MAX_CACHE_SIZE = 100;
    
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

        if (search_cache.containsKey(query.toString())) {

            view.setQueryResults(search_cache.get(query.toString()));
            
        } else {
            service.search(query, opts, new AsyncCallback<QueryResult>() {

                @Override
                public void onFailure(Throwable caught) {
                    view.setQueryResults(null);
                }

                @Override
                public void onSuccess(QueryResult result) {
                    
                    if (search_cache.size() > MAX_CACHE_SIZE) {
                        search_cache.clear();
                    }
                    
                    search_cache.put(query.toString(), result);
                    
                    view.setQueryResults(result);
                }
                
            });
        }
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
