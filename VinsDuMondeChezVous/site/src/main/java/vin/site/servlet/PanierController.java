package vin.site.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PanierController", urlPatterns = {"/PanierController"})
public class PanierController extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		

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
