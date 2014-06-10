//package vin.dal.db;
//
//import java.io.File;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Persistence;
//
//import org.dbunit.IDatabaseTester;
//import org.dbunit.JdbcDatabaseTester;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.dbunit.operation.DatabaseOperation;
//
//import vin.dal.db.dao.JPAMessageDAO;
//import junit.framework.TestCase;
//
//
//public class AppTest extends TestCase {
//
//	public void testMapping() {
//		EntityManager em = Persistence.createEntityManagerFactory("test")
//				.createEntityManager();
//		em.close();
//	}
//
//	public void testDAO() throws Exception {
//		EntityManager em = Persistence.createEntityManagerFactory("test")
//				.createEntityManager();
//		JPAMessageDAO dao = new JPAMessageDAO();
//		dao.setEntityManager(em);
//		em.getTransaction().begin();
//
//
//		//De commenter pour créer le jeu de données
//		IDataSet dataSet = readDataSet();
//		cleanlyInsertDataset(dataSet);
//	}
//
//	private IDataSet readDataSet() throws Exception {
//		File file = new File("src//main//resources//dataset//dataset_wine.xml");
//		return new FlatXmlDataSetBuilder().build(file);
//	}
//
//	private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
////		 IDatabaseTester databaseTester = new
////		 JdbcDatabaseTester("org.h2.Driver",
////		 "jdbc:h2:tcp://localhost/~/vin;DB_CLOSE_DELAY=-1", "sa", "");
//		IDatabaseTester databaseTester = new JdbcDatabaseTester(
//				"org.h2.Driver", "jdbc:h2:~/vin;INIT=CREATE SCHEMA IF NOT EXISTS PUBLIC;AUTO_SERVER=TRUE", "sa", "");
//
//		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//		databaseTester.setDataSet(dataSet);
//		databaseTester.onSetup();
//	}
//}
