package xyz.nickr.jomdb.model;

import java.util.Arrays;
import java.util.Calendar;

import java.util.GregorianCalendar;
import org.json.JSONObject;

import lombok.Getter;
import xyz.nickr.jomdb.JavaOMDB;

/**
 * Represents an episode of a season.
 */
public class SeasonEpisodeResult {

    @Getter
    private final JavaOMDB omdb;

    @Getter
    private final JSONObject json;

    @Getter
    private final String title, released, episode, imdbRating, imdbId;

    public SeasonEpisodeResult(JavaOMDB omdb, JSONObject json) {
        this.omdb = omdb;
        this.json = json;
        this.title = json.getString("Title");
        this.released = json.getString("Released");
        this.episode = json.getString("Episode");
        this.imdbRating = json.getString("imdbRating");
        this.imdbId = json.getString("imdbID");
    }

    /**
     * Gets the date this episode came out, as a calendar object.
     *
     * @return The calendar object (or null if unable to parse the {@code released} field).
     */
    public Calendar getReleaseDate() {
        int[] parts = Arrays.stream(this.released.split("-"))
                .mapToInt(Integer::valueOf)
                .toArray();
        return new GregorianCalendar(parts[0], parts[1], parts[2]);
    }

}
