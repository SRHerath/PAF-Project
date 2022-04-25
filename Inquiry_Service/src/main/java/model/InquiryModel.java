package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InquiryModel {
	
	private Connection connect(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
	
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid?autoReconnect=true&useSSL=false", "root", "admin");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		 return con;
	}
	
	public String insertInquiry(String account_no, String name, String inquiry_type, String date, String locaion, String inquiry_status){
		
		
		try{
			Connection con = connect();
		if (con == null){
			return "Error while connecting to the database for inserting!";
		}
		 
		String insertQuery = " insert into inquiry_service values (NULL, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmnt = con.prepareStatement(insertQuery);
		 
		pstmnt.setString(1, account_no);
		pstmnt.setString(2, name);
		pstmnt.setString(3, inquiry_type);
		pstmnt.setString(4, date);
		pstmnt.setString(5, locaion);
		pstmnt.setString(6, inquiry_status);
		
		pstmnt.execute();
		
		return "Inquiry Details Added Successfully!";
		
		} 
		
		catch (SQLException e) {
			return "Error Occur During Inserting!\n" + e.getMessage();
		}
	}
	
	public String viewInquiries()
	{
		 String output = "";
		 try{
			 Connection con = connect();
			 if (con == null){
				 return "Error while connecting to database for reading!";
			 }
			 
			output = "<table border='1'><tr><th>Account No</th>"
					 +"<th>Name</th>"
					 +"<th>Inquiry Type</th>"
					 +"<th>Date</th>"
					 +"<th>Location</th>"
					 +"<th>Status</th>"
					 +"<th>Update</th><th>Remove</th></tr>";
					 String query = "select * from inquiry_service";
					 Statement stmt = (Statement) con.createStatement();
					 ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query); 
					 
			while (rs.next()){
				String id = Integer.toString(rs.getInt("id"));
				String account_no = rs.getString("account_no");
				String name = rs.getString("name");
				String inquiry_type = rs.getString("inquiry_type");
				Date date = rs.getDate("date");
				String location = rs.getString("location");
				String status = rs.getString("inquiry_status");
				
				 
				output += "<tr><td>" + account_no + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + inquiry_type + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + location + "</td>";
				output += "<td>" + status + "</td>";
				 
				output += "<td><input name='btnUpdate' "
				 + " type='button' value='Update'></td>"
				 + "<td><form method='post'>"
				 + "<input name='btnRemove' "
				 + " type='submit' value='Remove' class='btn btn-danger'>"
				 + "<input name='id' type='hidden'"
				 + " value='" + id + "'>" + "</form></td></tr>";
			}
			
				con.close();
				 // Complete the html table
				output += "</table>";			 
					
		 }
		 catch (Exception e){
			 output = "Error while reading the records!";
			 System.err.println(e.getMessage());
		 }

		 return output;
	}
	
public String updateInquiry(String id, String account_no, String name, String inquiry_type, String date, String location, String inquiry_status) {
		
		String output = "";
		
		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for updating."; 
			}
			
			
			String query = "UPDATE inquiry_service SET account_no=?,name=?,inquiry_type=?,date=?,location=?,inquiry_status=? WHERE id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setString(1, account_no);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, inquiry_type);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, location);
			preparedStmt.setString(6, inquiry_status);
			preparedStmt.setInt(7, Integer.parseInt(id));
			
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		
		}
		catch (Exception e){
			output = "Error while updating the inquiry.";
			System.err.println(e.getMessage());
		}
		
		return output;
		
	}

public String deleteInquiry(String account_no){
	
	String output = "";
	try{
		Connection con = connect();
	if (con == null){
		return "Error While Connecting to the Database for Deleting!";
	}
	 
	String query = "delete from inquiry_service where account_no=?";
	PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
	 
	preparedStmt.setString(1, account_no);

	 
	preparedStmt.execute();
	con.close();
	output = "Inquiry Deleted Successfully!";
	}
	catch (Exception e){
		output = "Error While Deleting the Record!";
		System.err.println(e.getMessage());
	}
	return output;
}

}
