/** Class for holding the network tree's Nodes that aren't leaf nodes (City sub-categories)
*** Name: Node.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 17/04/2021     */

package model;

import java.util.*;
import java.lang.Math;  //for the random number generator

public class LocNode implements Node
{
    //Classfields
    private static String name;    //only letters and digits, UNIQUE identifier of node
    private static HashSet<Node> children; //list of children that have this node as parent
    private static LocNode parent;    //parent node to this node
    
    //CONSTRUCTORS
    //Alternate Constructor
    public LocNode(String inName)
    {
        name = inName;
        children = new HashSet<Node>();        
    }

    //ACCESSORS
    //for name field
    @Override public String getName()
    {
        return name;
    }

    //for list of children
    public static HashSet<Node> getChildren()
    {
        return children;
    }

    //for parent node
    public static LocNode getParent()
    {
        return parent;
    }

    //MUTATORS
    /*//for changing name to another unique identifier      (not necessary)
    public static void setName(String inName)
    {
        //Perform validation that inName does not already exist in tree
        ...
        name = inName;
    }*/

    @Override public void addChild(Node child)
    {
        children.add(child);    //HashSet will not insert a duplicate entry
    }

    @Override public void setParent(LocNode inParent)
    {
        parent = inParent;
    }

    //OTHER
    @Override public boolean isLeaf()
    {
        return false;
    }

    @Override public void displayNode(int indent, HashMap<String,Double> totPower)
    {
        for(int i = 0; i < indent; i++) //print indentation, if there is any
        {
            System.out.print("\t");
        }

        System.out.println(name);   //print node name
        
        indent++;   //increase indent level once before entering lower tree level        

        for (Node child : children)
        {
            child.displayNode(indent, totPower);
        }
        
        indent--;   //reset indent size upon exiting this tree level
    }

    public String toString()     //used in writing to files
    {
        String outStr = name + "," + parent.getName();
        
        return outStr;
    }
}
