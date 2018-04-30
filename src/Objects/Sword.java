package Objects;

import Model.Game;
import Moving.Character;
import Moving.Player;

public class Sword extends Weapon {

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Sword(int x, int y, int color, String description, int bonus, Game game){
        super(x,y,color,description, bonus, game);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public boolean equip(Player p){
        p.setWeaponEquip(this);
        p.setForce(bonus);
        return true;
    }

    @Override
    public void run(){
        Player p = game.getPlayer();
        attack(p.getFrontX(), p.getFrontY(), p.getForce(), game);
    }

    @Override
    public void attack(int x, int y,int force, Game game){
        GameObject o = null;
        Player p = game.getPlayer();
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(p.getFrontX(),p.getFrontY())){
                o =  object;
                break;
            }
        }
        if (o instanceof Character){
            Character aimedObject = (Character) o;
            aimedObject.modifyLife(-force);
        }else if(o instanceof BlockBreakable){
            BlockBreakable aimedObject = (BlockBreakable) o;
            aimedObject.activate(force);
            game.notifyView();
        }
    }
}
