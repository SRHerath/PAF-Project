package com;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

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
	
	@GET
	@Path("/{account_no}")
	@Produces(MediaType.TEXT_HTML)
	public String readBillDetailsbyAccountNO(@PathParam("account_no") String account_no){
		return billObj.readBillDetailsbyAccountNo(account_no);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBillingDetails(@FormParam("account_no") String account_no,
	 @FormParam("from_date") String from_date,
	 @FormParam("to_date") String to_date,
	 @FormParam("cur_meter_reading") int cur_meter_reading,
	 @FormParam("status") String status)
	{
		
		if(account_no.isEmpty()||status.isEmpty()) 
		{
			 return "Input Fields Cannot Be Empty!";
		}
		if(!from_date.matches("^(?:[0-9][0-9])?[0-9][0-9]-[0-3][0-9]-[0-3][0-9]$")) {
			return "Date Fields are not in Correct Format!";
		}
		if(!to_date.matches("^(?:[0-9][0-9])?[0-9][0-9]-[0-3][0-9]-[0-3][0-9]$")) {
			return "Date Fields are not in Correct Format!";
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
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBillDetails(String billData){
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser());
	
		//Read the value from the element <itemID>
		String account_no = doc.select("account_no").text();
		String output = billObj.deleteBillDetails(account_no);
		
		return output;
	}

}
