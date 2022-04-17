package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;

public class Billing {
	
	//A common method to connect to the DB
	private Connection connect(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
		
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid?autoReconnect=true&useSSL=false", "root", "Ravindu@0926");
		}
		catch (Exception e){
			e.printStackTrace();
		}
			return con;
	} 
	
	/*public String insertBillDetail(String account_no, String name, String address, Date from_date, ){
		
		String output = "";
		
		try{
			Connection con = connect();
		if (con == null){
			return "Error while connecting to the database for inserting.";
		}
		 // create a prepared statement
		Statement stmt = (Statement) con.createStatement();
		String query = "insert into new_table value(0, '"+code+"', '"+name+"','"+price+"','"+desc+"')";
		 
		int rs = ((java.sql.Statement) stmt).executeUpdate(query);
		 
		if(rs > 0) {
			output = "Successfully Inserted!"; 
		}
		else {
			output = "Error while inserting!";
		}
	 
	 
		}
		catch (Exception e){
			 
			 System.err.println(e.getMessage());
		}

		return output;
	}*/

}
