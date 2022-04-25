package model;

import java.sql.*; 

public class Power_Monitoring_Model
{ 
	private Connection connect() 
	 { 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
	 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", ""); 
		 } 
		
		 catch (Exception e) 
		 {e.printStackTrace();} 
		 return con; 
	 } 
	
	
	public String insertData(String name, String eType, String cMW, String status) 
	 { 
			 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {return "Error while connecting to the database for inserting."; } 
				 
				 String query = " insert into power_station_data(`PS_id`,`PS_name`,`PS_eType`,`PS_cMW`,`PS_status`)"
				 + " values (null, ?, ?, ?, ?)"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				 
				 preparedStmt.setString(1, name); 
				 preparedStmt.setString(2, eType); 
				 preparedStmt.setString(3, cMW); 
				 preparedStmt.setString(4, status); 
				

				 preparedStmt.execute(); 
				 con.close(); 
				 output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
	 { 
			 output = "Error while inserting Details."; 
			 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	public String readData() 
	 { 
			String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {return "Error while connecting to the database for reading."; } 
				 
				 output = "<table border='1'><tr><th>P.S. Name</th><th>Energy Type</th>" +
						 "<th>Mega Watts</th>" + 
						 "<th>Station Status</th>" +
						 "<th>Update</th><th>Delete</th></tr>"; 
				 
				 String query = "select * from power_station_data"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				
				 while (rs.next()) 
				 { 
						 String id = Integer.toString(rs.getInt("PS_id")); 
						 String name = rs.getString("PS_name"); 
						 String eType = rs.getString("PS_eType"); 
						 String cMW = rs.getString("PS_cMW");  
						 String status = rs.getString("PS_status"); 
						 
						
						 output += "<tr><td>" + name + "</td>"; 
						 output += "<td>" + eType + "</td>"; 
						 output += "<td>" + cMW + "</td>"; 
						 output += "<td>" + status + "</td>"; 
						 
						 // Remember add power_monitoring.jsp here
						 
						 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"		
									 + "<td><form method='post' action='Power_Monitoring.jsp'>"
									 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"		 		
									 + "<input name='id' type='hidden' value='" + id 
									 + "'>" + "</form></td></tr>"; 
				 } 
				 con.close(); 
				
				 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
				 output = "Error while reading Data."; 
				 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	 // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String readDatabyid(String ids) 
	 { 
			String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {return "Error while connecting to the database for reading."; } 
				 
				 output = "<table border='1'><tr><th>P.S. Name</th><th>Energy Type</th>" +
						 "<th>Mega Watts</th>" + 
						 "<th>Station Status</th>" +
						 "<th>Update</th><th>Delete</th></tr>"; 
				 
				 String query = "select * from power_station_data where PS_id='"+ids+"'"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				
				 while (rs.next()) 
				 { 
						 String id = Integer.toString(rs.getInt("PS_id")); 
						 String name = rs.getString("PS_name"); 
						 String eType = rs.getString("PS_eType"); 
						 String cMW = rs.getString("PS_cMW");  
						 String status = rs.getString("PS_status"); 
						 
						
						 output += "<tr><td>" + name + "</td>"; 
						 output += "<td>" + eType + "</td>"; 
						 output += "<td>" + cMW + "</td>"; 
						 output += "<td>" + status + "</td>"; 
						 
						 // Remember add power_monitoring.jsp here
						 
						 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"		
									 + "<td><form method='post' action='Power_Monitoring.jsp'>"
									 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"		 		
									 + "<input name='id' type='hidden' value='" + id 
									 + "'>" + "</form></td></tr>"; 
				 } 
				 con.close(); 
				
				 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
				 output = "Error while reading Data."; 
				 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	////////////////////////////////////////////////UPDATE///////////////////////////////////////////////////
	
	public String updateData(String PS_id,String PS_name, String PS_eType,String PS_cMW, String PS_status )
	
	{ 
		 String output = ""; 
		 try
		 { 
				 Connection con = connect(); 
				 
				 if (con == null) 
				 {return "Error while connecting to the database for updating."; } 
				 
				 String query = "UPDATE power_station_data SET PS_name=?, PS_eType=?, PS_cMW=?, PS_status=? WHERE PS_id=?"; 
				 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 
				 preparedStmt.setString(1, PS_name); 
				 preparedStmt.setString(2, PS_eType);
				 preparedStmt.setString(3, PS_cMW);
				 preparedStmt.setString(4, PS_status); 
				 preparedStmt.setInt(5, Integer.parseInt(PS_id));
				 
				 
				 
				 
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Updated successfully"; 
		 } 
		 catch (Exception e) 
			 { 
					 output = "Error while updating details."; 
					 System.err.println(e.getMessage()); 
			 } 
		 return output; 
		 } 
	
	///////////////////////////DELETE//////////////////////////////////////////////
	
		public String deleteRow(String PS_id) 
		 { 
			
		 String output = ""; 
		 try
		 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {return "Error while connecting to the database for deleting."; } 
				 
				 // create a prepared statement
				 String query = "delete from power_station_data where PS_id=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 // binding values
				 
				 preparedStmt.setInt(1, Integer.parseInt(PS_id)); 
				 // execute the statement
				 
				 preparedStmt.execute(); 
				 con.close(); 
				 output = "Power Station Data Deleted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
				 output = "Error while deleting the Details."; 
				 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	} 
} 
