package edu.jhu.library.biblehistoriale.website.shared;

import java.io.Serializable;
import java.util.Arrays;

public class CriteriaNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    
    private CriteriaNode[] children;
    
    public CriteriaNode() {
        this(null);
    }
    
    public CriteriaNode(String text) {
        this.text = text;
        this.children = null;
    }
    
    public CriteriaNode(String text, CriteriaNode... children) {
        this.text = text;
        this.children = children;
    }
    
    public String getText() {
        return text;
    }
    
    public CriteriaNode[] getChildren() {
        return children;
    }
    
    public boolean isLeaf() {
        return children == null;
    }
    
    public CriteriaNode addChildNode(CriteriaNode node) {
        CriteriaNode[] added = null;
   
        if (children == null) {
            added = new CriteriaNode[] { node };
        } else {
            added = new CriteriaNode[children.length + 1];

            System.arraycopy(children, 0, added, 0, children.length);
            added[added.length - 1] = node;
        }
        children = added;
       
        return this;
    }
    
    public CriteriaNode getChildNodeByText(String text) {
        if (children == null) {
            return null;
        }
        
        for (CriteriaNode cr : children) {
            if (cr.getText().equals(text))
                return cr;
            
            CriteriaNode child = cr.getChildNodeByText(text);
            if (child != null) 
                return child;
        }
        
        return null;
    }

    @Override
    public String toString() {
        return "CriteriaNode [text=" + text + ", children="
                + Arrays.toString(children) + "]";
    }
}
