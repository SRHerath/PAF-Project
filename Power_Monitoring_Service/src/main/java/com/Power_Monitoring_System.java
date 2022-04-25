package com;

import model.Power_Monitoring_Model;

import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;


@Path("/Power_Station_Data")
public class Power_Monitoring_System {
	
	Power_Monitoring_Model PSobj = new Power_Monitoring_Model();
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readData() 
	 { 
	 return PSobj.readData(); 
	}
	//////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String readDatabyid(@PathParam("id") String id){
		return PSobj.readDatabyid(id);
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertData(
	
	 @FormParam("name") String name, 
	 @FormParam("eType") String eType, 
	 @FormParam("cMW") String cMW, 
	 @FormParam("status") String status) 
	{ 
	 String output = PSobj.insertData(name, eType, cMW, status); 
	return output; 
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateData(String updateData) 
	{ 
	
	 JsonObject updateObject = new JsonParser().parse(updateData).getAsJsonObject(); 
	
	 
	 String PS_id = updateObject.get("PS_id").getAsString(); 
	 String PS_name = updateObject.get("PS_name").getAsString(); 
	 String PS_eType = updateObject.get("PS_eType").getAsString();
	 String PS_cMW = updateObject.get("PS_cMW").getAsString(); 
	 String PS_status = updateObject.get("PS_status").getAsString(); 
	
	 String output = PSobj.updateData(PS_id,PS_name,PS_eType, PS_cMW, PS_status); 
	return output; 
	}
	////////////////////////////////////////////////////////////////////////////////////////
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteRow(String deleteRow) 
	{ 
	
	 Document doc = Jsoup.parse(deleteRow, "", Parser.xmlParser()); 
	 

	 String PS_id = doc.select("PS_id").text(); 
	 
	 String output = PSobj.deleteRow(PS_id); 
	 
	 return output; 
	}

	
}
