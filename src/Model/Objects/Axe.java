package Model.Objects;

import Model.Directable;
import Model.Game;
import Model.Moving.Character;
import Model.Moving.Player;
import Model.Moving.Warrior;

import java.util.ArrayList;

public class Axe extends Weapon implements Directable {

    private int weight;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Axe(int x, int y, int color, String description, int bonus, int weight, Game game){
        super(x,y,color,description, bonus, game);
        this.weight = weight;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public boolean equip(Player p){
        boolean res = true;
        if (p instanceof Warrior){
            if (p.getWeaponEquip() != null){
                p.getWeaponEquip().unequip(p);
            }
            p.setWeaponEquip(this);
            p.setStrength(p.getStrength() + bonus);
        }else{
            res = false;
            System.out.println("Vous ne pouvez pas équiper cette arme !");
        }
        return res;
    }

    @Override
    public void run() {
        Player p = game.getPlayer();
        int face = p.getDirection();
        try {
            if (face == EAST){
                for (int i = 0; i <= 4; i++){ int X = p.getFrontX();  int Y = p.getFrontY();
                    if (i == 0 || i == 4){ X--; }
                    if (i == 0 || i == 1){ Y++; }
                    if (i == 3 || i == 4){ Y--; }
                    attack(X, Y);
                    Thread.sleep(weight);
                }
            }
            if (face == NORTH){
                for (int i = 0; i <= 4; i++){ int X = p.getFrontX(); int Y = p.getFrontY();
                    if (i == 0 || i == 4){ Y++; }
                    if (i == 0 || i == 1){ X++; }
                    if (i == 3 || i == 4){ X--; }
                    attack(X, Y);
                    Thread.sleep(weight);
                }
            }
            if (face == SOUTH){
                for (int i = 0; i <= 4; i++){ int X = p.getFrontX(); int Y = p.getFrontY();
                    if (i == 0 || i == 4){ Y--; }
                    if (i == 0 || i == 1){ X--; }
                    if (i == 3 || i == 4){ X++; }
                    attack(X, Y);
                    Thread.sleep(weight);
                }
            }
            if (face == WEST){
                for (int i = 0; i <= 4; i++){ int X = p.getFrontX(); int Y = p.getFrontY();
                    if (i == 0 || i == 4){ X++; }
                    if (i == 0 || i == 1){ Y--; }
                    if (i == 3 || i == 4){ Y++; }
                    attack(X, Y);
                    Thread.sleep(weight);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attack(int x, int y){
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
