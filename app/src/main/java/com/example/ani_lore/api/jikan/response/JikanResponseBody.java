package com.example.ani_lore.api.jikan.response;

import java.util.List;

public class JikanResponseBody {
	private Pagination pagination;
	private List<DataItem> data;

	public Pagination getPagination(){
		return pagination;
	}

	public List<DataItem> getData(){
		return data;
	}
}