package com;

import model.Payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment")
public class PaymentService {
	Payment PaymentObj = new Payment();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment()
	 {
		return PaymentObj.readPayment();
	 } 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("CardName") String CardName,
	@FormParam("CardNumber") String CardNumber,
	@FormParam("Ex_Month") String Ex_Month,
	@FormParam("Ex_Year") String Ex_Year,
	@FormParam("Amount") String Amount,
	@FormParam("CV") String CV,
	@FormParam("Date") String Date)
	{
	String output = PaymentObj.insertPayment(CardName, CardNumber, Ex_Month, Ex_Year,Amount, CV, Date);
	return output;
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String PaymentData)
	{
	//Convert the input string to a JSON object
	JsonObject PaymentObject = new JsonParser().parse(PaymentData).getAsJsonObject();

	//Read the values from the JSON object
	String PayID = PaymentObject.get("PayID").getAsString();
	String CardName = PaymentObject.get("CardName").getAsString();
	String CardNumber = PaymentObject.get("CardNumber").getAsString();
	String Ex_Month = PaymentObject.get("Ex_Month").getAsString();
	String Ex_Year = PaymentObject.get("Ex_Year").getAsString();
	String Amount = PaymentObject.get("Amount").getAsString();
	String CV = PaymentObject.get("CV").getAsString();
	String Date = PaymentObject.get("Date").getAsString();
	String output = PaymentObj.updatePayment(PayID,CardName,  CardNumber,  Ex_Month,  Ex_Year, Amount,  CV, Date);

	return output;
	}
	
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String PaymentData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(PaymentData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	String PayID = doc.select("PayID").text();
	String output = PaymentObj.deletePayment(PayID);

	return output;
	}
	
	
	
	
}
