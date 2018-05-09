package View;

import Model.Directable;
import Model.Moving.Character;
import Model.Moving.Mage;
import Model.Moving.Player;
import Model.Object.GameObject;
import Model.Object.InventoryObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class Map extends JPanel {

    ///////////////////////////////////////////////////////////////////////////////////////<Content>
    private static final long serialVersionUID = 1L;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private ArrayList<GameObject> objectsPaintLater; // list of the last objects to be painted on map
    private ArrayList<InventoryObject> inventory = new ArrayList<InventoryObject>();
    private Player player;
    private boolean inventoryState;

    private boolean axeSwung = false;
    private int size;
    private int addBlastRange = 0; // 1 for true, 0 for false

    ///////////////////////////////////////////////////////////////////////////////////////<Inventory>
    private int numInvY = 0;
    private int numInvX = 0;
    private int side = 0; // icon size
    private int inventoryEmp; // number of elements in inventory
    private Font font;
    private int posIc[]= {numInvY/2 + 1,numInvX/2 + 1}; // if posIc = {numInvY, numInvX+1}, equipment icon is selected

    ///////////////////////////////////////////////////////////////////////////////////////<Dimsensions>
    private final int width = 30;
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
        // loads all images to be drawn on map
    }

    ////////////////////////////////////////////////////////////////////////////////////////<paintMethods>

    public void paint(Graphics g) {
        if(objectsPaintLater != null){ // empties the list of objects in standby
            objectsPaintLater.clear();
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight() );
        for (int i = 0; i < size; i++) { // paints the grid of the map
            for (int j = 0; j < size; j++) {
                int x = i;
                int y = j;
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * xMulti, y * yMulti, width, height); // paints the interior (starts from top right)
                g.setColor(Color.BLACK);
                g.drawRect(x * xMulti, y * yMulti, width, height); // paints contour (starts from top right)
            }
        }
        for (GameObject object : objects) {
            if(object instanceof Character){ // Player must be painted last
                objectsPaintLater.add(object);
            }
            else{
                paintObject(g, object); // immediately paints objects
            }
        }
        for(GameObject object : objectsPaintLater){ // paints objects standing by
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

        if (color == 0) { // paints the object accordingly
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
            paintLine(g, object,x,y); // if object is directable, paints a line to indicate front
        }
    }

    public void paintLine(Graphics g, GameObject object, int x, int y){
        if(object instanceof Directable && !(object instanceof InventoryObject)) {
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
            g.drawLine(xCenter, yCenter, xCenter + deltaX, yCenter + deltaY); // paints line on character
        }
    }

    private void paintInventory(Graphics g){
        this.InventoryLeft(g); // loading the left part of the inventory
        this.InventoryRight(g); // loading the right part of the inventory
    }

    private void InventoryLeft(Graphics g){ //////////////LEFT PART
        Image image = getToolkit().getImage("src/Images/inventory.jpg");
        g.drawImage(image, 0, mapHeight-invHeight, invWidth, invHeight, this);
        image = getToolkit().getImage("src/Images/Imagesicone.jpg");
        inventoryEmp = inventory.size();
        for(int i=0; i<(numInvX*numInvY); i++){ // paints number of inventory spaces according to their X and Y coordinates. Same goes for elements present in inventory
            int xic = i%numInvX + 1;
            int yic = i/numInvX + 1;
            if(i<inventoryEmp){ // paints objects if in inventory
                image = getToolkit().getImage(inventory.get(i).getAddImage());
                g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, mapHeight-invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
            } else{ // paints a simple icon
                image = getToolkit().getImage("src/Images/icone.jpg");
                g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, mapHeight-invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
            }
        }
    }

    private void InventoryRight(Graphics g){ ////////////// RIGHT PART
        //TODO add blastRange for Mage class
        int div = 4 + this.addBlastRange; // divides sreen in order to adapt dimensions
        Image image = getToolkit().getImage("src/Images/inventoryPlayer.jpg");
        g.drawImage(image, invWidth, mapHeight-invHeight, mapWidth-invWidth, invHeight, this);

        String textLife = "Life: " + player.getLife() + "/" + player.getMaxLife();
        g.drawString(textLife, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight/div);

        String textStrengh = "Strengh: " + player.getStrength();
        g.drawString(textStrengh, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight*2/div);

        if(addBlastRange == 1){ // if Player is a Mage
            String textBlastRange = "BlastRange: " + ((Mage)player).getBlastRange();
            g.drawString(textBlastRange, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight*3/div);
        }

        String textWeapon = "Weapon: ";
        g.drawString(textWeapon, invWidth + (mapWidth-invWidth)*1/20, mapHeight-invHeight+ invHeight*(3+addBlastRange)/div);
        if(player.getWeaponEquip() == null){
            image = getToolkit().getImage("src/Images/icone.jpg");
        } else{
            image = getToolkit().getImage(player.getWeaponEquip().getAddImage());
        }
        g.drawImage(image, invWidth + (mapWidth-invWidth)*3/4, mapHeight-invHeight+ invHeight*(2+addBlastRange)/div, side, side, this);

        int xic = posIc[0]; // paints icon for selection
        int yic = posIc[1];
        image = getToolkit().getImage("src/Images/icone_select.png");
        if(posIc[0] == numInvX + 1){
            g.drawImage(image, invWidth + (mapWidth-invWidth)*3/4, mapHeight-invHeight+ invHeight*(2+addBlastRange)/div, side, side, this);
        } else{
            g.drawImage(image, (xic)*invWidth/(numInvX+1)-side/2, mapHeight-invHeight + (yic)*invHeight/(numInvY+1) - side/2, side, side, this);
        }
    }

    public void paintBlast(Graphics g){

    }

    public void paintAxe(Graphics g){
        Image image = getToolkit().getImage("src/Images/axe.png");
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

    public void setSize(int size){ // sets size of the map AND the inventory
        this.size = size;
        this.mapHeight = (size)*this.yMulti;
        this.mapWidth = (size)*this.xMulti;
        this.invWidth = this.mapWidth*19/30;
        this.invHeight = this.mapHeight/5;
        this.side = Integer.min(invWidth/10, invHeight/4);
        font = new Font("TimesRoman", Font.PLAIN, mapWidth/32 );
    }

    public void setInventoryState(boolean inventoryState){ // says if inventory is opened and must be painted
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
