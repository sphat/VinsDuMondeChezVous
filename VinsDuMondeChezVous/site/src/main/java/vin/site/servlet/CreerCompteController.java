package vin.site.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CreerCompteController", urlPatterns = {"/CreerCompteController"})
public class CreerCompteController extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String action = request.getParameter("action");
	    if (action.equals("CreerCompte")) {
	       String Email = request.getParameter("tbEmail");
	       String reEmail = request.getParameter("tbreEmail");
	       String Password = request.getParameter("tbPass");
	       String rePassword = request.getParameter("tbrePass");
	       if(Password.equals(rePassword)&&Email.equals(reEmail)){
	    	   request.setAttribute("annonce", "creer le compte reusir: "+Email);
	           request.getRequestDispatcher("CreerUnCompte.jsp").forward(request, response);
	       }
	       else{
	    	   request.setAttribute("annonce", "creer le compte eurreur");
	           request.getRequestDispatcher("CreerUnCompte.jsp").forward(request, response);
	       }
	       
	      }
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
