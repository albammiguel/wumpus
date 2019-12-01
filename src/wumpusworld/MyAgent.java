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
        closedList.add(graph.getNode(1,1));
        openList.add(graph.getNode(1,2));
        openList.add(graph.getNode(2,1));
        
        graph.getNode(1,1).setGValue(0);
        graph.getNode(1,1).isCheckedPit(true);
        graph.getNode(1,1).isCheckedSquarePit(true);
        graph.getNode(1,1).isCheckedCornerPit(true);
        graph.getNode(1,1).isPit(false);
        graph.getNode(1,1).isWumpus(false);
        graph.getNode(1,2).setGValue(1);
        graph.getNode(2,1).setGValue(1);
        
        checkPit();
        checkWumpus();
    }
   
            
    /**
     * Asks your solver agent to execute an action.
     */

    public void doAction()
    {
        System.out.println("--------DOACTION---------");
        
        int oldX = w.getPlayerX(); //Location of the player for the old position
        int oldY = w.getPlayerY();
        
        Node oldNode = graph.getNode(oldX, oldY);
         

        if (w.hasGlitter(oldX, oldY)) //Basic action: Grab Gold if we can.
        {
            w.doAction(World.A_GRAB);
            return;
        }
        if (w.isInPit()) //Basic action: We are in a pit. Climb up.
        {
            w.doAction(World.A_CLIMB);
            return;
        }
       
        if (w.hasPit(oldX, oldY))
        {
            oldNode.isPit(true);
            //System.out.println("I am in a Pit");
        }

        ArrayList<Node> adjacentNodesOldNode = graph.getAdjacentNodes(oldX,oldY);
        rnd = decideNextMove(adjacentNodesOldNode, oldNode);
        
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
        
        int newX = w.getPlayerX(); //Location of the player for the new position
        int newY = w.getPlayerY();
        int bestMove = Integer.MAX_VALUE; 
        int newGValue = Integer.MAX_VALUE;
        
        Node newNode = graph.getNode(newX, newY);

        if (!closedList.contains(newNode)) //update closed list and open list for newNode
        { 
            closedList.add(newNode);
            openList.remove(newNode);
        }

        ArrayList<Node> adjacentNodesNewNode = graph.getAdjacentNodes(newX,newY);
        for (Node n: adjacentNodesNewNode) //update open list for adjacentNodesNewNode
        {
            if (!closedList.contains(n) && !openList.contains(n)) 
                openList.add(n);
        }
        
        int minG;
        for (Node n : openList) //update g value for every node in the openList (best distance)
        {
                ArrayList<Node> adjacentNodes = graph.getAdjacentNodes(n.getX(),n.getY());
                for (Node n1 : adjacentNodes)
                {
                    if (closedList.contains(n1))
                    {
                         minG = n1.getGValue() + 1;
                        if ( minG < n.getGValue()  || n.getGValue() == 0)
                            n.setGValue(minG);
                    }
                }
        }
        
         checkPit();
         if (graph.getWumpusNode() == null)
            checkWumpus();
         if (openList.size() == 1 && graph.getAdjacentNodes(graph.wumpusNode.getX(),graph.wumpusNode.getY()).contains(graph.getNode(newX, newY)) )
         {
             System.out.println("Shoot Wumpus: ");
             w.doAction(World.A_SHOOT);
        }
        for (Node n: openList) //update the f value
        {
            n.setFValue(n.getLevelDanger() + n.getGValue());
        }
//        System.out.println("adjacentNodes del Nuevo Nodo: ");
//        for (Node n : adjacentNodesNewNode)
//            System.out.println("Adjacent: " + n.getX() + " , " + n.getY() + " con g(n) " + n.getGValue()  + " con levelDanger " + n.getLevelDanger());
        System.out.println("CloseList : ");
        for (Node n : closedList)
            System.out.println("Nodo closeList: " + n.getX() + " , " + n.getY() + " con g(n) " + n.getGValue()  + " con levelDanger " + n.getLevelDanger());
        System.out.println("OpenList : ");
        for (Node n : openList)
            System.out.println("Nodo openList: " + n.getX() + " , " + n.getY() + " con g(n) " + n.getGValue()  + " con levelDanger " + n.getLevelDanger());
        
    }    
    
    private void checkPit()
    {
        for (int x = 1 ; x < 5 ; x++)
        {
            for (int y = 1 ; y < 5 ; y++)
            {
                if (w.hasBreeze(x, y)) //in a square I found a breeze
                {
                    if (w.isValidPosition(x-1, y)) //LEFT of the breeze, if any of the surrounding squares does not have a breeze, the it is not a pit
                    {
                        if (graph.getNode(x-1, y).isPit() == false && graph.getNode(x-1, y).isCheckedPit() == false) //we make sure this is not marked as a Pit already and have not been checked previously
                        {
                            graph.getNode(x-1, y).isPit(true); //leave it marked as a Pit
                            graph.getNode(x-1, y).isCheckedPit(true); //leave it checked for the firs time a true, making sure that is not entering here again
                            graph.getNode(x-1, y).setLevelDanger( graph.getNode(x-1, y).getLevelDanger() +  10); //update the danger
                        }
                        if ((w.isValidPosition(x-2, y) && w.isVisited(x-2, y) && !w.hasBreeze(x-2, y)) || //lets look if there are any adjacent square that does not a breeze meaning there is no pit
                                w.isValidPosition(x-1, y+1) && w.isVisited(x-1, y+1) && !w.hasBreeze(x-1, y+1) ||
                                w.isValidPosition(x-1, y-1) && w.isVisited(x-1, y-1) && !w.hasBreeze(x-1, y-1)) 
                        {
                            graph.getNode(x-1, y).setLevelDanger(graph.getNode(x-1, y).getLevelDanger() - ((graph.getNode(x-1, y).getLevelDanger() / 10) * 10)); //if true, we decrease the danger value
                            graph.getNode(x-1, y).isPit(false); //there is no pit
                        }
                    }
                    if (w.isValidPosition(x+1, y)) //RIGHT of the breeze, if any of the surrounding squares does not have a breeze, the it is not a pit
                    {
                        if (graph.getNode(x+1, y).isPit() == false && graph.getNode(x+1, y).isCheckedPit() == false) //we make sure this is not marked as a Pit already and have not been checked previously
                        {
                            graph.getNode(x+1, y).isPit(true); //leave it marked as a Pit
                            graph.getNode(x+1, y).isCheckedPit(true); //leave it checked for the firs time a true, making sure that is not entering here again
                            graph.getNode(x+1, y).setLevelDanger( graph.getNode(x+1, y).getLevelDanger() +  10); //update the danger
                        }
                        if ((w.isValidPosition(x+2, y) && w.isVisited(x+2, y) && !w.hasBreeze(x+2, y)) || //lets look if there are any adjacent square that does not a breeze meaning there is no pit
                                w.isValidPosition(x+1, y+1) && w.isVisited(x+1, y+1) && !w.hasBreeze(x+1, y+1) ||
                                w.isValidPosition(x+1, y-1) && w.isVisited(x+1, y-1) && !w.hasBreeze(x+1, y-1)) 
                        {
                            graph.getNode(x+1, y).setLevelDanger(graph.getNode(x+1, y).getLevelDanger() - ((graph.getNode(x+1, y).getLevelDanger() / 10) * 10)); //if true, we decrease the danger value
                            graph.getNode(x+1, y).isPit(false); //there is no pit
                        }
                    }
                    if (w.isValidPosition(x, y+1)) //UPPER of the breeze, if any of the surrounding squares does not have a breeze, the it is not a pit
                    {
                        if (graph.getNode(x, y+1).isPit() == false && graph.getNode(x, y+1).isCheckedPit() == false) //we make sure this is not marked as a Pit already and have not been checked previously
                        {
                            graph.getNode(x, y+1).isPit(true); //leave it marked as a Pit
                            graph.getNode(x, y+1).isCheckedPit(true); //leave it checked for the firs time a true, making sure that is not entering here again
                            graph.getNode(x, y+1).setLevelDanger( graph.getNode(x, y+1).getLevelDanger() +  10); //update the danger
                        }
                        if ((w.isValidPosition(x, y+2) && w.isVisited(x, y+2) && !w.hasBreeze(x, y+2)) || //lets look if there are any adjacent square that does not a breeze meaning there is no pit
                                w.isValidPosition(x-1, y+1) && w.isVisited(x-1, y+1) && !w.hasBreeze(x-1, y+1) ||
                                w.isValidPosition(x+1, y+1) && w.isVisited(x+1, y+1) && !w.hasBreeze(x+1, y+1)) 
                        {
                            graph.getNode(x, y+1).setLevelDanger(graph.getNode(x, y+1).getLevelDanger() - ((graph.getNode(x, y+1).getLevelDanger() / 10) * 10)); //if true, we decrease the danger value
                            graph.getNode(x, y+1).isPit(false); //there is no pit
                        }
                    }
                    if (w.isValidPosition(x, y-1))  //LOWER of the breeze, if any of the surrounding squares does not have a breeze, the it is not a pit
                    {
                        if (graph.getNode(x, y-1).isPit() == false && graph.getNode(x, y-1).isCheckedPit() == false) //we make sure this is not marked as a Pit already and have not been checked previously
                        {
                            graph.getNode(x, y-1).isPit(true); //leave it marked as a Pit
                            graph.getNode(x, y-1).isCheckedPit(true); //leave it checked for the firs time a true, making sure that is not entering here again
                            graph.getNode(x, y-1).setLevelDanger( graph.getNode(x, y-1).getLevelDanger() +  10); //update the danger
                        }
                        if ((w.isValidPosition(x, y-2) && w.isVisited(x, y-2) && !w.hasBreeze(x, y-2)) || //lets look if there are any adjacent square that does not a breeze meaning there is no pit
                                w.isValidPosition(x-1, y-1) && w.isVisited(x-1, y-1) && !w.hasBreeze(x-1, y-1) ||
                                w.isValidPosition(x+1, y-1) && w.isVisited(x+1, y-1) && !w.hasBreeze(x+1, y-1)) 
                        {
                            graph.getNode(x, y-1).setLevelDanger(graph.getNode(x, y-1).getLevelDanger() - ((graph.getNode(x, y-1).getLevelDanger() / 10) * 10)); //if true, we decrease the danger value
                            graph.getNode(x, y-1).isPit(false); //there is no pit
                        }
                    }
                }
            }
        }
        //check corners
        if (w.hasBreeze(1, 3) && w.hasBreeze(2, 4) && graph.getNode(1, 4).isCheckedPit() == true && graph.getNode(1, 4).isCheckedCornerPit() == false) //upper left
        {
            graph.getNode(1, 4).setLevelDanger( graph.getNode(1, 4).getLevelDanger() +  10);
            graph.getNode(1, 4).isCheckedCornerPit(true);
            graph.getNode(1, 4).isPit(true);
        }
        if (w.hasBreeze(3, 4) && w.hasBreeze(4, 3) && graph.getNode(4, 4).isCheckedPit() == true && graph.getNode(4, 4).isCheckedCornerPit() == false) //upper right
        {
            graph.getNode(4, 4).setLevelDanger( graph.getNode(4, 4).getLevelDanger() +  10);
            graph.getNode(4, 4).isCheckedCornerPit(true);
            graph.getNode(4, 4).isPit(true);
        }
        if (w.hasBreeze(3, 1) && w.hasBreeze(4, 2) && graph.getNode(4, 1).isCheckedPit() == true && graph.getNode(4, 1).isCheckedCornerPit() == false) //bottom right
        {
            graph.getNode(4, 1).setLevelDanger( graph.getNode(4, 1).getLevelDanger() +  10);
            graph.getNode(4, 1).isCheckedCornerPit(true);
            graph.getNode(4, 1).isPit(true);
        }
        
         //check squares (to be more sure there is a pit)
        for (int x = 1 ; x < 5 ; x++)
        {
            for (int y = 1 ; y < 5 ; y++)
            {
                if (w.isVisited(x, y) && !w.hasPit(x, y)) //square of the intersection, lets check if there are breeze in their adjacents
                {
                    if(w.isValidPosition(x-1, y) && w.hasBreeze(x-1, y) &&
                        w.isValidPosition(x, y-1) && w.hasBreeze(x, y-1) &&
                        w.isValidPosition(x-1, y-1) && !w.hasPit(x-1, y-1) && graph.getNode(x-1, y-1).isCheckedSquarePit() == false
                            ) //left, down
                    {
                        graph.getNode(x-1, y-1).setLevelDanger( graph.getNode(x-1, y-1).getLevelDanger() +  10);
                        graph.getNode(x-1, y-1).isCheckedSquarePit(true);
                        graph.getNode(x-1, y-1).isPit(true);
                    }
                    if(w.isValidPosition(x+1, y) && w.hasBreeze(x+1, y) &&
                        w.isValidPosition(x, y-1) && w.hasBreeze(x, y-1) &&
                        w.isValidPosition(x+1, y-1) && !w.hasPit(x+1, y-1) && graph.getNode(x+1, y-1).isCheckedSquarePit() == false
                            ) //right, down
                    {
                        graph.getNode(x+1, y-1).setLevelDanger( graph.getNode(x+1, y-1).getLevelDanger() +  10);
                        graph.getNode(x+1, y-1).isCheckedSquarePit(true);
                        graph.getNode(x+1, y-1).isPit(true);
                    }
                    if(w.isValidPosition(x+1, y) && w.hasBreeze(x+1, y) &&
                        w.isValidPosition(x, y+1) && w.hasBreeze(x, y+1) &&
                        w.isValidPosition(x+1, y+1) && !w.hasPit(x+1, y+1) && graph.getNode(x+1, y+1).isCheckedSquarePit() == false
                            ) //right, top
                    {
                        graph.getNode(x+1, y+1).setLevelDanger( graph.getNode(x+1, y+1).getLevelDanger() +  10);
                        graph.getNode(x+1, y+1).isCheckedSquarePit(true);
                        graph.getNode(x+1, y+1).isPit(true);
                    }
                    if(w.isValidPosition(x-1, y) && w.hasBreeze(x-1, y) &&
                        w.isValidPosition(x, y+1) && w.hasBreeze(x, y+1) &&
                        w.isValidPosition(x-1, y+1) && !w.hasPit(x-1, y+1) && graph.getNode(x-1, y+1).isCheckedSquarePit() == false
                            ) //left, top
                    {
                        graph.getNode(x-1, y+1).setLevelDanger( graph.getNode(x-1, y+1).getLevelDanger() +  10);
                        graph.getNode(x-1, y+1).isCheckedSquarePit(true);
                        graph.getNode(x-1, y+1).isPit(true);
                    }
                }
                
            }
        }
    }
    private void checkWumpus()
    {
        ArrayList<Node> stenchNodes = new ArrayList<Node>();
        for (int x = 1 ; x < 5 ; x++)
        {
            for (int y = 1 ; y < 5 ; y++)
            {
                Node node = graph.getNode(x, y);
                if (w.hasStench(x, y))
                {
                    if (w.isValidPosition(x-1, y) && graph.getNode(x-1, y).isCheckedWumpus() == false) //left
                    {
                        graph.getNode(x-1, y).isCheckedWumpus(true);
                        graph.getNode(x-1, y).setLevelDanger( graph.getNode(x-1, y).getLevelDanger() +  100);
                    }
                    if (w.isValidPosition(x-1, y) && graph.getNode(x-1, y).isCheckedWumpus() == true) //left 
                    {
                        
                        if ((w.isValidPosition(x-2, y) && w.isVisited(x-2, y) && !w.hasStench(x-2, y)) ||
                                w.isValidPosition(x-1, y+1) && w.isVisited(x-1, y+1) && !w.hasStench(x-1, y+1) ||
                                w.isValidPosition(x-1, y-1) && w.isVisited(x-1, y-1) && !w.hasStench(x-1, y-1)) 
                        {
                            graph.getNode(x-1, y).setLevelDanger(graph.getNode(x-1, y).getLevelDanger() - ((graph.getNode(x-1, y).getLevelDanger() / 100) * 100));
                        }
                    }
                    if (w.isValidPosition(x+1, y) && graph.getNode(x+1, y).isCheckedWumpus() == false) //right
                    {
                        graph.getNode(x+1, y).isCheckedWumpus(true);
                        graph.getNode(x+1, y).setLevelDanger( graph.getNode(x+1, y).getLevelDanger() +  100);
                    }
                    if (w.isValidPosition(x+1, y) && graph.getNode(x+1, y).isCheckedWumpus() == true) //right
                    {
                        if ((w.isValidPosition(x+2, y) && w.isVisited(x+2, y) && !w.hasStench(x+2, y)) ||
                                w.isValidPosition(x+1, y+1) && w.isVisited(x+1, y+1) && !w.hasStench(x+1, y+1) ||
                                w.isValidPosition(x+1, y-1) && w.isVisited(x+1, y-1) && !w.hasStench(x+1, y-1)) 
                        {
                            graph.getNode(x+1, y).setLevelDanger(graph.getNode(x+1, y).getLevelDanger() - ((graph.getNode(x+1, y).getLevelDanger() / 100) * 100));
                        }
                    }
                    if (w.isValidPosition(x, y+1) && graph.getNode(x, y+1).isCheckedWumpus() == false) //upper
                    {
                        graph.getNode(x, y+1).isCheckedWumpus(true);
                        graph.getNode(x, y+1).setLevelDanger( graph.getNode(x, y+1).getLevelDanger() +  100);
                    }
                    if (w.isValidPosition(x, y+1) && graph.getNode(x, y+1).isCheckedWumpus() == true) //upper
                    {
                        if ((w.isValidPosition(x, y+2) && w.isVisited(x, y+2) && !w.hasStench(x, y+2)) ||
                                w.isValidPosition(x-1, y+1) && w.isVisited(x-1, y+1) && !w.hasStench(x-1, y+1) ||
                                w.isValidPosition(x+1, y+1) && w.isVisited(x+1, y+1) && !w.hasStench(x+1, y+1)) 
                        {
                            graph.getNode(x, y+1).setLevelDanger(graph.getNode(x, y+1).getLevelDanger() - ((graph.getNode(x, y+1).getLevelDanger() / 100) * 100));
                        }
                    }
                    if (w.isValidPosition(x, y-1) && graph.getNode(x, y-1).isCheckedWumpus() == false) //lower
                    {
                        graph.getNode(x, y-1).isCheckedWumpus(true);
                        graph.getNode(x, y-1).setLevelDanger( graph.getNode(x, y-1).getLevelDanger() +  100);
                    }
                    if (w.isValidPosition(x, y-1) && graph.getNode(x, y-1).isCheckedWumpus() == true) //lower
                    {
                        if ((w.isValidPosition(x, y-2) && w.isVisited(x, y-2) && !w.hasStench(x, y-2)) ||
                                w.isValidPosition(x-1, y-1) && w.isVisited(x-1, y-1) && !w.hasStench(x-1, y-1) ||
                                w.isValidPosition(x+1, y-1) && w.isVisited(x+1, y-1) && !w.hasStench(x+1, y-1)) 
                        {
                            graph.getNode(x, y-1).setLevelDanger(graph.getNode(x, y-1).getLevelDanger() - ((graph.getNode(x, y-1).getLevelDanger() / 100) * 100));
                        }
                    }
                }
            }
        }
        int stenchCounter = 0;
        for (Node n : openList)
        {
            stenchCounter = 0;
            for (Node n1 : graph.getAdjacentNodes(n.getX(),n.getY()))
            {
                if (w.hasStench(n1.getX(), n1.getY()) && !w.isVisited(n.getX(), n.getY()))
                    stenchCounter++;
            }
            if (stenchCounter >= 2)
            {
                graph.setWumpusNode(graph.getNode(n.getX(), n.getY()));
                break;
            }
        }  
        if (graph.getWumpusNode() != null)
        {
            System.out.println("Wumpus is in : " + graph.getWumpusNode().getX() + " , " + graph.getWumpusNode().getY());
            for (int x = 1 ; x < 5 ; x++)
            {
                for (int y = 1 ; y < 5 ; y++)
                {
                    if (graph.getNode(x, y) != graph.getWumpusNode() && graph.getNode(x, y).getLevelDanger() >= 100)
                        graph.getNode(x, y).setLevelDanger(graph.getNode(x, y).getLevelDanger() - ((graph.getNode(x, y).getLevelDanger() / 100) * 100));
                }
            }
        }
    }
    

     /**
     * Genertes a random instruction for the Agent.
     */
//    public int decideRandomMove()
//    {
//      return (int)(Math.random() * 4);
//    }
    
    public int decideNextMove(ArrayList<Node> adjacentNodes, Node oldNode)
    {

        Node bestNode =  null;
        Node possibleNode = null;
        int bestFValue = Integer.MAX_VALUE;
        //ArrayList<Node> bestNodes = new ArrayList<Node>();
        for (Node n: openList)// select best move
        {
                if (bestFValue > n.getFValue())
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
        int cont = 0;
        while (cont < 2)
        {
            System.out.println("Mi bestNode es:" + bestNode.getX() + " , " + bestNode.getY() + " con g(n) " + bestNode.getGValue() + " con levelDanger " + bestNode.getLevelDanger());
            if (bestNode.getX() > oldNode.getX() && !w.hasPit(oldNode.getX() + 1, oldNode.getY())) //derecha
            {
                boolean isNextNodeTheSameAsBestNode = (oldNode.getX() + 1 ==  bestNode.getX() && oldNode.getY() == bestNode.getY());
                if ((w.isVisited(oldNode.getX() + 1, oldNode.getY())) ||  isNextNodeTheSameAsBestNode)
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
            if (bestNode.getX() < oldNode.getX() && !w.hasPit(oldNode.getX() - 1, oldNode.getY())) //izquierda
            {
                boolean isNextNodeTheSameAsBestNode = (oldNode.getX() - 1 ==  bestNode.getX() && oldNode.getY() == bestNode.getY());
                if ((w.isVisited(oldNode.getX() - 1, oldNode.getY()) ) ||  isNextNodeTheSameAsBestNode)
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
            if (bestNode.getY() > oldNode.getY() && !w.hasPit(oldNode.getX(), oldNode.getY() + 1)) //arriba
            {
                boolean isNextNodeTheSameAsBestNode = (oldNode.getX()  ==  bestNode.getX() && oldNode.getY() + 1 == bestNode.getY());
                 if ((w.isVisited(oldNode.getX(), oldNode.getY() + 1) ) ||  isNextNodeTheSameAsBestNode)
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
            if (bestNode.getY() < oldNode.getY() && !w.hasPit(oldNode.getX(), oldNode.getY() - 1)) //abajo
            {
                boolean isNextNodeTheSameAsBestNode = (oldNode.getX()  ==  bestNode.getX() && oldNode.getY() - 1 == bestNode.getY() );
                 if ((w.isVisited(oldNode.getX(), oldNode.getY() - 1) ) ||  isNextNodeTheSameAsBestNode)
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
            for (Node n : graph.getAdjacentNodes(bestNode.getX(),bestNode.getY()))
            {
                if (openList.contains(n))
                {
                    bestNode = n;
                }
            }           
        }
        return 1;
    }
    
}

