package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.client.view.HeaderView;

public class HeaderViewImpl extends Composite implements HeaderView {
    
    private final FlowPanel main;
    
    private final Label info_link;
    private final Label contact_link;
    private final Label advanced_search_link;
    
    private final Button search_button;
    
    private final Image banner;
    
    private final TextBox search_box;
    
    // TODO: get all user messages from .properties file
    public HeaderViewImpl() {
        this.main = new FlowPanel();
        main.setStylePrimaryName("Header");
        
        this.info_link = new Label("Info");
        this.contact_link = new Label("Contact Us");
        this.advanced_search_link = new Label("Advanced Search");
        advanced_search_link.addStyleName("AdvancedSearchLink");
        
        this.search_button = new Button("Search");
        
        this.banner = new Image("Images/bible_historiale_banner.png");
        banner.setWidth(Window.getClientWidth() + "px");
        
        this.search_box = new TextBox();
        
        main.add(banner);
        main.add(info_link);
        main.add(contact_link);
        main.add(search_button);
        main.add(search_box);
        
        main.add(advanced_search_link);
        
        initWidget(main);
    }

    @Override
    public String searchBarValue() {
        return search_box.getValue();
    }

    @Override
    public HandlerRegistration addInfoLinkClickHandler(ClickHandler handler) {
        return info_link.addClickHandler(handler);
    }

    @Override
    public HandlerRegistration addContactInfoClickHandler(ClickHandler handler) {
        return contact_link.addClickHandler(handler);
    }

    @Override
    public HandlerRegistration addAdvancedSearchClickHandler(
            ClickHandler handler) {
        return advanced_search_link.addClickHandler(handler);
    }

    @Override
    public HandlerRegistration addSearchClickHandler(ClickHandler handler) {
        return search_button.addClickHandler(handler);
    }

    @Override
    public HandlerRegistration addSearchKeyPressHandler(KeyPressHandler handler) {
        return search_box.addKeyPressHandler(handler);
    }

    @Override
    public void resize(int width, int height) {
        banner.setWidth(width + "px");
    }
}
