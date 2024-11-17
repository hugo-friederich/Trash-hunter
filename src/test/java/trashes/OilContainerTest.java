package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.OilContainer;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class OilContainerTest {
    @Test

    public void shouldRespectDimensions () throws IOException {
        OilContainer oilContainer = new OilContainer(100,100);
        assertEquals(oilContainer.getWidth(),40);
        assertEquals(oilContainer.getHeight(),60);
    }
    @Test
    public void shouldNotSpawnNextTheDiver() throws IOException{

    }
}
