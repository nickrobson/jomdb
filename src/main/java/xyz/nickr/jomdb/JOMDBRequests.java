package xyz.nickr.jomdb;

import com.mashape.unirest.http.HttpResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
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
        String body = null;
        try {
            if (url.startsWith("/")) {
                url = API_URL + url;
            }
            HttpResponse<String> res = Unirest.get(url).asString();
            if (res.getStatus() / 100 == 5) { // 500 codes
                throw new JOMDBUnavailableException();
            }
            body = res.getBody();
            if (body.equals("The service is unavailable.")) {
                throw new JOMDBUnavailableException();
            }
            try {
                return new JSONObject(body);
            } catch (JSONException ex) {
                return new JSONObject(body.replace("\\", "\\\\"));
            }
        } catch (Exception e) {
            if (e instanceof JOMDBUnavailableException)
                throw (JOMDBUnavailableException) e;
            throw new JOMDBException(body != null ? "Failed to parse: " + body : "Could not retrieve JSON data", e);
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
