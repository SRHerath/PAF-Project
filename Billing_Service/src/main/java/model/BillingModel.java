package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillingModel {
	
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
		
		public String insertBillingDetails(String account_no, String from_date, String to_date, int cur_meter_reading, String status){
			
			/*String output = "";*/
			
			try{
				Connection con = connect();
				
			if (con == null){
				return "Error While Connecting to the Database for Inserting!";
			}
			
			
				String insertQuery = " insert into billing_service values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmnt = con.prepareStatement(insertQuery);
				
				int prev_meter_reading = this.getPrevReading(account_no);

				int no_of_units = this.calculateUnits(cur_meter_reading, prev_meter_reading);
				
				float arrears = this.getArrears(account_no, status);
				
				float current_amount = this.calculateCurrentAmount(no_of_units);
				
				float total_amount = this.calculateTotalAmount(no_of_units, arrears);
				
				String name = this.getUserName(account_no);
				
				String address = this.getUserAddress(account_no);
				
				// create a prepared statement for delete previous records by account_no
				String query = "delete from billing_service where account_no=?";
				PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(account_no));
				// execute the statement
				preparedStmt.execute();
				
				pstmnt.setString(1, account_no);
				pstmnt.setString(2, name);
				pstmnt.setString(3, address);
				pstmnt.setString(4, from_date);
				pstmnt.setInt(5, prev_meter_reading);
				pstmnt.setString(6, to_date);
				pstmnt.setInt(7, cur_meter_reading);
				pstmnt.setInt(8, no_of_units);
				pstmnt.setFloat(9, current_amount);
				pstmnt.setFloat(10, arrears);
				pstmnt.setFloat(11, total_amount);
				pstmnt.setString(12, status);

				pstmnt.execute();
				// Success Message
				return "Billing Details Added Successfully!";
				
			} 
			
			catch (SQLException e) {
				return "Error Occur During Inserting!\n" + e.getMessage();
			}
		}
		
		public int calculateUnits(int cur_meter_reading, int prev_meter_reading) {
			
			int no_of_units = 0;
			
			no_of_units = cur_meter_reading - prev_meter_reading;
			
			return no_of_units;
		}
		
		public float calculateCurrentAmount(int no_of_units) {
			
			float current_amount = 0;
			
			if(no_of_units <= 60) {
				current_amount = (float) (no_of_units * 7.85);
			}
			else if(no_of_units > 60 && no_of_units <= 90) {
				current_amount = (float) ((float) (60 * 7.85) + (no_of_units - 60) * 10.00);
			}
			else if(no_of_units > 90 && no_of_units <= 120) {
				current_amount = (float) ((float) (60 * 7.85) + (30 * 10.00) + (no_of_units - 90) * 27.75);
			}
			else if(no_of_units > 120 && no_of_units <= 180) {
				current_amount = (float) ((float) (60 * 7.85) + (30 * 10.00) + (30 * 27.75) + (no_of_units - 120) * 32.00);
			}
			else {
				current_amount = (float) ((float) (60 * 7.85) + (30 * 10.00) + (30 * 27.75) + (60 * 32.00) + (no_of_units - 180) * 45.00);
			}
			
			return current_amount;
			
		}
		
		public float calculateTotalAmount(int no_of_units, float arrears) {
			
			float total_amount = this.calculateCurrentAmount(no_of_units) + arrears;
			
			return total_amount;
			
		}
		
		private float getArrears(String account_no, String status) {
			
			float arrears = 0;
			
			try {
				
				Connection con = connect();
				
				String getQuery = "select total_amount\n"
								+ "from billing_service\n"
								+ "where account_No = ? and status='Pending'; ";
	
				PreparedStatement pstmt = con.prepareStatement(getQuery);
				pstmt.setString(1, account_no);
			
				float total_a= 0;
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					
					total_a = rs.getFloat("total_amount");
					
				}
				con.close();
				
				 
				arrears = total_a;
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return arrears;
		}
		
		private int getPrevReading(String account_no) {
			
			int prev_reading = 0;
			
			try {
				
				Connection con = connect();
				
				String getquery = "select current_meter_reading\n"
								+ "from billing_service\n"
								+ "where account_no = ?; ";
				
				PreparedStatement pstmt = con.prepareStatement(getquery);
				pstmt.setString(1, account_no);

				int cur_reading= 0;
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					
					cur_reading = rs.getInt("current_meter_reading");
					
				}
				con.close();
				
				prev_reading = cur_reading;
				
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return prev_reading;
				
			}
			

		private String getUserAddress(String account_no) {
			
			String address = "";
			
			try {
				
				Connection con = connect();
				
				String getquery = "select address\n"
								+ "from user_service\n"
								+ "where account_no = ?; ";
				
				PreparedStatement pstmt = con.prepareStatement(getquery);
				pstmt.setString(1, account_no);
				
				String getaddress = "";
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					
					getaddress = rs.getString("address");
					
				}
				con.close();
				
				address = getaddress;
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return address;
			
		}
		
		private String getUserName(String account_no) {
			
			String name = "";
			
			try {
				
				Connection con = connect();
				
				String getquery = "select name\n"
								+ "from user_service\n"
								+ "where account_no = ?; ";
				
				PreparedStatement pstmt = con.prepareStatement(getquery);
				pstmt.setString(1, account_no);
				
				String getname = "";
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					
					getname = rs.getString("name");
					
				}
				con.close();
				
				name = getname;
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return name;
			
		}
		
		public String readBillDetails()
		{
			 String output = "";
			 try{
				 Connection con = connect();
				 if (con == null){
					 return "Error While Connecting to the Database for Reading!";
				 }
				 
				output = "<table border='1'><tr><th>Account No</th>"
						 +"<th>Name</th><th>Address</th>"
						 +"<th>From Date</th>"
						 +"<th>Previous Meter Reading</th>"
						 +"<th>To Date</th>"
						 +"<th>Current Meter Reading</th>"
						 +"<th>No of Units</th>"
						 +"<th>Current Amount</th>"
						 +"<th>Arrears</th>"
						 +"<th>Total Amount</th>"
						 +"<th>Status</th>"
						 +"<th>Update</th><th>Remove</th></tr>";
						 String query = "select * from billing_service";
						 Statement stmt = (Statement) con.createStatement();
						 ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query); 
						 
				while (rs.next()){
					String id = Integer.toString(rs.getInt("id"));
					String account_no = rs.getString("account_no");
					String name = rs.getString("name");
					String address = rs.getString("address");
					Date from_date = rs.getDate("from_date");
					String prev_meter_reading = Integer.toString(rs.getInt("previous_meter_reading"));
					Date to_date = rs.getDate("to_date");
					String cur_meter_reading = Integer.toString(rs.getInt("current_meter_reading"));
					String no_of_units = Integer.toString(rs.getInt("no_of_units"));
					String current_amount = Float.toString(rs.getFloat("current_amount"));
					String arrears = Float.toString(rs.getFloat("amount_in_arrears"));
					String total_amount = Float.toString(rs.getFloat("total_amount"));
					String status = rs.getString("status");
					
					 // Add a row into the html table
					output += "<tr><td>" + account_no + "</td>";
					output += "<td>" + name + "</td>";
					output += "<td>" + address + "</td>";
					output += "<td>" + from_date + "</td>";
					output += "<td>" + prev_meter_reading + "</td>";
					output += "<td>" + to_date + "</td>";
					output += "<td>" + cur_meter_reading + "</td>";
					output += "<td>" + no_of_units + "</td>";
					output += "<td>" + current_amount + "</td>";
					output += "<td>" + arrears + "</td>";
					output += "<td>" + total_amount + "</td>";
					output += "<td>" + status + "</td>";
					 // buttons
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
				 output = "Error While Reading the Records!";
				 System.err.println(e.getMessage());
			 }

			 return output;
		}
		
		public String readBillDetailsbyAccountNo(String acc_no)
		{
			 String output = "";
			 try{
				 Connection con = connect();
				 if (con == null){
					 return "Error While Connecting to the Database for Reading!";
				 }
				 
				output = "<table border='1'><tr><th>Account No</th>"
						 +"<th>Name</th><th>Address</th>"
						 +"<th>From Date</th>"
						 +"<th>Previous Meter Reading</th>"
						 +"<th>To Date</th>"
						 +"<th>Current Meter Reading</th>"
						 +"<th>No of Units</th>"
						 +"<th>Current Amount</th>"
						 +"<th>Arrears</th>"
						 +"<th>Total Amount</th>"
						 +"<th>Status</th>"
						 +"<th>Update</th><th>Remove</th></tr>";
						 String query = "select * from billing_service where account_no = '"+acc_no+"'";
						 Statement stmt = (Statement) con.createStatement();
						 ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query); 
						 
				while (rs.next()){
					String id = Integer.toString(rs.getInt("id"));
					String account_no = rs.getString("account_no");
					String name = rs.getString("name");
					String address = rs.getString("address");
					Date from_date = rs.getDate("from_date");
					String prev_meter_reading = Integer.toString(rs.getInt("previous_meter_reading"));
					Date to_date = rs.getDate("to_date");
					String cur_meter_reading = Integer.toString(rs.getInt("current_meter_reading"));
					String no_of_units = Integer.toString(rs.getInt("no_of_units"));
					String current_amount = Float.toString(rs.getFloat("current_amount"));
					String arrears = Float.toString(rs.getFloat("amount_in_arrears"));
					String total_amount = Float.toString(rs.getFloat("total_amount"));
					String status = rs.getString("status");
					
					 // Add a row into the html table
					output += "<tr><td>" + account_no + "</td>";
					output += "<td>" + name + "</td>";
					output += "<td>" + address + "</td>";
					output += "<td>" + from_date + "</td>";
					output += "<td>" + prev_meter_reading + "</td>";
					output += "<td>" + to_date + "</td>";
					output += "<td>" + cur_meter_reading + "</td>";
					output += "<td>" + no_of_units + "</td>";
					output += "<td>" + current_amount + "</td>";
					output += "<td>" + arrears + "</td>";
					output += "<td>" + total_amount + "</td>";
					output += "<td>" + status + "</td>";
					 // buttons
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
				 output = "Error While Reading the Records!";
				 System.err.println(e.getMessage());
			 }

			 return output;
		}
		
		public String updateBillDetails(String id, String account_no, String from_date , String to_date, String cur_meter_reading, String status) {
			
			String output = "";
			
			try{
				Connection con = connect();
				if (con == null){
					return "Error While Connecting to the Database for Updating!"; 
				}
				
				// create a prepared statement
				String query = "UPDATE billing_service SET from_date=?, previous_meter_reading=?,to_date=?,current_meter_reading=?,no_of_units=?,current_amount=?,amount_in_arrears=?,total_amount=?,status=? WHERE id=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				int prev_meter_reading = this.getPrevReadingforUpdate(account_no);

				int no_of_units = this.calculateUnits(Integer.parseInt(cur_meter_reading), prev_meter_reading);
				
				float arrears = this.getArrears(account_no, status);
				
				float current_amount = this.calculateCurrentAmount(no_of_units);
				
				float total_amount = this.calculateTotalAmount(no_of_units, arrears);
				
				// binding values
				preparedStmt.setString(1, from_date);
				preparedStmt.setInt(2, prev_meter_reading);
				preparedStmt.setString(3, to_date);
				preparedStmt.setInt(4, Integer.parseInt(cur_meter_reading));
				preparedStmt.setInt(5, no_of_units);
				preparedStmt.setFloat(6, current_amount);
				preparedStmt.setFloat(7, arrears);
				preparedStmt.setFloat(8, total_amount);
				preparedStmt.setString(9, status);
				preparedStmt.setInt(10, Integer.parseInt(id));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Billing Details Updated Successfully!";
			
			}
			catch (Exception e){
				output = "Error While Updating the Record!";
				System.err.println(e.getMessage());
			}
			
			return output;
			
		}
		
		private int getPrevReadingforUpdate(String account_no) {
			
			int prev_reading = 0;
			
			try {
				
				Connection con = connect();
				
				String getquery = "select previous_meter_reading\n"
								+ "from billing_service\n"
								+ "where account_no = ?; ";
				
				PreparedStatement pstmt = con.prepareStatement(getquery);
				pstmt.setString(1, account_no);

				int cur_reading= 0;
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					
					cur_reading = rs.getInt("current_meter_reading");
					
				}
				con.close();
				
				prev_reading = cur_reading;
				
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return prev_reading;
				
		}
		
		public String deleteBillDetails(String account_no){
			
			String output = "";
			try{
				Connection con = connect();
			if (con == null){
				return "Error While Connecting to the Database for Deleting!";
			}
			 // create a prepared statement
			String query = "delete from billing_service where account_no=?";
			PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
			 // binding values
			preparedStmt.setString(1, account_no);

			 // execute the statement
			preparedStmt.execute();
			con.close();
			output = "Billing Details Deleted Successfully!";
			}
			catch (Exception e){
				output = "Error While Deleting the Record!";
				System.err.println(e.getMessage());
			}
			return output;
		}

}