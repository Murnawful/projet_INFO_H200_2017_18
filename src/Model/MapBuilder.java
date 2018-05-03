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
import Objects.Axe;
import Objects.BlockBreakable;
import Objects.BlockInactive;
import Objects.BoostConsumable;
import Objects.GameObject;
import Objects.HealingConsumable;
import Objects.InventoryObject;
import Objects.MapExit;
import Objects.Staff;
import Objects.Sword;
import Objects.Weapon;

public class MapBuilder {
  private String mapFile;
  private Game game;
  private int size;
  private ArrayList<GameObject> objects = new ArrayList<GameObject>();
  private ArrayList<InventoryObject> loot;
  private MapExit mapExit;


  public MapBuilder(Game game, String firstMap){
    this.game = game;
    this.mapFile = firstMap;
    this.objects = game.getGameObjects();
  }

  public void build(){

    objects.clear();
    if(game.getPlayer() != null){
      objects.add(game.getPlayer());
    }
    String text = "";

    try{
      BufferedReader in = new BufferedReader(new FileReader(mapFile));

      text = in.readLine();

      while(text != null){
        text = text.trim();

        if ("size".compareTo(text) == 0){
          size = Integer.valueOf(in.readLine());
        }
        else if("option".compareTo(text) == 0){
          mapOption(in);
        }
        else if("Character(type,x,y,ml,force,smi,c,s, vr, cn)".compareTo(text) == 0){
          mapCharacter(in);
        }
        else if("GameObject(id,x,y)".compareTo(text) == 0){
          mapObject(in);
        }
        text = in.readLine();
      }

      // Creating consumables
      for(int i = 0; i < 10; i++){
        InventoryObject potion = new HealingConsumable(5+i,2,2, "potion1", 1);
        potion.attachDeletable(game);
        objects.add(potion);
      }
      InventoryObject potion2 = new BoostConsumable(6,3,3, "potion2", "strength", 2);
      potion2.attachDeletable(game);
      objects.add(potion2);

      InventoryObject potion3 = new BoostConsumable(7,3,4, "potion2", "life", 2);
      potion3.attachDeletable(game);
      objects.add(potion3);

      InventoryObject staff = new Staff(10,12,5,"staff",3,3,200,game);
      staff.attachDeletable(game);
      objects.add(staff);

      Weapon weapon2 = new Axe(17, 16, 2, "axe", 2, 1, game);
      weapon2.attachDeletable(game);
      objects.add(weapon2);

      ArrayList<InventoryObject> loot = new ArrayList<InventoryObject>();
      loot.add(potion3);

      // Creating monsters /!\ use Monster type as it is Runnable !!
      //Monster monster1 = new Monster(15,15, 5, 5, 5, 750, 6, loot, 3, 6, 2, game);
      //monster1.attachDeletable(game);
      //Thread t1 = new Thread(monster1);
      //t1.start();
      //objects.add(monster1);

    } catch(FileNotFoundException e){
      System.out.println(e);
    } catch( IOException e){
      System.out.println(e);
    }
  }

  //On créé les différents caracters sur la map
  //!!! le player ne sera créé qu'une seule fois
  private void mapCharacter(BufferedReader in) throws IOException{
    String[] text;

    int numChara = Integer.valueOf(in.readLine());
    loot = new ArrayList<InventoryObject>();
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
          int exp = Integer.valueOf(text[8]);
          Player player = new Warrior(x, y, life, maxLife, force, new ArrayList<InventoryObject>(), sizeMaxInventory, color, exp, game);
          game.setPlayer(player);
        }
        else if("Mage".compareTo(Type) == 0 && game.getPlayer() == null){
          int blastRange = Integer.valueOf(text[6]);
          int sizeMaxInventory = Integer.valueOf(text[7]);
          int color = Integer.valueOf(text[8]);
          int exp = Integer.valueOf(text[9]);
          Player player = new Mage(x, y, life, maxLife, force, blastRange, new ArrayList<InventoryObject>(), sizeMaxInventory, color, exp, game);
          game.setPlayer(player);
        }
        else if ("Monster".compareTo(Type) == 0 && game.getPlayer() == null){
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
    String[] text;
    int posX=0;
    int posY=0;
    int posPlayerX=0;
    int posPlayerY=0;
    int numOption = Integer.valueOf(in.readLine());
    for (int i = 0; i < size; i++) {
      objects.add(new BlockInactive(i, 0));
      objects.add(new BlockInactive(0, i));
      objects.add(new BlockInactive(i, size - 1));
      objects.add(new BlockInactive(size - 1, i));
    }
    for(int j = 0; j<numOption ; j++)
    {
      text = in.readLine().split(" ");
      if(text[0].compareTo("exitEast") == 0){
        posX = size-1;
        posY = size/2;
        posPlayerX = 1;
        posPlayerY = posY;
      }
      else if(text[0].compareTo("exitWest") == 0){
        posX = 0;
        posY = size/2;
        posPlayerX = size - 2;
        posPlayerY = posY;
      }
      else if(text[0].compareTo("exitSouth") == 0){
        posX = size/2;
        posY = size-1;
        posPlayerX = posX;
        posPlayerY = 1;
      }
      else if(text[0].compareTo("exitNorth") == 0){
        posX = size/2;
        posY = 0;
        posPlayerX = posX;
        posPlayerY = size - 2;
      }
      for(int i=0;i<objects.size();i++){
        if(objects.get(i).getPosX()== posX){
          if(objects.get(i).getPosY() == posY){
            objects.remove(i);
          }
        }
      }
      this.mapExit = new MapExit(posX,posY, posPlayerX, posPlayerY, "map"+text[1]+".txt");
      game.addMapExit(mapExit);
    }
  }
  public void setMapFile(String mapFile){
    this.mapFile = mapFile;
  }
}
