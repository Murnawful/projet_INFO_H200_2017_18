package Objects;

import Model.Dropper;
import Model.Game;

import java.util.ArrayList;

public class Pot extends BlockBreakable implements Dropper {

    private ArrayList<InventoryObject> loot;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Pot(int X, int Y, ArrayList<InventoryObject> loot){
        super(X,Y,1);
        this.loot = loot;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void drop(int emp) {

    }

    @Override
    public void dropAll(Game game) {
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
