package xyz.nickr.jomdb.model;

import java.time.LocalDateTime;
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
    private final String title, release, episode, imdbRating, imdbId;

    private LocalDateTime releaseDate;
    private boolean releaseDateSet;

    public SeasonEpisodeResult(JavaOMDB omdb, JSONObject json) {
        this.omdb = omdb;
        this.json = json;
        this.title = json.getString("Title");
        this.release = json.getString("Released");
        this.episode = json.getString("Episode");
        this.imdbRating = json.getString("imdbRating");
        this.imdbId = json.getString("imdbID");
    }

    /**
     * Gets the date this episode came out, as a calendar object.
     *
     * @return The calendar object (or null if unable to parse the {@code released} field).
     */
    public LocalDateTime getReleaseDate() {
        if (this.releaseDateSet)
            return this.releaseDate;
        this.releaseDate = parseReleaseDate(this.release);
        this.releaseDateSet = true;
        return this.releaseDate;
    }

    /**
     * Parses a {@link LocalDateTime} from a date string in the form {@code yyyy-MM-dd}
     *
     * @param dateString The string to be parsed.
     *
     * @return The LocalDateTime instance, or null if the string does not represent one.
     */
    public static LocalDateTime parseReleaseDate(String dateString) {
        if (dateString == null || dateString.equals("N/A"))
            return null;
        int[] parts = Arrays.stream(dateString.split("-"))
                .mapToInt(Integer::valueOf)
                .toArray();
        return LocalDateTime.of(parts[0], parts[1], parts[2], 0, 0, 0);
    }

}
