package stick.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/date")
public class StickResource {
	@Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  static Date date = new Date();
	  public StickResource(){
		  //date = new Date();
	  }
	  public StickResource(Date _date){
		  date = _date;
	  }
//	  @GET
//	  @Produces(MediaType.TEXT_PLAIN)
//	  public Date getDate() {
//	    return date; 
//	  }
	  
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getDateString() {
		  DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		  //date = new Date();
		  return dateFormat.format(date);
		  
	  }
	  
	  @POST
	  @Path("/add")
	  public void increaseDate(){
		  Calendar c = Calendar.getInstance();
		  c.setTime(date);
		  c.add(Calendar.DATE, 1);  
		  date = c.getTime();
	  }
}
