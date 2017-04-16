import java.util.*;
import java.io.*;
import java.math.*;

class Player {

    public static void main(String args[]) throws NullPointerException {
        Scanner in = new Scanner(System.in);
        
        ShipHandler handler = new ShipHandler();
        String command = "";
        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x_ = in.nextInt();
                int y_ = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
                
                if(entityType.equals("BARREL")){
                    Barrel b1 = new Barrel(x_, y_, arg1, entityId);
                    handler.addBarrel(b1);
                }
                if(entityType.equals("SHIP")){
                    Ship s1 = new Ship(x_, y_, arg1, arg2, arg3, arg4, entityId);
                    handler.addShip(s1);
                }
            }
            
            
            
            for(Ship s : handler.getShips()) { //for every ship
                if(s.getFoe() == 1) { //if the ship is controlled by us
                
                    try{
                        command += s.move(handler.getBarrels()); //do something
                        if(command.length()>0){
                            System.out.println(command);
                        }
                        else{
                            System.out.println("WAIT");
                        }
                    }
                    catch(NullPointerException e){
                        System.out.println("WAIT");
                    }
                }
                
            }
            handler.clearBarrels();
            handler.clearShips();
            command = "";
        }
    }
}

class ShipHandler {
    private ArrayList<Ship> ships;
    
    private ArrayList<Barrel> barrels;
    
    private ArrayList<Mine> mines;
    
    public ShipHandler(){
        ships = new ArrayList<Ship>();
        barrels = new ArrayList<Barrel>();
        mines = new ArrayList<Mine>();
    }
    
    public void addShip(Ship s){
        ships.add(s);
    }
    
    public void addBarrel(Barrel b){
        barrels.add(b);
    }
    
    public void addMine(Mine m) {
        mines.add(m);
    }
    
    public void clearBarrels(){
        barrels.clear();
    }
    
    public void clearShips(){
        ships.clear();
    }
    
    public void clearMines(){
        mines.clear();
    }
    
    public ArrayList<Ship> getShips(){
        return ships;
    }
    
    public ArrayList<Barrel> getBarrels(){
        return barrels;
    }
    
    public ArrayList<Mine> getMines() {
        return mines;
    }
}

class Ship {
    private int x;
    private int y;
    private int rot;
    private int speed;
    private int rum;
    private int foe;
    private int id;
    
    public Ship(int _x, int _y, int ro, int s, int r, int f, int i){
        x = _x;
        y = _y;
        rot = ro;
        speed = s;
        rum = r;
        foe = f;
        id = i;
    }
    
    public Ship(){
        x = 0;
        y = 0;
        rot = 0;
        speed = 0;
        rum = 0;
        foe = 0;
        id = 0;
    }
    
    public String move(ArrayList<Barrel> barrels){ //to closest carrel 
        Barrel closest = null;
        boolean first = true;
        for(Barrel ba : barrels){
            if(first){
                closest = ba;
                first = false;
            }
            else{
                if(distanceFrom(this.getX(), this.getY(), ba.getX(), ba.getY()) <
                   distanceFrom(this.getX(), this.getY(), closest.getX(), closest.getY())
                ){
                    closest = ba;
                }
            }
        }
        
        return "MOVE " + closest.getX() + " " + closest.getY();
    }
    
    //public String cannon(ArrayList<Ship> s){ //will fire if enemy is within certain distance
        //find closest
    //}
    
    public double distanceFrom(int xFrom, int yFrom, int xTo, int yTo){
        int a = xTo - xFrom;
        int b = yTo - yFrom;
        double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
        
        return c;
    }
    
    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getRot(){ return rot; }
    public int getSpeed(){ return speed; }
    public int getRum(){ return rum; }
    public int getFoe(){ return foe; }
    public int getID(){ return id; }
    
    public void setX(int _x){ x = _x;}
    public void setY(int _y){ y = _y; }
    public void setRot(int r){ rot = r; }
    public void setSpeed(int s){ speed = s; }
    public void setRum(int r){ rum = r; }
    public void setFoe(int f){ foe = f; }
    public void setId(int i){ id = i;}
    
}

class Barrel {
    private int x;
    private int y;
    private int rum;
    private int id;

    public Barrel(int _x, int _y, int _r, int _i){
        x = _x;
        y = _y;
        rum = _r;
        id = _i;
    }
    
    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getRum(){ return rum; }
    public int getID(){ return id; }

}

class Mine {
    private int x;
    private int y;
    
    public Mine(int _x, int _y) {
        x = _x;
        y = _y;
    }
    
    public int getX(){ return x; }
    public int getY(){ return y; }
}