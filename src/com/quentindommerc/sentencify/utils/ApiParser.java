package com.quentindommerc.sentencify.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.quentindommerc.sentencify.bean.Album;
import com.quentindommerc.sentencify.bean.Artist;
import com.quentindommerc.sentencify.bean.Track;

public class ApiParser {
	public static Track parseTrack(JSONObject obj) {
		Track t = new Track();
		t.setHref(obj.optString("href"));
		t.setLength(Float.parseFloat(obj.optString("length", "0.0")));
		t.setName(obj.optString("name"));
		t.setPopularity(Float.parseFloat(obj.optString("popularity", "0.0")));
		t.setTrackNumber(Integer.parseInt(obj.optString("track-number", "0")));
		t.setAlbum(ApiParser.parseAlbum(obj.optJSONObject("album")));
		JSONArray artists = obj.optJSONArray("artists");
		if (artists != null)
			for (int i = 0; i < artists.length(); i++) {
				t.addArtist(ApiParser.parseArtist(artists.optJSONObject(i)));
			}
		return t;
	}

	private static Artist parseArtist(JSONObject obj) {
		Artist a = new Artist();
		a.setHref(obj.optString("href"));
		a.setName(obj.optString("name"));
		return a;
	}

	public static Album parseAlbum(JSONObject obj) {
		Album a = new Album();
		a.setHref(obj.optString("href"));
		a.setName(obj.optString("name"));
		a.setReleaseYear(obj.optString("release"));
		JSONObject availability = obj.optJSONObject("availability");
		String[] terr = availability.optString("territories").split(" ");
		ArrayList<String> terrArr = new ArrayList<String>();
		for (String s : terr) {
			terrArr.add(Utils.iso2CountryCodeToIso3CountryCode(s));
		}
		a.setTerritories(terrArr);
		return a;
	}

}
