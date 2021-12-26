
package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;


public abstract class RestartScreen implements Screen {

    @Override
    public abstract void display(AsciiPanel terminal) ;

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new PlayScreen();
            default:
                return this;
        }
    }

}
