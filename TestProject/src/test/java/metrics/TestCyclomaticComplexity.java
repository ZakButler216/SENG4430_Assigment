package metrics;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 Test Cyclomatic Complexity functionality
 */
public class TestCyclomaticComplexity {


    /**
     Testing: If predicate
     Test Case: One: IceCreamShop class: daysClosed method.
     */
    @Test
    public void testOneIfPredicate() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"daysClosed");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,2);

    }

    /**
     Testing: If Else predicate
     Test Case: One: IceCreamShop class: iceCreamPrice method.
     */
    @Test
    public void testTwoIf_ElsePredicate() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"iceCreamPrice");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,2);
    }

    /**
     Testing: If-Else if predicate
     Test Case: One: IceCreamShop class: delivery method.
     */
    @Test
    public void testThreeIf_ElseIfPredicate() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"delivery");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,3);
    }

    /**
     Testing: If-Elseif-Else predicate
     Test Case: One: IceCreamShop class: workingHours method.
     */
    @Test
    public void testFourIf_ElseIf_ElsePredicate() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"workingHours");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,3);
    }

    /**
     Testing: Switch-Case predicate
     Test Case: One: IceCreamShop class: flavourOnDiscount method.
     Note: Test Case contains nested switch cases for testing.
     */
    @Test
    public void testFiveSwitch_CasePredicates() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"flavourOnDiscount");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,10);
    }

    /**
     Testing: For and For each predicate
     Test Case: One: IceCreamShop class: salariesExpensesPerWeek method.
     */
    @Test
    public void testSixFor_ForeachPredicates() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"salariesExpensesPerWeek");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,3);

    }


    /**
     Testing: And Or Conditionals Predicates
     Test Case: One: IceCreamShop class: voucher method.
     */
    @Test
    public void testSevenAnd_OrPredicates() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"voucher");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,5);
    }

    /**
     Testing: While predicate
     Test Case: One: IceCreamShop class: scoops method.
     Note: Contains nested while loops for testing.
     */
    @Test
    public void testEightWhilePredicates() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"scoops");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,3);
    }


    /**
     Testing: Do-While predicate
     Test Case: One: IceCreamShop class: countHoursWorked method.
     Note: Contains nested do-while loops for testing.
     */
    @Test
    public void testNineDoWhilePredicates() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"countHoursWorked");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,3);

    }

    /**
     Testing: Try Catch predicate
     Test Case: One: IceCreamShop class: catchMethod method.
     */
    @Test
    public void testTenTry_CatchPredicate() {
        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"catchMethod");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,2);

    }

    /**
     Testing: Try Catch predicate with throw and finally statements
     Test Case: One: IceCreamShop class: tryThrowFinally method.
     */
    @Test
    public void testElevenTry_CatchPredicateWithThrowFinally() {
        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"tryThrowFinally");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,2);
    }

    /**
     Testing: a method with multiple predicates in it.
     Test Case: One: IceCreamShop class: electricityBillPerMonth method.
     */
    @Test
    public void testTwelveMultipleConditionals() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"electricityBillPerMonth");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,3);
    }

    /**
     Testing: a method with nested predicates in it.
     Test Case: One: IceCreamShop class: incomePerMonth method.
     */
    @Test
    public void testThirteenNestedConditionalsOne() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"incomePerMonth");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,5);
    }

    /**
     Testing: a method with nested predicates in it.
     Test Case: One: IceCreamShop class: countPriceForSingleFlavour method.
     */
    @Test
    public void testFourteenNestedConditionalsTwo() {

        //Arrange (Arrange everything needed to run)
        Parser parser = new Parser();
        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCOne_IceCreamShop");

        MethodDeclaration aMethod = parser.getMethodByName(cu,"countPriceForSingleFlavour");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Act (Invoke method and capture result)
        int methodCC = cc.findCCForAMethod(aMethod);

        //Assert (Check)
        Assert.assertEquals(methodCC,8);

    }


    /**
     Testing: getting cyclomatic complexity for a whole compilation unit.
     Test Case: Two: IncidentBean class

     How this is tested:

     CyclomaticComplexity class gets the list of IndividualMethod.
     Which consists of all the methods in the compilation unit.
     Each IndividualMethod has the method name, cyclomatic complexity, and evaluation.

     Then all methods with cyclomatic complexity = 1 are removed, for size reasons.
     Which leaves methods with cyclomatic complexity > 1 left.
     Stored in a methodsWithCCGreaterThanOne list.

     In an external environment, IntelliJ Metrics Reloaded plugin calculates the cyclomatic complexity
     for each method in the class.
     All methods that Metrics Reloaded show cyclomatic complexity > 1
     is entered into a calculatedCCGreaterThanOneList.

     To verify both lists are the same
     A double for loop is utilized to do matching.
     For every calculatedCCGreaterThanOne method, it will search the methodsWithCCGreaterThanOne list
     for a method that matches it's name, cyclomatic complexity, and evaluation level.
     If a match is found, the method is removed from the methodsWithCCGreaterThanOne list.

     Hence if all methods on the methodsWithCCGreaterThanOne list match the calculatedCCGreaterThanOne method,
     the size of the methodsWithCCGreaterThanOne list will equal 0 after the double for loop.

     An if conditional surrounds the double for loop
     to check that both lists (methodsWithCCGreaterThanOne and calculatedCCGreaterThanOne)
     are the of the same size,
     before doing method matching.

     AssertEquals check if methodsWithCCGreaterThanOne list equals 0,
     and passes the test if it is.

     Note: This way of testing is used for all compilation unit tests, i.e. IncidentBean, PostIncidentBean, and UserBean.

     */
    @Test
    public void testFifteenIncidentBean() {

        Parser parser = new Parser();

        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCTwo_IncidentBean");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Gets cyclomatic complexity for each method in a class
        List<CyclomaticComplexity.IndividualMethod> allClassMethodsCC = cc.findCCForAClass(cu);
        List<CyclomaticComplexity.IndividualMethod> methodsWithCCGreaterThanOne = new ArrayList<>();

        //Trims the list down to only methods whose cyclomatic complexity > 1
        for(int i=0;i<allClassMethodsCC.size();i++) {
            if(allClassMethodsCC.get(i).getCC()>1) {

                methodsWithCCGreaterThanOne.add(allClassMethodsCC.get(i));

            }
        }


        //Data from IntelliJ Metrics Reloaded plugin
        //Methods with cyclomatic complexity > 1 for the test case, is entered
        CyclomaticComplexity.IndividualMethod m1 = new CyclomaticComplexity().new IndividualMethod("autoAssignStaff",32, cc.evaluateLevelsForCC(32));
        CyclomaticComplexity.IndividualMethod m2 = new CyclomaticComplexity().new IndividualMethod("setAssignedStaffID",9, cc.evaluateLevelsForCC(9));
        CyclomaticComplexity.IndividualMethod m3 = new CyclomaticComplexity().new IndividualMethod("detectDuplicate",8, cc.evaluateLevelsForCC(8));
        CyclomaticComplexity.IndividualMethod m4 = new CyclomaticComplexity().new IndividualMethod("getAssignedStaffNameAndPosition",4, cc.evaluateLevelsForCC(4));
        CyclomaticComplexity.IndividualMethod m5 = new CyclomaticComplexity().new IndividualMethod("getIncidentKeywordsInString",2, cc.evaluateLevelsForCC(2));
        CyclomaticComplexity.IndividualMethod m6 = new CyclomaticComplexity().new IndividualMethod("getAssignedStaffIDInString",2, cc.evaluateLevelsForCC(2));

        List<CyclomaticComplexity.IndividualMethod> calculatedCCGreaterThanOneList = new ArrayList<>();

        calculatedCCGreaterThanOneList.add(m1);
        calculatedCCGreaterThanOneList.add(m2);
        calculatedCCGreaterThanOneList.add(m3);
        calculatedCCGreaterThanOneList.add(m4);
        calculatedCCGreaterThanOneList.add(m5);
        calculatedCCGreaterThanOneList.add(m6);

        //Checks that calculatedCCGreaterThanOneList and methodsWithCCGreaterThanOne list
        //are of the same size
        if(calculatedCCGreaterThanOneList.size()==methodsWithCCGreaterThanOne.size()) {

            //Double for loop to do matching
            for (int i = 0; i < calculatedCCGreaterThanOneList.size(); i++) {

                String methodName = calculatedCCGreaterThanOneList.get(i).getName();
                int methodCC = calculatedCCGreaterThanOneList.get(i).getCC();
                CyclomaticComplexity.Evaluation methodEval = calculatedCCGreaterThanOneList.get(i).getEval();

                innerloop:
                for (int j = 0; j < methodsWithCCGreaterThanOne.size(); j++) {

                    if (methodsWithCCGreaterThanOne.get(j).getName().equals(methodName)
                            && methodsWithCCGreaterThanOne.get(j).getCC() == methodCC
                            && methodsWithCCGreaterThanOne.get(j).getEval().equals(methodEval)) {

                        //If there is a match, remove method from methodsWithCCGreaterThanOne list
                        methodsWithCCGreaterThanOne.remove(j);
                        break innerloop;
                    }

                }

            }

        }

        //Check that after the double for loop, size of methodsWithCCGreaterThanOne is 0
        //Which means all method info gotten from CyclomaticComplexity class matches IntelliJ MetricsReloaded plugin
        //Thus completing verification
        Assert.assertEquals(methodsWithCCGreaterThanOne.size(),0);

    }


    /**
     Testing: getting cyclomatic complexity for a whole compilation unit.
     Test Case: Two: PostIncidentBean class
     */
    @Test
    public void testSixteenPostIncidentBean() {

        Parser parser = new Parser();

        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCTwo_PostIncidentBean");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Gets cyclomatic complexity for each method in a class
        List<CyclomaticComplexity.IndividualMethod> allClassMethodsCC = cc.findCCForAClass(cu);
        List<CyclomaticComplexity.IndividualMethod> methodsWithCCGreaterThanOne = new ArrayList<>();

        //Trims the list down to only methods whose cyclomatic complexity > 1
        for(int i=0;i<allClassMethodsCC.size();i++) {
            if(allClassMethodsCC.get(i).getCC()>1) {

                methodsWithCCGreaterThanOne.add(allClassMethodsCC.get(i));

            }
        }

        //Data from IntelliJ Metrics Reloaded plugin
        //Methods with cyclomatic complexity > 1 for the test case, is entered
        CyclomaticComplexity.IndividualMethod m1 = new CyclomaticComplexity().new IndividualMethod("getRiskEvaluationAsString",11, cc.evaluateLevelsForCC(11));
        CyclomaticComplexity.IndividualMethod m2 = new CyclomaticComplexity().new IndividualMethod("setAmountOfRatingsReceived",3, cc.evaluateLevelsForCC(3));
        CyclomaticComplexity.IndividualMethod m3 = new CyclomaticComplexity().new IndividualMethod("setPossibleCausesOfIncident",2, cc.evaluateLevelsForCC(2));

        List<CyclomaticComplexity.IndividualMethod> calculatedCCGreaterThanOneList = new ArrayList<>();

        calculatedCCGreaterThanOneList.add(m1);
        calculatedCCGreaterThanOneList.add(m2);
        calculatedCCGreaterThanOneList.add(m3);

        //Checks that calculatedCCGreaterThanOneList and methodsWithCCGreaterThanOne list
        //are of the same size
        if(calculatedCCGreaterThanOneList.size()==methodsWithCCGreaterThanOne.size()) {


            //Double for loop to do matching
            for (int i = 0; i < calculatedCCGreaterThanOneList.size(); i++) {

                String methodName = calculatedCCGreaterThanOneList.get(i).getName();
                int methodCC = calculatedCCGreaterThanOneList.get(i).getCC();
                CyclomaticComplexity.Evaluation methodEval = calculatedCCGreaterThanOneList.get(i).getEval();

                innerloop:
                for (int j = 0; j < methodsWithCCGreaterThanOne.size(); j++) {

                    if (methodsWithCCGreaterThanOne.get(j).getName().equals(methodName)
                            && methodsWithCCGreaterThanOne.get(j).getCC() == methodCC
                            && methodsWithCCGreaterThanOne.get(j).getEval().equals(methodEval)) {

                        //If there is a match, remove method from methodsWithCCGreaterThanOne list
                        methodsWithCCGreaterThanOne.remove(j);
                        break innerloop;
                    }

                }

            }
        }

        //Check that after the double for loop, size of methodsWithCCGreaterThanOne is 0
        //Which means all method info gotten from CyclomaticComplexity class matches IntelliJ MetricsReloaded plugin
        //Thus completing verification
        Assert.assertEquals(methodsWithCCGreaterThanOne.size(),0);


    }

    /**
     Testing: getting cyclomatic complexity for a whole compilation unit.
     Test Case: Two: UserBean class
     */
    @Test
    public void testSeventeenUserBean() {


        Parser parser = new Parser();

        List<CompilationUnit> allCompilationUnits = parser.getCompilationUnits("src\\test\\resources\\srcTest");

        CompilationUnit cu = parser.getCompilationUnitByName(allCompilationUnits,"cyclomatic_complexity.CC_TCTwo_UserBean");

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //Gets cyclomatic complexity for each method in a class
        List<CyclomaticComplexity.IndividualMethod> allClassMethodsCC = cc.findCCForAClass(cu);
        List<CyclomaticComplexity.IndividualMethod> methodsWithCCGreaterThanOne = new ArrayList<>();

        //Trims the list down to only methods whose cyclomatic complexity > 1
        for(int i=0;i<allClassMethodsCC.size();i++) {
            if(allClassMethodsCC.get(i).getCC()>1) {

                methodsWithCCGreaterThanOne.add(allClassMethodsCC.get(i));

            }
        }

        //Data from IntelliJ Metrics Reloaded plugin
        //Methods with cyclomatic complexity > 1 for the test case, is entered
        CyclomaticComplexity.IndividualMethod m1 = new CyclomaticComplexity().new IndividualMethod("getWorkloadBasedOnPriority",5, cc.evaluateLevelsForCC(5));
        CyclomaticComplexity.IndividualMethod m2 = new CyclomaticComplexity().new IndividualMethod("compare",3, cc.evaluateLevelsForCC(3));
        CyclomaticComplexity.IndividualMethod m3 = new CyclomaticComplexity().new IndividualMethod("removeAssignedIncidentsID",3, cc.evaluateLevelsForCC(3));
        CyclomaticComplexity.IndividualMethod m4 = new CyclomaticComplexity().new IndividualMethod("getRolesToDo",2, cc.evaluateLevelsForCC(2));
        CyclomaticComplexity.IndividualMethod m5 = new CyclomaticComplexity().new IndividualMethod("addFailedLoginCounter",2, cc.evaluateLevelsForCC(2));
        CyclomaticComplexity.IndividualMethod m6 = new CyclomaticComplexity().new IndividualMethod("getAssignedIncidentsID",2, cc.evaluateLevelsForCC(2));

        List<CyclomaticComplexity.IndividualMethod> calculatedCCGreaterThanOneList = new ArrayList<>();

        calculatedCCGreaterThanOneList.add(m1);
        calculatedCCGreaterThanOneList.add(m2);
        calculatedCCGreaterThanOneList.add(m3);
        calculatedCCGreaterThanOneList.add(m4);
        calculatedCCGreaterThanOneList.add(m5);
        calculatedCCGreaterThanOneList.add(m6);

        //Checks that calculatedCCGreaterThanOneList and methodsWithCCGreaterThanOne list
        //are of the same size
        if(calculatedCCGreaterThanOneList.size()==methodsWithCCGreaterThanOne.size()) {

            //Double for loop to do matching
            for (int i = 0; i < calculatedCCGreaterThanOneList.size(); i++) {

                String methodName = calculatedCCGreaterThanOneList.get(i).getName();
                int methodCC = calculatedCCGreaterThanOneList.get(i).getCC();
                CyclomaticComplexity.Evaluation methodEval = calculatedCCGreaterThanOneList.get(i).getEval();

                innerloop:
                for (int j = 0; j < methodsWithCCGreaterThanOne.size(); j++) {

                    if (methodsWithCCGreaterThanOne.get(j).getName().equals(methodName)
                            && methodsWithCCGreaterThanOne.get(j).getCC() == methodCC
                            && methodsWithCCGreaterThanOne.get(j).getEval().equals(methodEval)) {

                        //If there is a match, remove method from methodsWithCCGreaterThanOne list
                        methodsWithCCGreaterThanOne.remove(j);
                        break innerloop;
                    }

                }

            }
        }

        //Check that after the double for loop, size of methodsWithCCGreaterThanOne is 0
        //Which means all method info gotten from CyclomaticComplexity class matches IntelliJ MetricsReloaded plugin
        //Thus completing verification
        Assert.assertEquals(methodsWithCCGreaterThanOne.size(),0);

    }








}
