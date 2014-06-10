package vin.dal.db.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vin.dal.db.intf.DAOException;
import vin.dal.db.intf.IBottle;
import vin.dal.db.intf.ICommande;
import vin.dal.db.intf.ILocation;
import vin.dal.db.intf.IWine;
import vin.dal.db.intf.IWineDAO;
import vin.dal.db.model.Bottle;
import vin.dal.db.model.Client;
import vin.dal.db.model.Commande;
import vin.dal.db.model.CommandeArticle;
import vin.dal.db.model.Location;
import vin.dal.db.model.Wine;

@Component
public class JPAWineDAO {
	
	@PersistenceContext 
	private EntityManager em;
	private EntityManagerFactory emf;
	private static Query query;
	private static final String COMMAND_PREFIX = "CMD-";
	private static final String CMD_OUVERTE = "OUVERTE";
	private static final String CMD_CONFIRMEE = "CONFIRMEE";
	private static final String CMD_LIVREE = "LIVREE";
	
	//Paramètre pour la clef du Map de Commande Detail
	public static final String CMD_DETAIL_TOTAL_PRICE= "TotalPrice";
	public static final String CMD_DETAIL_CMD_ID = "CommandID";
	public static final String CMD_DETAIL_LIV_DATE = "LivraisonDate";
	
	public static final String _INTROUVABLE = "Introuvable";
	public static final String _INSUFFISANT = "Insuffisant";
	public static final String _LIVRE_DATE  = "DateDeLivraison";
	public static final String _LIVRE_DATE_STOCK_INSUFFISANT = "DateDeLivraisonStockInsuffisant";
	public static final String _CONFIRMEE   = "Confirmer_statut";
	
	
	// Après la confirmation, la date de livraison sera = date de confirmation + 1 jours
	private static final Integer _DELAI_LIVRAISON = 1;
	
	// Si le stock n'est pas suffisant la date de livraison est donc = date de confirmaition + 3
	private static final Integer _STOCK_INSUFFISANT_DELAIT_LIVRAISON = 3;

	public JPAWineDAO() {
	}

	public JPAWineDAO(EntityManager em_ref) {
		em = em_ref;
	}

	public EntityManagerFactory getEmf() {
		return this.emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
		em = emf.createEntityManager();
	}

	public void setEntityManager(EntityManager em_ref) {
		em = em_ref;
	}
	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insertOrUpdate(IWine wine) throws DAOException {
		try {
			Wine w = (Wine) wine;
			if (w.getFBId() != null && em.find(Wine.class, w.getFBId()) != null) {
				em.merge(w);
			} else {
				em.persist(w);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insertOrUpdate(IBottle bottle) throws DAOException {
		try {
			Bottle b = (Bottle) bottle;
			if (b.getId() != null && em.find(Bottle.class, b.getId()) != null) {
				em.merge(b);
			} else {
				em.persist(b);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insertOrUpdate(ILocation location) throws DAOException {
		try {
			Location l = (Location) location;
			if (l.getId() != null && em.find(Location.class, l.getId()) != null) {
				em.merge(l);
			} else {
				em.persist(l);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
	public Wine findWineById(String fbId) throws DAOException {
		return em.find(Wine.class, fbId);
	}

	 
	public Bottle findBottleById(long id) throws DAOException {
		return em.find(Bottle.class, id);
	}

	/**
	 * Chercher commande par commande_id<br />
	 * 
	 * @param cmd_id
	 * @return Commande <b>Objet Commande</b>
	 */
	 
	public Commande findCommandById(Long cmd_id) throws DAOException {
		return em.find(Commande.class, cmd_id);
	}

	/**
	 * Commande ID à communiquer avec le client
	 * 
	 * @param cmdID
	 * @return objet Commande
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
	public Commande findCommandById(String cmdID) throws DAOException {
		StringBuffer str_sql = new StringBuffer();
		str_sql.append("SELECT ");
		str_sql.append("cmd ");
		str_sql.append("FROM ");
		str_sql.append("vin.dal.db.model.Commande").append(" cmd ");
		str_sql.append("WHERE ");
		str_sql.append("cmd.cmdID =:cmdID_Val");
		query = em.createQuery(str_sql.toString());
		query.setParameter("cmdID_Val", cmdID);

		List<Commande> commande = query.getResultList();

		if (commande.size() > 0) {
			return (Commande) commande.get(0);
		}

		return null;
	}

	 
	public Location findOrCreateLocation(String country, String region,
			String subregion) throws DAOException {
		try {
			Location l = (Location) em
					.createQuery(
							"SELECT l FROM Location l WHERE l.country = :c AND l.region = :r AND l.subRegion = :s")
					.setParameter("c", country).setParameter("r", region)
					.setParameter("s", subregion).getSingleResult();
			return l;
		} catch (NoResultException e) {
			Location l = new Location();
			l.setCountry(country);
			l.setRegion(region);
			l.setSubRegion(subregion);
			em.persist(l);
			return l;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
	public Collection<IWine> getAllWines() {
		return new ArrayList<IWine>(em.createQuery("SELECT w FROM Wine w").getResultList());
	}

	@SuppressWarnings("unchecked")
	 
	public Collection<ILocation> getAllLocations() {
		return new ArrayList<ILocation>(em.createQuery(
				"SELECT l FROM Location l").getResultList());
	}

	@SuppressWarnings("unchecked")
	 
	public Collection<IBottle> getAllBottles() {
		return new ArrayList<IBottle>(em.createQuery("SELECT b FROM Bottle b")
				.getResultList());
	}

	@SuppressWarnings("unchecked")
	 
	public Collection<ICommande> getAllCommandes() {
		return new ArrayList<ICommande>(em.createQuery(
				"SELECT c FROM Commande c").getResultList());
	}

	 
	public void deleteWine(IWine wine) {
		em.remove((Wine) wine);
	}

	 
	public void deleteBottle(IBottle bottle) {
		em.remove((Bottle) bottle);
	}

	 
	public void deleteLocation(ILocation location) {
		em.remove((Location) location);
	}

	/**
	 * Generate Commande ID
	 * 
	 * @param commande
	 *            <b>Objet Commande</b>
	 * @throws DAOException
	 */
	public String generateCommandeID(Long cmdId) throws DAOException {
		String gener_cmd_id = null;
		if (em.find(Commande.class, cmdId) != null) {
			Commande cmd = this.findCommandById(cmdId);
			gener_cmd_id = COMMAND_PREFIX + cmd.getId();
			cmd.setCmdID(gener_cmd_id);
			em.merge(cmd);
		}
		return gener_cmd_id;
	}

	/**
	 * Supprimer la commande par ID
	 * 
	 * @param commande
	 *            <b>Objet Commande</b>
	 */
	public void deleteCommande(ICommande commande) {
		em.remove((Commande) commande);
	}

	/**
	 * COMMANDE STATUS<br />
	 * 1. OUVERT<br />
	 * 2. CONFIRMEE<br />
	 * 3. LIVREE<br />
	 * Permettre de mettre a jour le statut de la commande<br />
	 * 
	 * @param commande_id
	 *            <b>Commande ID</b>
	 * @param cmd_status
	 *            <b>Commande status</b>
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateCommandeStatus(Long cmd_id, String cmd_status)
			throws DAOException {
		// TODO update commande status
		Commande cmd = this.findCommandById(cmd_id);
		cmd.setCommandeStatus(cmd_status);
		em.merge(cmd);
	}

	/**
	 * Créer la date de livraison
	 * 
	 * @param commande_id
	 *            <b>Commande ID</b>
	 * @param lvr_date
	 *            <b>Date de livraison</b>
	 */
	public void setLivraisonDate(Long cmd_id, Date lvr_date)
			throws DAOException {
		// TODO update commande status
		Commande cmd = this.findCommandById(cmd_id);
		cmd.setDateLivraison(lvr_date);
		em.merge(cmd);
	}

	/**
	 * CREATE COMMANDE<br />
	 * Permettre de effecter la commande<br />
	 * 
	 * @param cmdArt
	 *            ,Objet Article
	 * @throws DAOException
	 * @return Object List pour CommandeInfo
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Object> createCommande(Map<String, Integer> cmdArt, Client cli)
			throws DAOException {
		// TODO return commandID and totalPrice
		// Commande Info
		List<Object> cmdInfo = new ArrayList<Object>();
		Double totalPrice = 0D;
		
		// Créer la commande
		Commande cmd = new Commande();
		cmd.setCommandeStatus(CMD_OUVERTE);
		cmd.setClient(cli);
		Calendar cmdDate = Calendar.getInstance();
		cmd.setDateCommande(cmdDate.getTime());
		em.persist(cmd);

		Long cmdID = cmd.getId();
		Wine wine = null;
		CommandeArticle cmd_art = null;
		// Créer commande article
		for (String wine_id : cmdArt.keySet()) {
			int wine_qty = cmdArt.get(wine_id);
			wine = this.findWineById(wine_id);
			cmd_art = new CommandeArticle();
			cmd_art.setQuantity(wine_qty);
			cmd_art.setWine(wine);
			cmd_art.setCommande(cmd);

			totalPrice += (wine.getPrice()* wine_qty);

			em.persist(cmd_art);
		}
		
		cmdInfo.add(new String(this.generateCommandeID(cmdID)));
		cmdInfo.add(new Double(totalPrice));
		return cmdInfo;
	}

	/**
	 * Confirm la commande et enlèver le stock de Vin<br />
	 * 1.Trouver commande d'Infos, si ne le trouve pas -> return "Introuvable".<br />
	 * -!- Commande à confirmer dois être dans le status "OUVERTE".<br />
	 * 2.Récupérer le nombre d'article.<br />
	 * 3.Récupérer le nombre de bouteilles, si le nombre d'articl <= nombre de
	 * bouteilles enlèver le bouteilles si non -> return "Insuffisant".<br />
	 * 4.Si tous l'opération réussites -> return date de livraison ( = date de
	 * confirmation + 3) , et MAJ commandeStatut
	 * 
	 * @param commande
	 *            .cmdID.<br />
	 * @return Map de troi keys[_INSUFFISANT, _LIVRE_DATE, _INTROUVABLE].<br />
	 *         Date de livraison prévu = date de confirmation + 3;
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Map<String, Object> confirmeCommande(String cmdID)
			throws DAOException {
		// Step 1
		Commande cmd = this.findCommandById(cmdID);
		Map<String, Object> cfr_return = new HashMap<String, Object>();
		Calendar lvr_date = Calendar.getInstance();
		if (cmd != null && cmd.getCommandeStatus().trim().equals(CMD_OUVERTE)) {
			// Step 2
			Boolean is_ok = true;
			for (CommandeArticle cmdArt : cmd.getCommandArticles()) {
				// Step 3
				Wine v = cmdArt.getWine();
				if (cmdArt.getQuantity() > v.getBottles().size()) {
					List<Object> cmdInfo = new ArrayList<Object>();
					cmdInfo.add(new String(v.getFBId()));
					cmdInfo.add(new Integer(cmdArt.getQuantity()));
					cfr_return.put(_INSUFFISANT, cmdInfo);
					is_ok = false;
				}
			}

			if (is_ok) {
				// Enlèver les bouteilles
				for (CommandeArticle cmdArt : cmd.getCommandArticles()) {
					Wine v = cmdArt.getWine();
					Collection<Bottle> b = v.getBottles();
					for (int i = 0; i < cmdArt.getQuantity(); i++) {
						// Begin Transaction
						this.deleteBottle((Bottle) b.toArray()[i]);
					}
				}
				// Step 4
				this.updateCommandeStatus(cmd.getId(), CMD_CONFIRMEE);
				lvr_date.add(Calendar.DATE, _DELAI_LIVRAISON);
				this.setLivraisonDate(cmd.getId(), lvr_date.getTime());
				cfr_return.put(_LIVRE_DATE, lvr_date.getTime());
			}
			return cfr_return;
		} else {
			cfr_return.put(_INTROUVABLE, cmdID);
			return cfr_return;
		}
	}

	/**
	 * Confirme la bonne livraison de la commande.
	 * 1. Trouver commande d'info (à condition que la commande a été CONFIRMEE).<br />
	 * Si ne trouve pas -> CommandeInconnu.<br />
	 * Si non -> Mettre la commande statut = 'LIVREE' et set 'TRUE'
	 * @return Map de deux keys [_INTROUVABLE, CMD_LIVREE]
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Map<String, Object> livraisonEffectuee(String cmdID)
			throws DAOException {
		Commande cmd = this.findCommandById(cmdID);
		Map<String, Object> cfr_return = new HashMap<String, Object>();
		if (cmd != null) {
			if(cmd.getCommandeStatus().trim().equals(CMD_CONFIRMEE)){
				cfr_return.put(_CONFIRMEE, true);
				//TODO à tester
				this.updateCommandeStatus(cmd.getId(), CMD_LIVREE);
			}else{
				cfr_return.put(_CONFIRMEE, false);
			}
		}else{
			cfr_return.put(_INTROUVABLE, cmdID);
		}
		return cfr_return;
	}
	
	/**
	 * Permettre de récupérer la detail de commande en fonction de commande ID
	 * @param cmdID
	 * @return Map<Key, Object> <br />
	 * ex: Map<TotalPrice, 28.9> <br />
	 *     Map<CommandID, CMD-9907>
	 * @throws DAOException 
	 */
	@Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> getCommandeDetail(String cmdID) throws DAOException{
		double prxiTotal = 0;
		Commande cmd = null;
		Map<String, Object> cmdDetail = new HashMap<String, Object>();
		if(cmdID != null){ cmd = this.findCommandById(cmdID); }
		if(cmd != null){
			//Chercher l'article dans la commande
			for (CommandeArticle cmdArt : cmd.getCommandArticles()) {
				//Récupérer le prix d'aticle
				Wine v = cmdArt.getWine();
				prxiTotal += (cmdArt.getQuantity() * v.getPrice());
			}
			cmdDetail.put(CMD_DETAIL_TOTAL_PRICE, prxiTotal);
			cmdDetail.put(CMD_DETAIL_CMD_ID, cmdID);
			cmdDetail.put(CMD_DETAIL_LIV_DATE, cmd.getDateLivraison());
		}
		
		return cmdDetail;
	}
	
	/**
	 * Utiliser pour calcule la date de livraision par VinID
	 * @param wID
	 * @return
	 * @throws DAOException 
	 */
	public Map<String, Object> getDateDeLivrasionByWineID(String wID) throws DAOException{
		Map<String, Object> dlvr_return = new HashMap<String, Object>();
		Calendar lvr_date = Calendar.getInstance();
		Wine wine = this.findWineById(wID);
		
		if(wine.getBottles().size() > 0){
			lvr_date.add(Calendar.DATE, _DELAI_LIVRAISON);
			dlvr_return.put(_LIVRE_DATE, lvr_date.getTime());
		}else{
			lvr_date.add(Calendar.DATE, _STOCK_INSUFFISANT_DELAIT_LIVRAISON);
			dlvr_return.put(_LIVRE_DATE_STOCK_INSUFFISANT, lvr_date.getTime());
		}
		
		return dlvr_return;
	}
	
	/**
	 * Utiliser pour calcule la date de livraision par VinID
	 * @param cmdArt
	 * @return
	 * @throws DAOException
	 */
	public CommandeArticle estimateArticleLvrDate(CommandeArticle cmdArt) throws DAOException{
		CommandeArticle _cmdArt = cmdArt;
		Calendar lvr_date = Calendar.getInstance();
		Wine wine = this.findWineById(cmdArt.getWine().getFBId());
		
		if(wine.getBottles().size() > 0){
			lvr_date.add(Calendar.DATE, _DELAI_LIVRAISON);
			_cmdArt.setEstimateLvrDate(lvr_date.getTime());
		}else{
			lvr_date.add(Calendar.DATE, _STOCK_INSUFFISANT_DELAIT_LIVRAISON);
			_cmdArt.setEstimateLvrDate(lvr_date.getTime());
		}
		
		return _cmdArt;
	}
}
