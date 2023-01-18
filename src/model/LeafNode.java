/** Class for holding the network tree's Nodes that ARE leaf nodes
*** Name: LeafNode.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 17/04/2021     */

package model;

import java.util.*;

public class LeafNode implements Node
{
    //Classfields
    private static String name;    //only letters and digits, UNIQUE identifier of node
    private static HashMap<String, Double> powerConsumptions;    //container for the 8 variations of power consumption of the leaf unit
    private static LocNode parent;    //parent node to this node

    //CONSTRUCTORS
    //Alternate Constructor
    public LeafNode(String inName, HashMap<String, Double> inPower)
    {
        name = inName;
        powerConsumptions = inPower;    //may need for-each loop to populate the map
    }

    //ACCESSORS
    @Override public String getName()
    {
        return name;
    }
    
    public static HashMap<String, Double> getPower()
    {
        return powerConsumptions;
    }

    public static LocNode getParent()
    {
        return parent;  
    }

    //MUTATORS
    /*//for changing the node's name to another unique identifier       (not necessary)
    public static void setName(String inName)
    {
        //Perform validation that inName does not already exist in tree
        ...
        name = inName;
    }*/

    //for adding a power consumption record to the node's map
    public static void addPower(String inCat, double inPow)
    {
        //Perform verification of Category name (inCat)
        if (checkCategory(inCat))   //If valid, add to map
        {
            powerConsumptions.put(inCat, inPow);    //Overwrites previous entries of same Category if they arise
        }
    }   

    //verifies category name format
    private static boolean checkCategory(String inCat)
    {
        String categories = "dm da de em ea ee h s";
        boolean correct = false;

        if (categories.contains(inCat))  //Verify abbreviation is correct format
        {
            correct = true;
        }

        return correct;
    }

    @Override public void setParent(LocNode inParent)
    {
        parent = inParent;
    }

    //OTHER
    @Override public boolean isLeaf()
    {
        return true;
    }

    //LeafNode's implementation of the recursive "displayNode" function, used in the Display class
    @Override public void displayNode(int indent, HashMap<String,Double> totPower)
    {
        double temp;        

        for(int i = 0; i < indent; i++) //print indentation, if there is any
        {
            System.out.print("\t");
        }
        
        System.out.println(name);   //print node name

        //add to total power values if the nodes map is not empty
        if (!powerConsumptions.isEmpty())
        {
            for (String key : powerConsumptions.keySet())   //iterates through each category in Leaf nodes's map
            {
                temp = powerConsumptions.get(key) + totPower.get(key);  //adds node's value to the total
                totPower.replace(key, temp);    //replaces the old value with new totalled value
            }
        }
    }

    //A method required only in LocNode, but useful to have when dealing with just Node Objects
    @Override public void addChild(Node child){}

    //toString method, used in Write class
    public String toString()     //used in writing to file
    {
        String outStr = name + "," + parent.getName();
        
        for (String key : powerConsumptions.keySet())
        {
            outStr += ("," + key + "=" + Double.toString(powerConsumptions.get(key)));
        }

        return outStr;
    }
}
