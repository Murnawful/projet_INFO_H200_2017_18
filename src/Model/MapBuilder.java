package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Moving.Mage;
import Moving.Monster;
import Moving.Player;
import Moving.Warrior;
import Objects.*;

public class MapBuilder {

    private String mapFile;
    private Game game;
    private int size;
    private Player player = null;
    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<InventoryObject> loot;
    private MapExit mapExit;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public MapBuilder(Game game, String firstMap){
        this.game = game;
        this.mapFile = firstMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<Builder>

    public void build(){

        String text = "";

        try{
            BufferedReader in = new BufferedReader(new FileReader(mapFile));

            text = in.readLine();
            if(game.getPlayer() != null){ //Sets back the player to initial position if clean() method has cleared it from Game
                objects.add(game.getPlayer());
            }
            while(text != null){
                text = text.trim();

                if ("size".compareTo(text) == 0){
                    size = Integer.valueOf(in.readLine());
                    game.setSize(size); // notifies Game on the size of the map
                } else if("option".compareTo(text) == 0){
                    mapOption(in);
                } else if("Character(type,x,y,ml,force,smi,c,s, vr, cn)".compareTo(text) == 0){
                    mapCharacter(in);
                } else if("GameObject(id,x,y)".compareTo(text) == 0){
                    mapObject(in);
                }
                text = in.readLine();
            }
            in.close();
        } catch(FileNotFoundException e){
            System.out.println(e);
        } catch( IOException e){
            System.out.println(e);
        }
        game.setGameObjects(objects); // returns the list of objects to Game
        objects = new ArrayList<>(); // empties the list
    }

    ////////////////////////////////////////////////////////////////////////////////////////<mapMethods>

    private void mapCharacter(BufferedReader in) throws IOException{ //Creates Character objects from the file. Player is only created once
        String[] text;

        int numChara = Integer.valueOf(in.readLine());
        loot = new ArrayList<>();
        for(int i=0; i<numChara; i++){
            text = in.readLine().split(" ");
            String Type = text[0];
            if(Type.compareTo("Loot") != 0){
                int x = Integer.valueOf(text[1]);
                int y = Integer.valueOf(text[2]);
                int life = Integer.valueOf(text[3]);
                int maxLife = Integer.valueOf(text[4]);
                int force = Integer.valueOf(text[5]);
                if("Warrior".compareTo(Type) == 0 && game.getPlayer() == null){
                    int sizeMaxInventory = Integer.valueOf(text[6]);
                    int color = Integer.valueOf(text[7]);
                    Player player = new Warrior(x, y, life, maxLife, force, new ArrayList<InventoryObject>(), sizeMaxInventory, color, game);
                    this.player = player; // stores the player as it needs to be added to the list of object to ensure compatibility with weapons
                    objects.add(player); // adds the player to the list to ensure compatibility
                    game.setPlayer(player); // goes directly to Game to ensure distinction
                } else if("Mage".compareTo(Type) == 0 && game.getPlayer() == null){
                    int blastRange = Integer.valueOf(text[6]);
                    int sizeMaxInventory = Integer.valueOf(text[7]);
                    int color = Integer.valueOf(text[8]);
                    Player player = new Mage(x, y, life, maxLife, force, blastRange, new ArrayList<InventoryObject>(), sizeMaxInventory, color, game);
                    this.player = player; // stores the player as it needs to be added to the list of object to ensure compatibility with weapons
                    objects.add(player); // adds the player to the list to ensure compatibility
                    game.setPlayer(player); // goes directly to Game to ensure distinction
                } else if ("Monster".compareTo(Type) == 0){ // Creating monsters /!\ use Monster type as it is Runnable !!
                    int speed = Integer.valueOf(text[6]);
                    int viewRange = Integer.valueOf(text[7]);
                    int sizeMaxInventory = Integer.valueOf(text[8]);
                    int color = Integer.valueOf(text[9]);
                    Monster monster = new Monster(x, y, life, maxLife, force, speed, viewRange, loot, sizeMaxInventory, color, game);
                    Thread t = new Thread(monster);
                    t.start();
                    monster.attachDeletable(game);
                    objects.add(monster);
                    loot = new ArrayList<InventoryObject>(); //reinitializing loot
                }
            } else{ //Creating loot for monsters; WARNING: Loot objects do not count in number of GameObjects (thus i-=1) !!!
                text = in.readLine().split(" ");
                GameObject creation = CreateObject(text, null);
                loot.add( ((InventoryObject)creation) );
                i-=1;
            }
        }
    }

    private void mapObject(BufferedReader in) throws IOException{
        String[] text;
    
        int numObject = Integer.valueOf(in.readLine());
        loot = new ArrayList<InventoryObject>();

        for(int i=0; i<numObject; i++){

            text = in.readLine().split(" ");
            String Type = text[0];

            if(Type.compareTo("Loot") != 0){
                GameObject creation = CreateObject(text,loot);
                creation.attachDeletable(game);
                objects.add(creation);
                loot = new ArrayList<>(); //reinitializing loot
            } else{ //Creating loot for the container; WARNING: Loot objects do not count in number of GameObjects (thus i-=1) !!!
                text = in.readLine().split(" ");
                GameObject creation = CreateObject(text,null);
                loot.add( ((InventoryObject)creation) );
                i-=1;
            }
        }
    }
  
    private GameObject CreateObject(String[] text, ArrayList<InventoryObject> loot){

        int id = Integer.valueOf(text[0]);
        int x = Integer.valueOf(text[1]);
        int y = Integer.valueOf(text[2]);
        GameObject creation = null; // what will be added to the ArrayList objects

        //BLOCKS
        if(id<100){
            switch(id) {
                case 001:
                    int lifePoint = Integer.valueOf(text[3]);
                    Block block = new BlockBreakable(x, y, lifePoint);
                    block.attachDeletable(game);
                    creation = block;
                    break;
            }
        }
        //POTIONS
        else if(id<200){
            int color = Integer.valueOf(text[3]);
            String description = text[4];
            switch(id){
                case 101:
                    String boostType = text[5];
                    int boostLength = Integer.valueOf(text[6]);
                    InventoryObject boostPotion = new BoostConsumable(x, y, color, description, boostType, boostLength,game);
                    creation = boostPotion;
                    break;
                case 102:
                    int healingPower = Integer.valueOf(text[5]);
                    InventoryObject lifePotion = new HealingConsumable(x, y, color, description, healingPower);
                    creation = lifePotion;
                    break;
            }
        }
        //WEAPONS
        else if(id<400){
            int color = Integer.valueOf(text[3]);
            String description = text[4];
            int bonus = Integer.valueOf(text[5]);
            switch(id){
                case 301:
                    int weight = Integer.valueOf(text[6]);
                    InventoryObject axe = new Axe( x, y, color, description, bonus, weight,game);
                    creation = axe;
                    break;
                case 302:
                    int bonusRange = Integer.valueOf(text[6]);
                    int speed = Integer.valueOf(text[7]);
                    InventoryObject staff = new Staff( x, y, color, description, bonus, bonusRange, speed,game);
                    creation = staff;
                    break;
                case 303:
                    InventoryObject sword = new Sword( x, y, color, description, bonus, game);
                    creation = sword;
                    break;
            }
        }
        //CONTAINER
        else{
            switch(id) {
                case 401:
                    Block pot = new Pot(x, y, loot, game);
                    pot.attachDeletable(game);
                    creation = pot;
                    break;
                case 402:
                    GameObject chest = new Chest(x, y, 1, loot, game);
                    creation = chest;
                    break;
            }

        }
        return creation;
    }

    private void mapOption(BufferedReader in) throws IOException{
        String[] text;
        int posX=0;
        int posY=0;
        int posPlayerX=0;
        int posPlayerY=0;
        int numOption = Integer.valueOf(in.readLine());
        for (int i = 0; i < size; i++) { // constructs borders of the map
            objects.add(new BlockInactive(i, 0));
            objects.add(new BlockInactive(0, i));
            objects.add(new BlockInactive(i, size - 1));
            objects.add(new BlockInactive(size - 1, i));
        }
        for(int j = 0; j<numOption ; j++)
        {
            text = in.readLine().split(" ");
            String fileNewMap = "src/MapFiles/map"+text[1]+".txt"; // fetches the number of the map that will be loaded
            int sizeNewExit = getSizeMap(fileNewMap);
            if(text[0].compareTo("exitEast") == 0){
                posX = size-1;
                posY = size/2;
                posPlayerX = 1;
                posPlayerY = sizeNewExit/2;
            }
            else if(text[0].compareTo("exitWest") == 0){
                posX = 0;
                posY = size/2;
                posPlayerX = sizeNewExit - 2;
                posPlayerY = sizeNewExit/2;
            }
            else if(text[0].compareTo("exitSouth") == 0){
                posX = size/2;
                posY = size-1;
                posPlayerX = sizeNewExit/2;
                posPlayerY = 1;
            }
            else if(text[0].compareTo("exitNorth") == 0){
                posX = size/2;
                posY = 0;
                posPlayerX = sizeNewExit/2;
                posPlayerY = sizeNewExit - 2;
            }
            for(int i=0;i<objects.size();i++){ // scans the list of objects and removes anything that stands on the exit
                if(objects.get(i).getPosX()== posX){
                    if(objects.get(i).getPosY() == posY){
                        objects.remove(i);
                    }
                }
            }
            this.mapExit = new MapExit(posX,posY, posPlayerX, posPlayerY, fileNewMap);
            game.addMapExit(mapExit); // notifies Game on the presence of an exit on the map
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setMapFile(String mapFile){
   this.mapFile = mapFile;
 }

    public void setPlayer(Player player){
        this.player = player;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    private int getSizeMap(String mapFile) { // scans a map file to find the size
        String text = "";
        int mapSize = 0;

        BufferedReader in2;
        try {
            in2 = new BufferedReader(new FileReader(mapFile));

            while(text != null){
                text = in2.readLine();
                text = text.trim();
                if ("size".compareTo(text) == 0){
                    mapSize = Integer.valueOf(in2.readLine());
                    break;
                }
            }
            in2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapSize;
    }
}
