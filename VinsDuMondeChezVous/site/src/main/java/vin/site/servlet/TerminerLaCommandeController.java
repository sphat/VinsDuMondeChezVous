package vin.site.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TerminerCommandeController", urlPatterns = {"/TerminerCommandeController"})
public class TerminerLaCommandeController extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String CommandeId = request.getParameter("CommandeId");
	    String TotalAPayer = request.getParameter("MotantAPayer");
 	   	request.setAttribute("annonce","Commandeid: " + CommandeId + " ; Motant total a Payer: " + TotalAPayer);
	    request.getRequestDispatcher("TerminerLaCommande.jsp").forward(request, response);
	}
	
	@Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
	      processRequest(request, response);
	   }
	
	@Override
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
	      processRequest(request, response);
	   }
	
}
