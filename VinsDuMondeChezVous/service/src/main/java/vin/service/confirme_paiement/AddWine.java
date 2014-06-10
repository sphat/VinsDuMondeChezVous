package vin.service.confirme_paiement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import vin.dal.db.dao.JPAWineDAO;
import vin.dal.db.intf.DAOException;
import vin.dal.db.model.Bottle;
import vin.dal.db.model.Wine;
import vin.service.Article;
import vin.service.bean.BeanAndDAO;

/**
 * MAJ Stock du VIN
 * @author sereivuth
 *
 */
@Component
@Path("/updatestock")
public class AddWine extends BeanAndDAO implements IAddWine {
	
	@POST
	@Path("/update")
	@Consumes("application/xml")
	/**
	 * La méthode retourne TRUE, tous les transactions réusiites
	 * Utiliser SOAPUI pour tester
	 * La structure du donnée pour tester
	 * <article>        
     *    <article>
     *       <reference>ABC_TEST</reference>
     *       <quantite>1</quantite>
     *    </article>
     *    <article>
     *       <reference>BourgogneRouge</reference>
     *       <quantite>1</quantite>
     *    </article>               
	 *	</article>
	 */
	public ReponseAddWine updateStockWine(Article[] approvArticle) throws DAOException {
		JPAWineDAO dao = gestionVinsBean.getWineDao();
		ReponseAddWine repAddWine = new ReponseAddWine();
		repAddWine.setOk(true);
		
		for(Article art :  approvArticle){
			Wine w = null;
			Bottle b = null;
			if((w=dao.findWineById(art.getReferenceVin()))!=null){
				for(Integer j=0; j<art.getNombre(); j++){
					b = w.newBottle();
					dao.insertOrUpdate(b);
				}
			}else{
				repAddWine.setOk(false);
			}
		}
		return repAddWine;
	}

}
