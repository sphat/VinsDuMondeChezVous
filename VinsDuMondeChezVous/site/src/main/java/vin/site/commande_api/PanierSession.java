package vin.site.commande_api;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import vin.dal.db.dao.JPAClientDAO;
import vin.dal.db.dao.JPAWineDAO;
import vin.dal.db.intf.DAOException;
import vin.dal.db.model.Client;
import vin.dal.db.model.CommandeArticle;
import vin.dal.db.model.Wine;
import vin.site.bean.GestionClientsBean;
import vin.site.bean.GestionVinsBean;
import vin.site.error_notification.NonDisponibleException;

public class PanierSession extends SessionInit implements ApplicationContextAware {		
	
	private ApplicationContext applicationContext;
	
	public CommandeArticle commandeArticle;
	public Commande cmd;
	public Article articleBean;
	public JSONObject json_obj;
	
    private static final String SESSION_ADD = "add";
    private static final String SESSION_SUBSTRACTION = "sub";
    private static final String SESSION_DELETE = "del";
    private static final String SESSION_NAME = "panier_session";
    private static final String JSON_TOTAL_PRICE = "total_price";
    private static final String JSON_TOTAL_UNIT_PRICE = "total_unit_price";
    private static final String JSON_TOTAL_QTY = "total_qty";
    		

    public Article createArticle(){
    	return this.applicationContext.getBean("articleBean", Article.class);
    }
    
	public CommandeArticle createCommandeArticle(){
		return this.applicationContext.getBean("commandeArticle", CommandeArticle.class);
	}
	
	public Commande createCommandeBean(){
		return this.applicationContext.getBean("cmd", Commande.class);		
	}
	
	public JSONObject createJSON(){
		return this.applicationContext.getBean("jsonBean", JSONObject.class);
	}
	
	/**
	 * 1.  Test si le vin n'est pa null
	 * 2.  Test si la session "panier_session" n'est pas null
	 * 2.1 Si oui (null) => Créer un
	 * 2.2 Si non => 
	 * 2.2.1	Test si le vin_id existe déjà ou pas
	 * 2.2.1.1		Si il exist
	 * 					Récupérer l'ancien puis
	 * 					Test la commande (+ / - / del)
	 * 2.2.1.2		Si non
	 * 					On peut ajouter seulement
	 * 
	 * Utilisation example : 
	 * http://localhost:8080/site/VinServlet?action=PanierSession&vid=WhiskeyFR&qty=1&cmd=add
	 * --------------------------------------------------------------------------------------
	 * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @author sereivuth
     * @throws DAOException 
     */
    @SuppressWarnings("unchecked")
	public void addPanierSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
		response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
        
//		session = request.getSession(true);
//		
//        // print session info
//
//        @SuppressWarnings("unused")
//		Date created = new Date(session.getCreationTime());
//        @SuppressWarnings("unused")
//		Date accessed = new Date(session.getLastAccessedTime());
        
        // set session info if needed

        String vid  = request.getParameter("vid");
        String qty = request.getParameter("qty");
        String cmd = request.getParameter("cmd"); //+ ou - ou del
        
        GestionVinsBean getVinTest = (GestionVinsBean) request.getAttribute("gestionVinsBean");
        Wine vin = getVinTest.getVin(vid);
        
        //CommandeArticle cmd_art = (CommandeArticle) request.getAttribute("commandeArticleBean");
        //CommandeArticle cmd_art = new CommandeArticle();
        CommandeArticle cmd_art = createCommandeArticle();
        List<CommandeArticle> lst_cmd_art = null;
        List<CommandeArticle> art_tmp = null; 
        boolean vin_existe = false;
        int index_found = -1;
        
        cmd_art.setWine(vin);
        cmd_art.setQuantity(Integer.valueOf(qty));
        
        //Set estimate DATE
        JPAWineDAO jpaDAO = getVinTest.getWineDao();
        cmd_art = jpaDAO.estimateArticleLvrDate(cmd_art);
        
        //1.
        if (vin != null) {
        	//2.2
        	if(session.getAttribute(SESSION_NAME)!=null){
        		//2.2.1	Test si le vin_id existe déjà ou pas
        		art_tmp = (List<CommandeArticle>) session.getAttribute(SESSION_NAME);
        		for(CommandeArticle cmdart : art_tmp){
        			Wine v_obj = cmdart.getWine();
        			if(v_obj.getFBId().equals(vid) && !vin_existe){
        				vin_existe = true;
        				index_found = art_tmp.indexOf(cmdart);
        			}
        		}
        		
        		//2.2.1.1
        		if(vin_existe){
        			if(index_found>=0){
	        			if(cmd.equals(SESSION_ADD)){
	        				cmd_art.setQuantity(Integer.valueOf(qty) + art_tmp.get(index_found).getQuantity());
	        				art_tmp.set(index_found, cmd_art);
	        			}else if(cmd.equals(SESSION_SUBSTRACTION)){
	            			if( (art_tmp.get(index_found).getQuantity() - Integer.valueOf(qty)) >= 1){
	            				cmd_art.setQuantity((art_tmp.get(index_found).getQuantity() - Integer.valueOf(qty)));
	            			}else{
	            				cmd_art.setQuantity(1);
	            			}
            				art_tmp.set(index_found, cmd_art);
	        			}else if(cmd.equals(SESSION_DELETE)){
	        				art_tmp.remove(index_found);
	        			}
        			}
        		}else{
            	//2.2.1.2
        			if(cmd.equals(SESSION_ADD)){
        				art_tmp.add(cmd_art);
        			}
        		}
        		
        		session.removeAttribute(SESSION_NAME);
        		session.setAttribute(SESSION_NAME, art_tmp);
        	}else{
        	//2.1
        		lst_cmd_art = new ArrayList<CommandeArticle> ();
        		lst_cmd_art.add(cmd_art);
        		session.setAttribute(SESSION_NAME, lst_cmd_art);
        	}
        	
        }
        
        this.countPanierProduit(request, response);

        // print session panier contents

//		for(CommandeArticle cmdart : (List<CommandeArticle>) session.getAttribute(SESSION_NAME)){
//			Wine vin_obj = cmdart.getWine();
//			out.println(SESSION_NAME + " : wine_id = " + vin_obj.getFBId() + " , wine_price = " + vin_obj.getPrice() + " , wine_qty = " + cmdart.getQuantity() + "<br />");
//		}
    }
    
    /**
     * Conter le nombre de produit et calcul le total quantité
     * @return 
     * @return JSON object
     * Pour appeller seulement ce fonction utilise le URL ci-dessous 
     * http://localhost:8080/site/VinServlet?action=countPanierProduit
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
	public void countPanierProduit(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		session = request.getSession(true);
//		
//        // print session info
//
//        @SuppressWarnings("unused")
//		Date created = new Date(session.getCreationTime());
//        @SuppressWarnings("unused")
//		Date accessed = new Date(session.getLastAccessedTime());
        
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        json_obj = this.createJSON();
        double totalPrice 	= 0;
        int totalProduit  	= 0;
        double totalUnitPrice	= 0;

        if(session.getAttribute(SESSION_NAME)!=null){
			for(CommandeArticle cmdart : (List<CommandeArticle>) session.getAttribute(SESSION_NAME)){
				Wine vin_obj = cmdart.getWine();
				totalPrice += (vin_obj.getPrice()*cmdart.getQuantity());
				totalProduit += cmdart.getQuantity();
				//calcule le prix unitaire
				if(request.getParameter("vid")!=null){
					if(vin_obj.getFBId().equals(request.getParameter("vid"))){
						totalUnitPrice = (vin_obj.getPrice()*cmdart.getQuantity());
					}
				}
			}
        }
		
        BigDecimal totalPriceR = new BigDecimal(totalPrice);
        BigDecimal totalPriceROff = totalPriceR.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        
        BigDecimal totalPriceU = new BigDecimal(totalUnitPrice);
        BigDecimal totalPriceUOff = totalPriceU.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        
		json_obj.put(JSON_TOTAL_PRICE, totalPriceROff);
		json_obj.put(JSON_TOTAL_UNIT_PRICE, totalPriceUOff);
		json_obj.put(JSON_TOTAL_QTY, totalProduit);
		
        out.println(json_obj.toJSONString());
        
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * Calcule la date de livraison de produit panier
	 * @param request
	 * @param response
	 * @throws DAOException
	 * @throws IOException 
	 * ex:
	 * pour utiliser appeller ce url
	 * http://localhost:8080/site/VinServlet?action=getPanierDateLvr&vid=WhiskeyFR
	 */
	public void getProduitPanierLivraisonDate(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException{
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
		Map<String, Object> dlvr_panier = new HashMap<String, Object>();
		GestionVinsBean wineBean = (GestionVinsBean) request.getAttribute("gestionVinsBean");
        String vid  = request.getParameter("vid");
        JPAWineDAO jpaDAO = wineBean.getWineDao();
        dlvr_panier = jpaDAO.getDateDeLivrasionByWineID(vid);
        if(dlvr_panier.get(JPAWineDAO._LIVRE_DATE_STOCK_INSUFFISANT) != null){
        	out.println(dlvr_panier.get(JPAWineDAO._LIVRE_DATE_STOCK_INSUFFISANT));
        }else{
        	out.println(dlvr_panier.get(JPAWineDAO._LIVRE_DATE));
        }
	}
	
	/**
	 * Permerter de compter le nombre de produit dans le panier
	 * @param request
	 * @param response
	 * @return Integer (le nombre total du produit dans le panier)
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountPanierProduit(HttpServletRequest request, HttpServletResponse response){
        int totalProduit  	= 0;
        if(session.getAttribute(SESSION_NAME)!=null){
			for(CommandeArticle cmdart : (List<CommandeArticle>) session.getAttribute(SESSION_NAME)){
				totalProduit += cmdart.getQuantity();
			}
        }
		return totalProduit;		
	}
	
	/**
	 * Permettre de générer l'array liste d'article depuis la session
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Article> getSessionArticle(HttpServletRequest request, HttpServletResponse response){
		
        List<CommandeArticle> art_tmp = null;
		ArrayList<Article> arr_article = new ArrayList<Article>();
 
		
		if(session.getAttribute(SESSION_NAME)!=null){
    		//2.2.1	Test si le vin_id existe déjà ou pas
    		art_tmp = (List<CommandeArticle>) session.getAttribute(SESSION_NAME);
    		for(CommandeArticle cmdart : art_tmp){
    			Article art = createArticle();
    			Wine v_obj = cmdart.getWine();
    			art.setNombre(cmdart.getQuantity());
    			art.setReferenceVin(v_obj.getFBId());
    			arr_article.add(art);
    		}
    	}
		
		return arr_article;
	}
	
	/**
	 * Permettre d'enregister la commande dans la base de données
	 * @param request
	 * @param response
	 * @throws NonDisponibleException 
	 * @return CommandeInfos
	 */
	@SuppressWarnings("unchecked")
	public CommandeInfos createCommande(HttpServletRequest request, HttpServletResponse response) throws NonDisponibleException{
		
		//Commande cmd = createCommandeBean();
		
        List<CommandeArticle> art_tmp = null;
		ArrayList<Article> arr_article = new ArrayList<Article>();
 
		
		if(session.getAttribute(SESSION_NAME)!=null){
    		//2.2.1	Test si le vin_id existe déjà ou pas
    		art_tmp = (List<CommandeArticle>) session.getAttribute(SESSION_NAME);
    		for(CommandeArticle cmdart : art_tmp){
    			Article art = createArticle();
    			Wine v_obj = cmdart.getWine();
    			art.setNombre(cmdart.getQuantity());
    			art.setReferenceVin(v_obj.getFBId());
    			arr_article.add(art);
    		}
    	}
		
		Article[] art_arr = new Article[arr_article.size()];
		for(int i=0; i< arr_article.size(); i++){
			art_arr[i] = arr_article.get(i);
		}
		//cmd.commande(art_arr);
		return commande(art_arr, request);
	}
	
	
	/**
	 * 
	 * @param vins
	 * @param request
	 * @return CommandeInfos
	 */
	public CommandeInfos commande(Article[] vins, HttpServletRequest request) {
		try {
	        GestionVinsBean wine = (GestionVinsBean) request.getAttribute("gestionVinsBean");
			JPAWineDAO wineDao = wine.getWineDao();
			
	        GestionClientsBean client = (GestionClientsBean) request.getAttribute("gestionClientsBean");
	        JPAClientDAO clientDao = client.getClientDao();
	        Client cli = (Client) clientDao.findClientById(Integer.parseInt(UserSession.session.getAttribute(UserSession.SESSION_USER_ID).toString()));
	        
			Map<String, Integer> cmdArt = new HashMap<String, Integer>();
			// Vérifier si le stock est suffisant pour la commande
			for (Article v_tmp : vins) {
				cmdArt.put(v_tmp.getReferenceVin(), v_tmp.getNombre());
			}
			
				//vider la session
				session.removeAttribute(SESSION_NAME);
			
				List<Object> cmdInfo = wineDao.createCommande(cmdArt, cli);
				return new CommandeInfos(Double.parseDouble(cmdInfo.get(1).toString()), cmdInfo.get(0).toString());
		} catch (DAOException e) {
			e.printStackTrace();
		}
			return null;
	}
	
}

