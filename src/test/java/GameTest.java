import org.junit.Test;
import org.trash_hunter.Game;
import org.trash_hunter.trashes.Bottle;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
   

    @Test
    public void shouldAvoidCollisionBetwwenTrashes () {
        Bottle bottle1 =new Bottle(100,100);
        Bottle bottle2 =new Bottle(100,100);
        assertTrue(Game.checkCollisionBetweenTrashes(bottle1,bottle2));

        Bottle bottle3 =new Bottle(100,100);
        Bottle bottle4 =new Bottle(119,100);
        assertTrue(Game.checkCollisionBetweenTrashes(bottle3,bottle4));

        Bottle bottle5 =new Bottle(100,100);
        Bottle bottle6 =new Bottle(100,120);
        assertTrue(Game.checkCollisionBetweenTrashes(bottle5,bottle6));

        Bottle bottle7 =new Bottle(100,100);
        Bottle bottle8 =new Bottle(131,100);
        assertFalse(Game.checkCollisionBetweenTrashes(bottle7,bottle8));
    }

    @Test
    public void shouldInitializeTrashes() throws SQLException {
        Game game=new Game();
        System.out.println(game.getLocalTrashset());
        game.getDiverDAO().clear();
    }
}
