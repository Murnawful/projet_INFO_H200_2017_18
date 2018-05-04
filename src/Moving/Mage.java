package Moving;

import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;

public class Mage extends Player {

    private int blastRange;
    private ArrayList<String> blastImage = new ArrayList<String>();

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Mage(int X, int Y, int life, int maxLife, int force, int blastRange, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, int exp, Game game) {
        super(X, Y, life, maxLife, force, inventory, sizeMaxInventory, color, exp, game);
        this.blastRange = blastRange;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setBlastRange(int blastRange){
        this.blastRange = blastRange;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getBlastRange(){
        return blastRange;
    }

    public ArrayList<String> getBlastImage(){
        return blastImage;
    }
}
