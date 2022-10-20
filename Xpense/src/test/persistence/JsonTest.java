package persistence;

import model.MyExpenses;
import model.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    //EFFECTS : compares title amount date category with an expense attributes
    protected void checkExpense(String title , double amount , LocalDate date , String category, Expense ex) {
        assertEquals(title, ex.getTitle());
        assertEquals(amount, ex.getAmount());
        assertEquals(date, ex.getDate());
        assertEquals(category, ex.getCategory());

    }
}
