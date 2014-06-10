//package fournisseur.db;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Persistence;
//
//import fournisseur.db.dao.FournisseurDAO;
//import fournisseur.db.intf.EApprovisionnementStatus;
//import fournisseur.db.model.Approvisionnement;
//import fournisseur.db.model.ApprovisionnementDetail;
//import fournisseur.db.model.WineStock;
//
//import junit.framework.TestCase;
//
///**
// * Unit test for simple App.
// */
//public class AppTest extends TestCase {
//	public void testApp() {
//		EntityManager em = Persistence.createEntityManagerFactory("testf")
//				.createEntityManager();
//		em.close();
//	}
//
////	public void testInsert() throws Exception {
////		EntityManager em = Persistence.createEntityManagerFactory("testf")
////				.createEntityManager();
////		FournisseurDAO dao = new FournisseurDAO(em);
////		em.getTransaction().begin();
////		Calendar calendar = Calendar.getInstance();
////		Date dateLivraison = calendar.getTime();
////		List<String> listMagasins = dao.getListMagasinLivraison(dateLivraison);
////		System.out.println(listMagasins.size());
////		for(int i=0;i<listMagasins.size();i++){
////			System.out.println(listMagasins.get(i).toString());
////		}
//		
////		WineStock w2 = dao.findWineStockById("WhiskeyFR");
//		
////		Approvisionnement a = new Approvisionnement();
////		a.setCmdStatus(EApprovisionnementStatus.Confirmé);
////		a.setMagazine("Vin du monde chez vous");
////		List<ApprovisionnementDetail> l = new ArrayList<ApprovisionnementDetail>();
////		l.add(new ApprovisionnementDetail(w,10, a));
////		l.add(new ApprovisionnementDetail(w2,20, a));
////		a.setListDetail(l);
////		dao.insertOrUpdate(a);
//		//long a = 1;
//		//WineStock result = dao.getWineStockById("CruRouge");
//		//System.out.println(result.getWineId());
//		
//		//em.getTransaction().commit();
//		//em.close();
////	}
//}
