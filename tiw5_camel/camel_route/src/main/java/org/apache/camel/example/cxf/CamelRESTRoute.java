package org.apache.camel.example.cxf;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelRESTRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
    	
//        from("restlet:http://localhost:9001/camel_route/webservices/temperature/f/{degrees}?restletMethods=get")
//        .setBody().groovy("(request.getHeader('degrees', Double.class) - 32) / 1.8");
		
     	from("cxf:bean:rapideBankReponse")
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            //do whatever you need to get your object and transform it for your rest service
        	 exchange.getOut().setBody(null);
          }
        })
        .to("http://localhost:8080/service/rest/paiement/confirme/CMD-1");
		
	}

}
