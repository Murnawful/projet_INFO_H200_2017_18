package Moving;

import Model.Game;
import Objects.*;

import java.util.ArrayList;

public abstract class Player extends Character {

    private int posIc[]; // position of the item the Player has in his inventory
    private Weapon weaponEquip = null;
    private InventoryObject objInHand = null;
    private final int numInvY = 2;
    private final int numInvX = 5;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Player(int X, int Y, int life, int maxLife, int strength, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, life, maxLife, strength, inventory, sizeMaxInventory, color, game);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void activate(int points) {}

    @Override
    public void drop(int emp){ // if no input to drop(), the selected item is dropped
        int CoordOjectInHand = this.getItemInHand();
        InventoryObject objInHand = this.getObjectInHand( CoordOjectInHand);
        if(objInHand != null){
            super.drop( CoordOjectInHand);
        }
    }

    public void useItem() {
        int CoordOjectInHand = this.getItemInHand();
        InventoryObject objInHand = this.getObjectInHand( CoordOjectInHand);
        if(objInHand != null){
            if(objInHand == weaponEquip){
                if(inventory.size() != sizeMaxInventory){
                    this.inventory.add(weaponEquip);
                    this.weaponEquip.unequip(this);
                }
            }
            else{
                if(objInHand.use(this)){
                    inventory.remove( CoordOjectInHand);
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

    public void setPosIc(int posIc[]){
        this.posIc = posIc;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setWeaponEquip( Weapon weaponEquip){
        this.weaponEquip = weaponEquip;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int[] getPosIc(){
        return this.posIc;
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

    private int getItemInHand(){ // converts posIC (x,y) coordinates to indices in arrayList that Map can understand
        int  CoordOjectInHand = 0;
        if (posIc[0] != this.sizeMaxInventory/2 + 1){
            CoordOjectInHand = posIc[0] - 1 + (posIc[1]-1)*5;
        }
        else{
            CoordOjectInHand = this.sizeMaxInventory; // position of equipped item
        }
        return  CoordOjectInHand;
    }

    private InventoryObject getObjectInHand(int  CoordOjectInHand){ // returns selected object
        InventoryObject objInHand = null;
        if( CoordOjectInHand < this.inventory.size()){ // if an object is on the selected space, add it
            objInHand = this.inventory.get( CoordOjectInHand);
        } else if( CoordOjectInHand == this.sizeMaxInventory){ // if the object is the equipped weapon
            objInHand = this.weaponEquip;
        }
        return objInHand;
    }
}
