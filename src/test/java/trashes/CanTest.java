package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Can;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CanTest {
    @Test

    public void shouldRespectDimensions () throws IOException {
        Can can = new Can(100,100);
        assertEquals(can.getWidth(),12);
        assertEquals(can.getHeight(),25);
    }
    @Test
    public void shouldNotSpawnNextTheDiver() throws IOException{

    }
}
