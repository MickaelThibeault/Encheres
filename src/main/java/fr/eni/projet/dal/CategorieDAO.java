package fr.eni.projet.dal;

import java.util.List;

import fr.eni.projet.bo.Categorie;
import fr.eni.projet.exception.DALException;

public interface CategorieDAO {
	
	public List<Categorie> selectByLibelle(String libelle) throws DALException;
	public List<Categorie> getCatalogue() throws DALException;
	public void insertCategorie(String libelle) throws DALException;
	public void update(String libelle,Integer id) throws DALException;
	public List<Categorie> selectAll() throws DALException;
	public void delete(int id) throws DALException;
}
