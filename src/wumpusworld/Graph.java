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
    Node wumpusNode;
    
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
                adjacentNodes.add(graph[x-1][y]);
       }
       if (x + 1 < 5)  // there is right
       {
                adjacentNodes.add(graph[x+1][y]);
       }
       if (y - 1 > 0) //there is bottom
       {
                adjacentNodes.add(graph[x][y-1]);
       }
        if(y + 1 < 5)  //there is upper
        {
                adjacentNodes.add(graph[x][y+1]);
        }
        return adjacentNodes; 
   }
    
    public Node getNode(int cX, int cY){return graph[cX][cY];}
    public Node getWumpusNode(){ return wumpusNode;}
    public void setWumpusNode(Node wumpusNode){this.wumpusNode = wumpusNode;}
}

