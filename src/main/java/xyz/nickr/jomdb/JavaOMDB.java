package xyz.nickr.jomdb;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
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
        try {
            return requests.getJSON(params);
        } catch (JSONException ex) {
            ex.printStackTrace();
            throw new JOMDBException("Invalid JSON", ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JOMDBException("Something went wrong", ex);
        }
    }

    public SearchResults search(String search) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("s", search);
            return new SearchResults(this, query, get(query));
        } catch (JOMDBException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public TitleResult titleByName(String title) {
        return titleByName(title, false);
    }

    public TitleResult titleByName(String title, boolean fullPlot) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("t", title);
            query.put("plot", fullPlot ? "full" : "short");
            return new TitleResult(this, get(query));
        } catch (JOMDBException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public TitleResult titleById(String imdbId) {
        return titleById(imdbId, false);
    }

    public TitleResult titleById(String imdbId, boolean fullPlot) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("i", imdbId);
            query.put("plot", fullPlot ? "full" : "short");
            return new TitleResult(this, get(query));
        } catch (JOMDBException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public SeasonResult seasonByName(String title, String season) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("t", title);
            query.put("Season", season);
            return new SeasonResult(this, get(query));
        } catch (JOMDBException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public SeasonResult seasonById(String imdbId, String season) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("i", imdbId);
            query.put("Season", season);
            return new SeasonResult(this, get(query));
        } catch (JOMDBException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
