package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Game;
import Model.Moving.Player;
import Model.Directable;

public class Keyboard implements KeyListener {

    private Game game;
    private Player p;
    private boolean inventoryOn;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Keyboard(Game game) {
        this.game = game;
        this.p = game.getPlayer();
        game.setKeyboard(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<KeyMethods>

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        if(inventoryOn){
            switch (key) {
                case KeyEvent.VK_RIGHT:
                    game.moveIc(Directable.EAST);
                    break;
                case KeyEvent.VK_LEFT:
                    game.moveIc(Directable.WEST);
                    break;
                case KeyEvent.VK_DOWN:
                    game.moveIc(Directable.SOUTH);
                    break;
                case KeyEvent.VK_UP:
                    game.moveIc(Directable.NORTH);
                    break;
                case KeyEvent.VK_E:
                    p.useItem();
                    break;
                case KeyEvent.VK_D:
                    p.drop(0);
            }

        }
        else{
            switch (key) {
                case KeyEvent.VK_RIGHT:
                    p.moveCharacter(1, 0);
                    break;
                case KeyEvent.VK_LEFT:
                    p.moveCharacter(-1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    p.moveCharacter(0, 1);
                    break;
                case KeyEvent.VK_UP:
                    p.moveCharacter(0, -1);
                    break;
                case KeyEvent.VK_SPACE:
                    p.attack();
                    break;
                case KeyEvent.VK_E:
                    p.action();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.VK_I:
                if (inventoryOn){
                    inventoryOn = false;
                }
                else{
                    inventoryOn = true;
                }
                game.setInventoryState(inventoryOn);
                break;
        }
    }

    public void setPlayer(Player p){
        this.p = p;
    }
}
