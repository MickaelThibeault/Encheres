package fr.eni.projet.dal;

import java.sql.SQLException;
import java.util.List;

import fr.eni.projet.exception.DALException;

public interface DAO<T> {
	
	public T selectById(int id) throws DALException, SQLException;

	public List<T> selectAll() throws DALException, SQLException;

	public void update(T data) throws DALException, SQLException;

	public void insert(T data) throws DALException, SQLException;

	public void delete(int id) throws DALException, SQLException;
}
