package Model.Objects;

import Model.Dropper;
import Model.Game;

import java.util.ArrayList;

public class Pot extends BlockBreakable implements Dropper {

    private ArrayList<InventoryObject> loot;
    private Game game;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Pot(int X, int Y, ArrayList<InventoryObject> loot, Game game){
        super(X,Y,1);
        this.loot = loot;
        this.game = game;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void drop(int emp) {}

    @Override
    public void dropAll() {
        for(InventoryObject elem : loot){
            elem.setPosX(posX);
            elem.setPosY(posY);
            elem.attachDeletable(game);
        }
        game.getGameObjects().addAll(loot);
    }

    @Override
    public void open(){}

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public ArrayList<InventoryObject> getLoot(){
        return loot;
    }

}
