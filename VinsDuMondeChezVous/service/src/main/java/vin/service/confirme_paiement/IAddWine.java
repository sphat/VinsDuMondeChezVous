package vin.service.confirme_paiement;

import vin.dal.db.intf.DAOException;
import vin.service.Article;

public interface IAddWine {
	public ReponseAddWine updateStockWine(Article[] approvArticle) throws DAOException;
}
