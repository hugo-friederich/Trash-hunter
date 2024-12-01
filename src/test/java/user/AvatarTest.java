package user;

import org.junit.Test;
import org.trash_hunter.user.Avatar;

public class AvatarTest {

    @Test
    public void shouldAffectColorToAvatar(){
        Avatar avatar = new Avatar("test","Red");
    }
}
