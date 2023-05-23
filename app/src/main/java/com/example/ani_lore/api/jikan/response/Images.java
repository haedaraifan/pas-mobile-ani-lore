package com.example.ani_lore.api.jikan.response;

public class Images{
	private Jpg jpg;
	private Webp webp;
	private String largeImageUrl;
	private String smallImageUrl;
	private String imageUrl;
	private String mediumImageUrl;
	private String maximumImageUrl;

	public Jpg getJpg(){
		return jpg;
	}

	public Webp getWebp(){
		return webp;
	}

	public String getLargeImageUrl(){
		return largeImageUrl;
	}

	public String getSmallImageUrl(){
		return smallImageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getMediumImageUrl(){
		return mediumImageUrl;
	}

	public String getMaximumImageUrl(){
		return maximumImageUrl;
	}
}
