package View;

import Model.Directable;
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
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Map extends JPanel {

  private static final long serialVersionUID = 1L;
  private ArrayList<GameObject> objects = null;
  private ArrayList<InventoryObject> inventory = null;
  private Player player;
  private boolean inventoryState;
  private boolean axeSwung = false;
  //vaut un si oui 0 sinon
  int addBlastRange = 0;

  private int numInvY = 0;
  private int numInvX = 0;
  private final int invWidth = 1000/3*2;
  private final int invHeight = 255;
  //taille de l'icone
  private final int side = 60;
  //Nombre d'ﾃｩlﾃｩments dans l'inventaire
  private int inventoryEmp; 
  private Font font;
  //si posIc = {numInvY, numInvX+1} on est ds la selection d'arme
  private int posIc[]= {numInvY/2 + 1,numInvX/2 + 1};

  ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

  public Map() {
    this.setFocusable(true);
    this.requestFocusInWindow();
    posIc[0] = numInvX/2 + 1;
    posIc[1] = numInvY/2 + 1;
    font = new Font("TimesRoman", Font.BOLD, 24);
  }

  ////////////////////////////////////////////////////////////////////////////////////////<paintMethods>

  public void paint(Graphics g) {

    g.setFont(font);
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight() );
    for (int i = 0; i < 20; i++) { // Virer la valeur 20 et parametrer ca
      for (int j = 0; j < 20; j++) {
        int x = i;
        int y = j;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x * 50, y * 50, 48, 48); //dessine l'interieur( commnence en haut ﾃ� droite )
        g.setColor(Color.BLACK);
        g.drawRect(x * 50, y * 50, 48, 48); //dessine le contour( commnence en haut ﾃ� droite )
      }
    }

    for (GameObject object : this.objects) {
      int x = object.getPosX();
      int y = object.getPosY();
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

      g.fillRect(x * 50, y * 50, 48, 48);
      g.setColor(Color.BLACK);
      g.drawRect(x * 50, y * 50, 48, 48);

      // Decouper en fontions
      if(object instanceof Directable && !(object instanceof Weapon) ) {
        int direction = ((Directable) object).getDirection();

        int deltaX = 0;
        int deltaY = 0;

        switch (direction) {
        case Directable.EAST:
          deltaX = +24;
          break;
        case Directable.NORTH:
          deltaY = -24;
          break;
        case Directable.WEST:
          deltaX = -24;
          break;
        case Directable.SOUTH:
          deltaY = 24;
          break;
        }

        int xCenter = x * 50 + 24;
        int yCenter = y * 50 + 24;
        g.drawLine(xCenter, yCenter, xCenter + deltaX, yCenter + deltaY);
      }
    }
    if( getInventoryState() == true){
      paintInventory(g);
    }
    if(axeSwung){
      paintAxe(g);
    }

  }

  //copiﾃｩ collﾃｩ : https://java.developpez.com/faq/gui?page=Les-images#Comment-combiner-deux-images
  public static BufferedImage CombineImage(BufferedImage image1, BufferedImage image2){ 
    Graphics2D g2d = image1.createGraphics(); 
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON); 
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, 
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY); 

    g2d.drawImage(image2, 0, 0, null); 

    g2d.dispose(); 

    return image1 ; 
  }

  private void paintInventory(Graphics g){
    //on charge l'image du fond de l'intentaire
    this.InventoryLeft(g);
    this.InventoryRight(g);
  }

  private void InventoryLeft(Graphics g){
    //////////////PARTIE GAUCHE
    Image image = getToolkit().getImage("inventory.jpg");
    g.drawImage(image, 0, 3*invHeight, invWidth, invHeight, this);
    image = getToolkit().getImage("icone.jpg");
    //on dessine le nombre d'emplacements d'inventaire suivant x et y
    //ainsi que les elements d'inventaire s'y trouvant
    inventoryEmp = inventory.size();
    for(int i=0; i<(numInvX*numInvY); i++){
      int xic = i%numInvX + 1;
      int yic = i/numInvX + 1;
      if(i<inventoryEmp){
        image = getToolkit().getImage(inventory.get(i).getAddImage());
        g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, 3*invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
      }
      else{
        image = getToolkit().getImage("icone.jpg");
        g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, 3*invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
      }
    }
  }

  private void InventoryRight(Graphics g){
    //////////////PARTIE DROITE
    int div = 7 + this.addBlastRange;
    Image image = getToolkit().getImage("inventoryPlayer.jpg");
    g.drawImage(image, invWidth, 3*invHeight, 1000-invWidth, invHeight, this);
    String textLive = "LIFE: " + player.getLife() + "/" + player.getMaxLife();
    g.drawString(textLive, invWidth + (1000-invWidth)*2/5, 3*invHeight+ invHeight/div);
    String textStrengh = "Strengh: " + player.getForce();
    g.drawString(textStrengh, invWidth + (1000-invWidth)*2/5, 3*invHeight+ invHeight*2/div);
    //Is the player a Mage
    if(addBlastRange == 1){
      String textBlastRange = "BlastRange: " + ((Mage)player).getBlastRange();
      g.drawString(textBlastRange, invWidth + (1000-invWidth)*2/5, 3*invHeight+ invHeight*3/div);
    }
    String textWeapon = "Weapon: " ;
    g.drawString(textWeapon, invWidth + (1000-invWidth)*2/5, 3*invHeight+ invHeight*(3+addBlastRange)/div);
    if(player.getWeaponEquip() == null){
      image = getToolkit().getImage("icone.jpg");
    }
    else{
      image = getToolkit().getImage(player.getWeaponEquip().getAddImage());
    }
    g.drawImage(image, invWidth + (1000-invWidth)*1/2, 3*invHeight+ invHeight*(7+addBlastRange)/15, side, side, this);

    //////////////on dessine l'icone sﾃｩlectionnﾃｩe
    int xic = posIc[0];
    int yic = posIc[1];
    image = getToolkit().getImage("icone_select.png");
    if(posIc[0] == numInvX + 1){
      g.drawImage(image, invWidth + (1000-invWidth)*1/2, 3*invHeight+ invHeight*(7+addBlastRange)/15, side, side, this);
    }
    else{
      g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, 3*invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
    } 
    
  }

  public void paintBlast(Graphics g){

  }

  public void paintAxe(Graphics g){
      Image image = getToolkit().getImage("axe.png");
      g.drawImage(image, posIc[1], posIc[1], invWidth, invHeight, this);
  }

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
