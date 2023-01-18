/** Class responsible for generating a network within the given specification, when program run with -g flag
*** Name: Generate.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 8/04/2021     */

package model;

import java.util.*;
import java.lang.Math;  //for the random number generator

public class Generate implements Importer
{
    //I'm sure this is all incredibly convoluted and could likely have been implemented in another way, but this 
    //made sense to me at the time.
    @Override public Network createNetwork(String fileName)  //fileName parameter ignored for this class
    {
        //Create Network Object
        Network genNetwork = new Network();

        //There will always be a root node, this will be our starting point
        LocNode root = new LocNode("1");    
        genNetwork.setRoot(root);   //add generated root to the network
        Node currNode = root;   //the current node being processed, set to node to begin        

        //nodes will be named simply by number, incrementing each time one is added as to keep the names unique
        int nameCounter = 2;    //begins at 2 as 1 is root, increments when node made

        //Choose number of levels that the tree will be
        int treeDepth = (int)(Math.random()*(5) + 1);   //upper bound (5) is inclusive and minimum (1) is inclusive (therefore will be 1, 2, 3, 4 or 5)
        int currDepth = 1;  //current tree level, set to 1 (root) to begin

        //Make a container to store the full list of nodes as recursion happens, to later put into Network
        HashSet<Node> allNodes = new HashSet<Node>();
        allNodes.add(root);

        //Container to hold the tree level's nodes that we're currently processing
        HashSet<Node> nextLevelNodes = new HashSet<Node>(); 
        nextLevelNodes.add(root);   //to begin with, will be a set of one node, the root

        allNodes = generateChildren(nextLevelNodes, allNodes, currDepth, treeDepth, nameCounter);    //Generates a full network tree into allNodes

        //Insert full tree into Network Container
        for (Node temp : allNodes)
        {
            genNetwork.addNode(temp);
        }

        return genNetwork;
    }
        
    //Generates a network tree recursively, with each call of this method being the next tree level down
    private static HashSet<Node> generateChildren(HashSet<Node> nextLevelNodes, HashSet<Node> allNodes, int currDepth, int treeDepth, int nameCounter)
    {
        //Only want generation to occur until the final tree level is hit
        if (currDepth < treeDepth)
        {
            //Retrieve all nodes for the current level of the tree from the previous method call
            HashSet<Node> currLevelNodes = new HashSet<Node>();
            //currLevelNodes = nextLevelNodes;
            for (Node xNode : nextLevelNodes)
            {
                currLevelNodes.add(xNode);
            }
            //Reset the nextLevelNodes Set, to be refilled in this method call
            nextLevelNodes.clear();

            for (Node currNode : currLevelNodes)    //iterate through each node on this tree level
            {
                //Determine number of children (between 2 and 5 inclusive) for currNode
                int numChildren = (int)(Math.random()*(4) + 2);   //will be 2, 3, 4, or 5

                for (int i = 0; i < numChildren; i++)   //iterates for each desired child to be generated
                {        
                    int nodeType;   //integer to denote the type of node to generate
                    Node tempNode;  //Temporary container for the generated node, set to default in case
                                    //the switch fails

                    //Determine node type for generated child (LocNode or LeafNode)
                    if (currDepth == (treeDepth - 1))    //i.e. approaching final level, can only make leaves
                    {
                        nodeType = 2;
                    }
                    else
                    {
                        nodeType = (int)(Math.random()*(2 - 1 + 1) + 1);   //will be 1 or 2
                    }
    
                    switch(nodeType)    //create node Object of appropriate type
                    {
                        case 1:     //LocNode (possible parent) 
                            tempNode = new LocNode(String.valueOf(nameCounter)); //create new LocNode of name
                            nextLevelNodes.add(tempNode);   //add potential parent to next levels node set
                            break;
            
                        case 2:     //LeafNode 
                            tempNode = new LeafNode(String.valueOf(nameCounter), generatePowerConsumptions());
                            break;

                        default:
                            tempNode = new LocNode("!");
                    }
                    nameCounter++;  //increment name counter for next node generated
                    currNode.addChild(tempNode);    //add generated child to child list of current node
                    tempNode.setParent((LocNode)currNode);  //set "parent" reference of child to current node
                    allNodes.add(tempNode);  //Add Node to full node list
                }
            }       

            currDepth++;    //increment level counter before moving to higher level
            //RECURSIVE CALL TO THIS METHOD once children are all generated for current level
            allNodes = generateChildren(nextLevelNodes, allNodes, currDepth, treeDepth, nameCounter);
        }

        return allNodes;
    }        
        
    //The following method generates and returns power-consumptions table, used for every leaf node generated
    //Called in LeafNode classes implementation of Generate() method
    private static HashMap<String,Double> generatePowerConsumptions()
    {
        HashMap<String,Double> tempPower = new HashMap<String,Double>();    //create consumption table container

        tempPower.put("dm", (Math.random()*(1001)));     //power level will be a double between 0 and 1000
        tempPower.put("da", (Math.random()*(1001)));     //for all categories
        tempPower.put("de", (Math.random()*(1001)));
        tempPower.put("em", (Math.random()*(1001)));
        tempPower.put("ea", (Math.random()*(1001)));
        tempPower.put("ee", (Math.random()*(1001)));
        tempPower.put("h", (Math.random()*(1001)));
        tempPower.put("s", (Math.random()*(1001)));

        return tempPower;
    }
}
