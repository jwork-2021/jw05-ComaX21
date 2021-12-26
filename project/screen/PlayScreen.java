package screen;

import world.*;
import asciiPanel.AsciiPanel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen {

    private World world;

    private int screenWidth;
    private int screenHeight;
    private List<String> messager;



    public PlayScreen() {
        this.screenWidth = 70;
        this.screenHeight = 40;
        this.messager = new ArrayList<String>();

        
        //生成世界
        createWorld();
        
        //新建生物工厂
        CreatureFactory creatureFactory = new CreatureFactory(this.world);
        //生成生物
        createCreatures(creatureFactory);
    }

    private void createWorld() {
        world = new WorldBuilder(70, 40).makeEmpty().build();
    }

    //生成生物
    private void createCreatures(CreatureFactory creatureFactory) {
        creatureFactory.newPlayer(this.messager);
        //for(int i = 0; i < 3; ++i)
            creatureFactory.newMonster();
    }


    @Override
    public void display(AsciiPanel terminal) {
        //将world内容投放到terminal
        displayWorld(terminal, getScrollX(), getScrollY());
        
        //显示信息
        String stats = String.format("%3d/%3d hp", this.world.getPlayer().hp(), this.world.getPlayer().maxHP());
        terminal.write(stats, 1, 40);
        for (int i = 0; i < this.messager.size(); i++) {
            terminal.write(this.messager.get(i), 1, 40 + i + 1);
        }
        this.messager.clear();
    }

    
    //显示世界
    private void displayWorld(AsciiPanel terminal, int left, int top) {
        // 显示背景
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                
                /*if (player.canSee(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else {
                    terminal.write(world.glyph(wx, wy), x, y, Color.DARK_GRAY);
                }*/
            }
        }
        // 显示所有生物
        for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + screenWidth && creature.y() >= top && creature.y() < top + screenHeight) {
                
                terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                /*if (player.canSee(creature.x(), creature.y())) {
                    terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                }*/
            }
        }
        // 世界中的生物进行活动，一个线程，串行执行
        //world.update();
    }


    //响应键盘输入操控player
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(!this.world.haveAlivePlayer())
            return new LoseScreen();
        else if(!this.world.haveMonster())
            return new WinScreen();


        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.world.getPlayer().move(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                this.world.getPlayer().move(1, 0);
                break;
            case KeyEvent.VK_UP:
                this.world.getPlayer().move(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                this.world.getPlayer().move(0, 1);
                break;
        }

        return this;
    }


    public int getScrollX() {
        return Math.max(0, Math.min(this.world.getPlayer().x() - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(this.world.getPlayer().y() - screenHeight / 2, world.height() - screenHeight));
    }

}
