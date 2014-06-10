package fournisseur.service.ressources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.WebApplicationException;

public class DateParam {
	private final Date date;

	  public DateParam(String dateStr) throws WebApplicationException, ParseException {
	    if (dateStr.equals("")) {
	      this.date = null;
	      return;
	    }
	    
	    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    this.date = dateFormat.parse(dateStr);
	     
	  }

	  public Date getDateParam() {
	    return date;
	  }

}
