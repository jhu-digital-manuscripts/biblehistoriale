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
        
        String[] cons = value.getContext().split("\\|");
      
        sb.appendHtmlConstant("<table class=\"OwnerTable\">");
        
        sb.appendHtmlConstant("<tr><td><b><i>");
        sb.appendHtmlConstant(value.getId());
        sb.appendHtmlConstant("</i></b></td></tr>");
        
        for (int i = 0; i < cons.length;) {
            sb.appendHtmlConstant("<tr>");
            sb.appendHtmlConstant("<td>");
            sb.appendHtmlConstant("<i>" + cons[i++] + "</i>");
            sb.appendHtmlConstant("</td>");
            
            sb.appendHtmlConstant("<td>");
            sb.appendHtmlConstant("&nbsp" + (i >= cons.length ? "" : cons[i++]));
            sb.appendHtmlConstant("</td>");
            sb.appendHtmlConstant("</tr>");
        }
        
        sb.appendHtmlConstant("</table>");
        
    }
}
