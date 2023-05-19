package fr.eni.projet.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import fr.eni.projet.exception.DALException;

public class JDBCTools {
	
	private static Connection connection;

	public static Connection getConnection() throws DALException {
		if (connection == null) {
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
				connection = dataSource.getConnection();
			} catch (NamingException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
