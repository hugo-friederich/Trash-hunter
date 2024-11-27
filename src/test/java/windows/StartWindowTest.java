package windows;

import org.junit.Test;
import org.trash_hunter.windows.Start_window;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StartWindowTest {
    @Test
    public void shouldOpenStartWindow () throws SQLException {
        Start_window startWindow=new Start_window();
        startWindow.dispose();
    }
}
