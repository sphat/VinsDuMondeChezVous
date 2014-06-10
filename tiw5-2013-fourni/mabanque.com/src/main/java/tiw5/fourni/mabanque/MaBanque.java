package tiw5.fourni.mabanque;

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

import tiw5.fourni.common.Banque;

public class MaBanque implements Runnable {

	private static final String HELP_OPTION = "help";
	private static final Logger LOG = LoggerFactory.getLogger(MaBanque.class);
	private static final String PORT_OPTION = "port";
	private Options options;
	private int port = 8084; //private int port = 8082;

	@SuppressWarnings("static-access")
	private void buildOptions() {
		options = new Options();
		options.addOption(OptionBuilder.withDescription("affiche cette aide")
				.create(HELP_OPTION));
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
		if (cl.hasOption(PORT_OPTION)) {
			port = Integer.parseInt(cl.getOptionValue(PORT_OPTION));
		}
	}

	@Override
	public void run() {
		try {
			Banque banque = Banque.fromXml();
			Server server = new Server(port);
			ServletContextHandler context = new ServletContextHandler();
			context.setContextPath("/");
			server.setHandler(context);
			context.addServlet(new ServletHolder(new AdminServlet(banque)),
					"/admin");
			context.addServlet(new ServletHolder(new DebitService(banque)), "/debit");
			server.start();
			server.join();
			LOG.info(
					"Interface administration disponible: http://localhost:{}/admin",
					port);
			LOG.info("Service de debit disponible: http://localhost:{}/debit",
					port);
		} catch (Exception e) {
			LOG.error("Error while running server", e);
		}
	}

	public void usage() {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("java -jar mabanque-com.jar", options);
	}

	private Options getOptions() {
		if (options == null) {
			buildOptions();
		}
		return options;
	}

	public static void main(String[] args) {
		MaBanque app = new MaBanque();
		try {
			app.parseOptions(args);
			app.run();
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			app.usage();
		}
	}

}
