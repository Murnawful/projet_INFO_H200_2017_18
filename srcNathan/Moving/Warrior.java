package Moving;

import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;

public class Warrior extends Player{

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Warrior(int X, int Y, int life, int maxLife, int strength, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, life, maxLife, strength, inventory, sizeMaxInventory, color, game);
    }


}
