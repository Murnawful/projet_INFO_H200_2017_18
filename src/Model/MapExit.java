package Model;

public class MapExit{ // contains informations on an exit on the map

    private int X;
    private int Y;
    private int playerX;
    private int playerY;
    private String mapOut; // map the player is pointing to

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public MapExit(int X, int Y, int playerX, int playerY, String mapOut){
      this.X = X;
      this.Y = Y;
      this.playerX = playerX;
      this.playerY = playerY;
      this.mapOut = mapOut;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>
  
    public int getX(){
    return X;
  }

    public int getY(){
    return Y;
  }

    public int getPlayerX(){
    return playerX;
  }

    public int getPlayerY(){
    return playerY;
  }

    public String getMapout(){
    return mapOut;
  }
}
