package vin.site.error_notification;

import vin.site.commande_api.Article;

public class NonDisponibleException extends Exception {
	private static final long serialVersionUID = 1L;
	
	//@XmlElement(name="fault")
	private String referenceVin;
	
	public NonDisponibleException(){}

	public NonDisponibleException(Article article) {
		super("Le vin " + article.getReferenceVin() + " n'est pas disponible");
		this.referenceVin = article.getReferenceVin();
	}

	public String getReferenceVin() {
		return referenceVin;
	}

	public void setReferenceVin(String referenceVin) {
		this.referenceVin = referenceVin;
	}
	
	/**
	 * Permettre de déactiver '<stackTrace/>'
	 */
    @Override
    public Throwable fillInStackTrace() {
        return null;
    } 
}
