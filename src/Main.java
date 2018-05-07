import Controller.Keyboard;
import Model.Game;
import View.Window;

public class Main {
    public static void main(String[] args) {

        ////////////////////////////////// Hack'n'Slash Project \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        //////////////////////////// Nathan Farber, Nathan Vaneberg \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        ///////////////////////////////////// ULB 2017-2018 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        Window window = new Window();

        ////////////////////////////////////////////////////////////////////////////////////////

        Game game = new Game(window);

        ////////////////////////////////////////////////////////////////////////////////////////

        Keyboard keyboard = new Keyboard(game);

        ////////////////////////////////////////////////////////////////////////////////////////

        window.setKeyListener(keyboard);
    }
}
