package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Tire;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TireTest {
    @Test

    public void shouldRespectDimensions () throws IOException {
        Tire tire = new Tire(100,100);
        assertEquals(tire.getWidth(),49);
        assertEquals(tire.getHeight(),40);
    }
    @Test
    public void shouldNotSpawnNextTheDiver() throws IOException{

    }
}
