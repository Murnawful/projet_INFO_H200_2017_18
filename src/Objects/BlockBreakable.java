package Objects;

import Model.Activable;
import Model.Deletable;

public class BlockBreakable extends Block implements Deletable, Activable {

    protected int lifepoints;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public BlockBreakable(int X, int Y, int lifepoints) {
        super(X, Y, 1);
        this.lifepoints = lifepoints;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>
    
    public void activate(int points){
        if (lifepoints - points <=  0 && points != 0){
            crush();
            if(this instanceof Pot){
                ((Pot) this).drop(0);
            }
        }
        else if(points != 0){
            lifepoints -= points;
            this.color = lifepoints + 2; // prevents block from turning grey
        }
    }

    public void crush(){
        notifyDeletableObserver();
    }
}
