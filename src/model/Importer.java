/** Interface for the two options of creating a network (Generation or Reading from file) (Strategy Pattern)
*** Name: Importer.java
*** Author: Lachlan Murray (19769390)
*** Last Mod: 8/04/2021     */

package model;

public interface Importer
{
    public Network createNetwork(String fileName);
}
