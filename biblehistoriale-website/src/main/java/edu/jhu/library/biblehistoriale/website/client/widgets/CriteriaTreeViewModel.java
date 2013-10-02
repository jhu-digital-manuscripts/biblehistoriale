package edu.jhu.library.biblehistoriale.website.client.widgets;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import edu.jhu.library.biblehistoriale.website.shared.CriteriaNode;

/**
 * Data model for the cell tree used to browse profiles by criteria.
 */
public class CriteriaTreeViewModel implements TreeViewModel {
    
    private class CriteriaCell extends AbstractCell<CriteriaNode> {

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context,
                CriteriaNode value, SafeHtmlBuilder sb) {
            sb.appendHtmlConstant(value.getMessage());
        }
        
    }
    
    private ListDataProvider<CriteriaNode> catagory_provider;
    private final SingleSelectionModel<CriteriaNode> selection_model;
    
    private final DefaultSelectionEventManager<CriteriaNode> manager;
    
    public CriteriaTreeViewModel(CriteriaNode node,
            SingleSelectionModel<CriteriaNode> selection_model) {
        this.selection_model = selection_model;
        this.catagory_provider = new ListDataProvider<CriteriaNode> ();
        
        this.manager = DefaultSelectionEventManager.createDefaultManager();
        
        List<CriteriaNode> cats = catagory_provider.getList();
        for (CriteriaNode c : node.getChildren()) {
            cats.add(c);
        }
    }
    
    @Override
    public <T> NodeInfo<?> getNodeInfo(T value) {
       
        if (value == null) {
           return new DefaultNodeInfo<CriteriaNode> (
                    catagory_provider, new CriteriaCell(), selection_model, manager, null); 
        }
        
        if (!(value instanceof CriteriaNode)) {
            throw new IllegalArgumentException("Unsupported data type: "
                    + value.getClass().getName());
        }
        
        CriteriaNode crit = (CriteriaNode) value;
        
        ListDataProvider<CriteriaNode> provider = 
                new ListDataProvider<CriteriaNode> ();
        List<CriteriaNode> sub_cats = provider.getList();
        for (CriteriaNode node : crit.getChildren()) {
            sub_cats.add(node);
        }
               
        DefaultNodeInfo<CriteriaNode> node_info = new DefaultNodeInfo<CriteriaNode>
                (provider, new CriteriaCell(), selection_model, manager, null);
        
        return node_info;
    }

    public CriteriaNode getSelectedNode() {
        return selection_model.getSelectedObject();
    }
    
    @Override
    public boolean isLeaf(Object value) {
        if (!(value instanceof CriteriaNode)) 
            return false;
        
        return ((CriteriaNode) value).isLeaf();
    }
    
    
    
}