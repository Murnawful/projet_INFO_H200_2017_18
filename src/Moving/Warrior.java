package Moving;

import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;

public class Warrior extends Player{

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Warrior(int X, int Y, int life, int maxLife, int force, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, int exp, Game game) {
        super(X, Y, life, maxLife, force, inventory, sizeMaxInventory, color, exp, game);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>
}
