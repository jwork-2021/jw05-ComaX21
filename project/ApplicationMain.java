
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screen.Screen;
import screen.StartScreen;


public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain() {
        super();

        //新建终端窗口
        terminal = new AsciiPanel(70, 45, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();

        screen = new StartScreen();
        addKeyListener(this);

        //产生初始画面
        repaint();
    }

    //终端显示
    @Override
    public void repaint() {
        terminal.clear();
        screen.display(terminal);
        super.repaint();
    }


    public void keyPressed(KeyEvent e) {
        //按键更新
        screen = screen.respondToUserInput(e);
        //终端显示
        repaint();
    }
    public void keyReleased(KeyEvent e) {
    }
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        while(true)
        {
            app.repaint();
            Thread.sleep(1000);
        }
    }

}
