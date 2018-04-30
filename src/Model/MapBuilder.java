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
    private ArrayList<GameObject> objects;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public MapBuilder(Game game, String firstMap){
        this.game = game;
        this.mapFile = firstMap;
        this.objects = game.getGameObjects();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<Builder>

    public void build(){

        String text = "";

        try{
            BufferedReader in = new BufferedReader(new FileReader(mapFile));

            text = in.readLine();

            while(text != null){
                text = text.trim();

                if ("size".compareTo(text) == 0){
                    size = Integer.valueOf(in.readLine());
                    game.setSize(size);
                } else if("option".compareTo(text) == 0){
                    mapOption(in);
                } else if("Character(type,x,y,ml,force,smi,c,s, vr, cn)".compareTo(text) == 0){
                    mapCharacter(in);
                } else if("GameObject(id,x,y)".compareTo(text) == 0){
                    mapObject(in);
                }
                text = in.readLine();
            }
        } catch(FileNotFoundException e){
            System.out.println(e);
        } catch( IOException e){
            System.out.println(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<mapMethods>

    private void mapCharacter(BufferedReader in) throws IOException{
        String[] text;

        int numChara = Integer.valueOf(in.readLine());
        ArrayList<InventoryObject> loot = new ArrayList<InventoryObject>();
        for(int i=0; i<numChara; i++){
            text = in.readLine().split(" ");
            String Type = text[0];
            if(Type.compareTo("Loot") != 0){
                int x = Integer.valueOf(text[1]);
                int y = Integer.valueOf(text[2]);
                int life = Integer.valueOf(text[3]);
                int maxLife = Integer.valueOf(text[4]);
                int force = Integer.valueOf(text[5]);
                if("Warrior".compareTo(Type) == 0 ){
                    int sizeMaxInventory = Integer.valueOf(text[6]);
                    int color = Integer.valueOf(text[7]);
                    int exp = Integer.valueOf(text[8]);
                    Player player = new Warrior(x, y, life, maxLife, force, new ArrayList<InventoryObject>(), sizeMaxInventory, color, exp, game);
                    game.setPlayer(player);
                }
                else if("Mage".compareTo(Type) == 0 ){
                    int blastRange = Integer.valueOf(text[6]);
                    int sizeMaxInventory = Integer.valueOf(text[7]);
                    int color = Integer.valueOf(text[8]);
                    int exp = Integer.valueOf(text[9]);
                    Player player = new Mage(x, y, life, maxLife, force, blastRange, new ArrayList<InventoryObject>(), sizeMaxInventory, color, exp, game);
                    game.setPlayer(player);
                }
                else if ("Monster".compareTo(Type) == 0 ){
                    // Creating monsters /!\ use Monster type as it is Runnable !!
                    int speed = Integer.valueOf(text[6]);
                    int viewRange = Integer.valueOf(text[7]);
                    int sizeMaxInventory = Integer.valueOf(text[8]);
                    int color = Integer.valueOf(text[9]);
                    Monster monster = new Monster(x, y, life, maxLife, force, speed, viewRange, loot, sizeMaxInventory, color, game);
                    monster.attachDeletable(game);
                    Thread t = new Thread(monster);
                    t.start();
                    objects.add(monster);
                    //on reinitialise le loot
                    loot = null;
                }
            }
            else{
                //Creating the loot for the monster
                text = in.readLine().split(" ");
                GameObject creation = CreateObject(text);
                loot.add( ((InventoryObject)creation) );
                i-=1;
            }
        }
    }

    private void mapObject(BufferedReader in) throws IOException{
        String[] text;
    
        int numObject = Integer.valueOf(in.readLine());
        for(int i=0; i<numObject; i++){
            text = in.readLine().split(" ");
            GameObject creation = CreateObject(text);
            creation.attachDeletable(game);
            objects.add(creation);
        }
    }
  
    private GameObject CreateObject(String[] text){
        int id = Integer.valueOf(text[0]);
        int x = Integer.valueOf(text[1]);
        int y = Integer.valueOf(text[2]);
        GameObject creation = null;

        //BLOCKS
        if(id<100){
            int lifePoint = Integer.valueOf(text[3]);
            switch(id){
                case 001:
                    BlockBreakable block = new BlockBreakable(x, y, lifePoint);
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
                    InventoryObject boostPotion = new BoostConsumable(x, y, color, description, boostType, boostLength);
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
        else{
            int color = Integer.valueOf(text[3]);
            String description = text[4];
            int bonus = Integer.valueOf(text[5]);
            switch(id){
                case 301:
                    int weight = Integer.valueOf(text[6]);
                    InventoryObject axe = new Axe( x, y, color, description, bonus, weight, game);
                    creation = axe;
                    break;
                case 302:
                    int bonusRange = Integer.valueOf(text[6]);
                    int speed = Integer.valueOf(text[7]);
                    InventoryObject staff = new Staff( x, y, color, description, bonus, bonusRange, speed,game);
                    creation = staff;
                    break;
                case 303:
                    Sword sword = new Sword( x, y, color, description, bonus, game);
                    creation = sword;
                    break;
            }
        }
        return creation;
    }

    private void mapOption(BufferedReader in) throws IOException{
        String text;
        text = in.readLine().trim();
        if ("exitEast".compareTo(text) == 0) {
            for (int i = 0; i < size; i++) {
                objects.add(new BlockInactive(i, 0));
                objects.add(new BlockInactive(0, i));
                objects.add(new BlockInactive(i, size - 1));
                if(i!= size/2){
                    objects.add(new BlockInactive(size - 1, i));
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setMapFile(String mapFile){
   this.mapFile = mapFile;
 }
 }
