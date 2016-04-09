package xyz.nickr.jomdb.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.nickr.jomdb.JOMDBException;
import xyz.nickr.jomdb.JavaOMDB;

public class SeasonResult implements Iterable<SeasonEpisodeResult> {

    private final JavaOMDB omdb;
    private final JSONObject json;

    public final String title, season;
    public final SeasonEpisodeResult[] episodes;

    public SeasonResult(JavaOMDB omdb, JSONObject json) {
        this.omdb = omdb;
        this.json = json;
        if (json.getBoolean("Response")) {
            this.title = json.getString("Title");
            this.season = json.getString("Season");
            JSONArray array = json.getJSONArray("Episodes");
            this.episodes = new SeasonEpisodeResult[array.length()];
            for (int i = 0, j = episodes.length; i < j; i++) {
                this.episodes[i] = new SeasonEpisodeResult(omdb, array.getJSONObject(i));
            }
        } else {
            throw new JOMDBException(json.getString("Error"));
        }
    }

    public JavaOMDB getOMDB() {
        return omdb;
    }

    public JSONObject getJSON() {
        return json;
    }

    @Override
    public Iterator<SeasonEpisodeResult> iterator() {
        return Arrays.asList(episodes).iterator();
    }

    @Override
    public Spliterator<SeasonEpisodeResult> spliterator() {
        return Spliterators.spliterator(iterator(), episodes.length, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.SIZED);
    }

    public Stream<SeasonEpisodeResult> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
