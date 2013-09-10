package edu.jhu.library.biblehistoriale.website.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.jhu.library.biblehistoriale.website.client.ClientFactory;
import edu.jhu.library.biblehistoriale.website.client.place.ContactUsPlace;
import edu.jhu.library.biblehistoriale.website.client.view.ContactUsView;

public class ContactUsActivity extends AbstractActivity 
        implements ContactUsView.Presenter {

    private final ContactUsView view;
    
    public ContactUsActivity(ContactUsPlace place,
            ClientFactory client_factory) {
        this.view = client_factory.newContactUsView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

}
