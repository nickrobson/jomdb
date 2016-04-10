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

public class SearchResultsPage implements Iterable<SearchResult> {

    private final SearchResults results;
    private final int page;
    private final JSONObject json;
    private final SearchResult[] res;

    public SearchResultsPage(SearchResults results, int page, JSONObject json) {
        this.results = results;
        this.page = page;
        this.json = json;
        if (json.getBoolean("Response")) {
            JSONArray arr = json.getJSONArray("Search");
            this.res = new SearchResult[arr.length()];
            for (int i = 0, j = arr.length(); i < j; i++)
                this.res[i] = new SearchResult(arr.getJSONObject(i));
        } else {
            throw new JOMDBException(json.getString("Error"));
        }
    }

    public SearchResults getSearch() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public JSONObject getJSON() {
        return json;
    }

    public SearchResult getResult(int x) {
        if (x < 0 || x >= res.length) {
            throw new IllegalArgumentException(String.format("(%d) is not in range [0,%d)", x, res.length));
        }
        return res[x];
    }

    public SearchResult[] getResults() {
        return res;
    }

    public int size() {
        return res.length;
    }

    @Override
    public Iterator<SearchResult> iterator() {
        return Arrays.asList(res).iterator();
    }

    @Override
    public Spliterator<SearchResult> spliterator() {
        return Spliterators.spliterator(iterator(), res.length, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.SIZED);
    }

    public Stream<SearchResult> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
