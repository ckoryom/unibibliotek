package com.project.unibibliotek.model;

import primo4j.data.QueryCriteria;

public class SearchFilter {
	
	private Filter filter;
	
	private String query;
	
	public String getFilterValue () {
		String value = "";
		switch (filter) {
			case TITLE:
				value =  QueryCriteria.FIELD_TITLE;
				break;
			case AUTHOR:
				value = QueryCriteria.FIELD_CREATOR;
				break;
			case SUBJECT:
				value = QueryCriteria.FIELD_SUBJECT;
				break;
			case ANY:
				value = QueryCriteria.FIELD_ANY;
				break;
			case ISBN:
				value = QueryCriteria.FIELD_ISBN;
				break;
			default:
				value = "";
				break;
			}
		return value;
	}
	
	public SearchFilter (Filter filter, String query) {
		this.filter = filter;
		this.query = query;
	}
	
	public Filter getFilter() {
		return filter;
	}

	public String getQuery() {
		return query;
	}
	
	
	
}
