package fr.eni.projet.dal;

import fr.eni.projet.dal.jdbc.ArticlesVendusDAOImpl;
import fr.eni.projet.dal.jdbc.CategorieDAOImpl;
import fr.eni.projet.dal.jdbc.EncheresDAOImpl;
import fr.eni.projet.dal.jdbc.UtilisateurDAOImpl;

public class DAOFactory {
	
	public static ArticlesVendusDAO getArticlesVendusDAO() {
		return new ArticlesVendusDAOImpl();
	}
	
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOImpl();
	}
	
	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOImpl();
	}

	public static EncheresDAO getEncheresDAO() {
		return new EncheresDAOImpl();
	}
}
