import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MovieRentalApp extends JFrame {
    private JTabbedPane tabbedPane;
    
    private static final String URL = "jdbc:postgresql://ep-nameless-glitter-a84rlmuv-pooler.eastus2.azure.neon.tech/neondb?sslmode=require";
    private static final String USER = "neondb_owner";
    private static final String PASS = "npg_0owVjT7Cveqt";
    
    public MovieRentalApp() {
        setTitle("Movie Rental System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Movies", createMoviePanel());
        tabbedPane.addTab("Rentals", createRentalPanel());
        tabbedPane.addTab("Payments", createPaymentPanel());
        tabbedPane.addTab("Employees", createEmployeePanel());
        add(tabbedPane);
    }
    private static class LoginPage extends JFrame {
        private JTextField employeeIdField;
        private JTextField nameField;

        public LoginPage() {
            setTitle("Login");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null); // Center the window

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Employee ID:"));
            employeeIdField = new JTextField();
            panel.add(employeeIdField);

            panel.add(new JLabel("Name:"));
            nameField = new JTextField();
            panel.add(nameField);

            JButton loginButton = new JButton("Login");
            panel.add(loginButton);

            loginButton.addActionListener(e -> {
                String employeeId = employeeIdField.getText();
                String name = nameField.getText();

                if (authenticate(employeeId, name)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose(); // Close the login window
                    new MovieRentalApp().setVisible(true); // Open the main application
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Employee ID or Name", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            });

            add(panel);
        }

        private boolean authenticate(String employeeId, String name) {
            String query = "SELECT * FROM employees WHERE employee_id = ? AND name = ?";
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(employeeId));
                stmt.setString(2, name);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Returns true if a matching record is found
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Customer ID:"));
        JTextField customerIdField = new JTextField();
        panel.add(customerIdField);
        
        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);
        
        JButton addCustomerButton = new JButton("Add Customer");
        JButton viewCustomersButton = new JButton("View Customers");
        JButton deleteCustomerButton = new JButton("Delete Customer");
        
        panel.add(addCustomerButton);
        panel.add(viewCustomersButton);
        panel.add(deleteCustomerButton);
        
        JTextArea customerDisplayArea = new JTextArea(15, 70);
        panel.add(new JScrollPane(customerDisplayArea));
        
        addCustomerButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("INSERT INTO customers (customer_id, name, email) VALUES (?, ?, ?)") ) {
                stmt.setInt(1, Integer.parseInt(customerIdField.getText()));
                stmt.setString(2, nameField.getText());
                stmt.setString(3, emailField.getText());
                
                int status = stmt.executeUpdate();
                customerDisplayArea.setText(status + " customer(s) inserted successfully!");
            } catch (Exception ex) {
                customerDisplayArea.setText("Error: " + ex.getMessage());
            }
        });

        viewCustomersButton.addActionListener(e -> displayData("SELECT * FROM customers", "Customers", customerDisplayArea));

        deleteCustomerButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("DELETE FROM customers WHERE customer_id = ?")) {
                stmt.setInt(1, Integer.parseInt(customerIdField.getText()));
                
                int status = stmt.executeUpdate();
                customerDisplayArea.setText(status + " customer(s) deleted successfully!");
            } catch (Exception ex) {
                customerDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel createMoviePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Movie ID:"));
        JTextField movieIdField = new JTextField();
        panel.add(movieIdField);
        
        panel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);
        
        panel.add(new JLabel("Genre:"));
        JTextField genreField = new JTextField();
        panel.add(genreField);
        
        JButton addMovieButton = new JButton("Add Movie");
        JButton viewMoviesButton = new JButton("View Movies");
        JButton deleteMovieButton = new JButton("Delete Movie");
        
        panel.add(addMovieButton);
        panel.add(viewMoviesButton);
        panel.add(deleteMovieButton);
        
        JTextArea movieDisplayArea = new JTextArea(15, 70);
        panel.add(new JScrollPane(movieDisplayArea));
        
        addMovieButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("INSERT INTO movies (movie_id, title, genre) VALUES (?, ?, ?)") ) {
                stmt.setInt(1, Integer.parseInt(movieIdField.getText()));
                stmt.setString(2, titleField.getText());
                stmt.setString(3, genreField.getText());
                
                int status = stmt.executeUpdate();
                movieDisplayArea.setText(status + " movie(s) inserted successfully!");
            } catch (Exception ex) {
                movieDisplayArea.setText("Error: " + ex.getMessage());
            }
        });

        viewMoviesButton.addActionListener(e -> displayData("SELECT * FROM movies", "Movies", movieDisplayArea));

        deleteMovieButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("DELETE FROM movies WHERE movie_id = ?")) {
                stmt.setInt(1, Integer.parseInt(movieIdField.getText()));
                
                int status = stmt.executeUpdate();
                movieDisplayArea.setText(status + " movie(s) deleted successfully!");
            } catch (Exception ex) {
                movieDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        return panel;
    }
    
    private JPanel createRentalPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Rental ID:"));
        JTextField rentalIdField = new JTextField();
        panel.add(rentalIdField);
        
        panel.add(new JLabel("Customer ID:"));
        JTextField customerIdField = new JTextField();
        panel.add(customerIdField);
        
        panel.add(new JLabel("Movie ID:"));
        JTextField movieIdField = new JTextField();
        panel.add(movieIdField);
        
        panel.add(new JLabel("Rental Date:"));
        JTextField rentalDateField = new JTextField();
        panel.add(rentalDateField);
        
        JButton addRentalButton = new JButton("Add Rental");
        JButton viewRentalsButton = new JButton("View Rentals");
        JButton deleteRentalButton = new JButton("Delete Rental");
        
        panel.add(addRentalButton);
        panel.add(viewRentalsButton);
        panel.add(deleteRentalButton);
        
        JTextArea rentalDisplayArea = new JTextArea(15, 70);
        panel.add(new JScrollPane(rentalDisplayArea));
        
        addRentalButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("INSERT INTO rentals (rental_id, customer_id, movie_id, rental_date) VALUES (?, ?, ?, ?)") ) {
                stmt.setInt(1, Integer.parseInt(rentalIdField.getText()));
                stmt.setInt(2, Integer.parseInt(customerIdField.getText()));
                stmt.setInt(3, Integer.parseInt(movieIdField.getText()));
                stmt.setString(4, rentalDateField.getText());
                
                int status = stmt.executeUpdate();
                rentalDisplayArea.setText(status + " rental(s) inserted successfully!");
            } catch (Exception ex) {
                rentalDisplayArea.setText("Error: " + ex.getMessage());
            }
        });

        viewRentalsButton.addActionListener(e -> displayData("SELECT * FROM rentals", "Rentals", rentalDisplayArea));

        deleteRentalButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("DELETE FROM rentals WHERE rental_id = ?")) {
                stmt.setInt(1, Integer.parseInt(rentalIdField.getText()));
                
                int status = stmt.executeUpdate();
                rentalDisplayArea.setText(status + " rental(s) deleted successfully!");
            } catch (Exception ex) {
                rentalDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Payment ID:"));
        JTextField paymentIdField = new JTextField();
        panel.add(paymentIdField);
        
        panel.add(new JLabel("Rental ID:"));
        JTextField rentalIdField = new JTextField();
        panel.add(rentalIdField);
        
        panel.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        panel.add(amountField);
        
        JButton addPaymentButton = new JButton("Add Payment");
        JButton viewPaymentsButton = new JButton("View Payments");
        JButton deletePaymentButton = new JButton("Delete Payment");
        
        panel.add(addPaymentButton);
        panel.add(viewPaymentsButton);
        panel.add(deletePaymentButton);
        
        JTextArea paymentDisplayArea = new JTextArea(15, 70);
        panel.add(new JScrollPane(paymentDisplayArea));
        
        addPaymentButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("INSERT INTO payments (payment_id, rental_id, amount) VALUES (?, ?, ?)") ) {
                stmt.setInt(1, Integer.parseInt(paymentIdField.getText()));
                stmt.setInt(2, Integer.parseInt(rentalIdField.getText()));
                stmt.setDouble(3, Double.parseDouble(amountField.getText()));
                
                int status = stmt.executeUpdate();
                paymentDisplayArea.setText(status + " payment(s) inserted successfully!");
            } catch (Exception ex) {
                paymentDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        
        viewPaymentsButton.addActionListener(e -> displayData("SELECT * FROM payments", "Payments", paymentDisplayArea));

        deletePaymentButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("DELETE FROM payments WHERE payment_id = ?")) {
                stmt.setInt(1, Integer.parseInt(paymentIdField.getText()));
                
                int status = stmt.executeUpdate();
                paymentDisplayArea.setText(status + " payment(s) deleted successfully!");
            } catch (Exception ex) {
                paymentDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Employee ID:"));
        JTextField employeeIdField = new JTextField();
        panel.add(employeeIdField);
        
        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Position:"));
        JTextField positionField = new JTextField();
        panel.add(positionField);
        
        JButton addEmployeeButton = new JButton("Add Employee");
        JButton viewEmployeesButton = new JButton("View Employees");
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        
        panel.add(addEmployeeButton);
        panel.add(viewEmployeesButton);
        panel.add(deleteEmployeeButton);
        
        JTextArea employeeDisplayArea = new JTextArea(15, 70);
        panel.add(new JScrollPane(employeeDisplayArea));
        
        addEmployeeButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("INSERT INTO employees (employee_id, name, position) VALUES (?, ?, ?)") ) {
                stmt.setInt(1, Integer.parseInt(employeeIdField.getText()));
                stmt.setString(2, nameField.getText());
                stmt.setString(3, positionField.getText());
                
                int status = stmt.executeUpdate();
                employeeDisplayArea.setText(status + " employee(s) inserted successfully!");
            } catch (Exception ex) {
                employeeDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        
        viewEmployeesButton.addActionListener(e -> displayData("SELECT * FROM employees", "Employees", employeeDisplayArea));

        deleteEmployeeButton.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement stmt = con.prepareStatement("DELETE FROM employees WHERE employee_id = ?")) {
                stmt.setInt(1, Integer.parseInt(employeeIdField.getText()));
                
                int status = stmt.executeUpdate();
                employeeDisplayArea.setText(status + " employee(s) deleted successfully!");
            } catch (Exception ex) {
                employeeDisplayArea.setText("Error: " + ex.getMessage());
            }
        });
        return panel;
    }

    private void displayData(String query, String title, JTextArea displayArea) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            StringBuilder sb = new StringBuilder(title + "\n");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(metaData.getColumnName(i)).append(": ").append(rs.getString(i)).append(" | ");
                }
                sb.append("\n");
            }
            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            displayArea.setText("Error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true); // Show the login page first
        });
    }
}