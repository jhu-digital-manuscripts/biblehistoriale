package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface License extends Constants {
    
    public static final License INSTANCE = GWT.create(License.class);
    
    public String license();
    
}
