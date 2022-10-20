package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.MyExpenses;

import javax.swing.*;

class ExpenseAppTest {
    private MyExpenses expenses;
    private String todayDate ;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd")  ;

    @BeforeEach
    void setup() {
        expenses = new MyExpenses();
        todayDate = LocalDate.now().toString();
    }

    public void setExpenses() {
        expenses.addExpense(new Expense("zara" , 29.99 , LocalDate.parse("2021-10-14") , "Clothing"   ));
        expenses.addExpense(new Expense("Nordstrom" , 120 ,LocalDate.parse("2021-10-13") ,  "Clothing"  ));
        expenses.addExpense(new Expense("Urban Outfitters" , 50.99 , LocalDate.parse("2021-10-01") , "Clothing" ));
        expenses.addExpense(new Expense("City Market" , 60.87 , LocalDate.parse("2021-09-01") , "Groceries" ));
    }

    //test the five constructors of Expense class
    @Test
    public void testConstructors(){
        //test the first Expense class constructor
        expenses.addExpense(new Expense("zara" ));
        assertEquals(1, expenses.getSize());
        assertEquals("zara\t0.00\t"+todayDate+"\tother" ,expenses.getExpense(0).toString() );

        //tests the second Expense class constructor
        expenses.addExpense(new Expense("zara" , 29.99   ));
        assertEquals(2, expenses.getSize());
        assertEquals("zara\t29.99\t"+todayDate+"\tother" ,expenses.getExpense(1).toString() );

        //tests the third Expense class constructor
        expenses.addExpense(new Expense("zara" , 29.99  , LocalDate.parse("2021-10-01")  ));
        assertEquals(3, expenses.getSize());
        assertEquals("zara\t29.99\t2021-10-01\tother" ,expenses.getExpense(2).toString() );

        //tests the forth Expense class constructor
        expenses.addExpense(new Expense("zara" , 29.99  , "clothing"  ));
        assertEquals(4, expenses.getSize());
        assertEquals("zara\t29.99\t"+todayDate+"\tclothing" ,expenses.getExpense(3).toString() );

        //tests the fifth Expense class constructor
        expenses.addExpense(new Expense("zara" , 29.99  ,LocalDate.parse("2021-10-01") ,  "clothing"  ));
        assertEquals(5, expenses.getSize());
        assertEquals("zara\t29.99\t2021-10-01\tclothing" ,expenses.getExpense(4).toString() );

    }


    // Effects : tests getExpenseInMonth(month) method by the expenses in the Expenses Set
    @Test
    public void testGetExpenseInMonthOf(){
        setExpenses();
        assertEquals("October expenses:\nzara\t29.99\t2021-10-14\tClothing\nNordstrom\t120.00\t2021-10-13\tClothing\nUrban Outfitters\t50.99\t2021-10-01\tClothing\n" , expenses.getExpenseInMonthOf(10));
        assertEquals("September expenses:\nCity Market\t60.87\t2021-09-01\tGroceries\n" , expenses.getExpenseInMonthOf(9));


    }

    //EFFECTS : tests getExpenseOfCategory method (either the category exists or not)
    @Test
    public void testGetExpenseOfCategory(){
        setExpenses();
        //The category exists in the list
        assertEquals("Clothing expenses :\nzara\t29.99\t2021-10-14\tClothing\nNordstrom\t120.00\t2021-10-13\tClothing\nUrban Outfitters\t50.99\t2021-10-01\tClothing\n", expenses.getExpenseOfCategory("Clothing"));
        //The category does not exist
        assertEquals("Travel expenses :\n", expenses.getExpenseOfCategory("Travel"));
    }

    //EFFECTS : tests getSumOfExpensesByCategory method (either the category exists or not)
    @Test
    public void testGetSumOfExpensesByCategory(){
        setExpenses();
        //the category exists
        assertEquals("200.98", String.format("%.2f", expenses.getSumOfExpensesByCategory("Clothing")));
        //the category does not exist
        assertEquals("0.00", String.format("%.2f", expenses.getSumOfExpensesByCategory("Travel")));
    }

    //EFFECTS : tests getSumOfExpensesByMonth method (either there is an expense in that month or not)
    @Test
    public void testGetSumOfExpensesByMonth(){
        setExpenses();
        assertEquals("$ 60.87", expenses.getSumOfExpensesByMonth(9));
        assertEquals( "$ 200.98", expenses.getSumOfExpensesByMonth(10));
        //no expense in the month
        assertEquals("$ 0.00", expenses.getSumOfExpensesByMonth(1));
    }

    //EFFECTS : tests getExistingCategories method 
    @Test
    public void testGetExistingCategories(){
        setExpenses();
        assertEquals("[Clothing, Groceries]", expenses.getExistingCategories());

    }

    @Test
    public void testGetExistingCategoriesList(){
        setExpenses();
        assertEquals("Clothing", expenses.getCategoriesList().get(0));
        assertEquals("Groceries", expenses.getCategoriesList().get(1));

    }

    @Test
    public void testGetSumOfExpensesByYear(){
        setExpenses();
        expenses.addExpense(new Expense("City Market" , 60.87 , LocalDate.parse("2019-09-01") , "Groceries" ));
        assertEquals("$ 261.85", expenses.getSumOfExpensesByYear(2021));
        assertEquals("$ 60.87", expenses.getSumOfExpensesByYear(2019));
        assertEquals( "$ 0.00", expenses.getSumOfExpensesByMonth(2020));
    }

    @Test
    public void testGetTotal(){
        setExpenses();
        assertEquals("261.85" ,String.format("%.2f", expenses.getTotal()));

    }





}