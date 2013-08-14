package edu.jhu.library.biblehistoriale.model.profile;

public class Materials {
    
    public class Binding {
        
        private String bindMaterial;
        private String bindDate;
        private int bindDateStartYear;
        private int bindDateEndYear;
        
        public Binding() {
            
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
    
    private String support;
    private Binding binding;
    
    public Materials() {
        
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }
    
}
