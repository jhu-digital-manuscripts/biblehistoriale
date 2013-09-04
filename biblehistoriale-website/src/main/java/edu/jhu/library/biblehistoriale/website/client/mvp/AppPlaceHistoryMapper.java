package edu.jhu.library.biblehistoriale.website.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import edu.jhu.library.biblehistoriale.website.client.place.BrowseProfilesPlace;
import edu.jhu.library.biblehistoriale.website.client.place.HomePlace;
import edu.jhu.library.biblehistoriale.website.client.place.ProjectInfoPlace;

// TODO: When a new Place is added, the associated tokenizer needs to be added to the @WithTokenizers annotation
@WithTokenizers(
        { HomePlace.Tokenizer.class, ProjectInfoPlace.Tokenizer.class,
            BrowseProfilesPlace.Tokenizer.class }
)
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
