package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-");
                String theatre = parts[0];
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot found file!", ioe);
        }
    }

    public List<String> findMovie(String title) {
        return shows.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(movie -> movie.getTitle().equals(title)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public LocalTime findLatestShow(String title) {
        return shows.values().stream()
                .flatMap(List::stream)
                .filter(movie -> movie.getTitle().equals(title))
                .map(Movie::getStartTime)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find movie!"));
    }
}
