package comment_percentage;

/**
 This is the Main class for Test Case Two
 */
public class CP_TCTwo_Main {

    public static void main(String[] args) {

        //aisle bakery, confectionery, grains, fruits, vegetables, meat, drinks, frozen
        CP_TCTwo_Supermarket coles = new CP_TCTwo_Supermarket();

        //System.out.println(coles.getAllAisles().length);


        /*
        Instantiate items
        And add them to aisles
         */

        CP_TCTwo_Item wholemealBread = new CP_TCTwo_Item("Wholemeal Bread",5);
        CP_TCTwo_Item scones = new CP_TCTwo_Item("Scones",6);
        coles.getAnAisle("bakery").addItemsToAisle(wholemealBread);
        coles.getAnAisle("bakery").addItemsToAisle(scones);

        CP_TCTwo_Item peanutButter = new CP_TCTwo_Item("Peanut Butter",7);
        CP_TCTwo_Item jam = new CP_TCTwo_Item("Jam",7);
        CP_TCTwo_Item mintCookies = new CP_TCTwo_Item("Mint Cookies",6);
        coles.getAnAisle("confectionery").addItemsToAisle(peanutButter);
        coles.getAnAisle("confectionery").addItemsToAisle(jam);
        coles.getAnAisle("confectionery").addItemsToAisle(mintCookies);

        //Print out price of an item
        System.out.println(coles.getAnAisle("confectionery").getItemFromAisle("Peanut Butter").getPrice());






    }

}
