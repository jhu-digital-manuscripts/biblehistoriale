package edu.jhu.library.biblehistoriale.website.client.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.client.view.ConstructAdvancedQueryView;
import edu.jhu.library.biblehistoriale.website.client.widgets.AdvancedQueryWidget;

public class ConstructAdvancedQueryViewImpl extends Composite
        implements ConstructAdvancedQueryView {
    
    private final FlowPanel main;
    
    private final FlexTable queries_table;
    
    private final Button add_field;
    private final Button search;
    
    private List<HandlerRegistration> handlers;
    
    public ConstructAdvancedQueryViewImpl() {
        handlers = new ArrayList<HandlerRegistration> ();
        
        this.main = new FlowPanel();

        this.queries_table = new FlexTable();
        
        this.add_field = new Button("Add Field");
        this.search = new Button("Search");

        main.add(queries_table);
        main.add(add_field);
        main.add(search);

        addQueryRow();
        
        initWidget(main);
        
        this.addAttachHandler(new AttachEvent.Handler() {
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
    public void addQueryRow() {
        AdvancedQueryWidget widget = new AdvancedQueryWidget();
        
        handlers.add(widget.addClickRemoveHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = queries_table.getCellForEvent(event);
                queries_table.removeRow(cell.getRowIndex());
            }
        }));
        
        // Add a handler that, when focus is on the search term textbox
        // and the Enter key is pressed, execute the search query
        handlers.add(widget.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    search.click();
                }
            }
        }));
        
        queries_table.setWidget(queries_table.getRowCount(), 0, widget);
    }

    @Override
    public String getOperation(int row) {
        AdvancedQueryWidget widget = 
                (AdvancedQueryWidget) queries_table.getWidget(row, 0);
        
        return widget.getOperation();
    }

    @Override
    public String getField(int row) {
        AdvancedQueryWidget widget = 
                (AdvancedQueryWidget) queries_table.getWidget(row, 0);
        
        return widget.getField();
    }

    @Override
    public String getSearchTerm(int row) {
        AdvancedQueryWidget widget = 
                (AdvancedQueryWidget) queries_table.getWidget(row, 0);
        
        return widget.getSearchTerm();
    }

    @Override
    public int getRowCount() {
        return queries_table.getRowCount();
    }

    @Override
    public HandlerRegistration addFieldClickHandler(ClickHandler handler) {
        return add_field.addClickHandler(handler);
    }

    @Override
    public HandlerRegistration addSearchClickHandler(ClickHandler handler) {
        return search.addClickHandler(handler);
    }
    
}
