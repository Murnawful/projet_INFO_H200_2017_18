package Objects;

import Model.Deletable;
import Model.Game;
import Moving.Player;

public class BoostConsumable extends InventoryObject implements Consumable, Deletable, Runnable {

    private String boostType;
    private int boostLength;
    private Game game;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public BoostConsumable(int X, int Y, int color, String description, String boostType, int boostLength, Game game) {
        super(X, Y, color, description, "src/Images/boostPotion.png");
        this.boostType = boostType;
        this.boostLength = boostLength;
        this.game = game;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods

    @Override
    public void run() {
        try{
            Player p = game.getPlayer();
            consume(p);
            Thread.sleep(boostLength);
            vanish(p);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void consume(Player p) {
        if(boostType.equals("force")){
            p.setForce(p.getForce() + 1);
        }else if(boostType.equals("life")){
            p.modifyLife(1);
        }else if(boostType.equals("force+")){
            p.setForce(p.getForce() + 2);
        }else if(boostType.equals("life+")) {
            p.modifyLife(2);
        }else if(boostType.equals("force++")){
            p.setForce(p.getForce() + 3);
        }else if(boostType.equals("life++")) {
            p.modifyLife(3);
        }
    }

    public void vanish(Player p){
        if(boostType.equals("force")){
            p.setForce(p.getForce() - 1);
        }else if(boostType.equals("life")){
            p.modifyLife(-1);
        }else if(boostType.equals("force+")){
            p.setForce(p.getForce() - 2);
        }else if(boostType.equals("life+")) {
            p.modifyLife(-2);
        }else if(boostType.equals("force++")){
            p.setForce(p.getForce() - 3);
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
