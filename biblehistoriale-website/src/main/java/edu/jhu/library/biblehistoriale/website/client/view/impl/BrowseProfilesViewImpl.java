package edu.jhu.library.biblehistoriale.website.client.view.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.client.BibleHistorialeWebsite;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.shared.BrowseCriteria;

public class BrowseProfilesViewImpl extends Composite 
        implements BrowseProfilesView {
    private final int CRITERIA_HEADER_HEIGHT = 40;
    private final int SUBHEADER_HEIGHT = 30;
    
    private final FlowPanel main;
    
    private final HTML intro;
    
    private final StackLayoutPanel criteria_panel;
    
    private final List<StackLayoutPanel> sub_panels;
    private HandlerRegistration handler;
    
    public BrowseProfilesViewImpl() {
        this.main = new FlowPanel();
        
        this.intro = new HTML("Here, the user will be able to browser the MS profiles, " 
                + "choose filtering criteria, and select a profile for detailed viewing.");
        
        main.add(intro);
        
        initWidget(main);
        
        this.sub_panels = new ArrayList<StackLayoutPanel> ();
        
        this.criteria_panel = new StackLayoutPanel(Unit.PX);
        criteria_panel.setSize(Window.getClientWidth() + "px",
                (Window.getClientHeight() 
                        - BibleHistorialeWebsite.HEADER_HEIGHT 
                        - intro.getOffsetHeight()) + "px");
        
        // TODO resizing
        handler = Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                resize(event.getWidth(), 
                        event.getHeight() - BibleHistorialeWebsite.HEADER_HEIGHT);
            }
        });
        
        addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                if (handler == null || event.isAttached())
                    return;
                
                handler.removeHandler();
            }
        });
    }

    @Override
    public List<Label> displayByCriteria(
            HashMap<BrowseCriteria, HashMap<String, String[]>> crit) {
        List<Label> links_to_profile = new ArrayList<Label> ();
        
        int content_height = 
                Window.getClientHeight() 
                - BibleHistorialeWebsite.HEADER_HEIGHT
                - CRITERIA_HEADER_HEIGHT * BrowseCriteria.values().length;
        
        for (BrowseCriteria bc : BrowseCriteria.values()) {
            Map<String, String[]> sub_crit = crit.get(bc);
            
            if (sub_crit == null) {
                continue;
            }
            
            HTML header = new HTML(bc.message());
            
            StackLayoutPanel sub_panel = new StackLayoutPanel(Unit.PX);
            
            sub_panel.addStyleName("BroswerSubsection");
            sub_panel.setSize(Window.getClientWidth() + "px",
                    content_height + "px");
            sub_panels.add(sub_panel);
            
            Set<String> sub_key = sub_crit.keySet();
            for (String key : sub_key) {
                HTML subheader = new HTML(key);
                subheader.setStylePrimaryName("BrowserSubheader");
                
                ScrollPanel scroll = new ScrollPanel();
                
                FlowPanel cont = new FlowPanel();
                scroll.add(cont);
                
                for (String val : sub_crit.get(key)) {
                    Label value_label = new Label(val);
                    value_label.setStylePrimaryName("Clickable");
                    
                    links_to_profile.add(value_label);
                    cont.add(value_label);
                }
                
                sub_panel.add(scroll, subheader, SUBHEADER_HEIGHT);
            }
            
            criteria_panel.add(sub_panel, header, CRITERIA_HEADER_HEIGHT);
            
        }
        
        main.add(criteria_panel);
        
        return links_to_profile;
    }
    
    // TODO: investigate CellTree and other Cell widgets to see which would be appropriate
    private void resize(int width, int height) {
        criteria_panel.setSize(width + "px", 
                (height - intro.getOffsetHeight()) + "px");
        
        int content_height = height 
                - CRITERIA_HEADER_HEIGHT * BrowseCriteria.values().length;
        for (StackLayoutPanel sp : sub_panels) {
            sp.setSize(width + "px", content_height + "px");
        }
    }
}
