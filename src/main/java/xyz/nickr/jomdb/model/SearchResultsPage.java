package xyz.nickr.jomdb.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Getter;
import xyz.nickr.jomdb.JOMDBException;

/**
 * Represents a single page of {@link SearchResults search results}.
 */
public class SearchResultsPage implements Iterable<SearchResult> {

    @Getter
    private final SearchResults results;

    @Getter
    private final int page;

    @Getter
    private final JSONObject json;

    @Getter
    private final SearchResult[] res;

    public SearchResultsPage(SearchResults results, int page, JSONObject json) {
        this.results = results;
        this.page = page;
        this.json = json;
        if (json.getBoolean("Response")) {
            JSONArray arr = json.getJSONArray("Search");
            this.res = new SearchResult[arr.length()];
            for (int i = 0, j = arr.length(); i < j; i++) {
                this.res[i] = new SearchResult(arr.getJSONObject(i));
            }
        } else {
            throw new JOMDBException(json.getString("Error"));
        }
    }

    /**
     * Gets a result from this page.
     *
     * @param x The search result index of this page, must be in {@code [0, size())}.
     *
     * @return The search result.
     */
    public SearchResult getResult(int x) {
        if (x < 0 || x >= this.res.length) {
            throw new IllegalArgumentException(String.format("(%d) is not in range [0,%d)", x, this.res.length));
        }
        return this.res[x];
    }

    /**
     * Gets the amount of {@link SearchResult search results} on this page.
     *
     * @return The number of results.
     */
    public int size() {
        return this.res.length;
    }

    @Override
    public Iterator<SearchResult> iterator() {
        return Arrays.asList(this.res).iterator();
    }

    @Override
    public Spliterator<SearchResult> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.res.length, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.SIZED);
    }

    /**
     * Gets the results on this page as a stream.
     *
     * @return The stream.
     */
    public Stream<SearchResult> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

}
