/**
 * Ingrid Sobieski Foehrkolb
 * Java167-001
 *
 * This program demonstrates lambda and stream operations on class Customer.
 * The program displays a menu:
 *      1. Add a new customer
 *          Prompt user for customer information, validates, and adds to list
 *      2. Remove a customer
 *          Prompt user for a last name, verifies, and removes customers
 *      3. View the list of current customers in ascending order by zip code
 *          Uses a lambda statement and stream to sort
 *      4. View the list of customers in descending alphabetical order by state
 *          Uses a lambda statement and stream to sort
 *      5. Search the list of current customers by partial or full name
 *      6. Print a list of current customers
 *      7. Quit and exit the program
 */
package lab3c;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lab3C {//open LAB3C

    public static void main(String[] args) {//Open MAIN

        Customer[] patrons = initializeArray();                                 //Declare new empty array, fill with initialized customers, calls INITIALIZE ARRAY
        List<Customer> customers = new ArrayList<>(Arrays.asList(patrons));     //Declare new list of customers, fill with array of customers
        printList(customers);                                                   //call PRINT LIST on customers to display current list

        Scanner input = new Scanner(System.in);                                 //new Scanner
        String selection;                                                       //to store input

        do {                                                                    //will repeate unless the user enters the number 7
            printMenu();                                                        //call PRINT MENU
            selection = input.next();                                           //get user's selection

            switch (selection) {                                                //Open switch statement based on the selection from user
                case "1": {                                                     //user selects 1
                    customers.add(addNew());                                    //call ADD NEW CUSTOMER, add new customer to list of current customers
                    break;
                }
                case "2": {                                                     //user selects 2
                    customers = removeCustomer(customers);                      //call REMOVE CUSTOMER, store new list with customer(s) removed in customers list
                    break;
                }
                case "3": {                                                     //user selects 3
                    sortByZip(customers);                                       //call SORT BY ZIP, no changes made to customers list
                    break;
                }
                case "4": {                                                     //user selects 4
                    sortByState(customers);                                     //call SORT BY STATE, no changes made to customers list
                    break;
                }
                case "5": {                                                     //user selects 5
                    searchList(customers);                                      //call SEARCH LIST, no changes made to customers list
                    break;
                }
                case "6": {                                                     //user selects 6
                    printList(customers);                                       //call PRINT LIST, no changes made to customers list
                    break;
                }
                case "7": {                                                     //user selects 7
                    System.out.println("Goodbye!");                             //Print goodbye statement
                    break;
                }
                default: {                                                      //user entry is invalid
                    System.out.println("That was an invalid entry.\nPlease " //print error statement
                            + "try again.\n\n");
                    break;
                }
            }

        } while (!selection.equals("7"));                                       //Repeat if the user did not enter 7
    }//End MAIN

    public static void printMenu() {//open PRINT MENU

        System.out.println("Please make a selection:");
        System.out.printf("%-5d%s%n%-5d%s%n%-5d%s%n%-5d%s%n%-5d%s%n%-5d%s%n%-5d"
                + "%s%n", 1, "Add a customer", 2, "Remove a customer", 3, "Sort"
                + " by zip code", 4, "Sort by state", 5, "Search by cus"
                + "tomer name", 6, "Display a list of customers"
                + "", 7, "Quit");
    }//Close PRINT MENU

    public static Customer[] initializeArray() {//open INITIALIZE ARRAY
        Customer[] patrons = new Customer[5];                                   //new empty array of Customers with five spots
        //Construct all the patrons and store them in the array
        patrons[0] = new Customer("Ansel Carter", "8397 Zip Rd", "Ellicott Ch"
                + "ance", "MD", "21999", "123-456-7890");
        patrons[1] = new Customer("Darby Hamsandwich", "147 LedStock Ave", "B"
                + "loomneld", "NJ", "07001", "890-123-4567");
        patrons[2] = new Customer("Ally Gator", "34 Main St", "Bloomneld", "N"
                + "J", "07001", "456-789-0123");
        patrons[3] = new Customer("Amanda Huginkiss", "1222 Mover Rd", "Tulso"
                + "m", "CA", "90001", "345-678-9012");
        patrons[4] = new Customer("Franken Stein", "190 Princeton Ct", "Brigg"
                + "on", "MI", "48003", "678-901-2345");

        return patrons;                                                         //Return full array of initialized customers

    }//Close INITIALIZE ARRAY

    public static void printList(List<Customer> customers) {//open PRINT LIST

        System.out.printf("%-21s%-20s%-20s%-10s%-10s%s%n", "Name", "Address", //Print statement title line for following
                "City", "State", "Zip", "Phone");
        customers.forEach((c) -> {                                              //Prints all items in list passed in
            System.out.println(c.toString());
        });
        System.out.println("");                                                 //Adds one more line of free space
    }//close PRINT LIST

    public static Customer addNew() {//open ADD NEW CUSTOMER
        Scanner input = new Scanner(System.in);                                 //new scanner
        Customer patron = new Customer();                                       //Declare a new empty customer
        System.out.printf("Please enter the following information:%n");         //Prompt

        System.out.print("Name: ");                                             //Prompt
        patron.setName(input.nextLine());                                       //set name to be next input line

        System.out.print("Street Address: ");                                   //Prompt
        patron.setAddress(input.nextLine());                                    //set address to be next input line

        System.out.print("City: ");                                             //Prompt
        patron.setCity(input.nextLine());                                       //set city to be next input line

        System.out.print("State abbreviation: ");                               //Prompt
        patron.setState(stateValidation(input));                                //set state to be the next VALID input line, calls STATE VALIDATION

        System.out.print("Zip code: ");                                         //Prompt
        patron.setZip(zipValidation(input));                                    //set zip to be the next VALID input line, calls ZIP VALIDATION

        System.out.print("Phone number xxx-xxx-xxxx: ");                        //Prompt
        patron.setPhone(phoneValidation(input));                                //set phone to be the next VALID input line, calls PHONE VALIDATION

        return patron;                                                          //returns the new fully valid customer to be added to the list
    }//Close ADD NEW CUSTOMER

    private static String stateValidation(Scanner input) {
        String[] abbrevs = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC",
            "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY",
            "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC",
            "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA",
            "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT",
            "WA", "WI", "WV", "WY"};                                            //Declare array of all state abbreviations
        List<String> usa = new ArrayList<>(Arrays.asList(abbrevs));             //Create List of state abbreviations
        String state;                                                           //to hold input
        do {                                                                    //Executes while input state is NOT contained in state list
            state = input.nextLine().toUpperCase();                             //Takes input as all upper case

            if (!usa.contains(state)) {                                         //if input is invalid and input is not in state list
                System.out.print("Please enter valid state abbreviation:");     //Print error statement
            }

        } while (!usa.contains(state));                                         //if input was not valid, go again

        return state;                                                           //Returns the valid state
    }//Close STATE VALIDATION

    private static String zipValidation(Scanner input) {//Open ZIP VALIDATION
        String zipCode;                                                         //To hold input
        String ZIP = "[0-9]{5}";                                                //Regex of a zip code
        Pattern pattern = Pattern.compile(ZIP);                                 //Declare a pattern that matches zip code regex

        do {                                                                    //Will execute while zip code is invalid
            zipCode = input.nextLine();                                         //get input
            if (!pattern.matcher(zipCode).matches()) {                          //If input is NOT valid
                System.out.print("Please enter numbers only: ");                //Print error statement
            }
        } while (!pattern.matcher(zipCode).matches());                          //If input matches the regex and is valid

        return zipCode;                                                         //Returns zip code
    }//Close ZIP VALIDATION

    private static String phoneValidation(Scanner input) {//open PHONE VALIDATION
        String phone = "";                                                      //to hold input
        String regex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";                            //Regex of a phone number
        Pattern pattern = Pattern.compile(regex);                               //Declare a Pattern of regex                                 

        do {                                                                    //Will execute as long as input does NOT match the regex
            phone = input.nextLine();                                           //Gets user input for phone number

            if (!pattern.matcher(phone).matches()) {                            //if input doesn't match the regex
                System.out.print("Please enter numbers only: ");                //Print statement
            }
        } while (!pattern.matcher(phone).matches());                            //Executes loop again if phone number does NOT match regex

        return phone;                                                           //returns the final valid phone number
    }//Close PHONE VALIDATION

    public static List<Customer> searchList(List<Customer> customers) {//Open SEARCH LIST
        Scanner input = new Scanner(System.in);                                 //New scanner

        System.out.print("Please enter a name to search: ");                    //Prompt
        String search = input.nextLine().toLowerCase();                         //search is the next line in all lower case

        List<Customer> subList = customers.stream() //subList will contain all elements of search that have a
                .filter(c -> c.getName() //name that matches all or some of each customers name
                .toLowerCase()
                .contains(search))
                .collect(Collectors.toList());

        if (!subList.isEmpty()) {                                               //if matches are found
            System.out.println("The following customers were found:");          //print statement
            printList(subList);                                                 //print subList, calls PRINT LIST
        } else {                                                                //if NO matches are found
            System.out.println("No customers were found.");                     //print statement
        }
        return subList;
    }//Close SEARCH LIST

    public static List<Customer> removeCustomer(List<Customer> customers) {//Open REMOVE CUSTOMER
        Scanner input = new Scanner(System.in);                                 //New Scanner

//        System.out.print("Please enter a last name to search: ");               //Prompt
//        String search = " " + input.nextLine().toLowerCase();                                 //Get search term from user
//
//        List<Customer> subList = customers.stream()                             //Sublist contains all items in customers that contain the search term
//                .filter(c -> c.getName().toLowerCase().contains(search))
//                .collect(Collectors.toList());
//
//        if (!subList.isEmpty()) {                                               //If search finds matches
//            System.out.println("The following customers were found:");          //Print stmt
//            printList(subList);                                                 //Print the list of matches, calls PRINT LIST
//        } else {                                                                //If search finds NO matches
//            System.out.println("No customers were found.");                     //Print stmt
//        }
        boolean valid;                                                          //Bool for input validation
        List<Customer> subList = searchList(customers);
        do {                                                                    //Executes while valid is false
            valid = true;                                                       //Reset to true
            System.out.print("Remove these customers from the mailing list? "
                    + "(Y/N) ");                                                //Print confirmation
            String choice = input.nextLine().toUpperCase();                     //choice is the next line in upper case

            switch (choice) {                                                   //Switch statement based on choice
                case "Y": {                                                     //If user confirms removal
                    //use a removeIf()?
                    subList.forEach((c) -> {
                        customers.remove(c);
                    });
                    //If a customer is on the sublist, it is removed from customers
                    System.out.println("The customers were removed.");          //Print statement
                    return customers;                                           //RETURN new customers list
                }
                case "N":                                                       //If user does NOT confirm removal
                    return customers;                                           //RETURN same customers list
                default: {                                                      //If invalid input
                    valid = false;                                              //Valid is now false, causing a loop back in do-while loop
                    System.out.println("Please enter a valid response: ");      //Print error message
                    break;                                                      //Break default case
                }
            }
        } while (!valid);                                                       //Check valid (false if invalid input, true if valid input)

        return customers;                                                       //Included this line to make the compiler happy
    }//Close REMOVE CUSTOMER

    public static void sortByState(List<Customer> customers) {//Open SORT BY STATE method
        Function<Customer, String> stateSort = c -> c.getState();               //Declare function that performs getState() method
        Comparator<Customer> byState = Comparator.comparing(stateSort);         //Declare comparator that compares the states from function

        List<Customer> subList = customers.stream() //sublist contains elements of customers in order by state
                .sorted(byState)
                .collect(Collectors.toList());

        System.out.println("Here is the list of customers sorted by State:");   //Print statement
        printList(subList);                                                     //prints Sublist, calls PRINT LIST
    }//Close SORT BY STATE

    public static void sortByZip(List<Customer> customers) {//Open SORT BY ZIP
        Function<Customer, String> zipSort = Customer::getZip;                  //Declare function that performs getZip()
        Comparator<Customer> byZip = Comparator.comparing(zipSort);             //Declare Comparator that compares zip Strings

        List<Customer> subList = customers.stream() //subList contains elements of customers that are sorted by zip code
                .sorted(byZip)
                .collect(Collectors.toList());

        System.out.println("Here is the list of customers sorted by Zip Code:");//Print statement
        printList(subList);                                                     //Print subList, calls PRINT LIST
    }//Close SORT BY ZIP

}//Close LAB3C

//Question: Should the search be able to do full or partial match, regardless of case or with case?
//Remove customer wants ONLY a last name, the search should ONLY take a last name as well?
//Should we have a toString() method in Customer or is it OK just to format the output? Does he care?
//Verify that search should ONLY take last name not any part of the name
