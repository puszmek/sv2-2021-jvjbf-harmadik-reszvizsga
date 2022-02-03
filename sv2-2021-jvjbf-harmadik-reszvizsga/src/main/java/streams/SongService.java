package streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SongService {

    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    public Optional<Song> shortestSong() {     // legrövidebb dalt, Optional-ként
        return songs.stream()
                .min(Comparator.comparing(Song::getLength));    //  .min((s1, s2) -> s1.getLength() - s2.getLength());
//        return songs.stream()
//                .sorted(Comparator.comparing(s->s.getLength()))
//                .findFirst();
    }

    public List<Song> findSongByTitle(String title) {       // dalok listáját cím alapján! (Két dalnak lehet ugyanaz a címe)
        return songs.stream()
                .filter(song -> song.getTitle().equals(title))
                .toList();
    }

    public boolean isPerformerInSong(Song song, String name) {     // egy előadó szerepel-e egy konkrét dal előadóinak listájában
        return song.getPerformers().stream()
                .anyMatch(s -> s.equals(name));
//        return songs.stream()
//                .filter(s->s.getTitle().equals(song.getTitle()))
//                .anyMatch(s->s.getPerformers().contains(name));
    }

    public List<String> titlesBeforeDate(LocalDate date) {      // az összes dal címét, ami egy paraméterül átadott dátum előtt jelent meg
        return songs.stream()
                .filter(song -> song.getRelease().isBefore(date))
                .map(Song::getTitle)
                .toList();
    }
}
