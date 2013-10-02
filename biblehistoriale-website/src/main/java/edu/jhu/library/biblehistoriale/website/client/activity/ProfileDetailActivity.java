package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeServiceAsync;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;

/**
 * Controls the profile detail view. Associated with ProfileDetailPlace.
 * 
 * @see ProfileDetailView
 * @see ProfileDetailPlace
 */
public class ProfileDetailActivity extends AbstractActivity 
        implements ProfileDetailView.Presenter {
    
    private ProfileDetailView view;
    
    private String profile_id;
    
    public ProfileDetailActivity(ProfileDetailPlace place, ClientFactory client_factory) {
        this.view = client_factory.newProfileDetailView();
        this.profile_id = place.id();
        
        // Get Bible object from profile id
        
        BibleHistorialeServiceAsync service = client_factory.service();
        service.lookupBible(profile_id, new AsyncCallback<Bible>() {

            @Override
            public void onFailure(Throwable caught) {
                //Window.alert(Messages.INSTANCE.unableToGetProfile() + profile_id);
                view.display(null);
            }

            @Override
            public void onSuccess(Bible result) {
                view.display(result);
            }
            
        });
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

}
