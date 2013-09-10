package edu.jhu.library.biblehistoriale.website.client.widgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import edu.jhu.library.biblehistoriale.model.query.QueryMatch;

public class QueryMatchCell 
        extends AbstractCell<QueryMatch> {

    public QueryMatchCell() {
        
    }

    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context,
            QueryMatch value, SafeHtmlBuilder sb) {
        
        if (value == null) {
            return;
        }
        
        String[] cons = value.getContext().split("'");
      
        sb.appendHtmlConstant("<table>");
        
        sb.appendHtmlConstant("<tr><td>");
        sb.appendHtmlConstant(value.getId());
        sb.appendHtmlConstant("</td></tr>");
        
        sb.appendHtmlConstant("<tr><td>");
        
        for (int i = 0; i < cons.length;) {
            sb.appendHtmlConstant("<i>" + cons[i++] + "</i>");
            sb.appendHtmlConstant("&nbsp" + (i >= cons.length ? "" : cons[i++]));
            sb.appendHtmlConstant("</br>");
        }
        sb.appendHtmlConstant("</td></tr>");
        
        sb.appendHtmlConstant("<tr></tr>");
        
        sb.appendHtmlConstant("</table>");
        
    }
    
    
    
}
