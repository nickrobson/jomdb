package xyz.nickr.jomdb.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.json.JSONObject;

import lombok.Getter;
import xyz.nickr.jomdb.JOMDBException;
import xyz.nickr.jomdb.JavaOMDB;

public class TitleResult {

    @Getter
    private final JavaOMDB omdb;

    @Getter
    private final JSONObject json;

    @Getter
    private final String title, type, year, rated, released, runtime, genre;

    @Getter
    private final String director, writer, actors, plot, language, country;

    @Getter
    private final String awards, poster, metascore, imdbRating, imdbVotes, imdbID;

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

    public Iterable<SeasonResult> seasons() {
        return () -> new Iterator<SeasonResult>() {

            private int curr = 1;
            private SeasonResult res;

            private void preload() {
                if (this.res != null) {
                    return;
                }
                try {
                    this.res = TitleResult.this.omdb.seasonById(TitleResult.this.imdbID, String.valueOf(this.curr));
                } catch (Exception ex) {
                    this.res = null;
                }
            }

            @Override
            public boolean hasNext() {
                this.preload();
                return this.res != null;
            }

            @Override
            public SeasonResult next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("no more seasons");
                }
                SeasonResult next = this.res;
                this.res = null;
                this.curr++;
                return next;
            }

        };
    }

}
