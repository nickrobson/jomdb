package xyz.nickr.jomdb.model;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import xyz.nickr.jomdb.JavaOMDB;

public class SeasonEpisodeResult {

    public static final DateTimeFormatter RELEASED_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

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

    public Instant getReleaseDate() {
        return Instant.from(RELEASED_FORMAT.parse(released));
    }

}
