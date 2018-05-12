package Model;

import Controller.Keyboard;
import Model.Moving.*;
import Model.Objects.*;
import View.Window;

import java.util.ArrayList;

public class Game implements DeletableObserver{

    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private ArrayList<MapExit> allExit = new ArrayList<MapExit>();
    private Player player;
    private Window window;
    private int size;
    private int posIc[] = {0,0};
    private MapBuilder mapBuilder;
    private int numInvY = 2;
    private int numInvX = 5;
    private boolean inventoryOn = false;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Game(Window window) {
        this.window = window;
        mapBuilder = new MapBuilder(this, "src/MapFiles/map0.txt");
        mapBuilder.build(); // creates all objects from the specified map file
        /*objects.add(player); // MapBuilder has already set Player, it only needs to be added to the list*/
        posIc[0] = player.getNumInvX()/2 + 1; // sets original inventory position
        posIc[1] = player.getNumInvY()/2 + 1;
        player.setPosIc(posIc);
        window.setInvX(numInvX);
        window.setInvY(numInvY);
        window.setGameObjects(this.getGameObjects()); //links the list of GameObjects of Game to the one in Map
        window.setPlayer(player);
        notifyView();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<characterMethods>

    public void playerPos(int playerNumber) {
        Player player = ((Player) objects.get(playerNumber));
        System.out.println(player.getPosX() + ":" + player.getPosY());
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////<windowMethods>

    public void switchInventoryState() {
      if (inventoryOn){
        inventoryOn = false;
      }
      else{
        inventoryOn = true;
      }
      window.setInventoryState(inventoryOn);
      this.notifyView();
    }

    public void notifyView() {
        window.update();
    }

    public void moveIc(int direction){ // moves icon if Character moves
        switch(direction){
            //Right
            case 0:
                if(posIc[0] < player.getNumInvX() + 1){
                    posIc[0] += 1;
                }
                break;
            //Up
            case 1:
                if(posIc[1] > 1){
                    posIc[1] -= 1;
                }
                break;
            //Left
            case 2:
                if(posIc[0] > 1){
                    posIc[0] -= 1;
                }
                break;
            //Down
            case 3:
                if(posIc[1] < player.getNumInvY()){
                    posIc[1] += 1;
                }
                break;
        }
        window.update();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    synchronized public void delete(Deletable ps) {
        //TODO find a better method
        objects.remove(ps);
        if(ps instanceof Monster){
            ((Monster) ps).dropAll();
            ((Monster) ps).stopThread();
        }else if (ps instanceof Pot){
            ((Pot) ps).dropAll();
        }
        notifyView();
    }

    public void addInventoryObjects(ArrayList<InventoryObject> inventory){
        this.objects.addAll(inventory);
    }

    public void addGameObject(GameObject object){
        this.objects.add(object);
    }

    public void addMapExit(MapExit mapExit){
        this.allExit.add(mapExit);
    }

    public void gameOver(){ // will create a new player and therefore notifies every class that needs it
        System.out.println("Game over");
        player = null;

        initializeMap("src/MapFiles/map0.txt");

        objects.add(player);

        posIc[0] = player.getNumInvX()/2 + 1; // creates a new, empty inventory for the player
        posIc[1] = player.getNumInvY()/2 + 1;
        player.setPosIc(posIc);
        window.setInvX(numInvX);
        window.setInvY(numInvY);
        window.setPlayer(player);
        notifyView();
    }

    public void clean(){ // clears list of GameObject and stops every thread
        for(GameObject object : objects){
            if(object instanceof Monster){
                ((Monster) object).stopThread();
            }
        }
        objects.clear();
    }

    public void changeMap(MapExit mapExit) {
        allExit.clear();
        initializeMap(mapExit.getMapout());
        player.setPosX(mapExit.getPlayerX());
        player.setPosY(mapExit.getPlayerY());
        notifyView();
    }

    private void initializeMap(String mapAdress) { // creates the indicated Map from MapFiles
        mapBuilder.setMapFile(mapAdress);
        this.clean();
        mapBuilder.build(); // a new list of objects is created and notified to game
        window.setGameObjects(this.getGameObjects()); // the list is sent to Window and Map
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setSize(int size){
        this.size = size;
        window.setSize(size);
    }

    public void setGameObjects(ArrayList<GameObject> objects){
        this.objects = objects;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public ArrayList<GameObject> getGameObjects() {
        return this.objects;
    }

    public Player getPlayer(){
        return player;
    }

    public int getSize(){
        return size;
    }

    public ArrayList<MapExit> getAllExit(){
        return this.allExit;
    }
}