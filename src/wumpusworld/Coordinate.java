/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author albam
 */
public class Coordinate {
    //ATRIBUTES
    private int x;
    private int y;
    private boolean [] percepts;
    private boolean isVisited;
    private int levelDanger;
    private ArrayList<Coordinate> adjacentNodes;
    
   //CONSTRUCTOR
   public Coordinate(int x, int y){
       this.x = x;
       this.y = y;
       this.percepts = new boolean[5];
       this.levelDanger = 1;
       this.adjacentNodes = new ArrayList<Coordinate>();
   }
   
   
   public void setPerceptStench(boolean isStench){
       this.percepts[0] = isStench;
   }
   
   public void setPerceptBreeze(boolean isBreeze){
       this.percepts[1] = isBreeze;
   }
   
   public void setPerceptGlitter (boolean isGlitter){
       this.percepts[2] = isGlitter;
   }
   
   public void setPerceptBump (boolean isBump){
       this.percepts[3] = isBump;
   }
   
   public void setPerceptScream (boolean isScream){
       this.percepts[4] = isScream;
   }
   
   public void setLevelDanger (int i){
       this.levelDanger = i;
   }
   
   public int getLevelDanger(){
       return levelDanger;
   }
   
   public ArrayList<Coordinate> getAdjacentNodes(){
       return adjacentNodes;
   }
   
   public void createAdjacentNodes() //4x4
   {
       Coordinate adj;
       if (x - 1 > 0){ // there is left
           adj = new Coordinate (x - 1,y);
           addAdjacentNode(adj);
       }
       if (x + 1 < 5){ // there is right
           adj = new Coordinate (x + 1,y );
           addAdjacentNode(adj);
       }
       if (y - 1 > 0){ // there is bottom
           adj = new Coordinate (x, y - 1);
           addAdjacentNode(adj);
       }
        if(y + 1 < 5){ //there is upper
            adj = new Coordinate (x, y + 1);
            addAdjacentNode(adj);
        }
   }
   
   private void addAdjacentNode(Coordinate node){
       if (!adjacentNodes.contains(node))
               adjacentNodes.add(node);
   }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.isVisited != other.isVisited) {
            return false;
        }
        if (this.levelDanger != other.levelDanger) {
            return false;
        }
        if (!Arrays.equals(this.percepts, other.percepts)) {
            return false;
        }
        if (!Objects.equals(this.adjacentNodes, other.adjacentNodes)) {
            return false;
        }
        return true;
    }
   
   

    
    
}
