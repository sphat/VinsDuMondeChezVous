package vin.site.controlleur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vin.dal.db.intf.DAOException;
import vin.dal.db.intf.IMessage;
import vin.site.bean.GestionClientsBean;
import vin.site.bean.GestionMessagesBean;
import vin.site.bean.GestionVinsBean;
import vin.site.commande_api.Commande;
import vin.site.commande_api.CommandeInfos;
import vin.site.commande_api.PanierSession;
import vin.site.commande_api.ServiceURLParam;
import vin.site.commande_api.UserSession;
import vin.site.error_notification.NonDisponibleException;
import vin.site.message_jms.Jms_message;

/**
 * Servlet implementation class VinServletHandler
 */

@Component("VinServletHandler")
public class VinRequestHandler implements org.springframework.web.HttpRequestHandler{
	private static final Logger LOG = LoggerFactory.getLogger(VinRequestHandler.class);
	
	@Autowired public Commande cmd;
	PanierSession panierSession;
	Jms_message jmsMessage;
	GestionVinsBean gestionVinsBean;
	GestionClientsBean gestionClientsBean;
	private GestionMessagesBean gestionMessagesBean;

	
	/////////////////////////////////Getter-Setter//////////////////////////////
	public GestionVinsBean getGestionVinsBean() {
		return gestionVinsBean;
	}

	public void setGestionVinsBean(GestionVinsBean gestionVinsBean) {
		this.gestionVinsBean = gestionVinsBean;
	}

	public PanierSession getPanierSession() {
		return panierSession;
	}

	public void setPanierSession(PanierSession panierSession) {
		this.panierSession = panierSession;
	}
	
	public void setJmsMessage(Jms_message jmsMessage) {
		this.jmsMessage = jmsMessage;
	}

	public GestionClientsBean getGestionClientsBean() {
		return gestionClientsBean;
	}

	public void setGestionClientsBean(GestionClientsBean gestionClientsBean) {
		this.gestionClientsBean = gestionClientsBean;
	}
	
	public GestionMessagesBean getGestionMessagesBean() {
		return gestionMessagesBean;
	}

	public void setGestionMessagesBean(GestionMessagesBean gestionMessagesBean) {
		this.gestionMessagesBean = gestionMessagesBean;
	}

	String version;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.info(this.version, "Bean Vin Servlet");
		try {
			this.processRequest(request, response);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonDisponibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException, NonDisponibleException {
		String result ="";
		String action = request.getParameter("action");
		switch (action){
		case "GetListeVins":
			request.setAttribute("linkCSS", "Styles/ListeVins.css");
			request.setAttribute("ListeVins", gestionVinsBean.getListeVins());
			request.setAttribute("ListeVins_Reponse", "1");
			request.getRequestDispatcher("ListeVins.jsp").forward(request, response);
			break;
			
		case "GetVinDetail":
			request.setAttribute("linkCSS", "Styles/VinDetail.css");
			String VinID = request.getParameter("VinID");
			request.setAttribute("Vin", gestionVinsBean.getVin(VinID));
			request.getRequestDispatcher("VinDetail.jsp").forward(request, response);
			break;
			
		case "panier" : 
			request.setAttribute("linkCSS", "Styles/Panier.css");
			request.getRequestDispatcher("Panier.jsp").forward(request, response);
			break;
			
		case "PanierSession":
			request.setAttribute("gestionVinsBean", gestionVinsBean);
			panierSession.addPanierSession(request, response);
			//System.out.println("email login :"+request.getAttribute("emailLogin"));
			break;
			
		case "countPanierProduit": 
			panierSession.countPanierProduit(request, response);
			break;
			
		case "getPanierDateLvr" :
			request.setAttribute("gestionVinsBean", gestionVinsBean);
			panierSession.getProduitPanierLivraisonDate(request, response);
			break;
		
		case "TerminerLaCommande" :
			if(UserSession.session.getAttribute(UserSession.SESSION_USER_ID)!=null && UserSession.session.getAttribute(UserSession.SESSION_USER_NAME)!=null){
				request.setAttribute("gestionVinsBean", gestionVinsBean);
				request.setAttribute("gestionClientsBean", gestionClientsBean);
				//ici, on enregistre la commande
				CommandeInfos cmdInfos = panierSession.createCommande(request, response);

				//ici, on appelle le REST (web service)
				URL url = new URL(ServiceURLParam.RECEVOIR_CMD+"/"+cmdInfos.getCmdId());
				HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
				System.out.println("sending request...");
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod("GET");
				httpCon.setRequestProperty( "Content-type", "text/xml" );
				httpCon.setRequestProperty( "accept", "text/xml" );
				httpCon.setRequestProperty( "authorization", "Basic " + ("administrator:collation"));
		        Map headerFields = httpCon.getHeaderFields();
		        System.out.println("header fields are: " + headerFields);

		         int rspCode = httpCon.getResponseCode();
		         if (rspCode == 200) {
		             InputStream ist = httpCon.getInputStream();
		             InputStreamReader isr = new InputStreamReader(ist);
		             BufferedReader br = new BufferedReader(isr);
		         
		             String nextLine = br.readLine();
		             while (nextLine != null) {
		                 System.out.println(nextLine);
		                 nextLine = br.readLine();
		             }
		             
		             br.close();
		             isr.close();
		             ist.close();
		         }
		       httpCon.disconnect();
		       //Terminer appelle le REST (web service) 
				
				request.setAttribute("cmdInfos", cmdInfos);
				
				request.setAttribute("linkCSS", "Styles/TerminerLaCommande.css");
				request.getRequestDispatcher("TerminerLaCommande.jsp").forward(request, response);
				
			}else{
				//Affichier la page login, s'il le client n'est pas encore se connecter
				request.setAttribute("linkCSS", "Styles/Login.css");
			    request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
			break;
			
		case "Login":
			request.setAttribute("linkCSS", "Styles/Login.css");
			String EmailLogin = request.getParameter("tbEmail");
		    String PasswordLogin = request.getParameter("tbPassword");
		    if(EmailLogin != null && PasswordLogin != null){
			    if(getGestionClientsBean().Anthentification(EmailLogin, PasswordLogin)){

			    	UserSession.sessionLogin(EmailLogin, getGestionClientsBean().getUtilisateur(EmailLogin).getNom(), String.valueOf(getGestionClientsBean().getUtilisateur(EmailLogin).getId()) );
			    	//Tester si il existe le produit dans le panier ou pas, si il existe redireger ver la page terminer la commande
			    	if(panierSession.getCountPanierProduit(request, response)>0){
						//request.setAttribute("linkCSS", "Styles/TerminerLaCommande.css");
						//request.getRequestDispatcher("TerminerLaCommande.jsp").forward(request, response);	
						request.getRequestDispatcher("VinServlet?action=TerminerLaCommande").forward(request, response);	
			    	}else{
				    	request.setAttribute("resultatLogin", "reusir");
				    	request.setAttribute("emailLogin", EmailLogin);

				    	request.setAttribute("NomUtilisateur", getGestionClientsBean().getUtilisateur(EmailLogin).getNom());
				    	request.getRequestDispatcher("Login.jsp").forward(request, response);			    		
			    	}
			    }else{
			    	request.setAttribute("resultatLogin", "fail");
			    	request.getRequestDispatcher("Login.jsp").forward(request, response);
			    }
		    }else{
		    	request.getRequestDispatcher("Login.jsp").forward(request, response);
		    }
			break;
		
		case "Logout" :
			UserSession.sessionLogout(UserSession.session.getAttribute(UserSession.SESSION_USER_NAME).toString(), UserSession.session.getAttribute(UserSession.SESSION_USER_ID).toString());
			request.setAttribute("linkCSS", "Styles/ListeVins.css");
			request.setAttribute("ListeVins", gestionVinsBean.getListeVins());
			request.setAttribute("ListeVins_Reponse", "1");
			request.getRequestDispatcher("ListeVins.jsp").forward(request, response);
			break;
			
		case "CreerUnCompte":
			request.setAttribute("linkCSS", "Styles/CreerUnCompte.css");
			//Vérifier si SubmitForm
			if(request.getParameter("btCreerCompte") != null){
				String Email = request.getParameter("tbEmail");
			    String Password = request.getParameter("tbPass");
			    String rePassword = request.getParameter("tbrePass");
			    double compte = Double.parseDouble(request.getParameter("tbCompte"));
			    String nom = request.getParameter("tbNom");
			    if(Password.equals(rePassword)){
			    	if(getGestionClientsBean().AjouterUnCient(Email, compte, Password, nom)){
			    	   request.setAttribute("resultatCreerCompte", "reusir");
			           request.getRequestDispatcher("CreerUnCompte.jsp").forward(request, response);
			    	}
			    	else{
			    		request.setAttribute("resultatCreerCompte", "adressExist");
			           request.getRequestDispatcher("CreerUnCompte.jsp").forward(request, response);
			    	}
			       }
			       else{
			    	   request.setAttribute("resultatCreerCompte", "fail");
			           request.getRequestDispatcher("CreerUnCompte.jsp").forward(request, response);
			       }
			}else{
				request.getRequestDispatcher("CreerUnCompte.jsp").forward(request, response);
			}
			break;
		
		case "Message":
			if(UserSession.session.getAttribute(UserSession.SESSION_USER_ID)!=null && UserSession.session.getAttribute(UserSession.SESSION_USER_NAME)!=null){
				request.setAttribute("linkCSS", "Styles/Message.css");
				ArrayList<IMessage> messages = (ArrayList<IMessage>)getGestionMessagesBean().getAllMessagesByIdClient(Integer.parseInt(UserSession.session.getAttribute(UserSession.SESSION_USER_ID).toString()));
				request.setAttribute("ListMessage",messages);
		        request.getRequestDispatcher("Message.jsp").forward(request, response);
			}else{
				request.setAttribute("linkCSS", "Styles/Login.css");
		    	request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
			break;
			
		case "Stock" : 
			request.setAttribute("ListeVins", gestionVinsBean.getListeVins());
			request.setAttribute("linkCSS", "Styles/Stock.css");
			request.getRequestDispatcher("Stock.jsp").forward(request, response);
			break;
		
		case "requestApprovisionement" :
			Jms_message.demanderApprovisionement(request, response);
			break;
			
		default:
			result = "Erreur dans les paramètres de la requête";
		}
		request.setAttribute("result", result);
		LOG.info("Requete traitee");
	}

	

}
