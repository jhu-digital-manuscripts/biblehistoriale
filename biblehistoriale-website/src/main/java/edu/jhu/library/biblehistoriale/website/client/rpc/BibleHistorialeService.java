package edu.jhu.library.biblehistoriale.website.client.rpc;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.website.shared.BrowseCriteria;

/**
 * Allows an index of bible profiles to be searched and bible profiles to be
 * retrieved.
 */

@RemoteServiceRelativePath("service")
public interface BibleHistorialeService extends RemoteService {
    public Bible lookupBible(String id) throws RPCException;

    public QueryResult search(Query query, QueryOptions opts) throws RPCException;
    
    public HashMap<BrowseCriteria, HashMap<String, String[]>> allProfilesByCriteria();
}
