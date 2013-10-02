package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.website.client.Messages;
import edu.jhu.library.biblehistoriale.website.client.view.ProfileDetailView;
import edu.jhu.library.biblehistoriale.website.client.widgets.BibleDisplay;

public class ProfileDetailViewImpl extends Composite implements ProfileDetailView {
    
    private final FlowPanel main;
    
    private final Label loading;
    private BibleDisplay display;
    
    public ProfileDetailViewImpl() {
        this.main = new FlowPanel();
        
        this.loading = new Label(Messages.INSTANCE.loading());
        main.add(loading);
        
        initWidget(main);
        
        final HandlerRegistration hr = 
                Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                if (display != null) {
                    display.resize(event.getWidth() - 12, 
                            event.getHeight() - 12);
                }
            }
        });
        
        addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                if (!event.isAttached())
                    hr.removeHandler();
            }
        });
    }

    @Override
    public void display(Bible bible) {
        if (bible == null) {
            main.clear();
            main.add(new HTML(Messages.INSTANCE.failedToLoadProfile()));
            return;
        }
        
        this.display = new BibleDisplay(bible);
        
        main.remove(loading);
        main.add(display);
    }
    
}
