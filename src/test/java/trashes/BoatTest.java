package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Boat;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BoatTest {
    @Test

    public void shouldRespectDimensions () throws IOException {
        Boat boat = new Boat(100,100);
        assertEquals(boat.getWidth(),148);
        assertEquals(boat.getHeight(),80);
    }
    @Test
    public void shouldNotSpawnNextTheDiver() throws IOException{

    }
}
