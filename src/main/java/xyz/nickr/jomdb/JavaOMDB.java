package xyz.nickr.jomdb;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.nickr.jomdb.model.SearchResults;
import xyz.nickr.jomdb.model.SeasonResult;
import xyz.nickr.jomdb.model.TitleResult;

/**
 * The API entry point.
 */
public class JavaOMDB {

    /**
     * A regular expression matching valid IMDB IDs.
     */
    public static final Pattern IMDB_ID_PATTERN = Pattern.compile("tt[0-9]{7}");

    private final JOMDBRequests requests;
    private final boolean printStackTraces;

    /**
     * Creates an instance that will print stack traces.
     */
    public JavaOMDB() {
        this(true);
    }

    /**
     * Creates an instance that may print stack traces.
     *
     * @param printStackTraces Whether or not to print stack traces.
     */
    public JavaOMDB(boolean printStackTraces) {
        this.requests = new JOMDBRequests();
        this.printStackTraces = printStackTraces;
    }

    /**
     * Gets the corresponding {@link JOMDBRequests} object.
     *
     * @return The requests object.
     */
    public JOMDBRequests getRequests() {
        return requests;
    }

    /**
     * Queries the OMDB API with given query parameters.
     *
     * @param params The parameters.
     *
     * @return The JSON object describing the query result.
     *
     * @throws JOMDBException when an error occurs while retrieving or parsing JSON
     */
    public JSONObject get(Map<String, String> params) throws JOMDBException {
        try {
            return requests.getJSON(params);
        } catch (JSONException ex) {
            if (printStackTraces)
                ex.printStackTrace();
            throw new JOMDBException("Invalid JSON", ex);
        } catch (Exception ex) {
            if (printStackTraces)
                ex.printStackTrace();
            throw new JOMDBException("Something went wrong", ex);
        }
    }

    /**
     * Searches for titles with specified text.
     *
     * @param search The search text.
     *
     * @return The search results.
     */
    public SearchResults search(String search) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("s", search);
            return new SearchResults(this, query, get(query));
        } catch (JOMDBException ex) {
            if (!printStackTraces)
                throw ex;
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a title by its name, with a short plot.
     *
     * @param title The title's name.
     *
     * @return The title.
     */
    public TitleResult titleByName(String title) {
        return titleByName(title, false);
    }

    /**
     * Gets a title by its name, optionally with a full plot.
     *
     * @param title The title's name.
     * @param fullPlot Whether or not to get the full plot.
     *
     * @return The title.
     */
    public TitleResult titleByName(String title, boolean fullPlot) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("t", title);
            query.put("plot", fullPlot ? "full" : "short");
            return new TitleResult(this, get(query));
        } catch (JOMDBException ex) {
            if (!printStackTraces)
                throw ex;
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a title by its IMDB ID, with a short plot.
     *
     * @param imdbId The title's IMDB ID.
     *
     * @return The title.
     */
    public TitleResult titleById(String imdbId) {
        return titleById(imdbId, false);
    }

    /**
     * Gets a title by its IMDB ID, optionally with a full plot.
     *
     * @param imdbId The title's IMDB ID.
     * @param fullPlot Whether or not to get the full plot.
     *
     * @return The title.
     */
    public TitleResult titleById(String imdbId, boolean fullPlot) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("i", imdbId);
            query.put("plot", fullPlot ? "full" : "short");
            return new TitleResult(this, get(query));
        } catch (JOMDBException ex) {
            if (!printStackTraces)
                throw ex;
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a season by title name and season number.
     *
     * @param title The title name.
     * @param season The season number.
     *
     * @return The season.
     */
    public SeasonResult seasonByName(String title, String season) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("t", title);
            query.put("Season", season);
            return new SeasonResult(this, get(query));
        } catch (JOMDBException ex) {
            if (!printStackTraces)
                throw ex;
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a season by title's IMDB ID and season number.
     *
     * @param imdbId The title's IMDB ID.
     * @param season The season number.
     *
     * @return The season.
     */
    public SeasonResult seasonById(String imdbId, String season) {
        try {
            Map<String, String> query = new HashMap<>();
            query.put("i", imdbId);
            query.put("Season", season);
            return new SeasonResult(this, get(query));
        } catch (JOMDBException ex) {
            if (!printStackTraces)
                throw ex;
            ex.printStackTrace();
            return null;
        }
    }

}
