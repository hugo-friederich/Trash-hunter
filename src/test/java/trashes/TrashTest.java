package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Bottle;

import static org.junit.Assert.assertTrue;

public class TrashTest {

    @Test
    public void shouldResetTrashes (){
        Bottle bottle = new Bottle(100,100);
        bottle.resetPosition();
        assertTrue(bottle.getX()<1440 &&
                bottle.getX()>0 &&
                bottle.getY()<700-bottle.getHeight()&&
                bottle.getY()>0);
    }
}
