package tiw5.fourni.mabanque;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jatl.Html;

import tiw5.fourni.common.Banque;
import tiw5.fourni.common.Compte;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VAL = "val";
	private static final String NUM = "num";
	private static final Logger LOG = LoggerFactory
			.getLogger(AdminServlet.class);
	private Banque banque;

	public AdminServlet(Banque banque) {
		this.banque = banque;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	private enum Action {
		virement, ajout, xml, aucune;
	};

	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String actionS = req.getParameter("action");
		Action action = Action.aucune;
		try {
			if (actionS != null) {
				action = Action.valueOf(actionS);
			}
		} catch (Exception e) {
			LOG.warn("Action non interprétée: {}", e.getLocalizedMessage());
		}
		LOG.info("Action: {}",action);
		List<String> messages = new ArrayList<String>();
		try {
			switch (action) {
			case aucune:
				break;
			case ajout: {
				String nom = req.getParameter("nom");
				if (nom == null || nom.trim().equals("")) {
					messages.add("Nom non fourni");
					break;
				}
				String numcarte = req.getParameter("numcarte");
				Long numCarteL = null;
				if (numcarte == null || numcarte.trim().equals("")) {
					messages.add("Numéro de carte non fourni");
					break;
				} else {
					numCarteL = Long.parseLong(numcarte);
				}
				String crypt = req.getParameter("crypt");
				Integer cryptI = null;
				if (crypt == null || crypt.trim().equals("")) {
					messages.add("Cryptogramme non fourni");
					break;
				} else {
					cryptI = Integer.parseInt(crypt);
				}
				Compte c = new Compte();
				c.setClient(nom);
				c.setNumeroCarte(numCarteL);
				c.setCryptogramme(cryptI);
				banque.addCompte(c);
				messages.add("Compte créé");
				break;
			}
			case virement: {
				Long num = null;
				Double val = null;
				try {
					num = (req.getParameter(NUM) == null ? null : Long
							.parseLong(req.getParameter(NUM)));
				} catch (NumberFormatException e) {
					messages.add("Erreur pour le numero de carte: "
							+ e.getLocalizedMessage());
				}
				try {
					val = (req.getParameter(VAL) == null ? null : Double
							.parseDouble(req.getParameter(VAL)));
				} catch (NumberFormatException e) {
					messages.add("Erreur pour la valeur du virement: "
							+ e.getLocalizedMessage());
				}
				if (num != null && val != null) {
					Compte c = banque.getCompte(num);
					if (c != null) {
						c.virement(val);
						messages.add("Le compte de "
								+ c.getClient()
								+ " a été "
								+ (val > 0.0 ? ("crédité de " + val)
										: ("débité de " + (-val)))
								+ " euro(s).");
					} else {
						messages.add("Compte inconnu: " + num);
					}
				}
				break;
			}
			case xml:
				banque.saveXml();
				messages.add("Sauvegarde xml effectuée.");
			}
		} catch (Exception e) {
			LOG.error("Erreur", e);
			messages.add("Erreur: " + e.getMessage());
		}
		view(req, resp, messages);
	}

	private void view(HttpServletRequest req, HttpServletResponse resp,
			final List<String> messages) throws IOException {
		new Html(resp.getWriter()) {
			{
				html();
				head().title().text("mabanque.com: administration").end(2);
				body();
				h1().text("mabanque.com: administration").end();
				for (String s : messages) {
					p().text(s).end();
				}
				h2().text("Comptes").end();
				for (Compte c : banque.comptes) {
					div();
					p().text(
							"Compte de " + c.getClient() + " ("
									+ c.getNumeroCarte() + ","
									+ c.getCryptogramme() + "): "
									+ c.getValeur() + " euros").end();
					form().method("post");
					input().type("hidden").name("action").value(Action.virement.name());
					p();
					text("Ajouter / Retirer");
					input().name(NUM).value(String.valueOf(c.getNumeroCarte()))
							.type("hidden");
					input().name(VAL).value("50.0").type("text");
					text("euros");
					input().type("submit").end();
					end(3);
				}
				h2().text("Ajout utilisateur").end();
				form().method("post");
				input().type("hidden").name("action").value(Action.ajout.name());
				p();
				text("nom").input().type("text").name("nom").end();
				text("Numéro de carte").input().type("text").name("numcarte")
						.end();
				text("Cryptogramme").input().type("text").name("crypt").end();
				end();
				p().input().type("submit").value("Ajouter").end(2);
				end(); // form
				h2().text("XML").end();
				form().method("post");
				input().type("hidden").name("action").value(Action.xml.name());
				p().input().type("submit").value("Enregistrer").end(2);
				end(); // form
				endAll();
			}
		};
	}
}
