package persistence;

import model.Expense;
import model.MyExpenses;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.time.LocalDate;

import org.json.*;

//This code is partially written from the
// Represents a reader that reads MyExpenses from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads MyExpenses from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyExpenses read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMyExpenses(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MyExpenses from JSON object and returns it
    private MyExpenses parseMyExpenses(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MyExpenses myExpenses = new MyExpenses();
        addExpenses(myExpenses, jsonObject);
        return myExpenses;
    }

    // MODIFIES: myExpenses
    // EFFECTS: parses expenses from JSON object and adds them to myExpenses
    private void addExpenses(MyExpenses myExpenses, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(myExpenses, nextExpense);
        }
    }

    // MODIFIES: myExpenses
    // EFFECTS: parses expense from JSON object and adds it to myExpenses
    private void addExpense(MyExpenses myExpenses, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        double amount = Double.valueOf(jsonObject.getString("amount"));
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        String category = jsonObject.getString("category");
        Expense expense = new Expense(title, amount, date, category);
        myExpenses.addExpense(expense);
    }
}
