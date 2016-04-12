package xyz.nickr.jomdb.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import org.json.JSONObject;

import xyz.nickr.jomdb.JavaOMDB;

public class SeasonEpisodeResult {

    public static final DateFormat RELEASED_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
        try {
            return RELEASED_FORMAT.parse(released).toInstant();
        } catch (ParseException e) {
            return null;
        }
    }

}
