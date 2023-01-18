----------------------------
    ElectricNetwork.jar
----------------------------

Purpose:
    To model the hierarchical nature of a city's power supply (Electricity Network).

Compiling:
    Program uses the Apache Ant tool to compile.
    Simply enter "ant" (not including the quotations) when in the main directory (/Assignment1_19769390) to 
        compile the program.

Execution:
    In order to run the program, change your current directory to the /dist directory and enter: 
        "java -jar ElectricNetwork.jar [flags]"   (not including the quotations or square brackets, with the 
        appropriate flags chosen).
        These flags should be separated by a space, with the following options to choose from:
            First, for controlling the input data:
                -g  : makes the program generate the network to work with
                -r filename : reads in the provided network data from the file specified with "filename"
            Second, for controlling the output of the program:
                -d  : prints the network model to the terminal (one line per tree level, with indentation for 
                        children), as well as the figures for total power consumptions of each time category
                -w filename : program writes the network model to a file of given filename
        For example: 
                java -jar ElectricNetwork.jar -g -w outputdata.csv                  (generates then writes)
                java -jar ElectricNetwork.jar -g -d                                 (generates then displays)
                    WARNING: above will sometimes try to print infinitely, may have to terminate with CTRL+Z
                java -jar ElectricNetwork.jar -r inputdata.csv -d                   (reads from file, displays)
                java -jar ElectricNetwork.jat -r inputdata.csv -w outputdata.csv    (reads then writes)

Notes (to the marker):
    Please refer to "criteria.txt" for how the Code achieves the assignment specification.
    At the moment, the code compiles but does not achieve the desired output.
    I assume both the Display and Write functions to be working as expected through testing, however the classes
        responsible for the creation of a Network appear to be functioning incorrectly.
    I have whittled down the Read class's issue to do with the successive calls of the "processLine" method to
        update the Network container "readNetwork". It appears to run through the input file as expected,
        but for some reason, the previous entries put into the Network container by the temporary variable "loc"
        in the processLine method seem to be overwritten with whatever I assign to loc in later, separate calls
        to the processLine method. i.e. With the default inputdata.csv provided and in the specification, it
        reads in the root node "city", stores this info temporarily into the "loc" variable, and then inserts
        loc into the network. On the second line of inputdata.csv ("northside, city"), processLine is called
        again, and loc is used once again to store the value of the "northside" node, which is then inserted into
        the Network. At this point, when root is printed, it returns "northside", and hence the problem, and why
        the final output to output.csv or terminal is just the last parent node ("southside") and the last leaf
        node read ("building 3"). I couldn't figure out how to resolve this issue in time.
    As for the Generate class's issue, it only seems to either be making a tree of depth 1, or infinitely
        printing a set of child nodes when the display ("-d") option is chosen, or writing ("-w outputdata.csv")
        an arbitrarily large number of duplicate children with an incorrect root to the destination file.
        This is a similar issue to Read's where the root is replaced by the last read/generated LocNode, and all
        of the LeafNode entries are overwritten with the last read/generated LeafNode. I thought it might then 
        have something to do with my Network/Node classes, but I couldn't find anything that stood out to me. 
        I therefore couldn't figure out how to resolve this issue in time.
