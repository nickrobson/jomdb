package xyz.nickr.jomdb.model;

import org.json.JSONObject;

import xyz.nickr.jomdb.JavaOMDB;

public class SeasonEpisodeResult {

    private final JavaOMDB omdb;
    private final JSONObject json;

    public final String title, released, episode, imdbRating, imdbId;

    public SeasonEpisodeResult(JavaOMDB omdb, JSONObject json) {
        this.omdb = omdb;
        this.json = json;
        this.title = json.getString("Title");
        this.released = json.getString("Released");
        this.episode = json.getString("Episode");
        this.imdbRating = json.getString("imdbRating");
        this.imdbId = json.getString("imdbID");
    }

    public JavaOMDB getOMDB() {
        return omdb;
    }

    public JSONObject getJSON() {
        return json;
    }

}
