# jomdb

An easy-to-use Java API wrapper for [OMDB API](http://omdbapi.com)

## Features
* Retrieving title information given a name or IMDB ID
* Retrieving season information (for series) given a name or IMDB ID
* Retrieving episode information (for series) given a name or IMDB ID, and the episode number
* Searching for titles given a name

## Maven

```xml
<repositories>
    <repository>
        <id>nickr</id>
        <url>http://nickr.xyz/maven/content/public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>xyz.nickr</groupId>
        <artifactId>jomdb</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```