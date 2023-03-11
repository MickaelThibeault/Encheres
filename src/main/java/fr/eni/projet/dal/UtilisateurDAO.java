package fr.eni.projet.dal;

import java.sql.SQLException;

import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.DALException;

public interface UtilisateurDAO extends DAO<Utilisateur>{

	Utilisateur selectByLogin(String login) throws DALException, SQLException;

	void updateActivate(boolean active, int id) throws DALException, SQLException;

	boolean UniquePseudoMail(String pseudo, String email) throws SQLException, DALException;

	Utilisateur selectByMail(String Mail) throws DALException, SQLException;

}
