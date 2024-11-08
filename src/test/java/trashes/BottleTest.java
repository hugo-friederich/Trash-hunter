package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Bottle;
import static org.junit.Assert.*;
import java.io.IOException;

public class BottleTest {
    @Test

    public void shouldRespectDimensions () throws IOException {
        Bottle bottle = new Bottle(100,100);
        assertEquals(bottle.getWidth(),9);
        assertEquals(bottle.getHeight(),25);
    }
    @Test
    public void shouldNotSpawnNextTheDiver() throws IOException{


    }
}
