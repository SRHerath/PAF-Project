package com;

import model.UserManagement; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/UserService")
public class UserService {
	
	UserManagement userObj = new UserManagement();
    @GET
    @Path("/read")
    @Produces(MediaType.TEXT_HTML)
    public String readItems()
    {
   	 return userObj.readItems();
    }
    
    
	// insert user Management API
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String insertUsertManagement(@FormParam("name") String name, @FormParam("phoneNum") String phoneNum,
			@FormParam("email") String email, @FormParam("account_no") String account_no,  @FormParam("address") String address) {
		String output = userObj.insertUserManagement(name, phoneNum, email,account_no, address );
		return output;
	}
	
	// API for update unit range
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateUserManagement(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String userID = itemObject.get("userID").getAsString();
		String name = itemObject.get("name").getAsString();
		String phoneNum = itemObject.get("phoneNum").getAsString();
		String email = itemObject.get("email").getAsString();
		String account_no = itemObject.get("account_no").getAsString();
		String address = itemObject.get("address").getAsString();

		String output = userObj.updateUserManagement(userID, name, phoneNum, email, account_no, address);
		
		
		return output;
		
	}
			// Delete operation
	
			@DELETE
			@Path("/delete") 
			@Consumes(MediaType.APPLICATION_XML) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String deleteUser(String userData) 
			{ 
			//Convert the input string to an XML document
			 Document doc = Jsoup.parse(userData, "", Parser.xmlParser()); 
			 
			//Read the value from the element <userID>
			 String userID = doc.select("userID").text(); 
			 String output = userObj.deleteUser(userID); 
			return output; 
			} 

}
