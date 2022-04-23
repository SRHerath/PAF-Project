package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserManagement {
	

public Connection connect()
{
         Connection con = null;
          try
          {
             Class.forName("com.mysql.jdbc.Driver");
             con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid",
                                              "root", "upekshahe");
//For testing
          System.out.print("Successfully connected");
          }
          catch(Exception e)
          {
             e.printStackTrace();
          }
return con;
}


//Insert

	public String insertUserManagement(String name, String phoneNum, String email, String account_no, String address ) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			
			// create a prepared statement
			String query = " insert into user_service( userID, name, phoneNum, email, account_no, address)"
					+ " values( ?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, Integer.parseInt(phoneNum));
			preparedStmt.setString(4, email);
			preparedStmt.setInt(5, Integer.parseInt(account_no));
			preparedStmt.setString(6, address);
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while Inserting the user.";
			System.err.println(e.getMessage());
		}
		return output;
		
	}

//read
public String readItems()
{
        String output = "";
        try
        {
            Connection con = connect();
            if (con == null)
            {
                 return "Error while connecting to the database for reading.";
            }
        // Prepare the html table to be displayed
        output = "<table border='1'><tr><th>Name</th>"
               +"<th>Phone Number</th><th>Email</th>"
               + "<th>AccountNo</th>"
               + "<th>Address</th>"
               + "<th>Update</th><th>Remove</th></tr>";
        String query = "select * from user_service ";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
       // iterate through the rows in the result set
       while (rs.next())
      {
               String userID = Integer.toString(rs.getInt("userID"));
               String name = rs.getString("name");
               String phoneNum = Integer.toString(rs.getInt("phoneNum"));
               String email = rs.getString("email");
               String account_no = Integer.toString(rs.getInt("account_no"));
               String address = rs.getString("address");
               
       // Add a row into the html table
             
       output += "<tr><td>" + name + "</td>";
       output += "<td>" + phoneNum + "</td>";
       output += "<td>" + email + "</td>";
       output += "<td>" +  account_no+ "</td>";
       output += "<td>" + address + "</td>";
       
      
       //buttons
       output += "<td><input name='btnUpdate' "
              + " type='button' value='Update'></td>"
              + "<td><form method='post' action='users.jsp'>"
              + "<input name='btnRemove' "
              + " type='submit' value='Remove'>"
              + "<input name='userID' type='hidden' "
              + " value='" + userID + "'>" + "</form></td></tr>";
        }
        con.close();
        //Complete the html table
        output += "</table>";
       }
       catch (Exception e)
        {
           output = "Error while reading the users.";
           System.err.println(e.getMessage());
        }
        return output;
}

//updating unit management
public String updateUserManagement(String userID, String name, String phoneNum, String email, String account_no, String address)

{
	String output = "";
	
	
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "UPDATE user_service SET name=?,phoneNum=?,email=?,account_no=?,address=? WHERE userID=?";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, phoneNum);
		preparedStmt.setString(3, email);
		preparedStmt.setString(4, account_no);
		preparedStmt.setString(5, address);
		preparedStmt.setInt(6, Integer.parseInt(userID));
		// execute the statement
		 preparedStmt.execute();
		con.close();
		
		output = "User updated successfully";
		
		
	} catch (Exception e) {
		
		output = "Error while updating the user.";
		System.err.println(e.getMessage());
		
	}
	return output;
}


}
