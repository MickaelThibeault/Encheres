package fr.eni.projet.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.bo.ArticlesVendus;
import fr.eni.projet.bo.Retraits;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.dal.ArticlesVendusDAO;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.dal.UtilisateurDAO;
import fr.eni.projet.exception.DALException;

public class ArticlesVendusDAOImpl implements ArticlesVendusDAO {
	
	public static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u ON (a.no_utilisateur = u.no_utilisateur) ORDER BY no_article DESC";
	public static final String SELECT_CATEGORIES = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON (a.no_categorie = c.no_categorie) WHERE c.no_categorie = ?";
	public static final String SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article =?";
	public static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur, no_categorie, etat_vente, vendeur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SELECT_ALL_BY_NAME = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u ON (a.no_utilisateur = u.no_utilisateur) WHERE nom_article LIKE '%";;
	public static final String DELETE_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
	public static final String UPDATE_ARTICLE = " UPDATE ARTICLES_VENDUS  set nom_article=? ,description=? ,date_debut_encheres=?, date_fin_encheres=? , prix_initial=? , prix_vente=? , no_utilisateur=? , no_categorie=? , etat_vente=? , vendeur=? WHERE no_article= ?";
	public static final String RETRAIT_ARTICLE = " UPDATE ARTICLES_VENDUS  set  etat_vente='RE'  WHERE no_article= ?";

	@Override
	public ArticlesVendus selectById(int id) throws DALException {
		ArticlesVendus article = null;
		Connection cnx = JDBCTools.getConnection();
		try {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			if (rs.next()) {
				article = new ArticlesVendus(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"), rs.getString("etat_vente"), rs.getString("vendeur"));
			}
		} catch (Exception e) {
//				e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - selectById()");
		}
		return article;
	}

	@Override
	public List<ArticlesVendus> selectAll() throws DALException {
		List<ArticlesVendus> listArticles = new ArrayList<>();
		ArticlesVendus article;
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UtilisateurDAO userDao = DAOFactory.getUtilisateurDAO();
				Utilisateur user = userDao.selectById(rs.getInt("no_utilisateur"));
				
				article = new ArticlesVendus(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"), rs.getString("etat_vente"), user.getPseudo());
				listArticles.add(article);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - selectAll()");
		}
		return listArticles;
	}

	@Override
	public Integer insertArticle(ArticlesVendus articles) throws DALException {
		UtilisateurDAO userDao = DAOFactory.getUtilisateurDAO();
		Integer no_article = null;
		Connection cnx = null;
		PreparedStatement stmt;
		PreparedStatement stmt2;
		// INSERT UN ARTICLE EN 2 ÉTAPES
		try {// INSERT UN ARTICLE
			cnx = JDBCTools.getConnection();
			stmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, articles.getNom_article());
			stmt.setString(2, articles.getDescription());
			stmt.setDate(3, articles.getDate_debut_encheres());
			stmt.setDate(4, articles.getDate_fin_encheres());
			stmt.setInt(5, articles.getPrix_initial() != 0 ? articles.getPrix_initial() : 0);
			stmt.setInt(6, 0);
			stmt.setInt(7, articles.getNo_utilisateur());
			stmt.setInt(8, articles.getNo_categorie());
			stmt.setString(9, articles.getEtat_vente());
			stmt.setString(10, articles.getVendeur());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				articles.setNo_article(rs.getInt(1));
				no_article = rs.getInt(1);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - insert Utilisateur()");
		}
		try {// INSERT UN RETRAIT POUR L'ARTICLE
			Utilisateur user = userDao.selectById(articles.getNo_utilisateur());
			Retraits retraits = new Retraits(no_article, user.getRue(), user.getCode_postal(), user.getVille());
			String sql = "INSERT INTO RETRAITS (no_article,rue,code_postal,ville) VALUES(?,?,?,?)";
			stmt2 = cnx.prepareStatement(sql);
			stmt2.setInt(1, retraits.getNo_article());
			stmt2.setString(2, retraits.getRue());
			stmt2.setString(3, retraits.getCode_postal());
			stmt2.setString(4, retraits.getVille());
			stmt2.executeUpdate();
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - insert Retrait()");
		}
		return no_article;
	}

	@Override
	public void setEtatArticle(ArticlesVendus article) throws DALException {
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt3;
		try {
			pstmt3 = cnx.prepareStatement("UPDATE ARTICLES_VENDUS  set etat_vente =?  Where no_article = ?");
			pstmt3.setString(1, article.getEtat_vente());
			pstmt3.setInt(2, article.getNo_utilisateur());
			pstmt3.executeUpdate();
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - setEtatArticle() ");
		}

	}

	@Override
	public void delete(int id) throws DALException {
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<ArticlesVendus> selectCategorie(int id) throws DALException {
		List<ArticlesVendus> listArticles = new ArrayList<>();
		ArticlesVendus article;
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIES);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				article = new ArticlesVendus(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"), rs.getString("etat_vente"), rs.getString("vendeur"));
				listArticles.add(article);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - selectCategorie()");
		}
		return listArticles;
	}

	@Override
	public List<ArticlesVendus> selectAllByName(String nom) throws DALException {
		List<ArticlesVendus> listArticles = new ArrayList<>();
		ArticlesVendus article;
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_BY_NAME + nom + "%'");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				article = new ArticlesVendus(rs.getInt("no_article"), rs.getString("nom_article"),
						rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"), rs.getString("etat_vente"), rs.getString("vendeur"));
				listArticles.add(article);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - selectAllByName()");
		}
		return listArticles;
	}

	@Override
	public void update(ArticlesVendus article) throws DALException {
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
			pstmt.setString(1, article.getNom_article());
			pstmt.setString(2, article.getDescription());
			pstmt.setDate(3, article.getDate_debut_encheres());
			pstmt.setDate(4, article.getDate_fin_encheres());
			pstmt.setInt(5, article.getPrix_initial());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, article.getNo_utilisateur());
			pstmt.setInt(8, article.getNo_categorie());
			pstmt.setString(9, article.getEtat_vente());
			pstmt.setString(10, article.getVendeur());
			// where
			pstmt.setInt(11, article.getNo_article());
			// execute
			int rows = pstmt.executeUpdate();
			// check 1 utilisateur modifier
			if (rows < 1) {
				throw new DALException("Articles_vendusDaoImpl - update()");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - update()");
		}
	}

	@Override
	public void retraitArticle(int id) throws DALException {
		try {
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(RETRAIT_ARTICLE);
			pstmt.setInt(1, id);
			// execute
			int rows = pstmt.executeUpdate();
			// check 1 utilisateur modifier
			if (rows < 1) {
				throw new DALException("Articles_vendusDaoImpl - update()");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DALException("Articles_vendusDaoImpl - update()");
		}
		
	}

	@Override
	public void insert(ArticlesVendus data) throws DALException, SQLException {
		// TODO Auto-generated method stub
		
	}
}
