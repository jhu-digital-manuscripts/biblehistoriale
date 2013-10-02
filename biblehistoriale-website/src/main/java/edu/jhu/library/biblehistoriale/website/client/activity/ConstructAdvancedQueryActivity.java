package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOperation;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.TermField;
import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseSearchResultsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ConstructAdvancedQueryPlace;
import edu.jhu.library.biblehistoriale.website.client.view.ConstructAdvancedQueryView;

/**
 * Controls the construct advanced query view. Associated with
 * ConstructAdvancedQueryPlace.
 * 
 * @see ConstructAdvancedQueryView
 * @see ConstructAdvancedQueryPlace
 */
public class ConstructAdvancedQueryActivity extends AbstractActivity
        implements ConstructAdvancedQueryView.Presenter {

    private ConstructAdvancedQueryView view;
    private PlaceController place_controller;
    
    public ConstructAdvancedQueryActivity(ConstructAdvancedQueryPlace place,
            ClientFactory client_factory) {
        this.view = client_factory.newConstructAdvancedQueryView();
        this.place_controller = client_factory.placeController();
        
        bind();
    }
    
    private void bind() {
        view.addFieldClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                view.addQueryRow();
            }
        });
        
        view.addSearchClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doSearch();
            }
        });
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }
    
    private void doSearch() {
        Query query = buildQuery();
        QueryOptions opts = buildOptions();
        
        if (query != null) {
            BrowseSearchResultsPlace place = 
                    new BrowseSearchResultsPlace(query, opts);
            
            place_controller.goTo(place);
        }
    }
    
    /**
     * Builds a Query from the information in the view
     * @return
     */
    private Query buildQuery() {
        
        if (view.getRowCount() == 0) {
            return null;
        }
        
        String search = view.getSearchTerm(0);
        String field = view.getField(0);
        
        if (search == null || search.equals("")) {
            return null;
        }
        
        Query query = new Query(
                TermField.getTermField(field), search);
        
        return buildQuery(1, query);
    }
    
    private Query buildQuery(int index, Query query) {
        
        if (index >= view.getRowCount()) {
            return query;
        }
        
        String op = view.getOperation(index);
        TermField field = TermField.getTermField(view.getField(index));
        String term = view.getSearchTerm(index);
        
        Query newQuery = new Query(field, term);
        
        Query q = null;
        try {
            QueryOperation qop = QueryOperation.valueOf(op);
            
            if (term == null || term.equals("")) {
                q = buildQuery(++index, query);
            } else {
                q = buildQuery(++index, new Query(qop, query, newQuery));
            }
            
        } catch (IllegalArgumentException e) {
            // If op is not a valid QueryOperation
            q = buildQuery(++index, query);
        }
        
        return buildQuery(++index, q);
    }
    
    private QueryOptions buildOptions() {
        // TODO
        return new QueryOptions();
    }

}
