package Objects;

import Moving.Character;
import Moving.Player;

import java.util.ArrayList;

public class Sword extends Weapon {

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Sword(int x, int y, int color, String description, int bonus, ArrayList<GameObject> objects){
        super(x,y,color,description, bonus, objects);
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
        attack(player.getFrontX(), player.getFrontY());
    }

    @Override
    public void attack(int x, int y){
        GameObject o = null;
        for(GameObject object : objects){
            if(object.isAtPosition(player.getFrontX(),player.getFrontY())){
                o =  object;
                break;
            }
        }
        if (o instanceof Character){
            Character aimedObject = (Character) o;
            aimedObject.modifyLife(-player.getStrength());
        }else if(o instanceof BlockBreakable){
            BlockBreakable aimedObject = (BlockBreakable) o;
            aimedObject.activate(player.getStrength());
        }
    }
}
