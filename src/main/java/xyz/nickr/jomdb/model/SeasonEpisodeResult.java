package xyz.nickr.jomdb.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public Calendar getReleaseDate() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(RELEASED_FORMAT.parse(released));
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }

}
