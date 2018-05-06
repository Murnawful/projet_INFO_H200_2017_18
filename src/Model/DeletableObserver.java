package Model;

import Objects.GameObject;
import Objects.InventoryObject;

import java.util.ArrayList;

public interface DeletableObserver {

    void delete(Deletable d);
}
