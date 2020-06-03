package comment_percentage;

import java.util.ArrayList;
import java.util.List;

/**
 This is the Aisle class for Test Case Two
 */
public class CP_TCTwo_Aisle {


    /* These are the
    variables for the class

     */
    private String name;
    private List<CP_TCTwo_Item> itemsInAisle;

    //Constructor
    public CP_TCTwo_Aisle() {

        itemsInAisle = new ArrayList<>();

    }

    public CP_TCTwo_Aisle(String name) {

        this.name = name;
        itemsInAisle = new ArrayList<>();

    }

    /*
    Getters and setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CP_TCTwo_Item> getItemsInAisle() {
        return itemsInAisle;
    }

    public void setItemsInAisle(List<CP_TCTwo_Item> itemsInAisle) {
        this.itemsInAisle = itemsInAisle;
    }

    //This adds items to aisles
    public void addItemsToAisle(CP_TCTwo_Item item) {
        itemsInAisle.add(item);
    }

    /*
    This removes an item from aisle
    Using it's itemName
     */
    public CP_TCTwo_Item removeItemFromAisle(String itemName) {

        for(int i=0;i<itemsInAisle.size();i++) {
            if(itemsInAisle.get(i).getName().equalsIgnoreCase(itemName)) {
                return itemsInAisle.get(i);
            }
        }

        return null;
    }

    /*
    This gets an item from aisle
    using it's item name

     */
    public CP_TCTwo_Item getItemFromAisle(String itemName) {

        for(int i=0;i<itemsInAisle.size();i++) {
            if(itemsInAisle.get(i).getName().equalsIgnoreCase(itemName)) {
                return itemsInAisle.get(i);
            }
        }
        return null;
    }

}
