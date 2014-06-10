package vin.dal.db.intf;

import java.util.Date;

public interface IMessage {

	Long getId();

	IClient getClient();
	public String getTitreMessage();
	public String getContenuMessage();
	public Date getDateEnvoyeMessage();

}
