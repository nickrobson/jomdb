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
                System.out.println("ID: " + sr.getImdbId());
                System.out.println("   Title: " + sr.getTitle());
                System.out.println("   Year: " + sr.getYear());
                System.out.println("   Type: " + sr.getType());
                System.out.println("   Poster: " + sr.getPoster());
            }
        }
    }

    @Test
    public void findStargateS1() {
        JavaOMDB omdb = new JavaOMDB();
        SeasonResult season = omdb.seasonByName("Stargate Universe", "1");
        System.out.println("Title: " + season.getTitle());
        System.out.println("Season: " + season.getSeason());
        for (SeasonEpisodeResult episode : season) {
            System.out.println("Episode: " + episode.getEpisode());
            System.out.println("   Title: " + episode.getTitle());
            System.out.println("   Released: " + episode.getReleased());
            System.out.println("   IMDB ID: " + episode.getImdbId());
            System.out.println("   IMDB Rating: " + episode.getImdbRating());
        }
    }

}
