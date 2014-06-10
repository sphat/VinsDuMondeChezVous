package vin.site.bean;

import java.util.ArrayList;
import java.util.Collection;

import vin.dal.db.intf.IMessage;

public class GestionMessagesBean extends MessageBean {
	public Collection<IMessage> getAllMessagesByIdClient(int idClient) {
		ArrayList<IMessage> results = new ArrayList<>();
		results = (ArrayList<IMessage>) messageDao.getAllMessagesByIdClient(idClient);
		return results;
	}
}
