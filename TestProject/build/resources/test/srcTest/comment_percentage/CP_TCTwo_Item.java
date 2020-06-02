package comment_percentage;

/**
 * This is the Item class for Test Case Two
 *
 */
public class CP_TCTwo_Item {

    //Variables
    private String name;
    private int price;

    /*
    These are the constructors

     */
    public CP_TCTwo_Item() {

    }


    public CP_TCTwo_Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     These are the getters and setters

     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}