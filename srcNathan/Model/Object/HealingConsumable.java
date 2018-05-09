package Model.Object;

import Model.Deletable;
import Model.Moving.Player;

public class HealingConsumable extends InventoryObject implements Consumable {

    private int healingPower;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public HealingConsumable(int X, int Y, int color, String description, int healingPower) {
        super(X, Y, color, description, "src/Images/healingPotion.png");
        this.healingPower = healingPower;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void consume(Player p) {
        p.modifyLife(healingPower);
    }


    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setHealingPower(int healingPower){
        this.healingPower = healingPower;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getHealingPower(){
        return healingPower;
    }
}
