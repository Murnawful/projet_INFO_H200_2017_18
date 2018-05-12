package View;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import Controller.Keyboard;
import Model.Moving.Player;
import Model.Objects.GameObject;

public class Window {

    private Map map;
    private Dimension dim;
    private Keyboard keyboard;
    boolean KeyboardSet = false;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Window() {
        JFrame window = new JFrame("Stupid Adventure");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1000, 1020);
        window.getContentPane().setBackground(Color.gray);
        map = new Map();
        window.getContentPane().add(this.map);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    public void update() {
        this.map.redraw();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setInventoryState(boolean inventoryState){
        map.setInventoryState(inventoryState);
        keyboard.setInventoryState(inventoryState);
    }

    public void setSize(int size){
        this.map.setSize(size);
    }

    public void setKeyListener(KeyListener keyboard) {
        this.map.addKeyListener(keyboard);
        this.keyboard = (Keyboard) keyboard;
        this.KeyboardSet = true; //The keyboard is initialiazed
    }

    public void setGameObjects(ArrayList<GameObject> objects) {
        this.map.setObjects(objects);
    }

    public void setPlayer(Player player) {
      this.map.setPlayer(player);
      if(this.KeyboardSet){
        this.keyboard.setPlayer(player);
      }
   }

    public void setInvX(int numInvX){
        map.setInvX(numInvX);
    }

    public void setInvY(int numInvY){
        map.setInvY(numInvY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>
}
