package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

/**
 * <p>Information about the physical materials that a bible is made out of.</p>
 * 
 * <p>Associated with the &ltmaterials&gt element in the bible schema.</p>
 */
public class Materials implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public enum Support {
        PARCHEMENT("parchment"), PAPER("paper"), UNKNOWN("unknown");
        
        private String support;
        
        private Support(String support) {
            this.support = support;
        }
        
        public String support() {
            return support;
        }
        
        public static Support getSupport(String support) {
            for (Support sup : Support.values()) {
                if (sup.support.equals(support)) {
                    return sup;
                }
            }
            return null;
        }
    }
    
    private Support support;
    private String bindMaterial;
    private String bindDate;
    
    private int bindDateStartYear;
    private int bindDateEndYear;
    
    public Materials() {
        bindDateStartYear = 0;
        bindDateEndYear = 0;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = Support.getSupport(support);
    }
    
    public String getBindMaterial() {
        return bindMaterial;
    }

    public void setBindMaterial(String bindMaterial) {
        this.bindMaterial = bindMaterial;
    }

    public String getBindDate() {
        return bindDate;
    }

    public void setBindDate(String bindDate) {
        this.bindDate = bindDate;
    }

    public int getBindDateStartYear() {
        return bindDateStartYear;
    }

    public void setBindDateStartYear(int bindDateStartYear) {
        this.bindDateStartYear = bindDateStartYear;
    }

    public int getBindDateEndYear() {
        return bindDateEndYear;
    }

    public void setBindDateEndYear(int bindDateEndYear) {
        this.bindDateEndYear = bindDateEndYear;
    }
    
}
