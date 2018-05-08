package Objects;

import Model.Deletable;
import Model.Game;
import Moving.Player;

public class BoostConsumable extends InventoryObject implements Consumable, Deletable, Runnable {

    private String boostType;
    private int boostLength;
    private Player player;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public BoostConsumable(int X, int Y, int color, String description, String boostType, int boostLength, Player player) {
        super(X, Y, color, description, "src/Images/boostPotion.png");
        this.boostType = boostType;
        this.boostLength = boostLength;
        this.player = player;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods

    @Override
    public void run() {
        try{
            Thread.sleep(boostLength);
            vanish(player);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void consume(Player p) { // lists the different effects of boosting potions
        if(boostType.equals("force")){
            p.setStrength(p.getStrength() + 1);
        }else if(boostType.equals("life")){
            p.modifyLife(1);
        }else if(boostType.equals("force+")){
            p.setStrength(p.getStrength() + 2);
        }else if(boostType.equals("life+")) {
            p.modifyLife(2);
        }else if(boostType.equals("force++")){
            p.setStrength(p.getStrength() + 3);
        }else if(boostType.equals("life++")) {
            p.modifyLife(3);
        }
        Thread t = new Thread(this);
        t.start();
    }

    public void vanish(Player p){ // removes effects of potions
        if(boostType.equals("force")){
            p.setStrength(p.getStrength() - 1);
        }else if(boostType.equals("life")){
            p.modifyLife(-1);
        }else if(boostType.equals("force+")){
            p.setStrength(p.getStrength() - 2);
        }else if(boostType.equals("life+")) {
            p.modifyLife(-2);
        }else if(boostType.equals("force++")){
            p.setStrength(p.getStrength() - 3);
        }else if(boostType.equals("life++")) {
            p.modifyLife(-3);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setBoostType(String boostType){
        this.boostType = boostType;
    }

    public void setBoostLength(int boostLength){
        this.boostLength = boostLength;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public String getBoostType(){
        return boostType;
    }

    public int getBoostLength() {
        return boostLength;
    }
}
