package comment_percentage;

/**
 * This is the Supermarket class for Test Case Two
 *
 */
public class CP_TCTwo_Supermarket {

    private CP_TCTwo_Aisle[] allAisles;

    //Constructor
    public CP_TCTwo_Supermarket() {

        //aisle bakery, confectionery, grains, fruits, vegetables, meat, dairy drinks, frozen
        allAisles = new CP_TCTwo_Aisle[9];

        allAisles[0] = new CP_TCTwo_Aisle("bakery");
        allAisles[1] = new CP_TCTwo_Aisle("confectionery");
        allAisles[2] = new CP_TCTwo_Aisle("grains");
        allAisles[3] = new CP_TCTwo_Aisle("fruits");
        allAisles[4] = new CP_TCTwo_Aisle("vegetables");
        allAisles[5] = new CP_TCTwo_Aisle("meat");
        allAisles[6] = new CP_TCTwo_Aisle("dairy");
        allAisles[7] = new CP_TCTwo_Aisle("drinks");
        allAisles[8] = new CP_TCTwo_Aisle("frozen");

    }

    //returns all aisles
    public CP_TCTwo_Aisle[] getAllAisles() {
        return allAisles;
    }

    //sets all aisles
    public void setAllAisles(CP_TCTwo_Aisle[] allAisles) {
        this.allAisles = allAisles;
    }

    /*
    This gets an aisle
    By matching a string

     */
    public CP_TCTwo_Aisle getAnAisle(String s) {
        for(int i=0;i<allAisles.length;i++) {
            if (allAisles[i].getName().equalsIgnoreCase(s)) {
                return allAisles[i];
            }
        }
        return null;
    }

}