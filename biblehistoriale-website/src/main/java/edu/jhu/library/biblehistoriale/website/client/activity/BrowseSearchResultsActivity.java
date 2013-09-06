package edu.jhu.library.biblehistoriale.website.client.activity;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryMatch;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseSearchResultsPlace;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseSearchResultsView;

public class BrowseSearchResultsActivity extends AbstractActivity 
        implements BrowseSearchResultsView.Presenter {

    private final BrowseSearchResultsView view;
    private final Query query;
    private final QueryOptions opts;
    
    private final BibleHistorialeServiceAsync service;
    
    public BrowseSearchResultsActivity(BrowseSearchResultsPlace place,
            ClientFactory client_factory) {
        this.view = client_factory.browseSearchResultsView();
        this.query = place.getQuery();
        this.opts = place.getQueryOptions();
        
        this.service = client_factory.service();
        
        service.search(query, opts, new AsyncCallback<QueryResult>() {

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                Window.alert("Search failed....");
            }

            @Override
            public void onSuccess(QueryResult result) {
                // TODO Auto-generated method stub
                List<QueryMatch> matches = result.matches();
                
                if (matches == null) {
                    return;
                }
                
                if (matches.size() == 0) {
                    Window.alert("No matches found :( ");
                    return;
                }
                Window.alert("Number of matches: " + matches.size()
                        + "\nFirst id = " + matches.get(0).getId()
                        + "\ncontext: " + matches.get(0).getContext());
                view.setLabelText("Number of matches: " + matches.size()
                        + "\nFirst id = " + matches.get(0).getId()
                        + "\ncontext: " + matches.get(0).getContext());
            }
            
        });
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }
    
    
    
}
