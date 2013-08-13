package edu.jhu.library.biblehistoriale.model;

public class QueryMatch {
	private final String id;
	private final String context;

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
