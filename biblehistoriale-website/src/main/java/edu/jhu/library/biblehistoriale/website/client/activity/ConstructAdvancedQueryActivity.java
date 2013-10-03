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
import edu.jhu.library.biblehistoriale.model.query.TermType;
import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.QueryUtils;
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

    private static final QueryUtils utils = QueryUtils.getInstance();
    
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
        QueryOptions opts = utils.buildQueryOptions("");
        
        if (query != null) {
            BrowseSearchResultsPlace place = 
                    new BrowseSearchResultsPlace(query, opts);
            
            place_controller.goTo(place);
        }
    }
    
    /**
     * Build a query from the UI fields. If no search terms
     * are present <code>NULL</code> is returned.
     * 
     * @return
     */
    private Query buildQuery() {
        // Turn UI fields into a query token
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < view.getRowCount(); i++) {
            
            QueryOperation op = i == 0 ? QueryOperation.AND 
                    : QueryOperation.valueOf(view.getOperation(i));
            TermField field = TermField.getTermField(view.getField(i));
            String term = view.getSearchTerm(i);
            TermType type = TermType.PHRASE;
            
            if (term == null || term.equals("")) {
                continue;
            }
            
            sb.append(op
                    + utils.valueDelimiter()
                    + field
                    + utils.valueDelimiter()
                    + term
                    + utils.valueDelimiter()
                    + type
                    + utils.searchDelimiter());
            
        }
        
        if (sb.toString().equals("")) {
            return null;
        }
        
        return utils.buildQuery(sb.toString());
    }
}
