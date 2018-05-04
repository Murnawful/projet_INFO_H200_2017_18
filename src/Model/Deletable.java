package Model;

public interface Deletable {

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    void attachDeletable(DeletableObserver po);

    void notifyDeletableObserver();
}
