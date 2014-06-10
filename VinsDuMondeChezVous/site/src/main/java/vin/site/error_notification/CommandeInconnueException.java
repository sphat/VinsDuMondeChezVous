package vin.site.error_notification;

public class CommandeInconnueException extends Exception {
	private static final long serialVersionUID = 1L;
	
	//@XmlElement(name="commandeId")
	private String commandeId;

	public CommandeInconnueException(){}
	
	public CommandeInconnueException(String commandeId) {
		super("La commande '" + commandeId + "' est inconnue");
		this.commandeId = commandeId;
	}

	public String getCommandeId() {
		return commandeId;
	}

	public void setCommandeId(String commandeId) {
		this.commandeId = commandeId;
	}
	
	/**
	 * Permettre de déactiver '<stackTrace/>'
	 */
    @Override
    public Throwable fillInStackTrace() {
        return null;
    }  
}
