package xyz.nickr.jomdb;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Handles all outgoing requests to the OMDB API.
 */
public class JOMDBRequests {

    public static final String API_URL = "http://omdbapi.com";

    /**
     * Gets the URL to be queried given query parameters.
     *
     * @param params The parameters.
     *
     * @return The URL.
     */
    public String getURL(Map<String, String> params) {
        String query = "";
        try {
            for (Entry<String, String> entry : params.entrySet()) {
                if (query.length() > 0) {
                    query += "&";
                }
                query += URLEncoder.encode(entry.getKey(), "UTF-8");
                query += "=";
                query += URLEncoder.encode(entry.getValue(), "UTF-8");
            }
        } catch (IOException ex) {
            return API_URL;
        }
        return API_URL + (query.length() > 0 ? "/?" : "") + query;
    }

    /**
     * Gets the JSON result at the given URL.
     *
     * @param url The URL.
     *
     * @return The JSON.
     */
    public JSONObject getJSON(String url) {
        try {
            if (url.startsWith("/")) {
                url = API_URL + url;
            }
            return Unirest.get(url).asJson().getBody().getObject();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the JSON result at the URL constructed with the given parameters.
     *
     * @param params The parameters.
     *
     * @return The JSON.
     */
    public JSONObject getJSON(Map<String, String> params) {
        return this.getJSON(this.getURL(params));
    }

}
