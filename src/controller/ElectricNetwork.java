/** Welcome to Electric Network, a tool for modelling a city's ever expanding power grid and its usage!
*** Name: ElectricNetwork.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 17/04/2021                     */

package controller;

import java.util.*;
import model.*;
import view.*;

public class ElectricNetwork
{
    //Option Maps
    private static HashMap<String,Importer> importMap;   //Container for network importation options
    private static HashMap<String,Exporter> exportMap;   //Container for network exportation options

    //Tried to keep things as neat as possible what with having to fit Strategy method use in somewhere
    public static void main(String[] args) throws IllegalArgumentException
    {
        try
        {
            //Create Objects
            Network network = new Network();//Container for generated/read-in network to be stored in
            Generate g = new Generate();    //create Generate Object for createNetwork() method in Generate 
            Read r = new Read();            //create Read Object for createNetwork() method of Read class
            Display d = new Display();      //create Display Object for exportNetwork() method of Display class
            Write w = new Write();          //create Write Object for exportNetwork() method of Write class
            Importer i;     //Holder for the chosen import option
            Exporter e;     //Holder for the chosen export option
            importMap = new HashMap<String,Importer>();
            exportMap = new HashMap<String,Exporter>(); //Initialise map objects

            //Initialise Option Maps
            importMap.put("-g", g);
            importMap.put("-r", r);
            exportMap.put("-d", d);
            exportMap.put("-w", w);

            //Check command line inputs (args)
            if (args.length == 2)   //e.g. -g -d
            {
                if ((args[0].equals("-g"))&&(args[1].equals("-d")))     //Input validation
                {            
                    i = importMap.get("-g");        //gets Generate Object
                    network = i.createNetwork("");  //generate a network within given specifications
                    e = exportMap.get("-d");        //gets Display Object
                    e.exportNetwork(network, "");   //diplay structured network tree to terminal
                }
                else
                {
                    throw new IllegalArgumentException("Incorrect use of arguments, refer to README.txt");
                }
            }
            else if (args.length == 3)  //(-g -w <file>) OR (-r <file> -d)
            {
                if ((args[0].equals("-g"))&&(args[1].equals("-w")))     //-g -w <file>
                {
                    i = importMap.get("-g");            //gets Generate Object
                    network = i.createNetwork("");      //generate a network within given specifications
                    e = exportMap.get("-w");            //gets Write Object
                    e.exportNetwork(network, args[2]);  //write it to specified file (args[2])
                }
                else if ((args[0].equals("-r"))&&(args[2].equals("-d")))  //-r <file> -d
                {
                    i = importMap.get("-r");            //gets Read Object
                    network = i.createNetwork(args[1]); //read in a network from file of name args[1]
                    e = exportMap.get("-d");            //gets Display Object
                    e.exportNetwork(network, "");       //display structured network tree to terminal
                }
                else
                {
                    throw new IllegalArgumentException("Incorrect use of arguments, refer to README.txt");
                }
            }
            else if (args.length == 4)  //-r <file> -w <file>
            {
                if ((args[0].equals("-r"))&&(args[2].equals("-w")))
                {
                    i = importMap.get("-r");            //gets Read Object
                    network = i.createNetwork(args[1]); //read in a network from file of name args[1]
                    e = exportMap.get("-w");            //gets Write Object
                    e.exportNetwork(network, args[3]);  //write it to specified file (args[3])
                }
                else
                {
                    throw new IllegalArgumentException("Incorrect use of arguments, refer to README.txt");
                }
            }
            else
            {
                throw new IllegalArgumentException("Incorrect number of arguments passed!");
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());     //Catches exception and displays appropriate message
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
