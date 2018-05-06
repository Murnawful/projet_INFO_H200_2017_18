package Objects;

import Model.Directable;
import Model.Dropper;
import Model.Game;
import Moving.Player;

import java.util.ArrayList;

public class Chest extends GameObject implements Dropper, Directable {

    private ArrayList<InventoryObject> loot;
    private Game game;
    private int direction = 1;
    private boolean opened = false;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Chest(int X, int Y, int color, ArrayList<InventoryObject> loot, Game game) {
        super(X, Y, color);
        this.loot = loot;
        this.game = game;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void activate(int points){
        open();
        game.notifyView();
    }

    @Override
    public void drop() {
        Player p = game.getPlayer();
        for (int i = 0; i <= loot.size() - 1; i++){
            InventoryObject obj = loot.get(i);
            if (p.getSizeInventory() + 1 <= p.getSizeMaxInventory()){
                p.setInventory(loot.get(i));
                obj.isInInventory();
            }else{
                obj.setPosX(p.getPosX());
                obj.setPosY(p.getPosY());
                game.getGameObjects().add(obj);
            }
        }
    }

    @Override
    public void dropAll(){}

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public void open(){
        if (!opened){
            this.opened = true;
            drop();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public ArrayList<InventoryObject> getLoot(){
        return loot;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    public int getFrontX() {
        int delta = 0;
        if (direction % 2 == 0){
            delta += 1 - direction;
        }
        return this.posX + delta;
    }

    public int getFrontY() {
        int delta = 0;
        if (direction % 2 != 0){
            delta += direction - 2;
        }
        return this.posY + delta;
    }
}