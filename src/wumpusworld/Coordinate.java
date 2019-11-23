/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.ArrayList;

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
    private ArrayList<Coordinate> adjacentsNodes;
    
   //CONSTRUCTOR
   public Coordinate(int x, int y){
       this.x = x;
       this.y = y;
       this.percepts = new boolean[5];
       this.levelDanger = 1;
       this.adjacentsNodes = new ArrayList<Coordinate>();
       createAdjacentNodes();
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
       return adjacentsNodes;
   }
   
   private void createAdjacentNodes() //4x4
   {
       if (adjacentsNodes.Contains())
       if (x - 1 != 0) // no left
            new Coordinate (x - 1,y );
       if (x + 1 == 5) // no right
           new Coordinate (x + 1,y );
       if (y - 1 == 0) // no bottom
           new Coordinate (y - 1, 0) 
                   
        if(y + 1 == 5) //no upper
            new Coordinate ()
   }
   
   
    
    
    
    
}
