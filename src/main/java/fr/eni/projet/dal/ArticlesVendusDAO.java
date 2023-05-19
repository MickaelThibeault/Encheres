package fr.eni.projet.dal;

import java.util.List;

import fr.eni.projet.bo.ArticlesVendus;
import fr.eni.projet.exception.DALException;

public interface ArticlesVendusDAO extends DAO<ArticlesVendus>{

	// ArticlesVendus selectById(int id) throws DALException;

	// List<ArticlesVendus> selectAll() throws DALException;

	public void retraitArticle(int id) throws DALException;

	public Integer insertArticle(ArticlesVendus articles) throws DALException;

	public void setEtatArticle(ArticlesVendus article) throws DALException;

	// void delete(int id) throws DALException;

	public List<ArticlesVendus> selectCategorie(int id) throws DALException;

	public List<ArticlesVendus> selectAllByName(String nom) throws DALException;

	// void update(ArticlesVendus article) throws DALException;

}
