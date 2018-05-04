package Objects;

import Model.Directable;
import Model.Game;
import Moving.*;
import Moving.Character;

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
        Player p = game.getPlayer();
        int X = p.getFrontX();
        int Y = p.getFrontY();
        int face = p.getDirection();
        try{
            Mage m = (Mage) p;
            int force = m.getForce();
            for(int i = 0; i <= m.getBlastRange(); i++){
                if (face == NORTH){
                    attack(X,Y-i,force, game);
                    Thread.sleep(speed);
                }else if(face == EAST){
                    attack(X+i,Y,force, game);
                    Thread.sleep(speed);
                }else if (face == SOUTH){
                    attack(X,Y+i,force, game);
                    Thread.sleep(speed);
                }else if (face == WEST){
                    attack(X-i,Y,force, game);
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
            p.setForce(p.getForce() + bonus);
        }else{
            res = false;
            System.out.println("Vous ne pouvez pas équiper cette arme !");
        }
        return res;
    }

    @Override
    public void unequip(Player p){
        p.setInventory(p.getWeaponEquip());
        p.getWeaponEquip().setInInventory();
        ((Mage) p).setBlastRange(((Mage) p).getBlastRange() - bonusRange);
        p.setForce(p.getForce() - bonus);
        p.setWeaponEquip(null);
    }

    @Override
    public void attack(int x, int y, int force, Game game) {
        GameObject o = null;
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(x,y)){
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

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    @Override
    public int getDirection() {
        return 0;
    }
}
