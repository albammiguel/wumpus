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
        
        this.closedList = new ArrayList<Node>();
        this.openList = new ArrayList<Node>();
        
        graph = new Graph(w);
    }
   
            
    /**
     * Asks your solver agent to execute an action.
     */

    public void doAction()
    {
        System.out.println("--------DOACTION---------");
        //Location of the player
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();
        
        Node currentNode = graph.getCurrentNode(cX, cY);
        
        if (!this.closedList.contains( currentNode)) //add new node visited to the closed list
        { 
            closedList.add(currentNode);
            currentNode.setLevelDanger(0);
            if (this.openList.contains(currentNode))
                this.openList.remove(currentNode);
        }
        
         ArrayList<Node> adjacentNodes = graph.getAdjacentNodes(cX,cY);
         
         System.out.println("adjacentNodes: ");
        for (Node n: adjacentNodes)
        {
            System.out.println("Nodo: " + n.getX() + " , " + n.getY() + " con g(n) " + n.getGValue()  + " con levelDanger " + n.getLevelDanger());
            if (!w.isVisited(n.getX(), n.getY()) && !openList.contains(n)) //add not visited nodes to the openlist
                this.openList.add(n);
        }
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
            currentNode.setPerceptBreeze(true);
             for (Node n: adjacentNodes)
            {
                if (!closedList.contains(n) && !w.isVisited(n.getX(), n.getY()))
                {
                    n.setLevelDanger( n.getLevelDanger() +  10);
                    for (Node n1 : graph.getAdjacentNodes(n.getX(), n.getY())) //check if the premise is wrong and we have more information
                    {
                        if ((n1.getPerceptBreeze() == false) && w.isVisited(n1.getX(), n1.getY()))
                        {
                            //n1.setPerceptBump(true);
                            n.setLevelDanger(n.getLevelDanger() - ((n.getLevelDanger() / 10) * 10));
                        }
                    }
                }
            }
             
            System.out.println("I am in a Breeze");
        }
        if (w.hasStench(cX, cY))
        {
            currentNode.setPerceptStench(true);
            for (Node n: adjacentNodes)
            {
                if (!closedList.contains(n)  && !w.isVisited(n.getX(), n.getY())){
                    n.setLevelDanger( n.getLevelDanger() +  100);
                    for (Node n1 : graph.getAdjacentNodes(n.getX(), n.getY())) //check if the premise is wrong and we have more information
                   {
                       if ((n1.getPerceptStench() == false) && w.isVisited(n1.getX(), n1.getY())){
                           n.setLevelDanger(n.getLevelDanger() - ((n.getLevelDanger() / 100) * 100));
                       }
                   }
                }
            }
            System.out.println("I am in a Stench");
        }
        //UPDATE LEVEL DANGER 
        if (w.hasPit(cX, cY))
        {
            System.out.println("I am in a Pit");
        }
        if (w.getDirection() == World.DIR_RIGHT)
        {
            //System.out.println("I am facing Right");
        }
        if (w.getDirection() == World.DIR_LEFT)
        {
            //System.out.println("I am facing Left");
        }
        if (w.getDirection() == World.DIR_UP)
        {
            //System.out.println("I am facing Up");
        }
        if (w.getDirection() == World.DIR_DOWN)
        {
            //System.out.println("I am facing Down");
        }
        
        //decide next move
        //rnd = decideRandomMove();

        rnd = decideNextMove(adjacentNodes, currentNode);
        
        if (rnd==0) //izquierda
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }
        
        if (rnd==1) //de frente
        {
            w.doAction(World.A_MOVE);
        }
                
        if (rnd==2) //opuesto
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }
                        
        if (rnd==3) //derecha
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
    
    public int decideNextMove(ArrayList<Node> adjacentNodes, Node currentNode)
    {
        int bestMove = Integer.MAX_VALUE; //left move by default 
        int newGValue = Integer.MAX_VALUE;
       // if (!w.isVisited(currentNode.getX(), currentNode.getY()))
        //{
            for (Node n: openList) //update g value for every node
            {
                //if (!w.isVisited(n.getX(), n.getY()))
                 if (!w.isVisited(n.getX(), n.getY()) && adjacentNodes.contains(n))
                 {
                newGValue = currentNode.getGValue() + 1;
                if (newGValue <  n.getGValue() || n.getGValue() == 0)
                        n.setGValue(newGValue);
                 }
            }
        //}
        
        
        for (Node n: openList) //update the f value
        {
            n.setFValue(n.getLevelDanger() + n.getGValue());
        }
        
        Node bestNode =  null;
        Node possibleNode = null;
        int bestFValue = Integer.MAX_VALUE;
        
        System.out.println("openList:");
        for (Node n: openList)// select best move
        {
            System.out.println("Nodo: " + n.getX() + " , " + n.getY() + " con g(n) " + n.getGValue() + " con levelDanger " + n.getLevelDanger());
            if (n.getFValue() < bestFValue )
            {
                bestFValue = n.getFValue();
                bestNode = n;
            }
            if (bestFValue == n.getFValue() && adjacentNodes.contains(n))
            {
                bestFValue = n.getFValue();
                bestNode = n;
            }
        }
        
        System.out.println("Mi bestNode es:" + bestNode.getX() + " , " + bestNode.getY() + " con g(n) " + bestNode.getGValue() + " con levelDanger " + bestNode.getLevelDanger());
        if (bestNode.getX() > currentNode.getX()) //derecha
        {
            boolean isNextNodeTheSameAsBestNode = currentNode.getX() + 1 ==  bestNode.getX() && currentNode.getY() == bestNode.getY();
            if (w.isVisited(currentNode.getX() + 1, currentNode.getY()) ||  isNextNodeTheSameAsBestNode )
            {
                switch(w.getDirection())
                {
                    case World.DIR_UP :
                        return 3;
                    case World.DIR_RIGHT:
                        return 1;
                    case World.DIR_DOWN:
                        return 0;
                    case World.DIR_LEFT:
                        return 2;
                }
            }
        }
        if (bestNode.getX() < currentNode.getX()) //izquierda
        {
            boolean isNextNodeTheSameAsBestNode = currentNode.getX() - 1 ==  bestNode.getX() && currentNode.getY() == bestNode.getY();
            if (w.isVisited(currentNode.getX() - 1, currentNode.getY()) ||  isNextNodeTheSameAsBestNode )
            {
                switch(w.getDirection())
                {
                    case World.DIR_UP :
                        return 0;
                    case World.DIR_RIGHT:
                        return 2;
                    case World.DIR_DOWN:
                        return 3;
                    case World.DIR_LEFT:
                        return 1;
                }  
            }
        }
        if (bestNode.getY() > currentNode.getY()) //arriba
        {
            boolean isNextNodeTheSameAsBestNode = currentNode.getX()  ==  bestNode.getX() && currentNode.getY() + 1 == bestNode.getY();
             if (w.isVisited(currentNode.getX(), currentNode.getY() + 1) ||  isNextNodeTheSameAsBestNode )
             {
                switch(w.getDirection())
                {
                    case World.DIR_UP :
                        return 1;
                    case World.DIR_RIGHT:
                        return 0;
                    case World.DIR_DOWN:
                        return 2;
                    case World.DIR_LEFT:
                        return 3;
                }
             }
            }
        if (bestNode.getY() < currentNode.getY()) //abajo
        {
            boolean isNextNodeTheSameAsBestNode = currentNode.getX()  ==  bestNode.getX() && currentNode.getY() - 1 == bestNode.getY() ;
             if (w.isVisited(currentNode.getX(), currentNode.getY() - 1) ||  isNextNodeTheSameAsBestNode )
             {
                switch(w.getDirection())
                {
                    case World.DIR_UP :
                        return 2;
                    case World.DIR_RIGHT:
                        return 3;
                    case World.DIR_DOWN:
                        return 1;
                    case World.DIR_LEFT:
                        return 0;
                }
             }
        }
        return -1;
    }
    
    
}

