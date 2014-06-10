package vin.service.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import vin.dal.db.model.Message;

public class MessageObj implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	public Message msgOjb;
	
	public Message createMsgObj(){
		return (Message) this.applicationContext.getBean("msgBean", Message.class);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
