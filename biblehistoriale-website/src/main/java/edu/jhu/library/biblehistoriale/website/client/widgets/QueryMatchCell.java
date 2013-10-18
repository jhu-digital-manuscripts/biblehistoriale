package edu.jhu.library.biblehistoriale.website.client.widgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;

import edu.jhu.library.biblehistoriale.model.query.QueryMatch;

/**
 * Cell used to display a QueryMatch.
 * 
 * @see QueryMatch
 */
public class QueryMatchCell 
        extends AbstractCell<QueryMatch> {
    
    private static final SimpleHtmlSanitizer sanitizer =
            SimpleHtmlSanitizer.getInstance();

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
        
        sb.appendHtmlConstant("<tr><td><b><i><u>");
        sb.appendEscaped(value.getId());
        sb.appendHtmlConstant("</u></i></b></td></tr>");
        
        for (int i = 0; i < cons.length;) {
            sb.appendHtmlConstant("<tr>");
            sb.appendHtmlConstant("<td><i>");
            sb.appendEscaped(cons[i++]);
            sb.appendHtmlConstant("</i></td>");
            
            sb.appendHtmlConstant("<td>&nbsp");
            sb.append(sanitizer.sanitize((i >= cons.length ? "" : cons[i++])));
            sb.appendHtmlConstant("</td>");
            sb.appendHtmlConstant("</tr>");
        }
        
        sb.appendHtmlConstant("</table>");
        
    }
    
    
}
