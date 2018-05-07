package Objects;

import Model.Game;
import Moving.*;

public abstract class Weapon extends InventoryObject implements Runnable, Equipable{

    protected int bonus;
    protected Game game;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Weapon(int x, int y, int color, String description, int bonus, Game game){
        super(x, y, color, description, description + ".png");
        this.bonus = bonus;
        this.game = game;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public abstract boolean equip(Player p);

    @Override
    public void unequip(Player p){
        p.setInventory(p.getWeaponEquip());
        p.getWeaponEquip().setInInventory();
        p.setStrength(p.getStrength() - bonus);
        p.setWeaponEquip(null);
    }

    public abstract void attack(int x, int y, int force, Game game);

    @Override
    public boolean isObstacle() {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setBonus(int bonus){
        this.bonus = bonus;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getBonus(){
        return bonus;
    }
}
