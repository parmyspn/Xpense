package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

//MyExpense tracker application
public class MyExpensesTrackerApp {

    private static final String JSON_STORE = "./data/myExpenses.json";

    private MyExpenses myExpenses;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS : runs the tracker application
    public MyExpensesTrackerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command = null;
        initialize();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add an expense");
        System.out.println("\tb -> view expenses by month");
        System.out.println("\tc -> view expenses by category");
        System.out.println("\td -> view the total by month");
        System.out.println("\te -> view the total by category");
        System.out.println("\tf -> view the categories");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addNewExpense();
        } else if (command.equals("b")) {
            doMonthView();
        } else if (command.equals("c")) {
            doCategoryView();
        } else if (command.equals("d")) {
            doMonthTotal();
        } else if (command.equals("e")) {
            doCategoryTotal();
        } else if (command.equals("f")) {
            viewCategories();
        }  else if (command.equals("s")) {
            saveMyExpenses();
        } else if (command.equals("l")) {
            loadMyExpenses();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes myExpenses
    private void initialize() {

        myExpenses = new MyExpenses();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES : this
    // EFFECTS : handles an expense addition to the list
    private void addNewExpense() {

        System.out.println("Enter the expense by the following format: title amount date category  ");
        String en = input.next().toLowerCase();
        String[] entered = en.split(" ");
        if (entered.length < 5 && entered.length > 0) {
            Expense ex = createExpense(entered);
            myExpenses.addExpense(ex);
            System.out.println("The Expense : " + ex.toString() + " added successfully");
        } else {
            System.out.println("Invalid Expense");
        }


    }

    //EFFECTS : creates an expense based on the user input and returns it

    private Expense createExpense(String[] entered) {
        Expense ex;
        switch (entered.length) {
            case 4:
                ex = new Expense(entered[0], Double.parseDouble(entered[1]), LocalDate.parse(entered[2]), entered[3]);
                break;
            case 3:
                if (entered[2].contains("-")) {
                    ex = new Expense(entered[0], Double.parseDouble(entered[1]), LocalDate.parse(entered[2]));
                } else {
                    ex = new Expense(entered[0], Double.parseDouble(entered[1]), entered[2].toLowerCase());
                }
                break;
            case 2:
                ex = new Expense(entered[0], Double.parseDouble(entered[1]));
                break;
            default:
                ex = new Expense(entered[0]);
                break;

        }
        return ex;
    }

    //EFFECTS : views the expenses within a month the user asks for
    private void doMonthView() {
        System.out.println("Enter the month number to view its expenses: ");
        int month = input.nextInt();
        if (month > 12 || month < 1) {
            System.out.println("Invalid month");
        } else {
            System.out.println(myExpenses.getExpenseInMonthOf(month));
        }

    }

    //EFFECTS : views the expenses within a category the user asks
    private void doCategoryView() {
        System.out.println("Enter the category to view its expenses: ");
        String cat = input.next();
        System.out.println(myExpenses.getExpenseOfCategory(cat));

    }

    //EFFECTS : views the total money spent in the month user asks for
    private void doMonthTotal() {
        System.out.println("Enter the month to view its total spending: ");
        int month = input.nextInt();
        System.out.println(myExpenses.getSumOfExpensesByMonth(month));
    }

    //EFFECTS : views the total money spent in the category user asks for
    private void doCategoryTotal() {
        System.out.println("Enter the category to view its total spending: ");
        String cat = input.next();
        System.out.println(myExpenses.getSumOfExpensesByCategory(cat));
    }

    //EEFECTS : views all the categories existing in the list
    private void viewCategories() {
        System.out.println(myExpenses.getExistingCategories());
    }


    // EFFECTS: saves the expense to file
    private void saveMyExpenses() {
        try {
            jsonWriter.open();
            jsonWriter.write(myExpenses);
            jsonWriter.close();
            System.out.println("Saved myExpenses to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads myExpenses from file
    private void loadMyExpenses() {
        try {
            myExpenses = jsonReader.read();
            System.out.println("Loaded myExpenses from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
