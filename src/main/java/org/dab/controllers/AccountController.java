package org.dab.controllers;

import org.dab.services.UserAccountAmountService;
import org.dab.services.UserAccountCheckService;
import org.dab.services.UserAccountCreationService;
import org.dab.services.UserAccountHistoryService;

import java.util.Scanner;

public class AccountController {

    private String accountId;
    public AccountController(){
    }
    public AccountController(String accountId){
        this.accountId = accountId;
    }
    public boolean validate(){
        return new UserAccountCheckService().validate(accountId);
    }

    public String addAccount(String firstName, String lastName){
        return new UserAccountCreationService().createAccount(firstName, lastName);
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        boolean exitAccountServices = false;
        while(!exitAccountServices) {
            try {
                System.out.println("");
                System.out.println("If you want to make a deposit on your account please enter 1");
                System.out.println("If you want to make a withdrawal from your account please enter 2");
                System.out.println("If you want to view your account history please enter 3");
                System.out.println("If you want to return to the previous step please enter 4");
                System.out.print("Please Enter your choice : ");
                String userOperation = sc.nextLine();
                int userOperationCode = Integer.valueOf(userOperation);
                String inputVal;
                int amount;
                switch (userOperationCode) {
                    case 1:
                        System.out.println("You choose to make a deposit");
                        System.out.print("Please enter the amount : ");
                        inputVal = sc.nextLine();
                        amount = Integer.valueOf(inputVal);
                        if(amount<0) {
                            System.out.println("Please enter a positive value");
                        } else {
                            new UserAccountAmountService().addAmount(accountId, amount);
                            System.out.println("Deposit is done !!");
                        }
                        break;
                    case 2:
                        System.out.println("You choose to make a withdrawal");
                        System.out.print("Please enter the amount : ");
                        inputVal = sc.nextLine();
                        amount = Integer.valueOf(inputVal);
                        if(amount<0) {
                            System.out.println("Please enter a positive value");
                        } else {
                            if(new UserAccountAmountService().subAmount(accountId, amount)) System.out.println("Please retrieve your money !!");
                            else System.out.println("This amount is not authorized to withdrawal.");
                        }
                        break;
                    case 3:
                        System.out.println("You choose to display account history");
                        System.out.println(new UserAccountHistoryService().getHistory(accountId));
                        break;
                    case 4:
                        System.out.println("You choose to return to previous step");
                        exitAccountServices = true;
                        break;
                    default:
                        System.err.println("You have entered a wrong operation value. Please retry");
                        break;

                }
            } catch(Exception ex){
                System.err.println("You have entered a wrong value. Please retry");
                //ex.printStackTrace();
                //here should be logged to dev team
            }
        }
    }
}
