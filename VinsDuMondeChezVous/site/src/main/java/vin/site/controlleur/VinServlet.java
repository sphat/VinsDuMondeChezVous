package vin.site.controlleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;

import org.springframework.beans.factory.annotation.Autowired;

import vin.dal.db.model.CommandeArticle;
import vin.site.commande_api.SessionInit;

/**
 * Servlet implementation class VinServlet
 */
@WebServlet(description = "Http Servlet using pure java / annotations", urlPatterns = { "/VinServlet" }, name = "VinServletHandler")
public class VinServlet extends org.springframework.web.context.support.HttpRequestHandlerServlet{
	
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionInit.sessionInit(request, response);
		ServletConfig sc = this.getServletConfig();
		super.service(request, response);
	}
}

