package edu.jhu.library.biblehistoriale.website.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.shared.CriteriaNode;

/**
 * Controls the browse profiles by criteria view. Associated with
 * BrowseProfilesPlace
 * 
 * @see BrowseProfilesView
 * @see BrowseProfilesPlace
 */
public class BrowseProfilesActivity extends AbstractActivity 
        implements BrowseProfilesView.Presenter {

    private static CriteriaNode criteria_cache;
    
    private BrowseProfilesView view;
    private PlaceController place_controller;
    
    private final BibleHistorialeServiceAsync service;
    
    private final List<HandlerRegistration> handlers;
    
    /*
     * Browse by:
     * 
     * Repository
     * Production date (prodDate)
     * Production place (prodLoc)
     * Owners/Patrons (ownerName)
     * Classification (bergerClass and subcategories; sneddonClass and subcategories)
     */
    
    public BrowseProfilesActivity(BrowseProfilesPlace place, 
            ClientFactory client_factory) {
        this.view = client_factory.newBrowseProfilesView();
        this.place_controller = client_factory.placeController();
        this.service = client_factory.service();
        
        this.handlers = new ArrayList<HandlerRegistration> ();
        
        if (criteria_cache != null) {
            
            view.displayByCriteria(criteria_cache);
            
        } else {
            service.allProfilesByCriteria(new AsyncCallback<CriteriaNode> () {
    
                @Override
                public void onFailure(Throwable caught) {
                    view.displayByCriteria(null);
                }
    
                @Override
                public void onSuccess(CriteriaNode result) {
                    criteria_cache = result;
                    view.displayByCriteria(result);
                }
                
            });
        }
        
        bind();
    }
    
    private void bind() {
        
        handlers.add(view.addSelectionChangeHandler(
                new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                CriteriaNode crit = view.getSelectedNode();
                
                if (crit.isLeaf()) {
                    goTo(new ProfileDetailPlace(crit.getId()));
                }
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
    
    private void goTo(Place place) {
        place_controller.goTo(place);
    }

}
