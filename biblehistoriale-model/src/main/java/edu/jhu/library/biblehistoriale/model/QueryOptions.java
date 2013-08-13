package edu.jhu.library.biblehistoriale.model;

public class QueryOptions {
    public static final int DEFAULT_MATCHES = 30;

    private long offset;
    private int matches;

    public QueryOptions() {
        this.offset = 0;
        this.matches = DEFAULT_MATCHES;
    }

    public QueryOptions(long offset, int matches) {
        this.offset = offset;
        this.matches = matches;
    }

    public QueryOptions(QueryOptions opts) {
        this.offset = opts.offset;
        this.matches = opts.matches;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        if (offset < 0) {
            offset = 0;
        }

        this.offset = offset;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        if (matches <= 0) {
            matches = DEFAULT_MATCHES;
        }

        this.matches = matches;
    }
}
