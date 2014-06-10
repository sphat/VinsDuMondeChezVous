package tiw5.fourni.rapidbank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;

import tiw5.fourni.common.Paiement;
import tiw5.fourni.common.Debit;
import tiw5.fourni.common.ReponsePaiement;
import tiw5.fourni.common.ReponseDebit;

import com.googlecode.jatl.Html;

public class DebitServlet extends HttpServlet {

	private static final String CRYPT = "crypt";

	private static final String NUMCARTE = "numcarte";

	private static final String VALEUR = "valeur";

	private static final String CMDID = "cmdid";

	private static final long serialVersionUID = 1L;

	private String bankEndpoint;

	private String rapidoEndpoint;

	private Unmarshaller unmarshaller;

	private Marshaller marshaller;

	private SOAPConnection connection;

	private MessageFactory factory;

	private static enum Statut {
		Formulaire, Reussie, Echec;
	}

	public DebitServlet(String bep, String rep) throws JAXBException,
			UnsupportedOperationException, SOAPException {
		this.bankEndpoint = bep;
		this.rapidoEndpoint = rep;
		JAXBContext ctx = JAXBContext.newInstance(Debit.class,
				ReponseDebit.class, Paiement.class, ReponsePaiement.class);
		unmarshaller = ctx.createUnmarshaller();
		marshaller = ctx.createMarshaller();
		connection = SOAPConnectionFactory.newInstance().createConnection();
		factory = MessageFactory.newInstance();
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

	private Double getDoubleP(String name, HttpServletRequest req) {
		try {
			String val = req.getParameter(name);
			return val == null ? null : Double.parseDouble(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SOAPException, JAXBException {
		Statut statut = Statut.Formulaire;
		String raison = "erreur";
		Double valeur = getDoubleP(VALEUR, req);
		Long numcarte = getLongP(NUMCARTE, req);
		Integer crypt = getIntP(CRYPT, req);
		String cmdid = req.getParameter(CMDID);
		if (valeur != null && cmdid != null) {
			if (numcarte != null || crypt != null) {
				if (numcarte == null) {
					raison = "Numéro de carte manquant";
					statut = Statut.Echec;
				} else if (crypt == null) {
					raison = "Cryptogramme manquant";
					statut = Statut.Echec;
				} else {
					ReponseDebit repD = callBanqueCom(numcarte, crypt, valeur);
					if (repD.debitAccorde) {
						statut = Statut.Reussie;
						ReponsePaiement repA = callPaiement(cmdid,
								valeur);
						if (!repA.reussi) {
							statut = Statut.Echec;
							raison = repA.raison;
						}
					} else {
						statut = Statut.Echec;
						raison = repD.raison;
					}
				}
			}
		}
		view(req, resp, statut, raison);
	}

	private Integer getIntP(String name, HttpServletRequest req) {
		String val = req.getParameter(name);
		try {
			return val == null ? null : Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Long getLongP(String name, HttpServletRequest req) {
		String val = req.getParameter(name);
		try {
			return val == null ? null : Long.parseLong(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void view(final HttpServletRequest req, HttpServletResponse resp,
			final Statut statut, final String raison) throws IOException {
		final String cmdid = req.getParameter(CMDID);
		final String valeur = req.getParameter(VALEUR);
		PrintWriter w = resp.getWriter();
		new Html(w) {
			{
				bind("cmdid", cmdid);
				bind("raison", raison);
				html();
				head().title().text("Payer").end(2);
				body();
				h1().text("Paiement en ligne").end();
				if (cmdid == null) {
					p().text("Utilisateur non specifie (parametre ").tt()
							.text(CMDID).end().text(")").end();
				} else {
					div();
					switch (statut) {
					case Echec:
						p().text("La demande a echoue: ${raison}").end();
					case Formulaire:
						form().method("post");
						input().type("hidden").name(CMDID).value(cmdid);
						input().type("hidden").name(VALEUR).value(valeur);
						p().text("Numéro de carte: ").input().type("text")
								.value(req.getParameter(NUMCARTE))
								.name(NUMCARTE).end(2);
						p().text("Cryptogramme").input().type("text")
								.value(req.getParameter(CRYPT)).name(CRYPT)
								.end(2);
						input().type("submit");
						break;
					case Reussie:
						p().text("La transaction est réussie").end();
					}
				}
				endAll();
			}
		};
	}

	private ReponseDebit callBanqueCom(long numCarte, int cryptogramme,
			double valeur) throws SOAPException, JAXBException {
		Debit debit = new Debit();
		debit.numeroCarte = numCarte;
		debit.crytogramme = cryptogramme;
		debit.montant = valeur;
		SOAPMessage req = factory.createMessage();
		marshaller.marshal(debit, req.getSOAPBody());
		SOAPMessage rep = connection.call(req, bankEndpoint);
		return (ReponseDebit) unmarshaller.unmarshal((Node) rep.getSOAPBody()
				.getChildElements().next());
	}

	private ReponsePaiement callPaiement(String cmdid, double montant)
			throws SOAPException, JAXBException {
		Paiement app = new Paiement();
		app.montant = montant;
		app.cmdid = cmdid;
		SOAPMessage req = factory.createMessage();
		marshaller.marshal(app, req.getSOAPBody());
		SOAPMessage rep = connection.call(req, rapidoEndpoint);
		return (ReponsePaiement) unmarshaller.unmarshal((Node) rep
				.getSOAPBody().getChildElements().next());
	}
}
