package ui;

import model.Event;
import model.EventLog;
import model.Expense;
import model.MyExpenses;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


//Represents the graphical user interface of the app

public class MyExpenseGUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    public static final int FIELD_WIDTH = 10;


    private JTextField monthlyExpensesField;

    private JLabel monthExpensesLabel;
    private JLabel yearExpensesLabel;

    private JTextField titleTextField;
    private JTextField amountTextField;
    private JTextField dateTextField;
    private JTextField categoryTextField;

    private JButton addExpenseButton;
    private JButton saveExpenseButton;
    private JButton loadExpenseButton;


    private JTable table;
    private DefaultTableModel model;

    private JPanel labelPanel;
    private JPanel bottomPanel;
    private MyExpenses myExpenses;

    private ArrayList<Part> slices;
    private JPanel piePanel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String JSON_STORE = "./data/myExpenses.json";

    //EFFECTS: creates myExpenseGUI u
    public MyExpenseGUI() {
        super("My Expenses");
        initializeFields();
        slices = getParts();
        setLayout(new BorderLayout());
        initializeGraphics();
        setSize(WIDTH, HEIGHT);

    }

    //EFFECTS: initializes fields
    public void initializeFields() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        myExpenses = new MyExpenses();

        titleTextField = new JTextField(FIELD_WIDTH);
        amountTextField = new JTextField(FIELD_WIDTH);
        dateTextField = new JTextField(FIELD_WIDTH);
        categoryTextField = new JTextField(FIELD_WIDTH);
    }

    //EFFECTS: creates the panels and adds it to the frame
    public void initializeGraphics() {
        createTopPanel();
        add(labelPanel, BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        createAddSavePanel();
        add(bottomPanel, BorderLayout.SOUTH);

        createPieChartComponent();
        add(piePanel, BorderLayout.WEST);

    }

    //EFFECTS creates the table 
    public JPanel createTable() {

        JPanel panel = new JPanel();

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("Title");
        model.addColumn("Amount");
        model.addColumn("Date");
        model.addColumn("category");


        table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer r, int data, int columns) {
                Component comp = super.prepareRenderer(r, data, columns);
                if (data % 2 == 0) {
                    comp.setBackground(Color.WHITE);
                } else {
                    comp.setBackground(Color.LIGHT_GRAY);
                }
                return comp;

            }

        };

        table.setPreferredScrollableViewportSize(new Dimension(400, 400));
        table.setFillsViewportHeight(true);

        panel.add(new JScrollPane(table));

        return panel;
    }

    //EFFECTS: creates the top panel
    //MODIFIES: labelPanel
    public void createTopPanel() {
        labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(WIDTH, 80));

        JLabel appName = new JLabel("My Expense Tracker");
        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon("./data/money.jpg").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        appName.setIcon(imageIcon);

        monthExpensesLabel = new JLabel("Current Month: "
                + myExpenses.getSumOfExpensesByMonth(LocalDate.now().getMonthValue()));
        yearExpensesLabel = new JLabel("Current Year: "
                + myExpenses.getSumOfExpensesByYear(LocalDate.now().getYear()));

        Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        monthExpensesLabel.setBorder(raisedEtched);
        yearExpensesLabel.setBorder(raisedEtched);


        labelPanel.add(appName);
        labelPanel.add(monthExpensesLabel);
        labelPanel.add(yearExpensesLabel);

    }

    //EFFECTS: creates the add/save panel in the bottom
    public void createAddSavePanel() {

        bottomPanel = new JPanel();
        JLabel titleLabel = new JLabel("title: ");
        JLabel amountLabel = new JLabel("amount: ");
        JLabel dateLabel = new JLabel("date: ");
        JLabel categoryLabel = new JLabel("category : ");

        bottomPanel.add(titleLabel);
        bottomPanel.add(titleTextField);
        bottomPanel.add(amountLabel);
        bottomPanel.add(amountTextField);
        bottomPanel.add(dateLabel);
        bottomPanel.add(dateTextField);
        bottomPanel.add(categoryLabel);
        bottomPanel.add(categoryTextField);

        addExpenseButton = new JButton("Add Expense");
        bottomPanel.add(addExpenseButton);

        saveExpenseButton = new JButton("Save Expenses");
        bottomPanel.add(saveExpenseButton);

        loadExpenseButton = new JButton("Load Expenses");
        bottomPanel.add(loadExpenseButton);

        activateAddButton(addExpenseButton);
        activateSaveButton(saveExpenseButton);
        activateLoadButton(loadExpenseButton);

        bottomPanel.setPreferredSize(new Dimension(WIDTH, 150));

    }


    //EFFECTS: activates the add button
    //MODIFIES: this
    private void activateAddButton(JButton button) {
        //add button listener
        class AddExpenseListener implements ActionListener {
            //EFFECTS: add an expense with the user input and redraws the graph
            public void actionPerformed(ActionEvent event) {
                String title = titleTextField.getText();
                double amount = Double.parseDouble(amountTextField.getText());
                LocalDate date = LocalDate.parse(dateTextField.getText());
                String category = categoryTextField.getText();
                myExpenses.addExpense(new Expense(title, amount, date, category));
                System.out.println(myExpenses);
                model.addRow(new Object[]{title, amount, date, category});
                slices = getParts();
                repaint();
                updateLabels(date);

            }
        }

        ActionListener listener = new AddExpenseListener();
        button.addActionListener(listener);

    }

    //EFFECTS: activates the save button
    private void activateSaveButton(JButton button) {
        //save button listener
        class SaveExpenseListener implements ActionListener {
            //EFFECTS: saves the expenses to the file when the button pressed
            public void actionPerformed(ActionEvent event) {
                saveMyExpenses();
            }
        }

        ActionListener listener = new SaveExpenseListener();
        button.addActionListener(listener);

    }

    //EFFECTS: activates the save button
    private void activateLoadButton(JButton button) {
        //save button listener
        class LoadExpenseListener implements ActionListener {
            //EFFECTS: Loads the expenses to the file when the button pressed
            public void actionPerformed(ActionEvent event) {
                loadMyExpenses();
            }
        }

        ActionListener listener = new LoadExpenseListener();
        button.addActionListener(listener);

    }

    //EFFECTS: updates the top labels
    public void updateLabels(LocalDate date) {
        monthExpensesLabel.setText("Current month: \n"
                + myExpenses.getSumOfExpensesByMonth(LocalDate.now().getMonthValue()));
        yearExpensesLabel.setText("Current Year: "
                + myExpenses.getSumOfExpensesByYear(LocalDate.now().getYear()));

    }


    //EFFECTS: creates the pie chart
    public void createPieChartComponent() {
        //pie chart class
        class PieChartComponent extends JPanel {
            PieChartComponent() {
            }

            //EFFECTS: paints the pie chart
            public void paint(Graphics g) {
                drawPie((Graphics2D) g, new Rectangle(20, 20, 150, 150), slices);
            }

            //EFFECTS: draws the pie chart based on the categories added in expenses
            void drawPie(Graphics2D g, Rectangle area, ArrayList<Part> slices) {
                double total = myExpenses.getTotal();
                double curValue = 0.0D;
                int startAngle = 0;
                for (int i = 0; i < slices.size(); i++) {
                    startAngle = (int) (curValue * 360 / total);
                    int arcAngle = (int) (slices.get(i).value * 360 / total);
                    g.setColor(slices.get(i).color);
                    g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
                    curValue += slices.get(i).value;
                }
            }
        }

        piePanel = new PieChartComponent();
        piePanel.setPreferredSize(new Dimension(200, 200));

    }


    //EFFECTS: creates the slices of the pie chart based on the categories and their amount
    public ArrayList<Part> getParts() {
        ArrayList<String> categories = myExpenses.getCategoriesList();
        ArrayList<Part> parts = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            String c = categories.get(i);
            parts.add(new Part(myExpenses.getSumOfExpensesByCategory(c), new Color(0,50+i*40,0)));
        }
        return parts;
    }

    //EFFECT: saves the expenses to the JSON_STORE file
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

    //EFFECT: loads the expenses to the JSON_STORE file
    private void loadMyExpenses() {
        try {
            myExpenses = jsonReader.read();
            for (Expense e : myExpenses.getExpenses()) {
                model.addRow(new Object[]{e.getTitle(), e.getAmount(), e.getDate(), e.getCategory()});
            }
            slices = getParts();
            repaint();
            System.out.println("Loaded myExpenses from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: displays the frame
    public static void main(String[] args) {

        JFrame frame = new MyExpenseGUI();
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next);
                }

            }
        });
    }
}