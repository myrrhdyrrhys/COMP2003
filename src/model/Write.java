/** Class responsible for writing a network model to a file, when program is run with -w "file" flag
*** Name: Write.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 8/04/2021     */

package model;

import java.util.*;
import java.io.*;

public class Write implements Exporter
{
    //Method for writing an Electrical Network model to a file of given filename, in a specified format
    @Override public void exportNetwork(Network network, String fileName)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw; 
        String outStr = "";

        try
        {
            fileStrm = new FileOutputStream(fileName);
            pw = new PrintWriter(fileStrm);
            
            //print root
            pw.println(network.getRoot().getName());

            //print parent nodes            
            for (Node n : network.getNodes())
            {
                //check to see if root or a leaf node
                if ((!(n.getName()).equals(network.getRoot().getName())) && (!n.isLeaf()))
                {
                    pw.println(n.toString());
                }
            }
            
            //print leaf nodes
            for (Node n : network.getNodes())
            {
                //check to see if a leaf node
                if (n.isLeaf())
                {
                    pw.println(n.toString());
                }
            }
        
            pw.close();
        }
        catch(IOException e)    //case of IOException thrown when writing to file
        {
            if(fileStrm != null)
            {
                try
                {
                    fileStrm.close();
                }
                catch(IOException ex2)  //exception thrown when attempting to close file
                {
                    System.out.println("Error when attempting to close file!");
                }
            }
            
            System.out.println("Error in writing to file: " + e.getMessage());  //error message output
        }
    }
}
