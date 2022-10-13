package org.dab;

import org.dab.controllers.AccountController;
import org.dab.services.UserAccountCheckService;

import java.util.Scanner;

import static java.lang.System.exit;

public class DabMain {
    public static void main(String[] args){
        AccountController ctrl = new AccountController();
        System.out.println("init by creating an account for Sabri Arfaoui wih account code : " + ctrl.addAccount("Sabri", "Arfaoui"));
        Scanner sc = new Scanner(System.in);
        String userAccountCode;
        while(true) {
            System.out.println("");
            System.out.println("Welcome To SG Bank user account service");
            System.out.println("If you want to exit please enter 'Q'");
            System.out.println("If you want to proceed please enter your account code");
            System.out.print("Please enter your command or your account code : ");
            try {
                userAccountCode = sc.nextLine();
                if ("q".equalsIgnoreCase(userAccountCode)) {
                    System.out.println("You choose to exit program. Goodbye!!");
                    exit(0);
                }
                ctrl = new AccountController(userAccountCode);
                if(!ctrl.validate()){
                    System.err.println("You have entered a wrong account code. Please retry");
                    continue;
                }
                ctrl.run();
            } catch (Exception ex) {
                System.err.println("You have entered a wrong value. Please retry");
                //ex.printStackTrace();
                //here should be logged to dev team
            }
        }
    }
}
