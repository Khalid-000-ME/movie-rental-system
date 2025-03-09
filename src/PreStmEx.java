import java.sql.Connection;  
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

class PreStmEx {  
    public static void main(String args[]) {  
        String url = "jdbc:postgresql://ep-nameless-glitter-a84rlmuv-pooler.eastus2.azure.neon.tech/neondb?sslmode=require";
        String user = "neondb_owner";
        String pass = "npg_0owVjT7Cveqt";

        try {  
            Class.forName("org.postgresql.Driver");  
            Connection con = DriverManager.getConnection(url, user, pass);  

            PreparedStatement stmt = con.prepareStatement("INSERT INTO customers (customer_id, name, email, phone_number, join_date) VALUES (?, ?, ?, ?, ?)"); 

            Scanner in = new Scanner(System.in);

            int customer_id, no;
            String email, name, join_date,phone_number;

            System.out.println("Enter how many records you want to create:");
            no = in.nextInt();
            in.nextLine(); // Consume leftover newline

            for (int i = 1; i <= no; i++) {
                System.out.println("Enter Customer ID:");
                customer_id = in.nextInt();
                in.nextLine(); // Consume newline

                System.out.println("Enter Customer Name:");
                name = in.nextLine(); // ✅ Now supports full names

                System.out.println("Enter Customer Email:");
                email = in.nextLine();

                System.out.println("Enter Customer Phone Number:");
                phone_number = in.next();
                in.nextLine(); // Consume newline

                System.out.println("Enter Join Date (YYYY-MM-DD):");
                join_date = in.nextLine();

                stmt.setInt(1, customer_id);  
                stmt.setString(2, name);  
                stmt.setString(3, email);  
                stmt.setString(4, phone_number);
                stmt.setDate(5, Date.valueOf(join_date));

                int status = stmt.executeUpdate();  
                System.out.println(status + " record(s) inserted successfully!");  
            }

            stmt.close();
            con.close();
            in.close();
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }  
    }  
}
