/** Class responsible for reading in a network model from a file, when program is run with -f "file" flag
*** Name: Read.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 8/04/2021     */

package model;

import java.util.*;
import java.io.*;

public class Read implements Importer
{
    //Method for reading in an Electrical Network model from a given file, currently only supports .csv files
    @Override public Network createNetwork(String fileName) 
    //throws FileNotFoundException, IllegalArgumentException, IOException
    //throws clause not included as superclass method need not always throw these exceptions (i.e. Generate)
    {
        Network readNetwork = new Network();    //Container for read network to be stored/exported in
        FileInputStream fileStream = null;      //Create a stream object for the file to be read
        InputStreamReader rdr;                  //Create an object that can read the stream
        BufferedReader bufRdr;                  //Create a buffered reader object for reading a line at a time
        String line;                            //String to store the line read
        try
        {
            fileStream = new FileInputStream(fileName);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            line = bufRdr.readLine();   //reads first line
            if (line != null)    //Check if file is empty by checking first line isn't null
            {
                while (line != null)
                {
                    readNetwork = processLine(line, readNetwork); //update network with each line read

                    line = bufRdr.readLine();       //read next line
                }
            }
            else
            {
                throw new IllegalArgumentException("The chosen file is empty.");
            }        
            fileStream.close();
        }
        catch (FileNotFoundException e)      //file non-existent
        {
            System.out.println("Couldn't find the file " + e.getMessage() + " Try again!"); 
        }
        catch (IllegalArgumentException e)   //Error when scanning empty file
        {
            System.out.println(e.getMessage()); 
        }
        catch (IOException e)                //Anything else
        {
            if (fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch (IOException ex2)     //Exception when trying to close file
                {
                    System.out.println("Error when attempting to close file");
                }
            }
            System.out.println("Error in file processing: " + e.getMessage());
        }       
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return readNetwork;
    }

    //Processes line depending on formatting (length) and processes/stores data appropriately
    //Should work fine as long as data is structured from network root downwards in read file
    //Structuring of input data file is assumed correct, I could've insert checks for incorrect formatting but...
        //thought it was unnecessary
    private static Network processLine(String line, Network readNetwork)
    {
        String[] splitLine = line.split(",");   //Splits line by delimiter ","
        String[] splitCategory;                 //Used in leaf node creation for its power categories
        LocNode loc;                            //Temporary LocNode (parent node) placeholder Object
                                                //Is doing this not enough to have it be a separate node from...
                                                //the last method call?
        LeafNode leaf;                          //Temporary LeafNode placeholder Object
        HashMap<String, Double> tempPower;      //Temporary power consumption map for leaf node creation
        double tempPowerValue;                  //Temporary double value for "category=value" in leaf lines

        try
        {
            if (splitLine.length == 1)        //Root (a LocNode)
            {
                loc = new LocNode(splitLine[0]);
                readNetwork.addNode(loc);           //Add Root to Network
                readNetwork.setRoot(loc);    
            }
            else if (splitLine.length == 2)   //LocNode
            {
                loc = new LocNode(splitLine[0]);    //create LocNode Object
                
                for (Node tempNode : readNetwork.getNodes())    //Locate parent node to give it a child
                {
                    if (splitLine[1].equals(tempNode.getName()))    //Parent found
                    {
                        tempNode.addChild(loc);     //give parent a child
                        loc.setParent((LocNode)tempNode);   //give child a parent
                        break;  //Should only be one entry with given name as they're unique, so can end here
                    }
                }

                readNetwork.addNode(loc);   //insert read LocNode into network
            }
            else                                //LeafNode
            {
                //Need to construct the HashMap of Power Consumption across different circumstances
                tempPower = new HashMap<String,Double>();
                
                for (int i = 2; i < splitLine.length; i++)  //Goes through every field after [name],[parent_name]
                {
                    splitCategory = splitLine[i].split("=");    //Splits a category field by its "=" delimiter
                    //Could verify category name here
                    tempPowerValue = Double.parseDouble(splitCategory[1]);  //Gets double value
                    tempPower.put(splitCategory[0], tempPowerValue);        //Inserts entry into HashMap
                }
                
                leaf = new LeafNode(splitLine[0], tempPower);

                for (Node tempNode : readNetwork.getNodes())    //Locate parent node to give it a child
                {
                    if (splitLine[1].equals(tempNode.getName()))    //Parent found
                    {
                        tempNode.addChild(leaf);     //give parent a child
                        leaf.setParent((LocNode)tempNode);  //give child a parent
                        break;  //Should only be one entry with given name as they're unique, so can end here
                    }
                }
                readNetwork.addNode(leaf);   //Insert Leaf node to Network
            }
        }

        return readNetwork;
    }
}
