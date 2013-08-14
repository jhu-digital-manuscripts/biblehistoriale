package edu.jhu.library.biblehistoriale.search;

public class SearchServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public SearchServiceException() {
        super();
    }

    public SearchServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SearchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchServiceException(String message) {
        super(message);
    }

    public SearchServiceException(Throwable cause) {
        super(cause);
    }
}
