package fr.eni.projet.dal;

import java.sql.SQLException;
import java.util.List;

import fr.eni.projet.bo.Encheres;
import fr.eni.projet.exception.DALException;

public interface EncheresDAO extends DAO<Encheres>{
	
	public Encheres recupererMaxEnchere(int idArticle) throws DALException;

	public Encheres selectByUser(int idArticle, Integer no_utilisateur) throws DALException;

	public List<Encheres> selectByIdSpec(int id) throws DALException, SQLException;

	public List<Encheres> selectByAuction() throws DALException;
}
