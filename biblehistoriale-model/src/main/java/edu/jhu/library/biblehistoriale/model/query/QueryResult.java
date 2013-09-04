package edu.jhu.library.biblehistoriale.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * The sublist in the list of total results matching a query given some query
 * options.
 */
public class QueryResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private long offset;
    private long total;
    private List<QueryMatch> matches;

    public QueryResult() {
        this(0, 0, null);
    }

    public QueryResult(long offset, long total, List<QueryMatch> matches) {
        this.offset = offset;
        this.total = total;
        this.matches = matches;
    }

    public long getOffset() {
        return offset;
    }

    public long getTotal() {
        return total;
    }

    public List<QueryMatch> matches() {
        return matches;
    }
}
