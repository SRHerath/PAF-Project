package model;
import java.sql.*;
public class Payment {

	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/paf_g69",
		 		"root", "#Group20"); 
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	//insert
	public String insertPayment(String cname, String cno, String exmnth, String exyr,String amount, String cvv, String tdate)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database"; 
	 } 
	 // create a prepared statement
	 String query = "insert into payment"
	 + "(`PayID`,`CardName`,`CardNumber`,`Ex_Month`,`Ex_Year`,`Amount`, `CV`, `Date`)"
	 + " values (?, ?, ?, ?, ?, ?, ?, ?)"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, cname); 
	 preparedStmt.setString(3, cno); 
	 preparedStmt.setString(4, exmnth); 
	 preparedStmt.setString(5, exyr); 
	 preparedStmt.setString(6, amount); 
	 preparedStmt.setString(7, cvv);
	 preparedStmt.setString(8, tdate);
	
	 //execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Inserted successfully"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while inserting"; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}
	
	
	
	//read
	public String readPayment()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Owner Name</th><th>Card No</th>" +
	 "<th>Exp. Month</th><th>Exp. Year</th>" +
	 "<th>Amount</th><th>CVC/CVV</th><th>Date</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 String query = "select * from payment";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String PayID = Integer.toString(rs.getInt("PayID"));
	 String CardName = rs.getString("CardName");
	 String CardNumber = rs.getString("CardNumber");
	 String Ex_Month = rs.getString("Ex_Month");
	 String Ex_Year = rs.getString("Ex_Year");
	 String Amount = rs.getString("Amount");
	 String CV = rs.getString("CV");
	 String Date = rs.getString("Date");
	 // Add into the html table
	 output += "<tr><td>" + CardName + "</td>";
	 output += "<td>" + CardNumber + "</td>";
	 output += "<td>" + Ex_Month + "</td>";
	 output += "<td>" + Ex_Year + "</td>";
	 output += "<td>" + Amount + "</td>";
	 output += "<td>" + CV + "</td>";
	 output += "<td>" + Date + "</td>";
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='payment.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + PayID
	 + "'>" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 } 
	
	
	public String updatePayment(String payID,String cname, String cno, String exmnth, String exyr,String amount, String cvv, String tdate)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database"; 
	 } 
	 // create a prepared statement
	 String query = "UPDATE payment SET CardName=?,CardNumber=?,Ex_Month=?,Ex_Year=?,Amount=?,CV=?,Date=? WHERE PayID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	
	 preparedStmt.setString(1, cname); 
	 preparedStmt.setString(2, cno); 
	 preparedStmt.setString(3, exmnth); 
	 preparedStmt.setString(4, exyr); 
	 preparedStmt.setString(5, amount); 
	 preparedStmt.setString(6, cvv);
	 preparedStmt.setString(7, tdate);
	 preparedStmt.setInt(8,Integer.parseInt(payID));
	
	 //execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Updated successfully"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while updating"; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}
	
	
	public String deletePayment(String PayID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 
	 // create a prepared statement
	 String query = "DELETE FROM payment where PayID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(PayID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 } 
}
