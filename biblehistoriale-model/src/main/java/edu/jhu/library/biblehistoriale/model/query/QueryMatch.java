package edu.jhu.library.biblehistoriale.model.query;

import java.io.Serializable;

public class QueryMatch implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
	private String context;

	public QueryMatch() {
	    this(null, null);
	}
	
	public QueryMatch(String id, String context) {
		this.id = id;
		this.context = context;
	}

	public String getId() {
		return id;
	}

	public String getContext() {
		return context;
	}
}
