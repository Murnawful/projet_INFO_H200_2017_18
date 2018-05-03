package Moving;

import Objects.InventoryObject;
import Objects.Weapon;

import java.util.ArrayList;

import Model.Game;

public class Player extends Character {

  private int exp;
  //Position de l'item que le joueur posssﾃｨde dans son inventaire
  private int posIc[];
  private Weapon weaponEquip = null;
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

  //Si il n'y a pas d'arguments au drop, on drop l'item en main
  public void drop(){
    int itemInHand = this.getItemInHand();
    InventoryObject objInHand = this.getObjectInHand(itemInHand);
    if(objInHand != null){
      super.drop(itemInHand);
    }
  }

  public void useItem() {
    int itemInHand = this.getItemInHand();
    InventoryObject objInHand = this.getObjectInHand(itemInHand);
    if(objInHand != null){
      if(objInHand == weaponEquip){
        if(inventory.size() != sizeMaxInventory){
          this.inventory.add(weaponEquip);
          this.weaponEquip.unequip(this);
        }
      }
      else{
        objInHand.use(this);
        inventory.remove(itemInHand);
      }
      game.notifyView();
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

  public void setPosIc(int posIc[]){
    this.posIc = posIc;
  }

  public void setWeaponEquip( Weapon weaponEquip){
    this.weaponEquip = weaponEquip;
  }

  ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

  public int getExp(){
    return exp;
  }

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
  //au language ItemInHand plus adapté pour se promener dans une liste d'inventaire
  private int getItemInHand(){
    int itemInHand = 0;
    if (posIc[0] != this.sizeMaxInventory/2 + 1){
      itemInHand = posIc[0] - 1 + (posIc[1]-1)*5;
    }
    else{
      itemInHand = this.sizeMaxInventory; //emplacement de l'objet ﾃｩquipﾃｩ
    }
    return itemInHand;
  }


  //Permet de savoir quel object on a dans la main
  private InventoryObject getObjectInHand(int itemInHand){
    InventoryObject objInHand = null;
    //si a l'emplacement se trouve un objet: on l'ajoute
    if(itemInHand < this.inventory.size()){
      objInHand = this.inventory.get(itemInHand);
    }
    //si notre object est l'arme équipée
    else if(itemInHand == this.sizeMaxInventory){
      objInHand = this.weaponEquip;
    }
    return objInHand;
  }
}
