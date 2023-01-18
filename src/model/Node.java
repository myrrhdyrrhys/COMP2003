/** Interface for both types of the network tree's Nodes to share
*** Name: Node.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 17/04/2021     */

package model;

import java.util.*;

public interface Node
{
    public boolean isLeaf();    //Returns true if leaf node
    public void displayNode(int indent, HashMap<String,Double> totPower);  //Used in display function
    public String getName();    //want to be able to get the name of a node without knowing type
    public void setParent(LocNode inParent);    //both node types should have a parent LocNode (excluding root)
    public void addChild(Node child);  
}
