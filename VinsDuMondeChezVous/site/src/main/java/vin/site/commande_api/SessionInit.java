package vin.site.commande_api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInit {
	public static HttpSession session;
	
	public static void sessionInit(HttpServletRequest request, HttpServletResponse response){
		session = request.getSession(true);
        // print session info

        @SuppressWarnings("unused")
		Date created = new Date(session.getCreationTime());
        @SuppressWarnings("unused")
		Date accessed = new Date(session.getLastAccessedTime());
	}
}

