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
public class Node {
    //ATRIBUTES
    private int x;
    private int y;
    private boolean [] percepts;
    private boolean isVisited;
    private int levelDanger;
    private int gValue; //g(n)
    private int fValue; //f(n)
    private ArrayList<Node> adjacentNodes;
    
   //CONSTRUCTOR
   public Node(int x, int y){
       this.x = x;
       this.y = y;
       this.percepts = new boolean[5];
       this.levelDanger = 0;
       //this.levelDanger = 1;
   }
   
    public int  getX(){return x;}
    public int  getY(){return y;}
   
    public boolean  getIsVisited(){return isVisited;}
    public void setIsVisited(boolean isVisited){this.isVisited = isVisited;}
    
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
   
//   public void setLevelDanger (int i){
//       this.levelDanger = i;
//   }
//   
//   public int getLevelDanger(){
//       return levelDanger;
//   }
   
   public ArrayList<Node> getAdjacentNodes(){
       return adjacentNodes;
   }

 public boolean  getPerceptStench(){
      return  this.percepts[0];
   }
   
 public boolean  getPerceptBreeze(){
      return  this.percepts[1];
   }
   
   
    public void setGValue(int gValue){this.gValue = gValue;}
    public int getGValue(){return  gValue;}
    public void setFValue(int fValue){this.fValue = fValue;}
    public int getFValue(){return  fValue;}
    public int getLevelDanger(){return levelDanger;}
    public void setLevelDanger(int levelDanger){this.levelDanger = levelDanger;}

   
   
   private void addAdjacentNode(Node node)
   {
       if (!adjacentNodes.contains(node))
               adjacentNodes.add(node);
   }



    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.isVisited != other.isVisited) {
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
