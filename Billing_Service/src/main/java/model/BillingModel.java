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
		
		public String insertBillingDetails(String account_no, Date from_date, Date to_date, int cur_meter_reading, String status){
			
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
				pstmnt.setDate(4, from_date);
				pstmnt.setInt(5, prev_meter_reading);
				pstmnt.setDate(6, to_date);
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
				current_amount = (float) ((float) (60 * 7.85) + (30 * 10.00) + (30 * 27.75) + (no_of_units - 120) * 32.75);
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

}