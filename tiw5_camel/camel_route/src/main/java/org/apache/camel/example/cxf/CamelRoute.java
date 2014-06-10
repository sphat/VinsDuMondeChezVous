/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.cxf;

import javax.servlet.ServletRequest;
import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.component.cxf.common.message.CxfConstants;

import fr.univ_lyon1.m2ti.services.paiement.Paiement;
import fr.univ_lyon1.m2ti.services.paiement.ReponsePaiement;

// this static import is needed for older versions of Camel than 2.5
// import static org.apache.camel.language.simple.SimpleLanguage.simple;

/**
 * The Camel route
 * 
 * @version
 */
// START SNIPPET: e1
public class CamelRoute extends RouteBuilder {

	// CXF webservice using code first approach
	private String uri = "cxf:/paiement?serviceClass="
			+ fr.univ_lyon1.m2ti.services.paiement.PaiementPortTypeImpl.class
					.getName();

	private String path_cmd_id;
	private String URI_VINDUMONDECHEZVOUS_RECEVOIR_CMD = "restlet:http://localhost:8080/service/rest";
	private static String URL_RECEVOIR_LA_COMMANDE = "cxfrs:/rest?resourceClasses="
			+ vin.service.recevoir_une_commande.IRevevoirUneCommande.class
					.getName();

	private static String URL_VINDUMONDECHEZ_VOUS_CONFIRME_PAIEMENT = "restlet:http://localhost:8080/service/rest/paiement/confirme/";

	@Override
	public void configure() throws Exception {

		// Router entre VinsDumondeChezVous et service (service)
		// Ce route permttre d'envoyer un message pour la confirmation de la
		// commande
		from(URL_RECEVOIR_LA_COMMANDE)
				.to("log:info")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						Message inMessage = exchange.getIn();
						String operationName = inMessage.getHeader(
								CxfConstants.OPERATION_NAME, String.class);

						String httpMethod = inMessage.getHeader(
								Exchange.HTTP_METHOD, String.class);

						path_cmd_id = inMessage.getHeader(Exchange.HTTP_PATH,
								String.class);

						System.out.println("operationName : " + operationName);
						System.out.println("httpMethod : " + httpMethod);
						System.out.println("path : " + path_cmd_id);

						// We return the remote client IP address this time
						org.apache.cxf.message.Message cxfMessage = inMessage
								.getHeader(CxfConstants.CAMEL_CXF_MESSAGE,
										org.apache.cxf.message.Message.class);
						ServletRequest request = (ServletRequest) cxfMessage
								.get("HTTP.REQUEST");
						String remoteAddress = request.getRemoteAddr();
						Response r = Response
								.status(200)
								.entity("The remoteAddress is " + remoteAddress)
								.build();
						exchange.getOut().setBody(r);

						exchange.getOut().setHeader("cmdid", path_cmd_id);

						r.close();

					}
				})
				.to("log:info")
				.recipientList(
						constant(URI_VINDUMONDECHEZVOUS_RECEVOIR_CMD).append(
								header("cmdid"))).endParent().end();
		// Terminer Router entre VinsDumondeChezVous et service (service)

		// Router Confirme paiement entre RapideBand et VinsDumondeChezVous
		from(uri).to("log:input").recipientList(
				simple("direct:${header.operationName}"));

		from("direct:PaiementOperation")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						// get the id of the input
						String id = exchange.getIn().getBody(Paiement.class)
								.getCmdid();

						// set reply including the id
						ReponsePaiement output = new ReponsePaiement();
						output.setOk(true);
						output.setRaison(id);
						exchange.getOut().setBody(output);
						exchange.getOut().setHeader("cmd-id", id);
					}
				})
				.to("log:info")
				.recipientList(
						constant(URL_VINDUMONDECHEZ_VOUS_CONFIRME_PAIEMENT).append(
								header("cmd-id"))).endParent().end();
		// Terminer Router Confirme paiement entre RapideBand et VinsDumondeChezVous

	}

}
// END SNIPPET: e1
