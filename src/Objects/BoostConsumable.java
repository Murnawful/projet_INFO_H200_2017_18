package Objects;

import Model.Deletable;
import Moving.Player;

public class BoostConsumable extends InventoryObject implements Consumable, Deletable {

    private String boostType;
    private int boostLength;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public BoostConsumable(int X, int Y, int color, String description, String boostType, int boostLength) {
        super(X, Y, color, description, "boostPotion.png");
        this.boostType = boostType;
        this.boostLength = boostLength;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods

    @Override
    public void consume(Player p) {
        if(boostType.compareTo("strength") == 0){
            p.setForce(p.getForce() + 1);
        }else if(boostType.compareTo("life") == 0){
            p.modififyMaxLife(1);
        }
        
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setBoostType(String boostType){
        this.boostType = boostType;
    }

    public void setBoostLength(int boostLength){
        this.boostLength = boostLength;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public String getBoostType(){
        return boostType;
    }

    public int getBoostLength() {
        return boostLength;
    }
}
