package xyz.nickr.jomdb;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONObject;

import xyz.nickr.jomdb.model.SearchResults;
import xyz.nickr.jomdb.model.SeasonResult;
import xyz.nickr.jomdb.model.TitleResult;

public class JavaOMDB {

    public static final Pattern IMDB_ID_PATTERN = Pattern.compile("tt[0-9]{7}");

    private final JOMDBRequests requests;

    public JavaOMDB() {
        this.requests = new JOMDBRequests();
    }

    public JOMDBRequests getRequests() {
        return requests;
    }

    public JSONObject get(Map<String, String> params) {
        return requests.getJSON(params);
    }

    public SearchResults search(String search) {
        Map<String, String> query = new HashMap<>();
        query.put("s", search);
        return new SearchResults(this, query, get(query));
    }

    public TitleResult titleByName(String title) {
        Map<String, String> query = new HashMap<>();
        query.put("t", title);
        return new TitleResult(this, get(query));
    }

    public TitleResult titleByName(String title, boolean fullPlot) {
        Map<String, String> query = new HashMap<>();
        query.put("t", title);
        query.put("plot", fullPlot ? "full" : "short");
        return new TitleResult(this, get(query));
    }

    public TitleResult titleById(String imdbId) {
        Map<String, String> query = new HashMap<>();
        query.put("i", imdbId);
        return new TitleResult(this, get(query));
    }

    public TitleResult titleById(String imdbId, boolean fullPlot) {
        Map<String, String> query = new HashMap<>();
        query.put("i", imdbId);
        query.put("plot", fullPlot ? "full" : "short");
        return new TitleResult(this, get(query));
    }

    public SeasonResult seasonByName(String title, String season) {
        Map<String, String> query = new HashMap<>();
        query.put("t", title);
        query.put("Season", season);
        return new SeasonResult(this, get(query));
    }

    public SeasonResult seasonById(String imdbId, String season) {
        Map<String, String> query = new HashMap<>();
        query.put("i", imdbId);
        query.put("Season", season);
        return new SeasonResult(this, get(query));
    }

}
