package edu.jhu.library.biblehistoriale.profile.builder;

public class ProfileBuilderException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public ProfileBuilderException() {
        super();
    }

    public ProfileBuilderException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProfileBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileBuilderException(String message) {
        super(message);
    }

    public ProfileBuilderException(Throwable cause) {
        super(cause);
    }

}
