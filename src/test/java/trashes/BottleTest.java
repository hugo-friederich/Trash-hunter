package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Bottle;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BottleTest {
    @Test

    public void shouldRespectDimensions ()  {
        Bottle bottle = new Bottle(100,100);
        assertEquals(bottle.getWidth(),9);
        assertEquals(bottle.getHeight(),25);
    }
    @Test
    public void shouldShowABottle() throws IOException{
        Bottle bottle = new Bottle();


    }
}
