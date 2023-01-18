/** Class responsible for displaying a network model to the terminal, when program is run with -d flag,
*** Name: Display.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 17/04/2021     */

package view;

import java.util.*;
import java.io.*;
import model.*;

public class Display implements Exporter
{
    //Method for displaying an Electrical Network model to the user by the terminal, in a specified format
    @Override public void exportNetwork(Network network, String fileName)    //fileName ignored here
    {
        //Format: one line per Node, parent-child relationship denoted by indentation
        //Will be done recusively to print in desired order
        Node root = network.getRoot();          //Get the network root node to begin with (City usually)
        int indent = 0;     //counter for number of indents needed for current tree level
        //Will need to total the power consumption levels during this process of tree traversal
        HashMap<String,Double> totalPower = new HashMap<String,Double>();
        totalPower.put("dm", 0.0);
        totalPower.put("da", 0.0);
        totalPower.put("de", 0.0);
        totalPower.put("em", 0.0);
        totalPower.put("ea", 0.0);
        totalPower.put("ee", 0.0);
        totalPower.put("h", 0.0);
        totalPower.put("s", 0.0);

        //Formatting
        System.out.println("NETWORK TREE:");

        //Full process to print nodes can be found in the method "displayNode()" shared by LocNode and LeafNode
        root.displayNode(indent, totalPower);

        //Formatting
        System.out.println("\nTOTAL POWER CONSUMPTION LEVELS:");

        //Print Power Consumption Levels
        System.out.println("Weekday morning\t: " + totalPower.get("dm"));
        System.out.println("Weekday afternoon\t: " + totalPower.get("da"));
        System.out.println("Weekday evening\t: " + totalPower.get("de"));
        System.out.println("Weekend morning\t: " + totalPower.get("em"));
        System.out.println("Weekend afternoon\t: " + totalPower.get("ea"));
        System.out.println("Weekend evening\t: " + totalPower.get("ee"));
        System.out.println("Heatwave\t: " + totalPower.get("h"));
        System.out.println("Special event\t: " + totalPower.get("s"));
    }
}
 
