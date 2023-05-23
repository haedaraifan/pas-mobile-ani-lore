package com.example.ani_lore.api.jikan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem{
	private String titleJapanese;
	private int favorites;
	private Broadcast broadcast;
	private int year;
	private String rating;
	private int scoredBy;
	private List<Object> titleSynonyms;
	private String source;
	@SerializedName("title")
	private String title;
	private String type;
	private Trailer trailer;
	private String duration;
	private Object score;
	private List<ThemesItem> themes;
	private boolean approved;
	private List<GenresItem> genres;
	private int popularity;
	private int members;
	private String titleEnglish;
	private int rank;
	private String season;
	private boolean airing;
	private Object episodes;
	private Aired aired;
	private Images images;
	private List<StudiosItem> studios;
	private int malId;
	private List<TitlesItem> titles;
	private String synopsis;
	private List<Object> explicitGenres;
	private List<LicensorsItem> licensors;
	private String url;
	private List<ProducersItem> producers;
	private Object background;
	private String status;
	private List<DemographicsItem> demographics;

	public String getTitleJapanese(){
		return titleJapanese;
	}

	public int getFavorites(){
		return favorites;
	}

	public Broadcast getBroadcast(){
		return broadcast;
	}

	public int getYear(){
		return year;
	}

	public String getRating(){
		return rating;
	}

	public int getScoredBy(){
		return scoredBy;
	}

	public List<Object> getTitleSynonyms(){
		return titleSynonyms;
	}

	public String getSource(){
		return source;
	}

	public String getTitle(){
		return title;
	}

	public String getType(){
		return type;
	}

	public Trailer getTrailer(){
		return trailer;
	}

	public String getDuration(){
		return duration;
	}

	public Object getScore(){
		return score;
	}

	public List<ThemesItem> getThemes(){
		return themes;
	}

	public boolean isApproved(){
		return approved;
	}

	public List<GenresItem> getGenres(){
		return genres;
	}

	public int getPopularity(){
		return popularity;
	}

	public int getMembers(){
		return members;
	}

	public String getTitleEnglish(){
		return titleEnglish;
	}

	public int getRank(){
		return rank;
	}

	public String getSeason(){
		return season;
	}

	public boolean isAiring(){
		return airing;
	}

	public Object getEpisodes(){
		return episodes;
	}

	public Aired getAired(){
		return aired;
	}

	public Images getImages(){
		return images;
	}

	public List<StudiosItem> getStudios(){
		return studios;
	}

	public int getMalId(){
		return malId;
	}

	public List<TitlesItem> getTitles(){
		return titles;
	}

	public String getSynopsis(){
		return synopsis;
	}

	public List<Object> getExplicitGenres(){
		return explicitGenres;
	}

	public List<LicensorsItem> getLicensors(){
		return licensors;
	}

	public String getUrl(){
		return url;
	}

	public List<ProducersItem> getProducers(){
		return producers;
	}

	public Object getBackground(){
		return background;
	}

	public String getStatus(){
		return status;
	}

	public List<DemographicsItem> getDemographics(){
		return demographics;
	}
}