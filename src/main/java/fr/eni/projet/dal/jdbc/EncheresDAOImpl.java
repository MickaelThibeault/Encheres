package fr.eni.projet.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.bo.Encheres;
import fr.eni.projet.dal.EncheresDAO;
import fr.eni.projet.exception.DALException;

public class EncheresDAOImpl implements EncheresDAO{
	
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?, ?, ?, ?)";
	private static final String SELECT_BY_ID = "SELECT * FROM ENCHERES WHERE no_article = ?";
	private static final String SELECT_BY_USER = "SELECT * FROM ENCHERES WHERE no_article = ? and no_utilisateur =?";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET   date_enchere=?, montant_enchere = ? WHERE no_utilisateur=? ";
	private static final String DERNIERE_ENCHERE_ARTICLE_ID = "SELECT * FROM ENCHERES WHERE no_article = ? AND montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ?)";
	private static final String SELECT_ALL = "SELECT * FROM ENCHERES";
	private static final String SELECT_AUCTION="SELECT ENCHERES.no_utilisateur, no_article, date_enchere,montant_enchere, UTILISATEURS.pseudo FROM ENCHERES INNER JOIN UTILISATEURS ON ( ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur )  ORDER BY montant_enchere DESC ";
	
	@Override
	public List<Encheres> selectByIdSpec(int id) throws DALException {
		
		Encheres enchere = null;
		List<Encheres> list = new ArrayList<>();
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt;
		
		try {
			pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				enchere = new Encheres(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere"),
						rs.getInt("montant_enchere"));
				list.add(enchere);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DALException("EncheresDaoImpl - selectById() ");
		}
		return list;
	}
	
	@Override
	public List<Encheres> selectByAuction() throws DALException {
		
		Encheres enchere = null;
		List<Encheres> list = new ArrayList<>();
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt;
		
		try {
			pstmt = cnx.prepareStatement(SELECT_AUCTION);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				enchere = new Encheres(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere"),
						rs.getInt("montant_enchere"),rs.getString("pseudo"));
				list.add(enchere);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DALException("EncheresDaoImpl - selectByAuction() ");
		}
		return list;
	}

	@Override
	public void insert(Encheres enchere) throws DALException {
		
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_ENCHERE);
			
			pstmt.setInt(1, enchere.getNo_utilisateur());
			pstmt.setInt(2, enchere.getNo_article());
			pstmt.setDate(3, Date.valueOf(LocalDate.now()));
			pstmt.setInt(4, enchere.getMontant_enchere());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
//				e.printStackTrace();
			throw new DALException("EncheresDaoImpl - insert() ");
		}
	}

	@Override
	public void update(Encheres enchere) throws DALException {
		
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt3;
		
		try {
			pstmt3 = cnx.prepareStatement(UPDATE_ENCHERE);
			
			pstmt3.setDate(1, enchere.getDate_enchere());
			pstmt3.setInt(2, enchere.getMontant_enchere());
			pstmt3.setInt(3, enchere.getNo_utilisateur());
			
			pstmt3.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DALException("EncheresDaoImpl - update() ");
		}

	}

	@Override
	public Encheres recupererMaxEnchere(int idArticle) throws DALException {
		
		Encheres enchere = null;
		PreparedStatement pstmt;
		Connection cnx;
		
		try {
			cnx = JDBCTools.getConnection();
			pstmt = cnx.prepareStatement(DERNIERE_ENCHERE_ARTICLE_ID);
			
			pstmt.setInt(1, idArticle);
			pstmt.setInt(2, idArticle);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				enchere = new Encheres(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere"),
						rs.getInt("montant_enchere"));
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DALException("EncheresDaoImpl - recuprerMaxEnchere() ");
		}
		return enchere;
	}

	@Override
	public Encheres selectByUser(int idArticle, Integer no_utilisateur) throws DALException {
		
		Encheres enchere = null;
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_USER);
			
			pstmt.setInt(1, idArticle);
			pstmt.setInt(2, no_utilisateur);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				enchere = new Encheres(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere"),
						rs.getInt("montant_enchere"));
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DALException("EncheresDaoImpl - selectByUser() ");
		}
		return enchere;
	}

	@Override
	public List<Encheres> selectAll() throws DALException {
		
		List<Encheres> encheres = new ArrayList<>();
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt;
		
		try {
			pstmt = cnx.prepareStatement(SELECT_ALL);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Encheres  enchere = new Encheres(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere"),
						rs.getInt("montant_enchere"));
				encheres.add(enchere);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DALException("EncheresDaoImpl - selectAll() ");
		}
		return encheres;
	}

	@Override
	public Encheres selectById(int id) throws DALException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) throws DALException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
