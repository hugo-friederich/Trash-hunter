package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Bottle;
import org.trash_hunter.trashes.PlasticBag;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PlasticBagTest {
    @Test

    public void shouldRespectDimensions () throws IOException {
        PlasticBag plasticBag = new PlasticBag(100,100);
        assertEquals(plasticBag.getWidth(),40);
        assertEquals(plasticBag.getHeight(),40);
    }
    @Test
    public void shouldNotSpawnNextTheDiver() throws IOException{

    }
}
