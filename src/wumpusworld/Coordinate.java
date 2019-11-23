/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

/**
 *
 * @author albam
 */
public class Coordinate {
    private int x;
    private int y;
    private boolean [] percepts;
    private boolean isVisited;
    private int levelDanger;
    
    
   public Coordinate(int x, int y){
       this.x = x;
       this.y = y;
       this.levelDanger = 1;
   }
    
    
    
    
}
