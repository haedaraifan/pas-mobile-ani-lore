package com.example.ani_lore.api.jikan.response;

public class Pagination{
	private boolean hasNextPage;
	private int lastVisiblePage;
	private Items items;
	private int currentPage;

	public boolean isHasNextPage(){
		return hasNextPage;
	}

	public int getLastVisiblePage(){
		return lastVisiblePage;
	}

	public Items getItems(){
		return items;
	}

	public int getCurrentPage(){
		return currentPage;
	}
}
