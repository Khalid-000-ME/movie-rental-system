import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement; 

class ResSetEx{  
	public static void main(String args[]){ 

		String url = "jdbc:postgresql://ep-nameless-glitter-a84rlmuv-pooler.eastus2.azure.neon.tech/neondb?sslmode=require";
		String user = "neondb_owner";
		String password = "npg_0owVjT7Cveqt";
	
	try{  
			//step1 load the driver class  
			Class.forName("org.postgresql.Driver");  
  
			//step2 create  the connection object  
			Connection con=DriverManager.getConnection(url,user,password);  
  
			//step3 create the statement object  
			Statement stmt=con.createStatement();  
  
			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select * from customers");  
			while(rs.next())  
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(5));  
  
			//step5 close the connection object  
			con.close();  
  
		}catch(Exception e){ System.out.println(e);}  
  
	}  
}