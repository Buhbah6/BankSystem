package banksystem;

import java.util.ArrayList;

/**
 * @author Anthony Nadeau
 */
public class Bank {
    private static ArrayList<AutomatedTellerMachine> atms = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    
    public static void addUser(String userName, String pin) {
        users.add(new User(userName, pin));
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
