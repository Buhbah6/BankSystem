package banksystem;

import java.util.Scanner;

/**
 * ATM class -> uses other classes to complete the transactions
 * @author Anthony Nadeau
 */
public class AutomatedTellerMachine {
    private String atmID;
    private User user;
    private Account account;
    private static int nextID = 1;

    public AutomatedTellerMachine() {
        this.atmID = String.format("%06d", nextID++);
        this.user = null;
        this.account = null;
    }

    public AutomatedTellerMachine(String atmID) {
        this.atmID = atmID;
        this.user = null;
        this.account = null;
    }
    
    public AutomatedTellerMachine(AutomatedTellerMachine automatedTellerMachine) {
        this.atmID = automatedTellerMachine.atmID;
        this.user = new User(automatedTellerMachine.user);
        this.account = new Account(automatedTellerMachine.account);
    }
    /**
     * The pipeline is the order of which things are done in the ATM
     */
    public void pipeline() {
        printWelcome();
        
        readUserId();
        if (user == null)
            System.exit(1);      
        if (!inputPin())
            System.exit(2);      
        
        chooseAccount();
        do {
            int oper = chooseOperation();
            switch (oper) {
                case 1:
                    withdraw();
                    break;
                case 2:
                    deposit();
                    break;
                default:
                    displayBalance();
            }
        } while(doesContinue());
        
        printGoodBye();
    }
    
    
    public void printWelcome() {
        System.out.println("Welcome to use our ATM");
    }
    
    
    public void readUserId() {
        Scanner console = new Scanner(System.in);
        
        System.out.println("Please enter your user ID");
        String inputId = console.next();
        
        for (int i = 0; i < Bank.getUsers().size(); i++)
            if (inputId.equals(Bank.getUsers().get(i).getUserId())) {
                user = Bank.getUsers().get(i);
                return;
            }
        
        user = null;
    }
    
        public boolean inputPin() {
        Scanner console = new Scanner(System.in);
        int maxTry = 3;
        
        for (int i = 0; i < maxTry; i++){
            System.out.println("Please enter your user pin");
            String password = console.next();
            if (user.getPassword().equals(password))
                return true;
            System.out.println("Your pin is wrong");
        }
        System.out.println("You input pin wrong for 3 times");
        return false;
    }
    
    public void chooseAccount() {
        Scanner console = new Scanner(System.in);
        
        System.out.println("Please choose the account you want to operate with"
                + "\n\t1. Checking account"
                + "\n\t2. Saving account");
        int accountChoice = console.nextInt();
        
        account = accountChoice == 1 ? user.getCheckingAccount() : user.getSavingAccount();
    }
    
    public int chooseOperation() {
        Scanner console = new Scanner(System.in);
        
        System.out.println("Please choose the operation"
                + "\n\t1. Withdraw"
                + "\n\t2. Deposit"
                + "\n\t3. Display balance");
        int operation = console.nextInt();
        
        return operation;
    }
    
    public boolean withdraw() {
        Scanner console = new Scanner(System.in);
        
        System.out.println("How much do you want to withdraw? ");
        double amount = console.nextDouble();
        if (account.getBalance() < amount) {
            System.out.println("Sorry, you don't have enough balance.");
            return false;
        }
        account.setBalance(account.getBalance() - amount);
        System.out.println("Withdraw successfully");
        user.getHistory().add(new Record("withdraw", amount, atmID));
        return true;
    }
    
    public boolean deposit() {
        Scanner console = new Scanner(System.in);
        
        System.out.println("How much do you want to deposit? ");
        double amount = console.nextDouble();

        account.setBalance(account.getBalance() + amount);
        System.out.println("deposit successfully");
        user.getHistory().add(new Record("deposit", amount, atmID));
        return true;
    }
    
    public void displayBalance() {
        System.out.printf("Your current balance is $%.2f\n", account.getBalance());
    } 
    
    public boolean doesContinue() {
        Scanner console = new Scanner(System.in);
        
        System.out.println("Do you want to do another operation? ");
        System.out.println("0. No");
        System.out.println("1. Yes");
        int answer = console.nextInt();     // 0, 1
        
        return answer == 1;
    }
    
    public void printGoodBye() {
        System.out.println("Thank you for using our ATM. Goodbye");
    }
    
    public boolean equals(AutomatedTellerMachine automatedTellerMachine) {
        return this.atmID.equals(automatedTellerMachine.atmID);
    }

    @Override
    public String toString() {
        return String.format("%-10s: %s", "ATM ID", atmID);
    }

    public String getAtmID() {
        return atmID;
    }

    public void setAtmID(String atmID) {
        this.atmID = atmID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
