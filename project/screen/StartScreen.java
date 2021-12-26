
package screen;

import asciiPanel.AsciiPanel;

public class StartScreen extends RestartScreen {
    public int i = 0;
    @Override
    public void display(AsciiPanel terminal) {
        terminal.write("This is the start screen.", 0, 0);
        terminal.write("Press ENTER to continue...", 0, 1);
    }

}
