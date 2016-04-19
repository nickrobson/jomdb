package xyz.nickr.jomdb.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.json.JSONObject;

import xyz.nickr.jomdb.JOMDBException;
import xyz.nickr.jomdb.JavaOMDB;

public class TitleResult {

    private final JavaOMDB omdb;
    private final JSONObject json;

    public final String title, type, year, rated;
    public final String released, runtime, genre;
    public final String director, writer, actors;
    public final String plot, language, country;
    public final String awards, poster, metascore;
    public final String imdbRating, imdbVotes, imdbID;

    public TitleResult(JavaOMDB omdb, JSONObject json) {
        this.omdb = omdb;
        this.json = json;
        if (json.getBoolean("Response")) {
            this.title = json.getString("Title");
            this.type = json.getString("Type");
            this.year = json.getString("Year");
            this.rated = json.getString("Rated");
            this.released = json.getString("Released");
            this.runtime = json.getString("Runtime");
            this.genre = json.getString("Genre");
            this.director = json.getString("Director");
            this.writer = json.getString("Writer");
            this.actors = json.getString("Actors");
            this.plot = json.getString("Plot");
            this.language = json.getString("Language");
            this.country = json.getString("Country");
            this.awards = json.getString("Awards");
            this.poster = json.getString("Poster");
            this.metascore = json.getString("Metascore");
            this.imdbRating = json.getString("imdbRating");
            this.imdbVotes = json.getString("imdbVotes");
            this.imdbID = json.getString("imdbID");
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

    public Iterable<SeasonResult> seasons() {
        return () -> new Iterator<SeasonResult>() {

            private int curr = 1;
            private SeasonResult res;

            private void preload() {
                if (res != null)
                    return;
                try {
                    res = omdb.seasonById(imdbID, String.valueOf(curr));
                } catch (Exception ex) {
                    res = null;
                }
            }

            @Override
            public boolean hasNext() {
                preload();
                return res != null;
            }

            @Override
            public SeasonResult next() {
                if (!hasNext())
                    throw new NoSuchElementException("no more seasons");
                SeasonResult next = res;
                res = null;
                curr++;
                return next;
            }

        };
    }

}
