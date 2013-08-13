package edu.jhu.library.biblehistoriale.model;

import java.util.List;

/**
 * The sublist in the list of total results matching a query given some query
 * options.
 */
public class QueryResult<T> {
    private final long offset;
    private final long total;
    private final List<T> matches;

    public QueryResult(long offset, long total, List<T> matches) {
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

    public List<T> matches() {
        return matches;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matches == null) ? 0 : matches.hashCode());
        result = prime * result + (int) (offset ^ (offset >>> 32));
        result = prime * result + (int) (total ^ (total >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof QueryResult))
            return false;
        QueryResult<?> other = (QueryResult<?>) obj;
        if (matches == null) {
            if (other.matches != null)
                return false;
        } else if (!matches.equals(other.matches))
            return false;
        if (offset != other.offset)
            return false;
        if (total != other.total)
            return false;
        return true;
    }
}
