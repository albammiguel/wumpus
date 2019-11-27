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
    Node [][] graph = new Node[5][5];
    
    public Graph(World world)
    {
        this.world = world;
        for (int x = 1 ; x < 5; x++) //create the graph
        {
            for (int y = 1; y < 5; y++) 
                graph[x][y] = new Node(x,y);
        }
    }
    
    public ArrayList<Node> getAdjacentNodes(int x, int y) //4x4
   {
       Node adj;
       ArrayList<Node> adjacentNodes = new ArrayList<Node>();
       
       if (x - 1 > 0) // there is left
       { 
           //if (!world.isVisited(x, y))
                adjacentNodes.add(graph[x-1][y]);
       }
       if (x + 1 < 5)  // there is right
       {
            //if (!world.isVisited(x, y))
                adjacentNodes.add(graph[x+1][y]);
       }
       if (y - 1 > 0) //there is bottom
       {
            //if (!world.isVisited(x, y))
                adjacentNodes.add(graph[x][y-1]);
       }
        if(y + 1 < 5)  //there is upper
        {
             //if (!world.isVisited(x, y))
                adjacentNodes.add(graph[x][y+1]);
        }
        return adjacentNodes; //1 left, 2 up, 3 right, 4 down
   }
    
    public Node getCurrentNode(int cX, int cY)
    {
        return graph[cX][cY];
    }
    
    
   
}
