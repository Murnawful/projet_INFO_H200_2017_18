package Model.Object;

import Model.Moving.Player;

public interface Equipable {

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    boolean equip(Player p);

    void unequip(Player p);
}
