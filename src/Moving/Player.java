package Moving;

import Model.Game;
import Objects.*;

import java.util.ArrayList;

public abstract class Player extends Character {

    protected int exp;
    //Position de l'item que le joueur posssède dans son inventaire
    protected int itemInHand[];
    protected Weapon weaponEquip = null;
    protected InventoryObject objInHand = null;
    private final int numInvY = 2;
    private final int numInvX = 5;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Player(int X, int Y, int life, int maxLife, int force, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, int exp, Game game) {
        super(X, Y, life, maxLife, force, inventory, sizeMaxInventory, color, game);
        this.exp = exp;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void activate(int points) {}

    public void useItem() {
        int empItem;
        if (itemInHand[0] != this.sizeMaxInventory/2 + 1){
            empItem = itemInHand[0] - 1 + (itemInHand[1]-1)*5;
        }
        else{
            empItem = 1000; //emplacement de l'objet ﾃｩquipﾃｩ
        }
        if( empItem == 1000 && weaponEquip != null){
            if(inventory.size() != sizeMaxInventory){
                this.inventory.add(weaponEquip);
                this.weaponEquip.unequip(this);
            }
        }
        else if(inventory.size() > empItem){
            objInHand = inventory.get(empItem);
            if (objInHand instanceof Consumable) {
                ((Consumable)objInHand).consume(this);
                inventory.remove(empItem);
            }
            else if (objInHand instanceof Weapon) {
                if( ((Weapon)objInHand).equip(this)){
                    inventory.remove(empItem);
                }
            }
        }
        game.notifyView();
    }

    public void attack(){
        Thread weaponThread = new Thread(weaponEquip);
        weaponThread.start();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setForce(int force){
        this.force = force;
    }

    public void setExp(int exp){
        this.exp = exp;
    }

    public void setItemInHand(int itemInHand[]){
        this.itemInHand = itemInHand;
    }

    public void setWeaponEquip( Weapon weaponEquip){
        this.weaponEquip = weaponEquip;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getExp(){
        return exp;
    }

    public int[] getItemInHand(){
        return this.itemInHand;
    }

    public Weapon getWeaponEquip(){
        return this.weaponEquip;
    }

    public int getNumInvX(){
        return this.numInvX;
    }

    public int getNumInvY(){
        return this.numInvY;
    }
}
