import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    Playlist pl;
    ArrayList<Integer> songIds1;
    ArrayList<Integer> songIds2;
    ArrayList<Integer> emptyIds;
    ArrayList<Integer> podcastIds;
    @BeforeEach
    public void setUp(){
        pl = new Playlist();
        songIds1 = new ArrayList<>();
        songIds1.add(1);
        songIds1.add(2);
        songIds1.add(4);
        songIds2 = new ArrayList<>();
        songIds2.add(2);
        songIds2.add(4);
        songIds2.add(6);
        emptyIds = new ArrayList<>();
        podcastIds = new ArrayList<>();
        podcastIds.add(2);
    }
    @Test
    void getSongIdsInPlaylist() {
        assertEquals(songIds1,pl.getSongIdsInPlaylist(1));
        assertEquals(songIds2,pl.getSongIdsInPlaylist(2));
        assertEquals(emptyIds,pl.getSongIdsInPlaylist(6));
    }

    @Test
    void getPodcastIdsInPlaylist() {
        assertEquals(podcastIds,pl.getPodcastIdsInPlaylist(5));
        assertEquals(emptyIds,pl.getPodcastIdsInPlaylist(2));
    }

    @Test
    void checkPlaylistName() {
        assertTrue(pl.checkPlaylistName("Myplaylist",1));
        assertTrue(pl.checkPlaylistName("favs",1));
        assertFalse(pl.checkPlaylistName("new",1));
        assertFalse(pl.checkPlaylistName("favs",2));
    }

    @Test
    void checkIfSongAlreadyInPlaylist() {
        assertTrue(pl.checkIfSongAlreadyInPlaylist("favs",2,1));
        assertTrue(pl.checkIfSongAlreadyInPlaylist("myplaylist",2,1));
        assertFalse(pl.checkIfSongAlreadyInPlaylist("favs", 5, 1));
    }

    @Test
    void checkIfPodcastAlreadyInPlaylist() {
        assertFalse(pl.checkIfPodcastAlreadyInPlaylist("favs", 2, 1));
        assertTrue(pl.checkIfPodcastAlreadyInPlaylist("test",2 , 1));
    }
}