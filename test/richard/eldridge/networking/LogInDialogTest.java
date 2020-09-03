package richard.eldridge.networking;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LogInDialogTest {
    LogInDialog login;
    List<Component> components;
    @Before
    public void setup() {
        login = new LogInDialog("Chat Test");
    }

    @Test
    public void testLoginAndClose() throws Exception {
        Robot robot = new Robot();
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(() -> {
            login.login();
            assertTrue(login.isVisible());
            JButton okButton = (JButton) login.getComponent(LogInDialog.LOG_IN_DIALOG_OK_BTN);

            robot.mouseMove(okButton.getX(), okButton.getY());
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
        );
        assertFalse(login.isVisible());
    }
}
