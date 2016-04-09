package xyz.nickr.jomdb;

import org.junit.Test;

import xyz.nickr.jomdb.model.SearchResult;
import xyz.nickr.jomdb.model.SearchResults;
import xyz.nickr.jomdb.model.SearchResultsPage;
import xyz.nickr.jomdb.model.SeasonEpisodeResult;
import xyz.nickr.jomdb.model.SeasonResult;

public class TestJOMDB {

    @Test
    public void findAntman() {
        JavaOMDB omdb = new JavaOMDB();
        SearchResults res = omdb.search("Ant-Man");
        for (SearchResultsPage page : res) {
            for (SearchResult sr : page) {
                System.out.println("ID: " + sr.imdbId);
                System.out.println("   Title: " + sr.title);
                System.out.println("   Year: " + sr.year);
                System.out.println("   Type: " + sr.type);
                System.out.println("   Poster: " + sr.poster);
            }
        }
    }

    @Test
    public void findStargateS1() {
        JavaOMDB omdb = new JavaOMDB();
        SeasonResult season = omdb.seasonByName("Stargate Universe", "1");
        System.out.println("Title: " + season.title);
        System.out.println("Season: " + season.season);
        for (SeasonEpisodeResult episode : season) {
            System.out.println("Episode: " + episode.episode);
            System.out.println("   Title: " + episode.title);
            System.out.println("   Released: " + episode.released);
            System.out.println("   IMDB ID: " + episode.imdbId);
            System.out.println("   IMDB Rating: " + episode.imdbRating);
        }
    }

}
