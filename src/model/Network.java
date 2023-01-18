/** Class for holding the full network structure with all nodes
*** Name: Network.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 8/04/2021     */

package model;

import java.util.*;

public class Network
{
    //Classfields
    private static HashSet<Node> nodes;    //Container for all nodes present in the network tree
    private static Node root;          //The root node of the network tree

    //CONSTRUCTOR
    public Network()
    {
        nodes = new HashSet<Node>();
    }

    //ACCESSORS
    //for Set nodes
    public static HashSet<Node> getNodes()
    {
        return nodes;
    }

    //for retrieving the first node (root, as made so in the methods responsible for reading a network...
    //from a file and responsible for generating networks from scratch)
    public static Node getRoot()
    {
        return root;
    }

    //MUTATORS
    //for inserting nodes into the Set
    public static void addNode(Node inNode)
    {
        nodes.add(inNode);
    }

    //for setting the root
    public static void setRoot(Node inRoot)
    {
        root = inRoot;
    }
}
