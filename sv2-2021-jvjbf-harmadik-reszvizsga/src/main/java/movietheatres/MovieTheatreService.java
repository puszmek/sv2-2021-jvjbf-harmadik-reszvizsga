package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file!", ioe);
        }
    }

    private void parseLine(String line) {
        String[] temp = line.split("-");
        String[] movieTemp = temp[1].split(";");
        if (!shows.containsKey(temp[0])) {
            shows.put(temp[0], new ArrayList<>());
        }
        shows.get(temp[0]).add(new Movie(movieTemp[0], LocalTime.parse(movieTemp[1])));
        Collections.sort(shows.get(temp[0]), Comparator.comparing(Movie::getStartTime));
    }

    public List<String> findMovie(String title) {       // visszaadja a mozik listáját, ahol a filmet adják
        return shows.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(movie -> movie.getTitle().equals(title)))
                .map(Map.Entry::getKey)
                .toList();
//        Set<String> result = new LinkedHashSet<>();
//        for (Map.Entry<String, List<Movie>> actual : shows.entrySet()) {
//            for (Movie m : actual.getValue()) {
//                if (m.getTitle().equals(title)) {
//                    result.add(actual.getKey());
//                }
//            }
//        }
//        return new ArrayList<>(result);
    }

    public LocalTime findLatestShow(String title) {     // visszadja azt a legkésőbbi időpontot, amikor játszák a paraméterül megadott filmet
//        return shows.values().stream()
//                .flatMap(List::stream)
//                .filter(movie -> movie.getTitle().equals(title))
//                .map(Movie::getStartTime)
//                .max(Comparator.naturalOrder())
//                .orElseThrow(() -> new IllegalArgumentException("Cannot find movie!"));
        return shows.entrySet().stream()
                .flatMap(e->e.getValue().stream())
                .filter(m->m.getTitle().equals(title))
                .sorted(Comparator.comparing(Movie::getStartTime).reversed())
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Cannot find movie!"))
                .getStartTime();
    }
}
