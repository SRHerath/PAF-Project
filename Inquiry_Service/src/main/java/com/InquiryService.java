package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.InquiryModel;

@Path("/Inquiries")
public class InquiryService {
	
	InquiryModel inqObj = new InquiryModel();
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertInquiry(@FormParam("account_no") String account_no,
	 @FormParam("name") String name,
	 @FormParam("inquiry_type") String inquiry_type,
	 @FormParam("date") String date,
	 @FormParam("location") String location,
	 @FormParam("inquiry_status") String inquiry_status)
	{
		String output = inqObj.insertInquiry(account_no, name, inquiry_type, date, location, inquiry_status);
		return output;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String viewInquiries(){
		return inqObj.viewInquiries();
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateInquiry(String inqData){
		
		JsonObject inqObject = new JsonParser().parse(inqData).getAsJsonObject();
		
		String id = inqObject.get("id").getAsString();
		String account_no = inqObject.get("account_no").getAsString();
		String name = inqObject.get("name").getAsString();
		String inquiry_type = inqObject.get("inquiry_type").getAsString();
		String date = inqObject.get("date").getAsString();
		String location = inqObject.get("location").getAsString();
		String inquiry_status = inqObject.get("inquiry_status").getAsString();
		String output = inqObj.updateInquiry(id, account_no, name, inquiry_type, date, location, inquiry_status);
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteInquiry(String inqData){
		
		Document doc = Jsoup.parse(inqData, "", Parser.xmlParser());
	
		
		String acc_no = doc.select("account_no").text();
		String output = inqObj.deleteInquiry(acc_no);
		
		return output;
	}

}
