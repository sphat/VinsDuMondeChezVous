package vin.site.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String action = request.getParameter("action");
	    if (action.equals("Login")) {
	       String Email = request.getParameter("tbEmail");
	       String Password = request.getParameter("tbPassword");
	       if(Email.equals("thangitkhtn@gmail.com")&&Password.equals("huynhvanthang")){
	    	   request.setAttribute("annonce", "bonjour: "+Email);
	           request.getRequestDispatcher("Login.jsp").forward(request, response);
	       }
	       else{
	    	   request.setAttribute("annonce", "authentifier erreur");
	           request.getRequestDispatcher("Login.jsp").forward(request, response);
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
