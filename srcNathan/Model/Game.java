package Model;

import Moving.*;
import Moving.Character;
import Objects.*;
import View.Window;
import java.io.*;
import java.util.ArrayList;

import Controller.Keyboard;

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
    private Keyboard keyboard;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Game(Window window) {
        this.window = window;
        mapBuilder = new MapBuilder(this, "src/MapFiles/map0.txt");
        mapBuilder.build();
        objects.add(player);
        posIc[0] = player.getNumInvX()/2 + 1;
        posIc[1] = player.getNumInvY()/2 + 1;
        player.setPosIc(posIc);
        window.setInvX(numInvX);
        window.setInvY(numInvY);
        window.setGameObjects(this.getGameObjects()); //lie la liste GameObjects de Map et celle de Game
        window.setPlayer(player);
        notifyView();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<characterMethods>

    public void playerPos(int playerNumber) {
        Player player = ((Player) objects.get(playerNumber));
        System.out.println(player.getPosX() + ":" + player.getPosY());

    }
    
    public void swingAxe(){
      window.swingAxe();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<windowMethods>

    public void setInventoryState(boolean inventoryState){
        window.setInventoryState(inventoryState);
    }

    public void notifyView() {
        window.update();
    }

    //on dﾃｩplace l'icone sﾃｩlectionnﾃｩe
    public void moveIc(int direction){
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

    synchronized public void delete(Deletable ps) {
        objects.remove(ps);
        if( ps instanceof Monster ){
          ((Monster) ps).dropAll();
          ((Monster) ps).stopThread();
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
    //on créé un nouveau player donc il faut l'annoncer à toutes les classes l'utilisant
    public void gameOver(){
      player = null;
      
      initializeMap("src/MapFiles/map0.txt");
      
      objects.add(player);
      //on recréé aussi son inventaire
      posIc[0] = player.getNumInvX()/2 + 1;
      posIc[1] = player.getNumInvY()/2 + 1;
      player.setPosIc(posIc);
      window.setInvX(numInvX);
      window.setInvY(numInvY);
      keyboard.setPlayer(player);
      window.setPlayer(player);
      notifyView();
    }
    
    //nettoye la map de tout ses objects et supprime les Threads
    public void clean(){
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
      this.notifyView();
    }

    //créer la carte indiqué
    private void initializeMap(String mapAdress) {
      mapBuilder.setMapFile(mapAdress);
      this.clean();
      mapBuilder.build();
      //En recréant une nouvelle map on créé une nouvelle liste de GameObjects
      //Il faut prévenir la Map
      window.setGameObjects(this.getGameObjects());
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>
    
    public void setSize(int size){
      this.size = size;
      window.setSize(size);
    }
    
    public void setPlayer(Player player){
      this.player = player;
    }
    
    public void setGameObjects(ArrayList<GameObject> objects){
      this.objects = objects;
    }
    
    public void setKeyboard(Keyboard keyboard){
      this.keyboard = keyboard;
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