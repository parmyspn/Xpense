package model;

import org.json.JSONObject;



import java.time.LocalDate;

//Represents an expense with a title, amount in dollars, date in local date and category
public class Expense  {

    private String title;
    private double amount;
    private LocalDate date;
    private String category;


    /*
     * REQUIRES: title has a non-zero length
     * EFFECTS: the title will be assigned to the expense title, today's date to the expense's date,
     *          zero to the expense's amount and other to the category
     */
    public Expense(String title) {

        this.title = title;
        date = LocalDate.now();
        amount = 0;
        category = "other";
    }

    /*
     * REQUIRES: title has a non-zero length , amount is non-negative
     * EFFECTS: the title will be assigned to the expense title, today's date to the expense's date,
     *          amt to expense's amount and other to the category
     */
    public Expense(String title, double amt) {
        this.title = title;
        date = LocalDate.now();
        amount = amt;
        category = "other";
    }
    /*
     * REQUIRES: title has a non-zero length , amount is non-negative , date is a valid date
     * EFFECTS: the title will be assigned to the expense title, today's date to the expense's date,
     *          zero to the expense's amount and other to the category
     */

    public Expense(String title, double amt, LocalDate date) {
        this.title = title;
        this.date = date;
        amount = amt;
        category = "other";
    }
    /*
     * REQUIRES: title has a non-zero length , amt is non-negative , cat has a non-zero length
     * EFFECTS: the title will be assigned to the expense title, today's date to the expense's date,
     *          amt to the expense's amount and cat to the category
     */

    public Expense(String title, double amt, String cat) {
        this.title = title;
        this.date = LocalDate.now();
        amount = amt;
        category = cat;
    }

    /*
     * REQUIRES: title has a non-zero length , amt is non-negative , cat has a non-zero length
     * EFFECTS: the title will be assigned to the expense title, date to the expense's date,
     *          amt to the expense's amount and cat to the category
     */

    public Expense(String title, double amt, LocalDate date, String cat) {
        this.title = title;
        this.date = date;
        amount = amt;
        category = cat;
    }

    //Effects : returns title
    public String getTitle() {
        return title;
    }

    //EFFECTS : returns the date of the expense
    public LocalDate getDate() {
        return date;
    }

    //EFFECTS : returns the category of expense
    public String getCategory() {
        return category;
    }

    //EFFECTS : returns the amount of expense
    public double getAmount() {
        return amount;
    }

    //EFFECTS : returns a string with the expense's title   amount  date    category
    public String toString() {
        String str = title + "\t" + String.format("%.2f", amount) + "\t" + date + "\t" + category;
        return str;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("amount", amount + "");
        json.put("date", date);
        json.put("category", category);
        return json;
    }

}
