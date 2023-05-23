package com.example.ani_lore.api.jikan.response;

import com.google.gson.annotations.SerializedName;

public class Jpg{
	@SerializedName("large_image_url")
	private String largeImageUrl;
	@SerializedName("small_image_url")
	private String smallImageUrl;
	@SerializedName("image_url")
	private String imageUrl;

	public String getLargeImageUrl(){
		return largeImageUrl;
	}

	public String getSmallImageUrl(){
		return smallImageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}
}
