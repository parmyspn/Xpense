package persistence;


import model.MyExpenses;
import model.Expense;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Represent a test class for JsonReader
class JsonReaderTest extends JsonTest {

    //EFFECTS : tests reading from a file that does not exist
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MyExpenses ex = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    //EFFECTS : tests reading from an empty file
    @Test
    void testReaderNoExpenses() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMyExpenses.json");
        try {
            MyExpenses myExpenses = reader.read();
            assertEquals("MyExpenses", myExpenses.getName());
            assertEquals(0, myExpenses.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    //EFFECTS : tests reading from a file with some expenses
    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMyExpenses.json");
        try {
            MyExpenses myExpenses = reader.read();
            assertEquals("MyExpenses", myExpenses.getName());
            List<Expense> expenses = myExpenses.getExpenses();
            assertEquals(2, expenses.size());
            checkExpense("zara", 29.99 , LocalDate.parse("2021-10-14") , "Clothing" , expenses.get(0));
            checkExpense("Nordstrom" , 120 ,LocalDate.parse("2021-10-13") ,  "Clothing" , expenses.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}