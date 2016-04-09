package xyz.nickr.jomdb.model;

import org.json.JSONObject;

public class SearchResult {

    private final JSONObject json;

    public final String title, year, imdbId, type, poster;

    public SearchResult(JSONObject json) {
        this.json = json;
        this.title = json.getString("Title");
        this.year = json.getString("Year");
        this.imdbId = json.getString("imdbID");
        this.type = json.getString("Type");
        this.poster = json.optString("Poster");
    }

    public JSONObject getJSON() {
        return json;
    }

}
