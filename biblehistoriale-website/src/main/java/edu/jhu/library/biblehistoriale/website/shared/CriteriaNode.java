package edu.jhu.library.biblehistoriale.website.shared;

import java.io.Serializable;
import java.util.Arrays;

public class CriteriaNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String message;
    
    // TODO change array to ArrayList?
    private CriteriaNode[] children;
    
    public CriteriaNode() {
        this(null);
    }
    
    public CriteriaNode(String id) {
        this.id = id;
        this.message = null;
        this.children = null;
    }
    
    public CriteriaNode(String id, String message) {
        this.id = id;
        this.message = message;
        this.children = null;
    }
    
    public CriteriaNode(String id, CriteriaNode... children) {
        this.id = id;
        this.message = null;
        this.children = children;
    }
    
    public CriteriaNode(String id, String message, CriteriaNode... children) {
        this.id = id;
        this.message = message;
        this.children = children;
    }
    
    public String getId() {
        return id;
    }
    
    public String getMessage() {
        if (message == null) {
            return id;
        }
        
        return message;
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
            if (cr.getId().equals(text))
                return cr;
            
            CriteriaNode child = cr.getChildNodeByText(text);
            if (child != null) 
                return child;
        }
        
        return null;
    }

    @Override
    public String toString() {
        return "CriteriaNode [text=" + id + ", children="
                + Arrays.toString(children) + "]";
    }
}
