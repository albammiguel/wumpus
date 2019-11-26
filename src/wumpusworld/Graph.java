/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.ArrayList;

/**
 *
 * @author S
 */
public class Graph 
{
    World world;
    Node [][] graph = new Node[4][4];
    Node nodeGold;
            
    public Graph(World world)
    {
        this.world = world;
        for (int x = 1 ; x < 5; x++) //create the graph
        {
            for (int y = 1; y < 5; y++) 
            {
                graph[x][y] = new Node(x,y);
                if (world.hasGlitter(x, y)) //find gold
                    nodeGold = graph[x][y];
            }
        }
        
        for (int x = 1 ; x < 5; x++) //set heuristic value for every coordinate
        {
             int hValueX = Math.abs(x - nodeGold.getX());
            for (int y = 1; y < 5; y++) 
            {
                int hValueY = Math.abs(y - nodeGold.getY());
                int hValue = hValueX + hValueY;  //heuristic based on distance to gold (value 0)
                
               // if (world.hasWumpus(i, j))
                    
                graph[x][y].setHeuristicValue(hValue);
            }
        }
    }
    
    public ArrayList<Node> getAdjacentNodes(int x, int y) //4x4
   {
       Node adj;
       ArrayList<Node> adjacentNodes = new ArrayList<Node>();
       
       if (x - 1 > 0) // there is left
       { 
           //if (!world.isVisited(x, y))
                adjacentNodes.add(1,graph[x-1][y]);
       }
       if (x + 1 < 5)  // there is right
       {
            //if (!world.isVisited(x, y))
                adjacentNodes.add(2,graph[x+1][y]);
       }
       if (y - 1 > 0) //there is bottom
       {
            //if (!world.isVisited(x, y))
                adjacentNodes.add(3,graph[x][y-1]);
       }
        if(y + 1 < 5)  //there is upper
        {
             //if (!world.isVisited(x, y))
                adjacentNodes.add(4,graph[x][y+1]);
        }
        return adjacentNodes; //1 left, 2 up, 3 right, 4 down
   }
    
    
   
}
