package View;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import Moving.Player;
import Objects.GameObject;

public class Window {

    private Map map;
    private Dimension dim;

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

    public void swingAxe(){
        map.swingAxe();
        this.map.redraw();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setInventoryState(boolean inventoryState){
        map.setInventoryState(inventoryState);
        this.map.redraw();
    }

    public void setSize(int size){
        this.map.setSize(size);
    }

    public void setKeyListener(KeyListener keyboard) {
        this.map.addKeyListener(keyboard);
    }

    public void setGameObjects(ArrayList<GameObject> objects) {
        this.map.setObjects(objects);
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
