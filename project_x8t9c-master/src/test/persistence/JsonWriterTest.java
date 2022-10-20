package persistence;


import model.Expense;
import model.MyExpenses;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This class contains some codes from JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    //EFFECTS : tests writing to an invalid file
    @Test
    void testWriterInvalidFile() {
        try {
            MyExpenses myExpense = new MyExpenses();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    //EFFECTS : tests writing an empty file
    @Test
    void testWriterEmptyMyExpenses() {
        try {
            MyExpenses myExpenses = new MyExpenses();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMyExpenses.json");
            writer.open();
            writer.write(myExpenses);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMyExpenses.json");
            myExpenses = reader.read();
            assertEquals(0, myExpenses.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    //EFFECTS : tests writing a general myExpenses with some expenses
    @Test
    void testWriterGeneralMyExpenses() {
        try {
            MyExpenses myExpenses = new MyExpenses();
            myExpenses.addExpense(new Expense("zara" , 29.99 , LocalDate.parse("2021-10-14") , "Clothing"   ));
            myExpenses.addExpense(new Expense("Nordstrom" , 120 ,LocalDate.parse("2021-10-13") ,  "Clothing"  ));
            myExpenses.addExpense(new Expense("Urban Outfitters" , 50.99 , LocalDate.parse("2021-10-01") , "Clothing" ));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMyExpenses.json");
            writer.open();
            writer.write(myExpenses);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMyExpenses.json");
            myExpenses = reader.read();
            assertEquals("MyExpenses", myExpenses.getName());

            List<Expense> expenses = myExpenses.getExpenses();
            assertEquals(3, expenses.size());
            checkExpense("zara", 29.99 , LocalDate.parse("2021-10-14") , "Clothing" , expenses.get(0));
            checkExpense("Nordstrom" , 120 ,LocalDate.parse("2021-10-13") ,  "Clothing" , expenses.get(1));
            checkExpense("Urban Outfitters" , 50.99 , LocalDate.parse("2021-10-01") , "Clothing" , expenses.get(2) );
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}