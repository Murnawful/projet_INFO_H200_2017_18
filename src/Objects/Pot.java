package Objects;

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
    public void drop() {

    }

    @Override
    public void dropAll() {

    }

    @Override
    public void open(){}

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public ArrayList<InventoryObject> getLoot(){
        return loot;
    }

}
