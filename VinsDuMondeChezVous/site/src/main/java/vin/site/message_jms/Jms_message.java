package vin.site.message_jms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import vin.site.commande_api.SessionInit;

public class Jms_message extends SessionInit implements ApplicationContextAware  {

	private ApplicationContext applicationContext;
	
	public JSONObject json_obj;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	public JSONObject createJSON(){
		return this.applicationContext.getBean("jsonBean", JSONObject.class);
	}

	public static void demanderApprovisionement(HttpServletRequest request, HttpServletResponse response){
		System.out.println("Test : " + request.getParameter("request"));
	}
	
}
