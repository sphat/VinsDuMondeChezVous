package tiw5.fourni.mockup;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tiw5.fourni.common.ReponsePaiement;

public class PaiementMockup {

	private static Logger LOG = LoggerFactory
			.getLogger(PaiementMockup.class);

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		server.setHandler(new AbstractHandler() {

			@Override
			public void handle(String target, Request baseRequest,
					HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				try {
					SOAPMessage msg = MessageFactory.newInstance()
							.createMessage();
					ReponsePaiement repA = new ReponsePaiement();
					repA.reussi = true;
					response.setContentType(SOAPConstants.SOAP_1_1_CONTENT_TYPE);
					JAXBContext.newInstance(ReponsePaiement.class)
							.createMarshaller()
							.marshal(repA, msg.getSOAPBody());
					msg.writeTo(response.getOutputStream());
					response.getOutputStream().flush();
					LOG.info("Sent response");
				} catch (SOAPException e) {
					throw new ServletException(e);
				} catch (JAXBException e) {
					throw new ServletException(e);
				}
			}
		});
		server.start();
		server.join();
	}

}
