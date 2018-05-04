package Objects;

import Model.Game;

import java.util.ArrayList;

public class Pot extends BlockBreakable implements Container{

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
        for (int i = 0; i <= loot.size(); i++){
            InventoryObject obj = loot.get(i);
            game.getGameObjects().add(obj);
        }
    }

    @Override
    public void open(){}

}
