package Moving;

import Objects.Consumable;
import Objects.InventoryObject;
import Objects.Weapon;

import java.util.ArrayList;

import Model.Game;

public class Player extends Character {

  //Position de l'item que le joueur posssède dans son inventaire
  private int posIc[];
  private Weapon weaponEquip = null;
  private final int numInvY = 2;
  private final int numInvX = 5;


  ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

  public Player(int X, int Y, int life, int maxLife, int strenght, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
    super(X, Y, life, maxLife, strenght, inventory, sizeMaxInventory, color, game);
  }

  ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

  @Override
  public void activate(int points) {}

  //Si il n'y a pas d'arguments au drop, on drop l'item en main
  public void drop(){
    int CoordOjectInHand = this.getItemInHand();
    InventoryObject objInHand = this.getObjectInHand( CoordOjectInHand);
    if(objInHand != null){
      super.drop( CoordOjectInHand);
    }
  }

  public void useItem() {
    int  CoordOjectInHand = this.getItemInHand();
    InventoryObject objInHand = this.getObjectInHand( CoordOjectInHand);
    if(objInHand != null){
      if(objInHand == weaponEquip){
        if(inventory.size() != sizeMaxInventory){
          this.inventory.add(weaponEquip);
          this.weaponEquip.unequip(this);
        }
      }
      else{
        if(objInHand.use(this) == true){
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

  //permet de passer du language PosIc plus adapté à la map
  //au language CoordOjectInHand qui se réfère directement à sa position dans l'arrayList
  private int getItemInHand(){
    int  CoordOjectInHand = 0;
    if (posIc[0] != this.sizeMaxInventory/2 + 1){
      CoordOjectInHand = posIc[0] - 1 + (posIc[1]-1)*5;
    }
    else{
      CoordOjectInHand = this.sizeMaxInventory; //emplacement de l'objet équipé
    }
    return  CoordOjectInHand;
  }


  //Permet de savoir quel object on a dans la main
  private InventoryObject getObjectInHand(int  CoordOjectInHand){
    InventoryObject objInHand = null;
    //si a l'emplacement se trouve un objet: on l'ajoute
    if( CoordOjectInHand < this.inventory.size()){
      objInHand = this.inventory.get( CoordOjectInHand);
    }
    //si notre object est l'arme équipée
    else if( CoordOjectInHand == this.sizeMaxInventory){
      objInHand = this.weaponEquip;
    }
    return objInHand;
  }


}
