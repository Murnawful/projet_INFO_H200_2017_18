package Model.Objects;

import Model.Directable;
import Model.Game;
import Model.Moving.*;
import Model.Moving.Character;

import java.util.ArrayList;

public class Staff extends Weapon implements Directable{

    private int bonusRange;
    private int speed;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Staff(int x, int y, int color, String description, int bonus, int bonusRange, int speed, Game game) {
        super(x, y, color, description, bonus, game);
        this.bonusRange = bonusRange;
        this.speed = speed;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void run() {
        Player player = game.getPlayer();
        int X = player.getFrontX();
        int Y = player.getFrontY();
        int face = player.getDirection();
        try{
            Mage m = (Mage) player;
            int force = m.getStrength();
            for(int i = 0; i <= m.getBlastRange(); i++){
                if (face == NORTH){
                    attack(X,Y-i);
                    Thread.sleep(speed);
                }else if(face == EAST){
                    attack(X+i,Y);
                    Thread.sleep(speed);
                }else if (face == SOUTH){
                    attack(X,Y+i);
                    Thread.sleep(speed);
                }else if (face == WEST){
                    attack(X-i,Y);
                    Thread.sleep(speed);
                }
                if(force - 1 > 0){
                    force -= 1;
                }else{
                    force = 0;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equip(Player p){
        boolean res = true;
        if (p instanceof Mage){
            if (p.getWeaponEquip() != null){
                p.getWeaponEquip().unequip(p);
            }
            p.setWeaponEquip(this);
            ((Mage) p).setBlastRange(((Mage) p).getBlastRange() + bonusRange);
            p.setStrength(p.getStrength() + bonus);
        }else{
            res = false;
            System.out.println("Vous ne pouvez pas équiper cette arme !");
        }
        return res;
    }

    @Override
    public void unequip(Player p){
        p.setInventory(p.getWeaponEquip());
        ((Mage) p).setBlastRange(((Mage) p).getBlastRange() - bonusRange);
        p.setStrength(p.getStrength() - bonus);
        p.setWeaponEquip(null);
    }

    @Override
    public void attack(int x, int y) {
        GameObject o = null;
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(x,y)){
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

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    @Override
    public int getDirection() {
        return 0;
    }
}
