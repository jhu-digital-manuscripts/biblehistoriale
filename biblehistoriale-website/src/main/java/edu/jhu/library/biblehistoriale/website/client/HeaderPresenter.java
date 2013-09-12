package edu.jhu.library.biblehistoriale.website.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.TermField;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseSearchResultsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ConstructAdvancedQueryPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ContactUsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProjectInfoPlace;
import edu.jhu.library.biblehistoriale.website.client.view.HeaderView;

/**
 * Provides logic for the HeaderView
 */
public class HeaderPresenter implements HeaderView.Presenter {

    private final ClientFactory client_factory;
    private final HeaderView view;
    
    public HeaderPresenter(HeaderView view,
            ClientFactory client_factory) {
        this.client_factory = client_factory;
        this.view = view;
        
        List<HandlerRegistration> handlers = 
                new ArrayList<HandlerRegistration>();
        bind(handlers);
    }
    
    private void bind(final List<HandlerRegistration> handlers) {
        handlers.add(view.addBrowseLinkClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                goTo(new BrowseProfilesPlace(BrowseCriteria.ALL));
            }
        }));
        
        handlers.add(view.addInfoLinkClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                goTo(new ProjectInfoPlace());
            }
        }));
        
        handlers.add(view.addContactInfoClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                goTo(new ContactUsPlace());
            }
        }));
        
        handlers.add(view.addAdvancedSearchClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                goTo(new ConstructAdvancedQueryPlace());
            }
        }));
        
        handlers.add(view.addSearchClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                basicSearch();
            }
        }));
        
        handlers.add(view.addSearchKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    basicSearch();
                }
            }
        }));
        
        handlers.add(Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                int width = Window.getClientWidth();
                int height = Window.getClientHeight();
                
                resize(width, height);
            }
        }));
        
        // On widget detach, remove all handlers
        asWidget().addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                // When the view is detached, remove all handlers
                if (!event.isAttached()) {
                    for (HandlerRegistration handler : handlers) {
                        handler.removeHandler();
                    }
                }
            }
        });
    }

    @Override
    public void goTo(Place place) {
        client_factory.placeController().goTo(place);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }
    
    private void basicSearch() {
        String term = view.searchBarValue().trim();
        
        // For empty search, do nothing!
        if (term == null || term.equals("")) {
            return;
        }
        
        view.clearSearchBar();
        client_factory.placeController().goTo(
                new BrowseSearchResultsPlace(
                        new Query(TermField.ALL, term),
                        new QueryOptions()));
        
    }

}
