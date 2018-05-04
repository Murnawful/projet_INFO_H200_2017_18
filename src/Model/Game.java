package Model;

import Moving.*;
import Objects.*;
import View.Window;

import java.util.ArrayList;

public class Game implements DeletableObserver{

    private ArrayList<GameObject> objects = new ArrayList<>();
    private Player player;
    private Window window;
    private int size;
    private int posIc[] = {0,0};
    private MapBuilder mapBuilder;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Game(Window window) {
        this.window = window;

        mapBuilder = new MapBuilder(this, "map0.txt");
        mapBuilder.build();
        objects.add(player);

        posIc[0] = player.getNumInvX()/2 + 1;
        posIc[1] = player.getNumInvY()/2 + 1;
        player.setItemInHand(posIc);
        window.setGameObjects(this.getGameObjects()); //lie la liste GameObjects de Map et celle de Game
        window.setPlayer(player);
        window.setSize(size);
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

    @Override
    synchronized public void delete(Deletable ps) {
        objects.remove(ps);
        ArrayList<InventoryObject> loot = null;
        if(ps instanceof Monster){
            loot = ((Monster)ps).getInventory();
            for(InventoryObject elem : loot){
                elem.setPosX(((Monster) ps).getPosX());
                elem.setPosY(((Monster) ps).getPosY());
                elem.attachDeletable(this);
            }
            this.objects.addAll(loot);
        }
        notifyView();
    }

    public void gameOver(){}

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setSize(int size){
        this.size = size;
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
}