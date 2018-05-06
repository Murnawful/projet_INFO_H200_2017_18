package Moving;

import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;

public class Mage extends Player {

    private int blastRange;
    private ArrayList<String> blastImage = new ArrayList<String>();

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Mage(int X, int Y, int life, int maxLife, int force, int blastRange, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, life, maxLife, force, inventory, sizeMaxInventory, color, game);
        this.blastRange = blastRange;
        setBlastImage(blastRange);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setBlastRange(int blastRange){
        this.blastRange = blastRange;
    }

    public void setBlastImage(int blastRange){
        for(int i = 0; i <= blastRange; i++){
            blastImage.add("blast.png");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getBlastRange(){
        return blastRange;
    }

    public ArrayList<String> getBlastImage(){
        return blastImage;
    }
}
