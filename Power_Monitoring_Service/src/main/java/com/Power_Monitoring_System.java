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

public class Power_Monitoring_System {

	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	 { 
	 return itemObj.readItems(); 
	}
	
	
	
	
	
	
	
	
}
