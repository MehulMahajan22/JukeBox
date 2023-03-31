import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PodcastsTest {
    Podcasts p;
    @BeforeEach
    public void setUp(){
        p = new Podcasts();
    }
    @Test
    void convertMicrosecondsToMinutes() {
        assertEquals("01:00",p.convertMicrosecondsToMinutes(60000000L));
        assertEquals("03:30",p.convertMicrosecondsToMinutes(210000000L));
    }

    @Test
    void getTotalPodcastsInDatabase() {
        assertEquals(2,p.getTotalPodcastsInDatabase());
    }

    @Test
    void searchPodcastByName() {
        assertEquals(1,p.searchPodcastByName("Talks at google"));
        assertEquals(2,p.searchPodcastByName("Business Wars"));
        assertEquals(0,p.searchPodcastByName("abc"));
    }
}