package edu.jhu.library.biblehistoriale.website.client.activity;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.shared.BrowseCriteria;

public class BrowseProfilesActivity extends AbstractActivity 
        implements BrowseProfilesView.Presenter {

    private BrowseProfilesView view;
    private PlaceController place_controller;
    
    private final BibleHistorialeServiceAsync service;
    
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
        
        service.allProfilesByCriteria(new AsyncCallback
                <HashMap<BrowseCriteria, HashMap<String, String[]>>> () {
            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSuccess(
                    HashMap<BrowseCriteria, HashMap<String, String[]>> result) {
                bindLinks(view.displayByCriteria(result));
            }      
        });
    }
    
    private void bindLinks(List<Label> links) {
        
        for (Label label : links) {
            final String profile_id = label.getText();
            
            label.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    goTo(new ProfileDetailPlace(profile_id));
                }
            });
        }
        
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }
    
    private void goTo(Place place) {
        place_controller.goTo(place);
    }

}
