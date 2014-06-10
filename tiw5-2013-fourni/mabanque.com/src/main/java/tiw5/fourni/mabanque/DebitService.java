package tiw5.fourni.mabanque;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import tiw5.fourni.common.Banque;
import tiw5.fourni.common.Compte;
import tiw5.fourni.common.Debit;
import tiw5.fourni.common.ReponseDebit;

public class DebitService extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(DebitService.class);
	private Banque banque;
	private MessageFactory factory;
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;

	public DebitService(Banque banque) throws SOAPException, JAXBException {
		this.banque = banque;
		this.factory = MessageFactory.newInstance();
		JAXBContext ctx = JAXBContext.newInstance(Debit.class,
				ReponseDebit.class);
		unmarshaller = ctx.createUnmarshaller();
		marshaller = ctx.createMarshaller();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (SOAPException e) {
			throw new ServletException(e);
		} catch (JAXBException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (SOAPException e) {
			throw new ServletException(e);
		} catch (JAXBException e) {
			throw new ServletException(e);
		}
	}

	private SOAPMessage msgFromRequest(HttpServletRequest req)
			throws IOException, SOAPException {
		Enumeration<String> headers = req.getHeaderNames();
		MimeHeaders mheaders = new MimeHeaders();
		while (headers.hasMoreElements()) {
			String header = headers.nextElement();
			mheaders.addHeader(header, req.getHeader(header));
		}
		return factory.createMessage(mheaders, req.getInputStream());
	}

	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SOAPException, JAXBException {
		LOG.info("Receiving message");
		SOAPMessage msg = msgFromRequest(req);
		Node node = msg.getSOAPBody().getFirstChild();
		while (node.getNodeType() != Node.ELEMENT_NODE) {
			node = node.getNextSibling();
		}
		Debit debit = (Debit) unmarshaller.unmarshal(node);
		Compte c = banque.getCompte(debit.numeroCarte);
		String raison = null;
		if (c == null) {
			raison = "Compte inconnu";
		} else if (c.getCryptogramme() == debit.crytogramme) {
			if (c.getValeur() < debit.montant) {
				raison = "Compte insuffisant";
			} else {
				c.virement(-debit.montant);
			}
		} else {
			raison = "Cryptogramme incorrect";
		}
		ReponseDebit repDebit = new ReponseDebit();
		repDebit.debitAccorde = (raison == null);
		repDebit.raison = raison;
		SOAPMessage rep = factory.createMessage();
		SOAPBody body = rep.getSOAPBody();
		marshaller.marshal(repDebit, body);
		resp.setContentType(SOAPConstants.SOAP_1_1_CONTENT_TYPE);
		rep.writeTo(resp.getOutputStream());
		resp.getOutputStream().flush();
		LOG.info("Message processed: {}", raison == null ? "ok" : raison);
	}
}
