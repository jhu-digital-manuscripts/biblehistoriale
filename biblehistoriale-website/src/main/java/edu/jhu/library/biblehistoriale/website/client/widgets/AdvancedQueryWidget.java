package edu.jhu.library.biblehistoriale.website.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.model.query.QueryOperation;
import edu.jhu.library.biblehistoriale.model.query.TermField;
import edu.jhu.library.biblehistoriale.website.client.Messages;

public class AdvancedQueryWidget extends Composite {
    
    private boolean isFirst;
    
    private final Grid main;
    
    private final ListBox operation;
    private final ListBox field;
    
    private final TextBox term;
    
    private final Button remove;
    
    public AdvancedQueryWidget() {
        this(false);
    }
    
    public AdvancedQueryWidget(boolean isFirst) {
        this.isFirst = isFirst;
        
        this.main = new Grid(1, 4);
        
        this.operation = new ListBox();
        this.field = new ListBox();
        this.term = new TextBox();
        this.remove = new Button(Messages.INSTANCE.removeField());
        
        if (!isFirst) {
            main.setWidget(0, 0, operation); 
        }
        main.setWidget(0, 1, field);
        main.setWidget(0, 2, term);
        main.setWidget(0, 3, remove);
        
        initWidget(main);
        
        for (QueryOperation op : QueryOperation.values()) {
            operation.addItem(op.toString());
        }
        
        for (TermField f : TermField.values()) {
            field.addItem(f.term());
        }
        
        field.setSelectedIndex(0);
    }
    
    public String getSearchTerm() {
        return term.getValue();
    }
    
    public String getOperation() {
        return isFirst ? "AND" : operation.getItemText(operation.getSelectedIndex());
    }
    
    public String getField() {
        return field.getItemText(field.getSelectedIndex());
    }
    
    public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
        return term.addKeyPressHandler(handler);
    }
    
    public HandlerRegistration addClickRemoveHandler(ClickHandler handler) {
        return remove.addClickHandler(handler);
    }
    
}
