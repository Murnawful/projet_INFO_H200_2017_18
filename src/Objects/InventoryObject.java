package Objects;

public abstract class InventoryObject extends GameObject{

    protected String description;
    protected boolean isInInventory = false;
    private String addImage;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public InventoryObject(int X, int Y, int color, String description, String addImage) {
        super(X, Y, color);
        this.addImage = addImage;
        this.description = description;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void activate(int points){ // action de ramasser l'objet
        notifyDeletableObserver();
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
