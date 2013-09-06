package edu.jhu.library.biblehistoriale.website.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.BrowseSearchResultsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ConstructAdvancedQueryPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ContactUsPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProfileDetailPlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProjectInfoPlace;

// TODO: When a new Place is added, the associated tokenizer needs to be added to the @WithTokenizers annotation
@WithTokenizers(
        {   
            ProjectInfoPlace.Tokenizer.class,
            BrowseProfilesPlace.Tokenizer.class,
            ContactUsPlace.Tokenizer.class,
            ProfileDetailPlace.Tokenizer.class,
            ConstructAdvancedQueryPlace.Tokenizer.class,
            BrowseSearchResultsPlace.Tokenizer.class
        }
)
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
