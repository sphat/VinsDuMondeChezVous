package vin.site.commande_api;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class UserSession  extends SessionInit implements ApplicationContextAware {
	
	@SuppressWarnings("unused")
	private ApplicationContext applicationContext;
	private static final String SESSION_USER_MAIL = "session_user_mail";
	public static final String SESSION_USER_NAME = "session_user_name";
	public static final String SESSION_USER_ID	= "session_user_id";
	
	public static void sessionLogin(String mail, String usName, String usID){
		if(session.getAttribute(SESSION_USER_MAIL)==null){
			session.setAttribute(SESSION_USER_MAIL, mail);
			session.setAttribute(SESSION_USER_NAME, usName);
			session.setAttribute(SESSION_USER_ID, usID);
		}
	}
	
	public static void sessionLogout(String usName,String usID){
		if(session.getAttribute(SESSION_USER_ID).equals(usID) && session.getAttribute(SESSION_USER_NAME).equals(usName)){
			session.removeAttribute(SESSION_USER_NAME);
			session.removeAttribute(SESSION_USER_MAIL);
			session.removeAttribute(SESSION_USER_ID);
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}

