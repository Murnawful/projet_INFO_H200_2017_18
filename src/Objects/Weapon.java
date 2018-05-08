package Objects;

import Moving.*;

import java.util.ArrayList;

public abstract class Weapon extends InventoryObject implements Runnable, Equipable{

    protected int bonus;
    protected ArrayList<GameObject> objects;
    protected Player player;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Weapon(int x, int y, int color, String description, int bonus, ArrayList<GameObject> objects){
        super(x, y, color, description, description + ".png");
        this.bonus = bonus;
        this.objects = objects;
        this.player = extractPlayer(objects);
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

    public abstract void attack(int x, int y);

    @Override
    public boolean isObstacle() {
        return false;
    }

    public Player extractPlayer(ArrayList<GameObject> objects){ // extract Player object from the list of GameObject
        Player p = null;
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i) instanceof Player){
                p = (Player)objects.get(i);
                break;
            }
        }
        return p;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setBonus(int bonus){
        this.bonus = bonus;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getBonus(){
        return bonus;
    }
}
