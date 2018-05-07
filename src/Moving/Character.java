package Moving;

import Model.*;
import Objects.*;

import java.util.ArrayList;

public abstract class Character extends GameObject implements Directable, Deletable, Dropper {

    protected int life;
    private int direction;
    protected int strength;
    protected ArrayList<InventoryObject> inventory;
    protected int sizeMaxInventory;
    private int maxLife;
    protected Game game;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Character(int X, int Y, int life, int maxLife, int force, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, color);
        this.life = life;
        this.maxLife = maxLife;
        this.strength = force;
        this.inventory = inventory;
        this.sizeMaxInventory = sizeMaxInventory;
        this.game = game;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<movingMethods>

    synchronized public void moveCharacter(int x, int y) {
        int nextX = this.getPosX() + x;
        int nextY = this.getPosY() + y;

        boolean obstacle = false;
        for (GameObject object : game.getGameObjects()) {
            if (object.isAtPosition(nextX, nextY)) {
                obstacle = object.isObstacle();
            }
            if (obstacle) {
                break;
            }
        }
        this.rotate(x, y);
        if (! obstacle) {
            move(x, y);
        }
        MapExit exitUse = null; // evaluates of map is exited by Character
        ArrayList<MapExit> allExit = game.getAllExit();
        for (MapExit mapExit : allExit){
            if(mapExit.getX() == this.posX){
                if(mapExit.getY() == this.posY){
                    exitUse = mapExit;
                    break;
                }
            }
        }
        if(exitUse!=null){
            game.changeMap(exitUse);
        }
        game.notifyView();
    }

    private void move(int X, int Y) {
        this.posX = this.posX + X;
        this.posY = this.posY + Y;
    }

    private void rotate(int x, int y) {
        if(x == 0 && y == -1)
            direction = NORTH;
        else if(x == 0 && y == 1)
            direction = SOUTH;
        else if(x == 1 && y == 0)
            direction = EAST;
        else if(x == -1 && y == 0)
            direction = WEST;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public void drop(int empInt){ // pulls item out of the inventory and places it on Map
        InventoryObject drop = inventory.get(empInt);
        drop.setPosX(posX);
        drop.setPosY(posY);
        game.addGameObject(drop);
        inventory.remove(empInt);
        game.notifyView();
    }

    @Override
    public void dropAll(){ // pulls every item out of the inventory and places it on Map
        for(InventoryObject elem : inventory){
            elem.setPosX(posX);
            elem.setPosY(posY);
            elem.attachDeletable(game);
        }
        game.addInventoryObjects(inventory);
        inventory = new ArrayList<InventoryObject>();
        game.notifyView();
    }

    public void modifyLife(int change) {
        if(getLife() + change <= 0){
            if(this instanceof Monster){
                notifyDeletableObserver();
            }else {
                game.gameOver(); // Player is dead
            }
        }else if (getLife() + change <= getMaxLife()) {
            setLife(getLife() + change);
        }else if(getLife() + change == getMaxLife()){
            setLife(getMaxLife());
            System.out.println("Points de vie au maximum !");
        }
    }

    public void modififyMaxLife(int change){
        this.maxLife += change;
    }

    public void action() {
        Activable aimedObject = null;
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(getFrontX(),getFrontY())){
                aimedObject =  object;
            }
        }
        if(aimedObject != null){
            if(aimedObject instanceof InventoryObject){
                pickUp(aimedObject);
            }else if(aimedObject instanceof Chest){
                Chest chest = (Chest) aimedObject;
                int dir = chest.getDirection();
                if((dir == NORTH && direction == SOUTH) || (dir == SOUTH && direction == NORTH) || (dir == EAST && direction == WEST) || (dir == WEST && direction == EAST)){
                    chest.activate(0);
                }
            }
        }

    }

    private void pickUp(Activable aimedObject){
        if(inventory.size() < sizeMaxInventory){
            setInventory((InventoryObject) aimedObject);
            ((InventoryObject) aimedObject).setInInventory();
            aimedObject.activate(0);
            game.notifyView();
        }else{
            System.out.println("Inventaire plein !");
        }
    }

    public void attack(){
        GameObject o = null;
        Player p = game.getPlayer();
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(getFrontX(),getFrontY())){
                o =  object;
                break;
            }
        }
        if (o instanceof Character){
            Character aimedObject = (Character) o;
            aimedObject.modifyLife(-strength);
        }else if(o instanceof BlockBreakable){
            BlockBreakable aimedObject = (BlockBreakable) o;
            aimedObject.activate(p.getStrength());
            game.notifyView();
        }
    }

    @Override
    public void open(){}

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setLife(int life){
        this.life = life;
    }

    public void setMaxLife(int maxLife){
        this.maxLife = maxLife;
    }

    public void setSizeMaxInventory(int sizeMaxInventory) {
        this.sizeMaxInventory = sizeMaxInventory;
    }

    public void setInventory(InventoryObject object){
        inventory.add(object);
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    @Override
    public int getDirection() {
        return direction;
    }

    public int getFrontX() {
        int delta = 0;
        if (direction % 2 == 0){
            delta += 1 - direction;
        }
        return this.posX + delta;
    }

    public int getFrontY() {
        int delta = 0;
        if (direction % 2 != 0){
            delta += direction - 2;
        }
        return this.posY + delta;
    }

    public int getLife(){
        return life;
    }

    public int getStrength(){
        return strength;
    }

    public int getSizeInventory(){
        return inventory.size();
    }

    public int getSizeMaxInventory(){
        return sizeMaxInventory;
    }

    public int getMaxLife(){
        return maxLife;
    }

    public ArrayList<InventoryObject> getInventory(){
        return inventory;
    }
}
