package cyclomatic_complexity;

public class CC_TCOne_Main {

    public static void main(String[] args) {
        CC_TCOne_IceCreamShop baskinRobbins = new CC_TCOne_IceCreamShop();

        baskinRobbins.daysClosed();

        System.out.println("Ice cream price is: "+baskinRobbins.iceCreamPrice());

        baskinRobbins.delivery();

        baskinRobbins.workingHours();

        baskinRobbins.flavourOnDiscount();

        System.out.println("Salaries expenses per week is: "+baskinRobbins.salariesExpensesPerWeek());

        baskinRobbins.voucher("Vanilla");

        baskinRobbins.scoops(3);

        //int a = baskinRobbins.countHoursWorked(8);
        //System.out.print(a);
        System.out.println("Hours worked is: "+baskinRobbins.countHoursWorked(8));

        System.out.println("Electricity bill per month is: "+baskinRobbins.electricityBillPerMonth(50));

        System.out.println("Income per month is: "+baskinRobbins.incomePerMonth(30));

        System.out.println("Purchase price for customer is: "+baskinRobbins.countPriceForSingleFlavour(2, "Mango"));

        baskinRobbins.catchMethod();

        baskinRobbins.tryThrowFinally();

    }

}
