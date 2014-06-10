package fournisseur.service.ressources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.stereotype.Component;

 
@Component
@Path("/payment")
public class MyTest {
 
	@GET
	@Path("/mkyong2")
	public String savePayment() {
 
		String result = "hihihihi";
 
		return result;
 
	}
 
}