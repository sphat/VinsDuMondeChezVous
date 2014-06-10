//package fournisseur.db;
//
//import java.io.File;
//
//import junit.framework.TestCase;
//
//import org.dbunit.IDatabaseTester;
//import org.dbunit.JdbcDatabaseTester;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.stream.IDataSetProducer;
//import org.dbunit.dataset.stream.StreamingDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.dbunit.dataset.xml.FlatXmlProducer;
//import org.dbunit.operation.DatabaseOperation;
//
//import org.xml.sax.InputSource;
//
//public class Dbunit4Test extends TestCase {
//
//	public void test() throws Exception {
//		IDataSet dataSet = readDataSet();
//		cleanlyInsertDataset(dataSet);
//	}
//
//	private IDataSet readDataSet() throws Exception {
//		File file = new File("src//main//resources//dataset//dataset_stock.xml");
//		return new FlatXmlDataSetBuilder().build(file);
//	}
//
//	private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
////		IDatabaseTester databaseTester = new JdbcDatabaseTester(
////				"org.h2.Driver",
////				"jdbc:h2:tcp://localhost/~/fournisseur;DB_CLOSE_DELAY=-1", "sa", "");
//		
//		IDatabaseTester databaseTester = new JdbcDatabaseTester(
//				"org.h2.Driver",
//				"jdbc:h2:~/vin;AUTO_SERVER=TRUE", "sa", "");
//		
//		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//		databaseTester.setDataSet(dataSet);
//		databaseTester.onSetup();
//
//	}
//
//}
