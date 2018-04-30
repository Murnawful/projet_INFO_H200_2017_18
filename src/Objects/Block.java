package Objects;

public abstract class Block extends GameObject {

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Block(int x, int y, int color) {
        super(x, y, color);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods

    @Override
    public boolean isObstacle() {
        return true;
    }

}
