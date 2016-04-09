package xyz.nickr.jomdb.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONObject;

import xyz.nickr.jomdb.JOMDBException;
import xyz.nickr.jomdb.JavaOMDB;
import xyz.nickr.utils.Cache;

public class SearchResults implements Iterable<SearchResultsPage> {

    private final JavaOMDB omdb;
    private final Map<String, String> query;
    private final JSONObject json;
    private final Cache<SearchResultsPage> pages = new Cache<>();
    private final int pageCount;
    private final int totalResults;

    public SearchResults(JavaOMDB omdb, Map<String, String> query, JSONObject json) {
        this.omdb = omdb;
        this.query = query;
        this.json = json;
        if (json.getBoolean("Response")) {
            this.totalResults = json.getInt("totalResults");
            this.pageCount = totalResults > 0 ? 1 + totalResults / 10 : 0;
            int page = Integer.parseInt(query.getOrDefault("page", "1"));
            this.pages.put(new Object[]{"getPage", page}, new SearchResultsPage(this, page, json));
        } else {
            throw new JOMDBException(json.getString("Error"));
        }
    }

    public JavaOMDB getOMDB() {
        return omdb;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public JSONObject getJSON() {
        return json;
    }

    public SearchResultsPage getPage(int page) {
        if (page < 1 || page > pageCount)
            throw new IllegalArgumentException(String.format("(%d) is not in range [1,%d]", page, pageCount));
        Object[] key = {"getPage", page};
        if (pages.has(key))
            return pages.get(key);
        Map<String, String> q = new HashMap<>(query);
        q.put("page", Integer.toString(page));
        return new SearchResultsPage(this, page, omdb.get(q));
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public Iterator<SearchResultsPage> iterator() {
        return new Iterator<SearchResultsPage>() {

            int page = 1;

            @Override
            public boolean hasNext() {
                return page <= pageCount;
            }

            @Override
            public SearchResultsPage next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return getPage(page++);
            }

        };
    }

    @Override
    public Spliterator<SearchResultsPage> spliterator() {
        return Spliterators.spliterator(iterator(), pageCount, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.SIZED);
    }

    public Stream<SearchResultsPage> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
