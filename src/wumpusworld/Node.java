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
    private int levelDanger;
    private int gValue; //g(n)
    private int fValue; //f(n)
    private boolean isPit;
    private boolean isWumpus;
    private ArrayList<Node> adjacentNodes;
    private boolean isCheckedPit;
    private boolean isCheckedSquarePit;
    private boolean isCheckedBreeze;
    private boolean isCheckedWumpus;
    private boolean isCheckedCornerPit;
    
       //CONSTRUCTOR
   public Node(int x, int y)
   {
       this.x = x;
       this.y = y;
       this.levelDanger = 0;
   }
   
    public int  getX(){return x;}
    public int  getY(){return y;}
    public void setGValue(int gValue){this.gValue = gValue;}
    public int getGValue(){return  gValue;}
    public void setFValue(int fValue){this.fValue = fValue;}
    public int getFValue(){return  fValue;}
    public int getLevelDanger(){return levelDanger;}
    public void setLevelDanger(int levelDanger){this.levelDanger = levelDanger;}
    public boolean isPit(){return isPit;}
    public void isPit(boolean isPit){this.isPit = isPit;}
    public boolean isCheckedPit(){return isCheckedPit;}
    public void isCheckedPit(boolean isCheckedPit){this.isCheckedPit = isCheckedPit;}
    public boolean isCheckedBreeze(){return isCheckedBreeze;}
    public void isCheckedBreeze(boolean isCheckedBreeze){this.isCheckedBreeze = isCheckedBreeze;}
    public boolean isCheckedCornerPit(){return isCheckedCornerPit;}
    public void isCheckedCornerPit(boolean isCheckedCornerPit){this.isCheckedPit = isCheckedCornerPit;}
    public boolean isCheckedSquarePit(){return isCheckedSquarePit;}
    public void isCheckedSquarePit(boolean isCheckedSquarePit){this.isCheckedSquarePit = isCheckedSquarePit;}
    public boolean isWumpus(){return isWumpus;}
    public void isWumpus(boolean isWumpus){this.isWumpus = isWumpus;}
    public boolean isCheckedWumpus(){return isCheckedWumpus;}
    public void isCheckedWumpus(boolean isCheckedWumpus){this.isCheckedWumpus = isCheckedWumpus;}

//
//    @Override
//    public boolean equals(Object obj) 
//    {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Node other = (Node) obj;
//        if (this.x != other.x) {
//            return false;
//        }
//        if (this.y != other.y) {
//            return false;
//        }
//       
//        if (this.numVisit != other.numVisit) {
//            return false;
//        }
//        if (!Arrays.equals(this.percepts, other.percepts)) {
//            return false;
//        }
//        if (!Objects.equals(this.adjacentNodes, other.adjacentNodes)) {
//            return false;
//        }
//        return true;
//    }
   
   

    
    
}
