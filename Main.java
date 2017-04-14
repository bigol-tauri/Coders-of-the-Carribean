import java.util.*;
import java.io.*;
import java.math.*;

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
            }
            for (int i = 0; i < myShipCount; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                System.out.println("MOVE 11 10"); // Any valid action, such as "WAIT" or "MOVE x y"
            }
        }
    }
}

class Ship {
    private int rot;
    private int speed;
    private int rum;
    private int foe;
    
    public Ship(int ro, int s, int r, int f){
        rot = ro;
        speed = s;
        rum = r;
        foe = f;
    }
    
    public int getRot(){ return rot; }
    public int getSpeed(){ return speed; }
    public int getRum(){ return rum; }
    public int getFoe(){ return foe; }
    
    public void setRot(int r){ rot = r; }
    public void setSpeed(int s){ speed = s; }
    public void setRum(int r){ rum = r; }
    public void setFoe(int f){ foe = f; }   
}

