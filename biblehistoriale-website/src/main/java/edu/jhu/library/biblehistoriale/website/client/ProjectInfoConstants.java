package edu.jhu.library.biblehistoriale.website.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface ProjectInfoConstants extends Constants{
    
    public static final ProjectInfoConstants INSTANCE =
            GWT.create(ProjectInfoConstants.class);
    
    String projectInfo();
    
}
