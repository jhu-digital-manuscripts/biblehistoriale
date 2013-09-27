package edu.jhu.library.biblehistoriale.website.client.view.impl;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.HandlerRegistration;

import edu.jhu.library.biblehistoriale.website.client.CellTreeResources;
import edu.jhu.library.biblehistoriale.website.client.Messages;
import edu.jhu.library.biblehistoriale.website.client.view.BrowseProfilesView;
import edu.jhu.library.biblehistoriale.website.client.widgets.CriteriaTreeViewModel;
import edu.jhu.library.biblehistoriale.website.shared.CriteriaNode;

public class BrowseProfilesViewImpl extends Composite implements BrowseProfilesView {
    
    private final FlowPanel main;
    
    private final HTML intro;
    private final HTML loading;
    
    private CellTree crit_tree;
    private SingleSelectionModel<CriteriaNode> selection_model;
    
    public BrowseProfilesViewImpl() {
        this.main = new FlowPanel();
        
        this.selection_model = new SingleSelectionModel<CriteriaNode> ();
        
        this.intro = new HTML(Messages.INSTANCE.browseByCriteriaDescription());
        this.loading = new HTML(Messages.INSTANCE.loadingCriteria());
        
        main.add(loading);
        
        initWidget(main);
    }
    
    @Override
    public void displayByCriteria(CriteriaNode node) {
        main.clear();
        
        if (node == null) {
            main.add(new HTML(Messages.INSTANCE.failedToGetCriteria()));
            return;
        }
        
        CellTreeResources cell_res = GWT.create(CellTreeResources.class);
        
        this.crit_tree = new CellTree(
                new CriteriaTreeViewModel(node, selection_model), null, cell_res);
        crit_tree.setAnimationEnabled(true);
        
        main.add(intro);
        main.add(crit_tree);
    }

    @Override
    public HandlerRegistration addSelectionChangeHandler(Handler handler) {
        return selection_model.addSelectionChangeHandler(handler);
    }

    @Override
    public CriteriaNode getSelectedNode() {
        return selection_model.getSelectedObject();
    }
}
