package Objects;

import Moving.Player;

public abstract class InventoryObject extends GameObject implements Usable{

    private String description;
    private boolean isInInventory = false;
    private String addImage;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public InventoryObject(int X, int Y, int color, String description, String addImage) {
        super(X, Y, color);
        this.addImage = addImage;
        this.description = description;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    public void drop(){}

    @Override
    public void activate(int points){ // action de ramasser l'objet
        notifyDeletableObserver();
    }

    @Override
    public boolean use(Player p){ // output is true if object must be removed from inventory
        boolean remove = false;
        if(this instanceof Weapon){
            remove = ((Weapon)this).equip(p);
        }
        else if(this instanceof Consumable){
            remove = true;
            ((Consumable)this).consume(p);
        }
        return remove;
    }

    public void setInInventory(){
        if(isInInventory){
            this.isInInventory = false;
        }else {
            this.isInInventory = true;
        }
    }

    @Override
    public boolean isObstacle() {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public String getDescription(){
        return description;
    }

    public boolean isInInventory(){
        return isInInventory;
    }

    public String getAddImage(){
        return  addImage;
    }
}
