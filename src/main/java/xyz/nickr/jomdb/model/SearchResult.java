package xyz.nickr.jomdb.model;

import org.json.JSONObject;

import lombok.Getter;

/**
 * Represents a single result of a {@link SearchResults set of search results}.
 */
public class SearchResult {

    @Getter
    private final JSONObject json;

    @Getter
    private final String title, year, imdbId, type, poster;

    public SearchResult(JSONObject json) {
        this.json = json;
        this.title = json.getString("Title");
        this.year = json.getString("Year");
        this.imdbId = json.getString("imdbID");
        this.type = json.getString("Type");
        this.poster = json.optString("Poster");
    }

}
