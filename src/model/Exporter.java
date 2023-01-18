/** Interface for the two options of exporting a network (Displaying or Writing to a file) (Strategy Pattern)
*** Name: Exporter.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 8/04/2021     */

package model;

public interface Exporter
{
    public void exportNetwork(Network network, String fileName);
}
