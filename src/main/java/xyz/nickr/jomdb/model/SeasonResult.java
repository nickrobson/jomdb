package xyz.nickr.jomdb.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Getter;
import xyz.nickr.jomdb.JOMDBException;
import xyz.nickr.jomdb.JavaOMDB;

/**
 * Represents a season of a title.
 */
public class SeasonResult implements Iterable<SeasonEpisodeResult> {

    @Getter
    private final JavaOMDB omdb;

    @Getter
    private final JSONObject json;

    @Getter
    private final String title, season;

    @Getter
    private final SeasonEpisodeResult[] episodes;

    public SeasonResult(JavaOMDB omdb, JSONObject json) {
        this.omdb = omdb;
        this.json = json;
        if (json.getBoolean("Response")) {
            this.title = json.getString("Title");
            this.season = json.getString("Season");
            JSONArray array = json.getJSONArray("Episodes");
            this.episodes = new SeasonEpisodeResult[array.length()];
            for (int i = 0, j = this.episodes.length; i < j; i++) {
                this.episodes[i] = new SeasonEpisodeResult(omdb, array.getJSONObject(i));
            }
        } else {
            throw new JOMDBException(json.getString("Error"));
        }
    }

    @Override
    public Iterator<SeasonEpisodeResult> iterator() {
        return Arrays.asList(this.episodes).iterator();
    }

    @Override
    public Spliterator<SeasonEpisodeResult> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.episodes.length, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.SIZED);
    }

    /**
     * Gets the episodes of this season as a stream.
     *
     * @return The stream.
     */
    public Stream<SeasonEpisodeResult> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

}
