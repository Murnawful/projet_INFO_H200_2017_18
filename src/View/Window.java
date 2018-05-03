package View;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import Moving.Player;
import Objects.GameObject;


public class Window {

    private Map map = new Map();

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Window() {
        JFrame window = new JFrame("Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1000, 1020);
        window.getContentPane().setBackground(Color.gray);
        window.getContentPane().add(this.map);
        window.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    public void update() {
        this.map.redraw();
    }
    
    public void swingAxe(){
      map.swingAxe();
      this.map.redraw();
  }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setInventoryState(boolean inventoryState){
        map.setInventoryState(inventoryState);
        this.map.redraw();
    }

    public void setKeyListener(KeyListener keyboard) {
        this.map.addKeyListener(keyboard);
    }

    public void setGameObjects(ArrayList<GameObject> objects) {
        this.map.setObjects(objects);
        this.map.redraw();
    }

    public void setPlayer(Player player) {
        this.map.setPlayer(player);
    }
    
    public void setInvX(int numInvX){
      map.setInvX(numInvX);
    }
    
    public void setInvY(int numInvY){
      map.setInvY(numInvY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

}
