package xyz.nickr.jomdb;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JOMDBRequests {

    public static final String API_URL = "http://omdbapi.com";

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

    public JSONObject getJSON(Map<String, String> params) {
        return this.getJSON(this.getURL(params));
    }

}
