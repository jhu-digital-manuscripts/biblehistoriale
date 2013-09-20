package edu.jhu.library.biblehistoriale.website.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.website.shared.CriteriaNode;

public interface BibleHistorialeServiceAsync {
    public void lookupBible(String id, AsyncCallback<Bible> cb);

    public void search(Query query, QueryOptions opts,
            AsyncCallback<QueryResult> cb);
    
    //public void allProfilesByCriteria(
    //        AsyncCallback<HashMap<BrowseCriteria, HashMap<String, String[]>>> cb);
    public void allProfilesByCriteria(AsyncCallback<CriteriaNode> cb);
}
