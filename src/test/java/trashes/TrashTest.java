package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrashTest {

    @Test
    public void shouldResetTrashes () throws InterruptedException {
        Bottle bottle = new Bottle(100,100);
        bottle.updatePosition();
        assertTrue(bottle.getX()<1440 &&
                bottle.getX()>0 &&
                bottle.getY()<700-bottle.getHeight()&&
                bottle.getY()>0);
    }
    @Test
    public void boatShouldRespectDimensions () {
        Boat boat = new Boat(100,100);
        assertEquals(boat.getWidth(),148);
        assertEquals(boat.getHeight(),80);
    }
    @Test
    public void bottleShouldRespectDimensions ()  {
        Bottle bottle = new Bottle(100,100);
        assertEquals(bottle.getWidth(),9);
        assertEquals(bottle.getHeight(),25);
    }
    @Test
    public void canShouldRespectDimensions () {
        Can can = new Can(100,100);
        assertEquals(can.getWidth(),12);
        assertEquals(can.getHeight(),25);
    }
    @Test
    public void oilContainerShouldRespectDimensions () {
        OilContainer oilContainer = new OilContainer(100,100);
        assertEquals(oilContainer.getWidth(),40);
        assertEquals(oilContainer.getHeight(),60);
    }
    @Test
    public void plasticBagShouldRespectDimensions () {
        PlasticBag plasticBag = new PlasticBag(100,100);
        assertEquals(plasticBag.getWidth(),40);
        assertEquals(plasticBag.getHeight(),40);
    }
    @Test
    public void tireShouldRespectDimensions () {
        Tire tire = new Tire(100,100);
        assertEquals(tire.getWidth(),49);
        assertEquals(tire.getHeight(),40);
    }

    @Test
    public void shouldCreateABottleWithId(){
        Bottle bottle=new Bottle(1);
        System.out.println(bottle.getX());
    }
}
