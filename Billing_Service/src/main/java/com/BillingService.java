package com;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.BillingModel;

@Path("/BillDetails")
public class BillingService {
	
	BillingModel billObj = new BillingModel();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBillDetails(){
		return billObj.readBillDetails();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBillingDetails(@FormParam("account_no") String account_no,
	 @FormParam("from_date") Date from_date,
	 @FormParam("to_date") Date to_date,
	 @FormParam("cur_meter_reading") int cur_meter_reading,
	 @FormParam("status") String status)
	{
		
		if(account_no.isEmpty()||status.isEmpty()) 
		{
			 return "Input Fields Cannot Be Empty!";
		}
		
		else {
			String output = billObj.insertBillingDetails(account_no, from_date, to_date, cur_meter_reading, status);
			return output;
		}
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBillDetails(String billData){
		//Convert the input string to a JSON object
		JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();
		//Read the values from the JSON object
		String id = billObject.get("id").getAsString();
		String account_no = billObject.get("account_no").getAsString();
		String from_date = billObject.get("from_date").getAsString();
		String to_date = billObject.get("to_date").getAsString();
		String cur_meter_reading = billObject.get("cur_meter_reading").getAsString();
		String status = billObject.get("status").getAsString();
		String output = billObj.updateBillDetails(id, account_no, from_date, to_date, cur_meter_reading, status);
		
		return output;
	}

}
