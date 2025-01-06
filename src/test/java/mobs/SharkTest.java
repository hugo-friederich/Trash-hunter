package mobs;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.mobs.Shark;
import org.trash_hunter.util.Direction;

import java.awt.image.BufferedImage;

public class SharkTest {
    @Test
    public void shouldUpdateSpriteWithDirection(){
        Shark myshark= new Shark(0,10,Direction.LEFT);
        BufferedImage image1=myshark.getSprite();
        myshark.setDirection(Direction.RIGHT);
        myshark.update();
        BufferedImage image2=myshark.getSprite();
        Assert.assertNotEquals(image2,image1);
    }

    @Test
    public void shouldConvertMobDBtoShark(){

    }
}
