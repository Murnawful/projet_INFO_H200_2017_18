package Model.Objects;

import Model.Moving.Player;

public interface Equipable {

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    boolean equip(Player p);

    void unequip(Player p);
}
