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
    public void drop(int emp) {
        Player p = game.getPlayer();
        int lootSize = loot.size();
        for (int i = 0; i < lootSize; i++){
            InventoryObject obj = loot.get(0);
            if (p.getSizeInventory() + 1 <= p.getSizeMaxInventory()){
                p.addInventory(loot.get(0));
            }else{
                dropAll();
                break;
            }
            loot.remove(0);
        }
    }

    @Override
    public void dropAll(){
        for(InventoryObject elem : loot){
            elem.setPosX(posX + (-1)*(direction-1));
            elem.setPosY(posY + (direction-2));
            elem.attachDeletable(game);
        }
        game.getGameObjects().addAll(loot);
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public void open(){
        if (!opened){
            this.opened = true;
            drop(0);
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
