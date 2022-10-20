package model;

import org.json.JSONArray;
import org.json.JSONObject;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

//Represents a list of expenses
public class MyExpenses {

    private ArrayList<Expense> expenses;        //list of expenses

    public static final String[] MONTHS = {
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"};

    //EFFECTS : creates an array list of expenses
    public MyExpenses() {
        expenses = new ArrayList<Expense>();
    }

    //MODIFIES : this
    //EFFECTS  : adds an expense to the list of expenses
    public void addExpense(Expense ex) {
        expenses.add(ex);
        EventLog.getInstance().logEvent(new Event("Added the expense : " + ex));
    }


    //REQUIRES : the entered month is a number from 1 to 12
    //EFFECTS  : displays the expenses with the entered month
    public String getExpenseInMonthOf(int month) {
        String str = MONTHS[month - 1] + " expenses:\n";

        for (Expense ex : expenses) {
            if (ex.getDate().getMonthValue() == month) {
                str += ex.toString();
                str += "\n";
            }
        }
        return str;
    }

    //REQUIRES :  cat is a non-zero length string
    //EFFECTS  :  displays the expenses within this category
    public String getExpenseOfCategory(String cat) {
        String str = cat + " expenses :\n";
        for (Expense ex : expenses) {
            if (ex.getCategory().equals(cat)) {
                str += ex.toString();
                str += "\n";
            }
        }
        return str;
    }

    //REQUIRES : cat is a non-zero length category
    //EFFECTS  : displays the total amount spent in this category
    public double getSumOfExpensesByCategory(String cat) {

        double sum = 0;
        for (Expense ex : expenses) {
            if (ex.getCategory().equals(cat)) {
                sum += ex.getAmount();
            }
        }
        return sum;
    }

    //REQUIRES : month is a valid month number from 1 to 12
    //EFFECTS  : displays the total amount spent in this month
    public String getSumOfExpensesByMonth(int month) {

        double sum = 0;
        for (Expense ex : expenses) {
            if (ex.getDate().getMonthValue() == month) {
                sum += ex.getAmount();
            }
        }
        return "$ " + String.format("%.2f", sum);
    }

//NOT TESTED
    //REQUIRES : month is a valid month number from 1 to 12
    //EFFECTS  : displays the total amount spent in this month
    public String getSumOfExpensesByYear(int year) {

        double sum = 0;
        for (Expense ex : expenses) {
            if (ex.getDate().getYear() == year) {
                sum += ex.getAmount();
            }
        }
        return "$ " + String.format("%.2f", sum);
    }



    //EFFECTS : displays the categories existing so far
    public String getExistingCategories() {
        ArrayList<String> categories = new ArrayList<String>();
        String str = "";
        String category;
        for (Expense ex : expenses) {
            category = ex.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories.toString();
    }

    public ArrayList<String> getCategoriesList() {
        ArrayList<String> categories = new ArrayList<String>();
        String category;
        for (Expense ex : expenses) {
            category = ex.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories;
    }

    //EFFECTS : returns the number of expenses in the list
    public int getSize() {
        return expenses.size();
    }

    //EFFECTS : returns the expense number index-1 in the list
    public Expense getExpense(int index) {
        return expenses.get(index);
    }

    //EFFECTS : returns MyExpenses as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenses", expensesToJson());
        json.put("name", getName());
        return json;
    }

    // EFFECTS: returns expenses in this myExpenses as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : expenses) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

    public double getTotal() {
        double sum = 0;
        for (Expense e : expenses) {
            sum += e.getAmount();
        }
        return sum;
    }

    //Effects : returns MyExpenses as a string
    public String getName() {
        return "MyExpenses";
    }

    //EFFECTS: returns the list of expenses
    public List<Expense> getExpenses() {
        return expenses;
    }
}

