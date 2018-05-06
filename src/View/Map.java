package View;

import Model.Directable;
import Moving.Character;
import Moving.Mage;
import Moving.Player;
import Objects.GameObject;
import Objects.InventoryObject;
import Objects.Weapon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Map extends JPanel {

  ///////////////////////////////////////////////////////////////////////////////////////<Contenu>
  /*Attributs servant à obtenir des informations sur le contenu de la map*/
  private static final long serialVersionUID = 1L;
  private ArrayList<GameObject> objects = null;
  //liste des objects qui seront peind en dernier sur la map
  private ArrayList<GameObject> objectsPaintLater = null;
  private ArrayList<InventoryObject> inventory = null;
  private Player player;
  private boolean inventoryState;
  
  private boolean axeSwung = false;
  private int size;
  //vaut un si oui 0 sinon
  int addBlastRange = 0;
  
  ///////////////////////////////////////////////////////////////////////////////////////<Inventaire>
  /*Attributs donnant des informations sur l'inventaire(nombre d'elements, dimension, taille des lettres,...)*/
  private int numInvY = 0;
  private int numInvX = 0;
  //taille de l'icone
  private int side = 0;
  //Nombre d'ﾃｩlﾃｩments dans l'inventaire
  private int inventoryEmp; 
  private Font font;
  //si posIc = {numInvY, numInvX+1} on est ds la selection d'arme
  private int posIc[]= {numInvY/2 + 1,numInvX/2 + 1};

  ///////////////////////////////////////////////////////////////////////////////////////<Dimsension globale>
  /*Attribut concernant aux dimensions de la map et de ses éléments*/
  private final int width = 30; // original values for width and height: 48 and for xy multiplier: 50
  private final int height = 30;
  private final int xMulti = 32;
  private final int yMulti = 32;
  private int invWidth = 0;
  private int invHeight = 0;
  private int mapWidth = 0;
  private int mapHeight = 0;

  ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

  public Map() {
    this.setFocusable(true);
    this.requestFocusInWindow();
    posIc[0] = numInvX/2 + 1;
    posIc[1] = numInvY/2 + 1;
    this.objectsPaintLater = new ArrayList<GameObject>();
    //charge toutes les images qui seront dessiné dans la map
  }

  ////////////////////////////////////////////////////////////////////////////////////////<paintMethods>
  
  public void paint(Graphics g) {
    //on vide notre liste d'objects à dessiner au dessus des autres
    if(this.objectsPaintLater != null){
      this.objectsPaintLater.clear();
    }
    g.setFont(font);
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight() );
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int x = i;
        int y = j;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x * xMulti, y * yMulti, width, height); //dessine l'interieur( commnence en haut à droite )
        g.setColor(Color.BLACK);
        g.drawRect(x * xMulti, y * yMulti, width, height); //dessine le contour( commnence en haut à droite )
      }
    }
    
    for (GameObject object : this.objects) {
      if(object instanceof Character){
        this.objectsPaintLater.add(object);
      }
      else{
        paintObject(g, object);
      }   
    }
    for(GameObject object : this.objectsPaintLater){
      paintObject(g, object);
    }
    
    if( getInventoryState()){
      paintInventory(g);
    }
    if(axeSwung){
      paintAxe(g);
    }
  }

  private void paintObject(Graphics g, GameObject object) {
    int x = object.getPosX();
    int y = object.getPosY();
    if(object instanceof InventoryObject){
     
    }
    int color = object.getColor();

    if (color == 0) {
      g.setColor(Color.DARK_GRAY);
    } else if (color == 1) {
      g.setColor(Color.GRAY);
    } else if (color == 2) {
      g.setColor(Color.BLUE);
    } else if (color == 3) {
      g.setColor(Color.GREEN);
    } else if (color == 4) {
      g.setColor(Color.RED);
    } else if (color == 5) {
      g.setColor(Color.ORANGE);
    }

    g.fillRect(x * xMulti, y * yMulti, width, height);
    g.setColor(Color.BLACK);
    g.drawRect(x * xMulti, y * yMulti, width, height);
    if(object instanceof Directable && !(object instanceof InventoryObject)) {
      paintLine(g, object,x,y);
    }
  }

  public void paintLine(Graphics g, GameObject object, int x, int y){
    int direction = ((Directable) object).getDirection();

    int deltaX = 0;
    int deltaY = 0;

    switch (direction) {
    case Directable.EAST:
      deltaX = +width/2;
      break;
    case Directable.NORTH:
      deltaY = -height/2;
      break;
    case Directable.WEST:
      deltaX = -width/2;
      break;
    case Directable.SOUTH:
      deltaY = height/2;
      break;
    }

    int xCenter = x * xMulti + width/2;
    int yCenter = y * yMulti + height/2;
    g.drawLine(xCenter, yCenter, xCenter + deltaX, yCenter + deltaY); // paint line on character
  }
  
  private void paintInventory(Graphics g){
    //on charge l'image du fond de l'intentaire
    this.InventoryLeft(g);
    this.InventoryRight(g);
  }

  private void InventoryLeft(Graphics g){
    //////////////PARTIE GAUCHE
    Image image = getToolkit().getImage("inventory.jpg");
    g.drawImage(image, 0, mapHeight-invHeight, invWidth, invHeight, this);
    image = getToolkit().getImage("icone.jpg");
    //on dessine le nombre d'emplacements d'inventaire suivant x et y
    //ainsi que les elements d'inventaire s'y trouvant
    inventoryEmp = inventory.size();
    for(int i=0; i<(numInvX*numInvY); i++){
      int xic = i%numInvX + 1;
      int yic = i/numInvX + 1;
      //si il y a un objet à cet emplacement: on le dessine
      if(i<inventoryEmp){
        image = getToolkit().getImage(inventory.get(i).getAddImage());
        g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, mapHeight-invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
      }
      //sinon on dessine une icone
      else{
        image = getToolkit().getImage("icone.jpg");
        g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, mapHeight-invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
      }
    }
  }

  private void InventoryRight(Graphics g){
    //////////////PARTIE DROITE
    int div = 4 + this.addBlastRange;
    Image image = getToolkit().getImage("inventoryPlayer.jpg");
    g.drawImage(image, invWidth, mapHeight-invHeight, mapWidth-invWidth, invHeight, this);
    String textLife = "Life: " + player.getLife() + "/" + player.getMaxLife();
    g.drawString(textLife, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight/div);
    String textStrengh = "Strengh: " + player.getStrength();
    g.drawString(textStrengh, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight*2/div);
    //Is the player a Mage
    if(addBlastRange == 1){
      String textBlastRange = "BlastRange: " + ((Mage)player).getBlastRange();
      g.drawString(textBlastRange, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight*3/div);
    }
    String textWeapon = "Weapon: " ;
    g.drawString(textWeapon, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight*(3+addBlastRange)/div);
    if(player.getWeaponEquip() == null){
      image = getToolkit().getImage("icone.jpg");
    }
    else{
      image = getToolkit().getImage(player.getWeaponEquip().getAddImage());
    }
    g.drawImage(image, invWidth + (mapWidth-invWidth)*3/4, mapHeight-invHeight+ invHeight*(2+addBlastRange)/div, side, side, this);

    //////////////on dessine l'icone sﾃｩlectionnﾃｩe
    int xic = posIc[0];
    int yic = posIc[1];
    image = getToolkit().getImage("icone_select.png");
    if(posIc[0] == numInvX + 1){
      g.drawImage(image, invWidth + (mapWidth-invWidth)*3/4, mapHeight-invHeight+ invHeight*(2+addBlastRange)/div, side, side, this);
    }
    else{
      g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, mapHeight-invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
    }

  }

  public void paintBlast(Graphics g){

  }

  public void paintAxe(Graphics g){
    Image image = getToolkit().getImage("axe.png");
    g.drawImage(image, posIc[1], posIc[1], invWidth, invHeight, this);
  }

  ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

  public void redraw() {
    this.repaint();
  }


  ////////////////////////////////////////////////////////////////////////////////////////<movingMethods>

  public void swingAxe(){

  }


  ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

  public void setObjects(ArrayList<GameObject> objects) {
    this.objects = objects;
  }

  public void setPlayer(Player player){
    this.player = player;
    this.inventory = player.getInventory();
    this.posIc = player.getPosIc();

    if(player instanceof Mage){
      this.addBlastRange = 1;
    }
  }

  public void setSize(int size){
    this.size = size;
    //On définit la taille de l'inventaire et de la map
    this.mapHeight = (size)*this.yMulti;
    this.mapWidth = (size)*this.xMulti;
    this.invWidth = this.mapWidth*19/30;
    this.invHeight = this.mapHeight/5;
    this.side = Integer.min(invWidth/10, invHeight/4);
    font = new Font("TimesRoman", Font.PLAIN, mapWidth/32 );
  }

  //On dﾃｩfini l'affichage de l'inventaire
  public void setInventoryState(boolean inventoryState){
    this.inventoryState = inventoryState;
  }

  public void setInvX(int numInvX){
    this.numInvX = numInvX;
  }

  public void setInvY(int numInvY){
    this.numInvY = numInvY;
  }

  ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

  public boolean getInventoryState(){
    return inventoryState;
  }

}
