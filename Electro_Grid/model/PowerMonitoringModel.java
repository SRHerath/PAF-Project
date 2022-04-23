package model;

import java.sql.DriverManager;

public class PowerMonitoringModel 
{
		private Connection connect() 
		{
			Connection con = null;
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			}
			
			catch (Exception e)
			{e.printStackTrace();}
			
			return con;
		}
		
		public String insertPSData(String id,String name, String eType, String mW, String status) {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null) {return "ERROR While connecting to the database while inserting";}
				
				String query = "insert into Power Station Data('PS_id','PS_name','PS_eType','PS_cMW','PS_opStat')" + "values(?,?,?,?,?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				 preparedStmt.setString(1, id); 
				 preparedStmt.setString(2, name); 
				 preparedStmt.setString(3, etype); 
				 preparedStmt.setDouble(4, cMW); 
				 preparedStmt.setString(5, status); 
				 
				 
				 preparedStmt.execute(); 
				 con.close(); 
				 output = "Inserted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 	output = "Error while inserting the item."; 
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
						
						output = "<table border='1'><tr><th>Power Station ID</th><th>Power Station Name</th>\" +\r\n"
								+ " \"<th>Generated Energy type</th>\" + \r\n"
								+ " \"<th>Currently generating Megawatts</th>\" +\r\n"
								+ " \"<th>Power generation status</th>\" +\r\n"
								+ " \"<th>Update Info</th><th>Delete</th></tr>\";"
								
						 String query = "select * from PSdata"; 
						 Statement stmt = con.createStatement(); 
						 ResultSet rs = stmt.executeQuery(query); 
						 
						 while (rs.next()) 
						 { 
							 String id = rs.getString("PS_id"); 
							 String name = rs.getString("PS_name"); 
							 String eType = rs.getString("PS_eType"); 
							 String cMW = Double.toString(rs.getDouble("PS_cMW")); 
							 String status = rs.getString("PS_status"); 
							 
							
							 output += "<tr><td>" + itemCode + "</td>"; 
							 output += "<td>" + itemName + "</td>"; 
							 output += "<td>" + itemPrice + "</td>"; 
							 output += "<td>" + itemDesc + "</td>"; 
							 output += "<td>" + itemDesc + "</td>"; 
						 
						
								 output += "<td><input name='btnUpdate' type='button' value='Update' 
								 class='btn btn-secondary'></td>"
								 + "<td><form method='post' action='items.jsp'>"
								 + "<input name='btnRemove' type='submit' value='Remove' 
								 class='btn btn-danger'>"
						 + "<input name='itemID' type='hidden' value='" + itemID 
						 + "'>" + "</form></td></tr>";
		
		
		
		
		
		
		
				
			}
			
		}
		
		
		
		
}
