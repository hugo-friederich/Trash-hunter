package user;

import org.junit.Test;
import org.trash_hunter.user.Avatar;

public class AvatarTest {
    @Test
    public void shouldLoadSprites(){
        Avatar avatar= new Avatar("Bob","Animated");
        avatar.loadSpritesForAnimation();
    }
}
