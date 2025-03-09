import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class VendorForms{
	JFrame f; // Creating JFrame Object
	JPanel p1,p2,p3; // Creating JPanel Object
	JTabbedPane tp; // Creating JTabbedPane
	JLabel l1,l2,l3,l4, l5, l7,l8,l9,l10; // Creating JLabel Fields
	JTextField tf1,tf2,tf3,tf4, tf5, tf7,tf8,tf9,tf10; // Creating JTextField
	JButton savebtn,resetbtn,editbtn,updatebtn,deletebtn ; // Creating JButton

	// Constructor to Initialize Objects of Various JPanel and to handle Events
	VendorForms(){
		
		f=new JFrame("Form");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p1=new JPanel(new GridLayout(5,2));
		p2=new JPanel(new GridLayout(5,2));
		p3=new JPanel(new GridLayout(2,2));

		tp=new JTabbedPane();

		l1=new JLabel("Vendor ID:");
		l2=new JLabel("Vendor Name:");
		l3=new JLabel("Vendor City:");
		l4=new JLabel("Credits:");
		
		l5=new JLabel("Enter the Vendor ID:");

		l7=new JLabel("Enter the Vendor ID:");
		l8=new JLabel("Vendor Name:");
		l9=new JLabel("Vendor City:");
		l10=new JLabel("Credits:");

		tf1=new JTextField(3);
		tf2=new JTextField(25);
		tf3=new JTextField(25);
		tf4=new JTextField(3);

		tf5=new JTextField(3);
		
		tf7=new JTextField(3);
		tf8=new JTextField(25);
		tf9=new JTextField(25);
		tf10=new JTextField(3);

		savebtn=new JButton("Add");
		resetbtn=new JButton("Reset");
		editbtn=new JButton("Edit");
		updatebtn=new JButton("Save");
		deletebtn=new JButton("Delete");

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);
		p1.add(l4);
		p1.add(tf4);
		p1.add(savebtn);
		p1.add(resetbtn);

		p2.add(l7);
		p2.add(tf7);
		p2.add(l8);
		p2.add(tf8);
		p2.add(l9);
		p2.add(tf9);
		p2.add(l10);
		p2.add(tf10);
		p2.add(editbtn);
		p2.add(updatebtn);

		p3.add(l5);
		p3.add(tf5);
		p3.add(deletebtn);

		resetbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
			}
		});

		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				String value1=tf1.getText();
				String value2=tf2.getText();
				String value3=tf3.getText();
				String value4=tf4.getText();
				Connection con = null;

				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:ORCL","SYSTEM","Passw0rd");
					PreparedStatement st=con.prepareStatement("INSERT INTO VENDOR(VID,VNAME,CITY,CREDITS) VALUES(?,?,?,?)");
					int CV4 = Integer.parseInt(value4);
					st.setString(1,value1);
					st.setString(2,value2);
					st.setString(3,value3);
					st.setInt(4,CV4);
					st.executeUpdate();
					JOptionPane.showMessageDialog(p1,"Data is Successfully Inserted into Database");
					con.close();
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(p1,"Error in Record Insertion!");
				}
			}
		});

		deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){

				String value1=tf5.getText();
				Connection con = null;

				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:ORCL","SYSTEM","Passw0rd");
					PreparedStatement st=con.prepareStatement("DELETE FROM VENDOR WHERE VID = ?");
					st.setString(1,value1);
					int status = st.executeUpdate();
					if (status==1)
					{
						JOptionPane.showMessageDialog(p3,"Record is Deleted Successfully!");
					}
					else
					{
						JOptionPane.showMessageDialog(p3,"Vendor ID Does not Exists!");
					}
					
					con.close();
				}
				catch(Exception exp3)
				{
					JOptionPane.showMessageDialog(p3,"Error in Record Deletion!");
				}
			}
		});

		editbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){

				String value1=tf7.getText();
				Connection con = null;
			
				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:ORCL","SYSTEM","Passw0rd");
					PreparedStatement st=con.prepareStatement("SELECT * FROM VENDOR WHERE VID = ?");
					st.setString(1,value1);
					ResultSet res=st.executeQuery();
					res.next();
					tf7.setText(res.getString(1));
					tf8.setText(res.getString(2));
					tf9.setText(res.getString(3));
					tf10.setText(Integer.toString(res.getInt(4)));
					con.close();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(p2,"Vendor ID Does not Exists!");
				}
			}
		});

		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				Connection con = null;

				try
				{
					int x=JOptionPane.showConfirmDialog(p2,"Confirm Edit? Record Data will be Replaced");
					if(x==0){
						try{
							String value1=tf7.getText();
							String value2=tf8.getText();
							String value3=tf9.getText();
							String value4=tf10.getText();

							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:ORCL","SYSTEM","Passw0rd");;
							PreparedStatement ustmt=con.prepareStatement("UPDATE VENDOR SET VNAME=?, CITY=?, CREDITS=? WHERE VID=?");  
								
							ustmt.setString(1,value2);  
							ustmt.setString(2,value3);	
							int VC2 = Integer.parseInt(value4);
							ustmt.setInt(3,VC2);
							ustmt.setString(4,value1);

	  						ustmt.executeUpdate();  
							JOptionPane.showMessageDialog(p2,"Updated Successfully");
							con.close();
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(p2,"Error in Updating a Record");
						}
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(p2,"Optional Error");
				}
			}
		});

	}

	void dis()
	{
		f.getContentPane().add(tp);
		tp.addTab("Add Record",p1);
		tp.addTab("Edit Record",p2);
		tp.addTab("Delete Record",p3);

		f.setSize(350,180); // JFrame Size
		f.setVisible(true); // Make JFrame Visible to the User
		f.setResizable(false); // Resizing the JPane Enable/Disable
	}

	public static void main(String args[]){
		VendorForms vfo=new VendorForms();
		vfo.dis();
	}

} // End of VendorForms