package tiw5.fourni.rapidbank;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RapidBank implements Runnable {

	private static final String HELP_OPTION = "help";
	private static final Logger LOG = LoggerFactory.getLogger(RapidBank.class);
	private static final String PORT_OPTION = "port";
	private static final String SITE_VENTE_ENDPOINT_OPTION = "sitevente";
	private static final String MABANQUE_ENDPOINT_OPTION = "mabanque";
	private Options options;
	private String mabanqueUrl = "http://localhost:8084/debit";
	private String siteVenteUrl = "http://localhost:8081/services/paiement";
	private int port = 8083;

	@SuppressWarnings("static-access")
	private void buildOptions() {
		options = new Options();
		options.addOption(OptionBuilder.withDescription("affiche cette aide")
				.create(HELP_OPTION));
		options.addOption(OptionBuilder
				.hasArg()
				.withArgName("endpoint")
				.withDescription(
						"Change l'URL du point d'acces au service de mabanque.com")
				.create(MABANQUE_ENDPOINT_OPTION));
		options.addOption(OptionBuilder
				.hasArg()
				.withArgName("endpoint")
				.withDescription(
						"change l'URL pour le point d'accès au service de vente pour la confirmation du paiement")
				.create(SITE_VENTE_ENDPOINT_OPTION));
		options.addOption(OptionBuilder.hasArg().withArgName("num")
				.withDescription("Change le port du serveur en <num>")
				.withType(Integer.class).create(PORT_OPTION));
	}

	public void parseOptions(String[] args) throws ParseException {
		CommandLineParser clp = new PosixParser();
		CommandLine cl = clp.parse(getOptions(), args);
		if (cl.hasOption(HELP_OPTION)) {
			throw new ParseException("");
		}
		if (cl.hasOption(MABANQUE_ENDPOINT_OPTION)) {
			mabanqueUrl = cl.getOptionValue(MABANQUE_ENDPOINT_OPTION);
		}
		if (cl.hasOption(SITE_VENTE_ENDPOINT_OPTION)) {
			siteVenteUrl = cl.getOptionValue(SITE_VENTE_ENDPOINT_OPTION);
		}
		if (cl.hasOption(PORT_OPTION)) {
			port = Integer.parseInt(cl.getOptionValue(PORT_OPTION));
		}
	}

	@Override
	public void run() {
		try {
			Server server = new Server(port);
			ServletContextHandler context = new ServletContextHandler();
			context.setContextPath("/");
			server.setHandler(context);
			context.addServlet(new ServletHolder(new DebitServlet(mabanqueUrl,
					siteVenteUrl)), "/*");
			LOG.info("Using {} for MaBanque.com service",mabanqueUrl);
			LOG.info("Using {} for site vente service",siteVenteUrl);
			server.start();
			server.join();
		} catch (Exception e) {
			LOG.error("Error while running server", e);
		}
	}

	public void usage() {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("java -jar rapidbank-com.jar", options);
	}

	private Options getOptions() {
		if (options == null) {
			buildOptions();
		}
		return options;
	}

	public static void main(String[] args) {
		RapidBank app = new RapidBank();
		try {
			app.parseOptions(args);
			app.run();
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			app.usage();
		}
	}

}
