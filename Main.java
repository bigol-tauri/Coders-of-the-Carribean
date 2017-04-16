import java.util.*;
import java.io.*;
import java.math.*;

class Player {

    public static void main(String args[]) throws NullPointerException {
        Scanner in = new Scanner(System.in);
        
        ShipHandler handler = new ShipHandler();
        String command = "";
        int frame = 0; //counts game frames. used for deciding when to fire, eg. every 2 frames
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
                        command = s.move(handler.getBarrels()); //do something
                        if(!(command.length()>0)){
                            command = "WAIT";
                        }  
                    }
                    catch(NullPointerException e){
                        System.out.println("WAIT");
                    }
                    
                    String fireCommand = s.fire(handler.getShips());
                    if(frame%2==0 && fireCommand.length()>1){ //if ship within 5 blocks and even frame
                        command = fireCommand;
                    }
                    
                    System.out.println(command);
                }
                
            }
            handler.clearBarrels();
            handler.clearShips();
            handler.clearMines();
            command = "";
            
            frame++;
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
    private Coordinate coord;
    private int rot;
    private int speed;
    private int rum;
    private int foe;
    private int id;
    
    public Ship(int _x, int _y, int ro, int s, int r, int f, int i){
        coord = new Coordinate(_x,_y);
        rot = ro;
        speed = s;
        rum = r;
        foe = f;
        id = i;
    }
    
    public Ship(){
        coord = new Coordinate(0,0);
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
                if(hexDistance(this.getX(), this.getY(), ba.getX(), ba.getY()) <
                   hexDistance(this.getX(), this.getY(), closest.getX(), closest.getY())
                ){
                    closest = ba;
                }
            }
        }
        
        return "MOVE " + closest.getX() + " " + closest.getY();
    }
    
    public String fire(ArrayList<Ship> ships){ //to closest carrel 
        Ship closest = null;
        boolean first = true;
        for(Ship s : ships){
            if(s.getFoe()==0){ //enemy ship
                if(first){
                    closest = s;
                    first = false;
                }
                else{
                    if(hexDistance(this.getX(), this.getY(), s.getX(), s.getY()) <
                       hexDistance(this.getX(), this.getY(), closest.getX(), closest.getY())
                    ){
                        closest = s;
                    }
                }
            }
        }
        //if ship is within 5 units
        if(hexDistance(this.getX(), this.getY(), closest.getX(), closest.getY()) <= 10){
            
            
            return "FIRE 10 10";
        }
        else{
            return "";
        }
    } 
    
    //actual distance betwen two points
    //public double distanceFrom(int xFrom, int yFrom, int xTo, int yTo){
    //    int a = xTo - xFrom;
    //    int b = yTo - yFrom;
    //    double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
    //    
    //    return c;
    //}
    
    //distance (moves required) between two points on the hex grid
    public static int hexDistance(int xf, int yf, int xt, int yt){
        int x1 = xf - (yf - (xf&1)) / 2;
        int z1 = yf;
        int y1 = -x1 - z1;
        
        int x2 = xt - (yt - (xt&1)) / 2;
        int z2 = yt;
        int y2 = -x2 - z2;
        
        int m = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    	return Math.max(m, Math.abs(z1 - z2));
    }
    
    public int getX(){ return coord.getX(); }
    public int getY(){ return coord.getY(); }
    public int getRot(){ return rot; }
    public int getSpeed(){ return speed; }
    public int getRum(){ return rum; }
    public int getFoe(){ return foe; }
    public int getID(){ return id; }
    
}

class Barrel {
    private Coordinate coord;
    private int rum;
    private int id;

    public Barrel(int _x, int _y, int _r, int _i){
        coord = new Coordinate(_x,_y);
        rum = _r;
        id = _i;
    }
    
    public int getX(){ return coord.getX(); }
    public int getY(){ return coord.getY(); }
    public int getRum(){ return rum; }
    public int getID(){ return id; }

}

class Mine {
    private Coordinate coord;
    
    public Mine(int _x, int _y) {
        coord = new Coordinate(_x,_y);
    }
    
    public int getX(){ return coord.getX(); }
    public int getY(){ return coord.getY(); }
}
    
class Coordinate {
    private int x;
    private int y;
    
    public Coordinate(int x1, int y1){
        x = x1;
        y = y1;
    }
    
    public int getX(){ return x; }
    public int getY(){ return y; }   
}
        