package wumpusworld;

import java.util.ArrayList;

/**
 * Contains starting code for creating your own Wumpus World agent.
 * Currently the agent only make a random decision each turn.
 * 
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent
{
    private World w;
    int rnd;
    Graph graph;
    private ArrayList<Node> closedList;
    private ArrayList<Node> openList;
    
    /**
     * Creates a new instance of your solver agent.
     * 
     * @param world Current world state 
     */
    public MyAgent(World world)
    {
        w = world;
        graph = new Graph(w);
    }
   
            
    /**
     * Asks your solver agent to execute an action.
     */

    public void doAction()
    {
        //Location of the player
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();

        
//        if(!closedList.contains(c)){
//            closedList.add(c);
//        }
//        

        //Basic action:
        //Grab Gold if we can.
        if (w.hasGlitter(cX, cY))
        {
            w.doAction(World.A_GRAB);
            return;
        }
        
        //Basic action:
        //We are in a pit. Climb up.
        if (w.isInPit())
        {
            w.doAction(World.A_CLIMB);
            return;
        }
        
        //Test the environment
        if (w.hasBreeze(cX, cY))
        {
            System.out.println("I am in a Breeze");
        }
        if (w.hasStench(cX, cY))
        {
            System.out.println("I am in a Stench");
        }
        if (w.hasPit(cX, cY))
        {
            System.out.println("I am in a Pit");
        }
        if (w.getDirection() == World.DIR_RIGHT)
        {
            System.out.println("I am facing Right");
        }
        if (w.getDirection() == World.DIR_LEFT)
        {
            System.out.println("I am facing Left");
        }
        if (w.getDirection() == World.DIR_UP)
        {
            System.out.println("I am facing Up");
        }
        if (w.getDirection() == World.DIR_DOWN)
        {
            System.out.println("I am facing Down");
        }
        
        //decide next move
        //rnd = decideRandomMove();
        
        ArrayList<Node> adjacentNodes = graph.getAdjacentNodes(cX,cY);
        rnd = decideNextMove(adjacentNodes);
        
        if (rnd==0)
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }
        
        if (rnd==1)
        {
            w.doAction(World.A_MOVE);
        }
                
        if (rnd==2)
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }
                        
        if (rnd==3)
        {
            w.doAction(World.A_TURN_RIGHT);
            w.doAction(World.A_MOVE);
        }
                
    }    
    
     /**
     * Genertes a random instruction for the Agent.
     */
//    public int decideRandomMove()
//    {
//      return (int)(Math.random() * 4);
//    }
    
    public int decideNextMove(ArrayList<Node> adjacentNodes)
    {
        int move = 1; //left move by default 
        
        for (Node n: adjacentNodes)
        {
            if (!w.isVisited(n.getX(), n.getY()) && !openList.contains(n)) //add not visited nodes to the openlist
                openList.add(n);
        }
        //funcion auxiliar
        for (Node n: openList)
        {
            //update g value for every node
        }
        
           if (!closedList.contains( adjacentNodes.get(move))) //add new node visited to the closed list
               closedList.add(adjacentNodes.get(move));
       
        return move;
    }
    
    
}

