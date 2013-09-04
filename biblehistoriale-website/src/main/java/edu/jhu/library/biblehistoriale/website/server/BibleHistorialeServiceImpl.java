package edu.jhu.library.biblehistoriale.website.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.jhu.library.biblehistoriale.model.profile.Bible;
import edu.jhu.library.biblehistoriale.model.query.Query;
import edu.jhu.library.biblehistoriale.model.query.QueryOptions;
import edu.jhu.library.biblehistoriale.model.query.QueryResult;
import edu.jhu.library.biblehistoriale.website.client.rpc.BibleHistorialeService;

public class BibleHistorialeServiceImpl extends RemoteServiceServlet implements
        BibleHistorialeService {
    private static final long serialVersionUID = 1L;

    @Override
    public Bible lookupBible(String id) {
        return null;
    }

    @Override
    public QueryResult search(Query query, QueryOptions opts) {
        return null;
    }
}
