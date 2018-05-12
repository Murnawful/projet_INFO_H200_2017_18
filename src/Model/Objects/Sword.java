package Model.Objects;

import Model.Game;
import Model.Moving.Character;
import Model.Moving.Player;

import java.util.ArrayList;

public class Sword extends Weapon {

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Sword(int x, int y, int color, String description, int bonus, Game game){
        super(x,y,color,description, bonus, game);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public boolean equip(Player p){
        if (p.getWeaponEquip() != null){
            p.getWeaponEquip().unequip(p);
        }
        p.setWeaponEquip(this);
        p.setStrength(p.getStrength() + bonus);
        return true;
    }

    @Override
    public void run(){
        Player player = game.getPlayer();
        attack(player.getFrontX(), player.getFrontY());
    }

    @Override
    public void attack(int x, int y){
        GameObject o = null;
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(game.getPlayer().getFrontX(),game.getPlayer().getFrontY())){
                o =  object;
                break;
            }
        }
        if (o instanceof Character){
            Character aimedObject = (Character) o;
            aimedObject.modifyLife(-game.getPlayer().getStrength());
        }else if(o instanceof BlockBreakable){
            BlockBreakable aimedObject = (BlockBreakable) o;
            aimedObject.activate(game.getPlayer().getStrength());
        }
    }
}
