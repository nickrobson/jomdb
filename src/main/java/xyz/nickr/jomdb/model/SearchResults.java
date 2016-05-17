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

import lombok.Getter;
import xyz.nickr.jomdb.JOMDBException;
import xyz.nickr.jomdb.JavaOMDB;
import xyz.nickr.utils.Cache;

public class SearchResults implements Iterable<SearchResultsPage> {

    @Getter
    private final JavaOMDB omdb;

    @Getter
    private final Map<String, String> query;

    @Getter
    private final JSONObject json;

    @Getter
    private final Cache<SearchResultsPage> pages = new Cache<>();

    @Getter
    private final int pageCount, totalResults;

    public SearchResults(JavaOMDB omdb, Map<String, String> query, JSONObject json) {
        this.omdb = omdb;
        this.query = query;
        this.json = json;
        if (json.getBoolean("Response")) {
            this.totalResults = json.getInt("totalResults");
            this.pageCount = this.totalResults > 0 ? 1 + this.totalResults / 10 : 0;
            int page = Integer.parseInt(query.getOrDefault("page", "1"));
            this.pages.put(new Object[] {"getPage", page}, new SearchResultsPage(this, page, json));
        } else {
            throw new JOMDBException(json.getString("Error"));
        }
    }

    public SearchResultsPage getPage(int page) {
        if (page < 1 || page > this.pageCount) {
            throw new IllegalArgumentException(String.format("(%d) is not in range [1,%d]", page, this.pageCount));
        }
        Object[] key = {"getPage", page};
        if (this.pages.has(key)) {
            return this.pages.get(key);
        }
        Map<String, String> q = new HashMap<>(this.query);
        q.put("page", Integer.toString(page));
        return new SearchResultsPage(this, page, this.omdb.get(q));
    }

    @Override
    public Iterator<SearchResultsPage> iterator() {
        return new Iterator<SearchResultsPage>() {

            int page = 1;

            @Override
            public boolean hasNext() {
                return this.page <= SearchResults.this.pageCount;
            }

            @Override
            public SearchResultsPage next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return SearchResults.this.getPage(this.page++);
            }

        };
    }

    @Override
    public Spliterator<SearchResultsPage> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.pageCount, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.SIZED);
    }

    public Stream<SearchResultsPage> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

}
