package fr.eni.projet.bll;

import java.sql.SQLException;
import java.util.List;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.dal.CategorieDAO;
import fr.eni.projet.exception.DALException;

public class CategorieManager {
	
	private static CategorieDAO daoCategories;

	public CategorieManager() {
		daoCategories = DAOFactory.getCategorieDAO();
	}

	public List<Categorie> getCategories() throws DALException, SQLException {
		return daoCategories.selectAll();

	}

	public List<Categorie> getCatalogueLibelle(String libelle) throws DALException {
		return daoCategories.selectByLibelle(libelle);
	}
	
	public void ajouterCategorie(String libelle) throws DALException  {
			daoCategories.insertCategorie(libelle);
	}
	
	public void deleteCategorie(Integer id) throws DALException, SQLException {
			daoCategories.delete(id);
	}
	public void updateCategorie(String libelle, int id) throws DALException {
		daoCategories.update(libelle, id);
	}
}
