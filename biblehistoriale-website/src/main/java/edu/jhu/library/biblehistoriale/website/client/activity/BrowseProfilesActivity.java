package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;

public class BrowseProfilesActivity extends AbstractActivity 
        implements BrowseProfilesView.Presenter {

    private BrowseProfilesView view;
    private ClientFactory client_factory;
    
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
        this.client_factory = client_factory;
        
        // Temporary link to profiles
        view.addClickHandlerToProfileLink(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                goTo(new ProfileDetailPlace("BrusselsKBR9001-2"));
            }
        });
        
        view.addClickHandlerToVatLink(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                goTo(new ProfileDetailPlace("VatBarbLat613"));
            }
        });
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }
    
    private void goTo(Place place) {
        client_factory.placeController().goTo(place);
    }

}
