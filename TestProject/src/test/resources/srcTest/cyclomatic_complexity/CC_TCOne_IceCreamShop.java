package cyclomatic_complexity;

public class CC_TCOne_IceCreamShop {

    private String day;
    private boolean isClosed;
    private int price;
    private boolean hasDiscount;
    private EmployeeStatus employeeStatus;
    private int workingHours;
    private String flavourDiscount;
    private String[] employeeNames;
    private int deliveryDistance;
    private boolean hasDelivery;
    private boolean hasVoucher;
    private int amountOfScoops;
    private String[] daysOfWeek;

    enum EmployeeStatus {
        FullTime,PartTime,Casual;
    }

    public CC_TCOne_IceCreamShop() {
        day = "Tuesday";
        isClosed = false;
        price = 5;
        hasDiscount = true;
        employeeStatus = EmployeeStatus.FullTime;
        flavourDiscount = "";
        employeeNames = new String[] {"Bob","Olivia","Lorenzo","Kelly"};
        deliveryDistance = 10;
        hasDelivery = false;
        //flavour = "Vanilla";
        hasVoucher = false;
        //customerHungerLevel = 3;
        amountOfScoops = 0;
        //shiftHours = 8;
        daysOfWeek = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    }

    public CC_TCOne_IceCreamShop(String day, boolean isClosed, int price,
                                 boolean hasDiscount, EmployeeStatus employeeStatus, String flavourDiscount,
                                 int deliveryDistance, boolean hasDelivery, String flavour, boolean hasVoucher,
                                 int customerHungerLevel, int amountOfScoops, int shiftHours) {

        this.day = day;
        this.isClosed = isClosed;
        this.price = price;
        this.hasDiscount = hasDiscount;
        this.employeeStatus = employeeStatus;
        this.flavourDiscount = flavourDiscount;
        this.deliveryDistance = deliveryDistance;
        this.hasDelivery = hasDelivery;
        //this.flavour = flavour;
        this.hasVoucher = hasVoucher;
        //this.customerHungerLevel = customerHungerLevel;
        this.amountOfScoops = amountOfScoops;
        //this.shiftHours = shiftHours;

        employeeNames = new String[] {"Bob","Olivia","Lorenzo","Kelly"};
        daysOfWeek = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    }



    // test if
    public void daysClosed() {

        if(day.equalsIgnoreCase("Monday")) {
            isClosed=true;

        }
        System.out.println("Is closed: "+isClosed);
    }

    // test if else
    public int iceCreamPrice() {
        int originalPrice = price;

        int resultantPrice=0;

        if(hasDiscount == true) {
            resultantPrice = originalPrice - 1;
        } else {
            resultantPrice = originalPrice;
        }

        return resultantPrice;
    }

    // test if, else if
    public void delivery() {
        int addedPrice = 0;

        if(deliveryDistance<20) {
            addedPrice = 0;
        } else if (deliveryDistance>=20) {
            addedPrice = 5;
        }

        System.out.println("Added Price for delivery is: "+addedPrice);
    }

    // test if, else if, else
    public void workingHours() {

        if(employeeStatus.equals(EmployeeStatus.FullTime)) {
            workingHours = 40;
        } else if (employeeStatus.equals(EmployeeStatus.PartTime)) {
            workingHours = 25;
        } else {
            workingHours = 20;
        }

        System.out.println("Working hours is: "+workingHours);
    }

    // test switch case
    public void flavourOnDiscount() {

        int number = 0;

        switch(day) {
            case "Tuesday":
                flavourDiscount = "Vanilla";
                break;
            case "Wednesday":
                flavourDiscount = "Mint Choc Chip";
                break;
            case "Thursday":
                flavourDiscount = "Strawberry";
                break;
            case "Friday":
                flavourDiscount = "Lime Sherbert";
                break;
            case "Saturday":
                flavourDiscount = "Mango";
                break;
            case "Sunday":
                flavourDiscount = "Bubblegum";
                switch(number) {
                    case 0:
                        System.out.println("Zero");
                        break;
                    /*case 1:
                        System.out.println("One");
                        break;*/
                    /*case 2:
                        System.out.println("two");
                        break;*/
                    default:
                        System.out.println("More than zero");
                }
                break;
            default:
                System.out.println("N/A");


        }

        System.out.println("Flavour Discount is: "+flavourDiscount);
    }

    // test for loop and for each loop
    public int salariesExpensesPerWeek() {
        int salaryPerWeekPerEmployee = 20 * 40;
        int totalSalaryExpensePerWeek = 0;
        int a =0;
        for(int i=0;i<employeeNames.length;i++) {
            totalSalaryExpensePerWeek += salaryPerWeekPerEmployee;

            for(String s:daysOfWeek) {
                a++;
            }

        }

        return totalSalaryExpensePerWeek;
    }

    // test ||
    public void voucher(String flavour) {

        int a =0;
        int c = 3;
		/*if(flavour.equalsIgnoreCase("Vanilla")&&day.equalsIgnoreCase("Friday")&&employeeNames.length<3)
				//flavour.equalsIgnoreCase("Strawberry")||
				//flavour.equalsIgnoreCase("Chocolate")||flavour.equalsIgnoreCase("Bubblegum"))
		{
			hasVoucher = true;

		}*/
        //int a;
        if((8+12)<=3||(9-2)>1&&7%2>1||(3*5)%8>2) {
            System.out.println("Less");
        }
        //(8+12)<=3||(9-2)>1&&7%2>1||(3*5)%8>2

        System.out.println("Has voucher is: "+hasVoucher);
    }

    // test while loop
    public void scoops(int customerHungerLevel) {
		int a = 3;
		int b=5;


        while(customerHungerLevel!=0) {
            amountOfScoops++;
            customerHungerLevel--;


			while(a!=0) {
				b++;
				a--;
			}

        }
        System.out.println("Amount of scoops of ice cream is: "+amountOfScoops);
    }

    // test do while loop
    public int countHoursWorked(int shiftHours) {
        int hoursWorked=0;


        do {
            hoursWorked++;
            shiftHours--;

			int a = 3;
			int b= 5;

			 //System.out.println("One");

			 do {
				 b++;
				 a--;
				//System.out.println(a);
			 }while(a!=0);

			 //System.out.println("Three");

        }while(shiftHours!=0);

        return hoursWorked;
    }

    //test try catch throw finally
    //result: try, throw, finally v(G) +0; catch v(G) +1 for each
    public void catchMethod() {
        try {
            String intNumber = "5A";
            Integer.parseInt(intNumber);
            //throw new NullPointerException("demo");
        }
        catch(NumberFormatException e) {
            System.out.println("Convertion error");
            //throw e;
        }
        //catch (NullPointerException e) {
        //	System.out.println("Null exception");
        //}
        //finally
        //{
        //	System.out.println("Completed");
        //}
    }

    public void tryThrowFinally() {
        try {
            String intNumber = "5A";
            Integer.parseInt(intNumber);
            throw new NullPointerException("demo");
        }
        catch(NumberFormatException e) {
            System.out.println("Convertion error");
            //throw e;
        }
        //catch (NullPointerException e) {
        //	System.out.println("Null exception");
        //}
        finally
        {
        	System.out.println("Completed");
        }
    }

    // test multiple conditionals
    public int electricityBillPerMonth(int averageElectricityBillPerDay) {

        int weeklyElectricityBill = 0;

        for(int i=0;i<daysOfWeek.length;i++) {
            if(daysOfWeek[i].equalsIgnoreCase("Monday")) {

                weeklyElectricityBill += 0;

            } else {

                weeklyElectricityBill += averageElectricityBillPerDay;
            }
        }

        return weeklyElectricityBill * 4;


    }

    // test nested conditionals
    public int incomePerMonth(int iceCreamSoldPerHour) {

        //int monthsPerQuarter = 3;
        int weeksPerMonth = 4;
        int dayIncome = 0;
        int averageIceCreamPrice = 5;
        int shopOpeningHours = 12;
        int totalIncome = 0;

        for(int i=0;i<weeksPerMonth;i++) {

            for(int j =0;j<daysOfWeek.length;j++) {

                if(daysOfWeek[j].equalsIgnoreCase("Monday")) {

                    dayIncome = 0;
                    totalIncome += dayIncome;

                } else if(daysOfWeek[j].equalsIgnoreCase("Saturday")) {

                    dayIncome = iceCreamSoldPerHour * averageIceCreamPrice * shopOpeningHours * 2;
                    totalIncome += dayIncome;

                } else {

                    dayIncome = iceCreamSoldPerHour * averageIceCreamPrice * shopOpeningHours;
                    totalIncome += dayIncome;
                }
            }
        }

        return totalIncome;


    }

    //test nested conditionals
    public int countPriceForSingleFlavour(int numOfScoops,String flavour) {
        int purchasePrice = 0;

        while(numOfScoops!=0) {

            if(flavour.equalsIgnoreCase("Chocolate")||flavour.equalsIgnoreCase("Strawberry")
                    ||flavour.equalsIgnoreCase("Vanilla")) {
                purchasePrice += 4;
            } else {

                switch(flavour) {
                    case "Mango":
                        purchasePrice += 7;
                        break;
                    case "Bubblegum":
                        purchasePrice += 6;
                        break;
                    default:
                        purchasePrice += 5;
                        break;
                }

            }

            numOfScoops--;
        }

        return purchasePrice;
    }









}
