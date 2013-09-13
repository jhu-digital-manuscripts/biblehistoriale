package edu.jhu.library.biblehistoriale.model.profile;

import java.io.Serializable;

public class MasterTableOfContents implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public enum Detail {
        BOOK("book"), CHAPTER("chapter"), MIXED("mixed"), OTHER("other");
        
        private String detail;
        
        private Detail(String detail) {
            this.detail = detail;
        }
        
        public String detail() {
            return detail;
        }
        
        public static Detail getDetail(String detail) {
            for (Detail d : Detail.values()) {
                if (d.detail.equals(detail)) {
                    return d;
                }
            }
            return null;
        }
    }
    
    private Detail tableDetail;
    private String startPage;
    private String text;
    
    private boolean matchesContents;

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean matchesContents() {
        return matchesContents;
    }

    public void setMatchesContents(boolean matchesContents) {
        this.matchesContents = matchesContents;
    }
    
    public Detail getTableDetail() {
        return tableDetail;
    }
    
    public void setTableDetail(String tableDetail) {
        if (tableDetail.equals("book")) {
            this.tableDetail = Detail.BOOK;
        } else if (tableDetail.equals("chapter")) {
            this.tableDetail = Detail.CHAPTER;
        } else if (tableDetail.equals("mixed")) {
            this.tableDetail = Detail.MIXED;
        } else if (tableDetail.equals("other")) {
            this.tableDetail = Detail.OTHER;
        } else {
            this.tableDetail = null;
        }
    }
    
}
