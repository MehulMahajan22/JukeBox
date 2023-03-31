import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongsTest {
Songs s;
@BeforeEach
public void setUp(){
    s = new Songs();
}

    @Test
    void checkConvertMicrosecondsToMinutes() {
    assertEquals("01:00",s.convertMicrosecondsToMinutes(60000000L));
    assertEquals("03:30",s.convertMicrosecondsToMinutes(210000000L));
    }

    @Test
    void checkSearchByName() {
    assertEquals(1, s.searchByName("Cheap Thrills"));
    assertEquals(0, s.searchByName("abc"));
    }

    @Test
    void checkTotalSongsinDatabase() {
    assertEquals(6,s.totalSongsinDatabase());
    }
}