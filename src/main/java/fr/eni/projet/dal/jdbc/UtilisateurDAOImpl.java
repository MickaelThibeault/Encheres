package fr.eni.projet.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.dal.UtilisateurDAO;
import fr.eni.projet.exception.DALException;

public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	public static final String SELECT_BY_LOGIN = "SELECT no_utilisateur,nom,pseudo,prenom,email,telephone,rue,code_postal,ville,mot_de_passe ,credit,administrateur,active FROM UTILISATEURS where pseudo = ?";
	public static final String SELECT_BY_MAIL = "SELECT * FROM UTILISATEURS where email=?";	
	public static final String DELETE_USER = "DELETE FROM UTILISATEURS where no_utilisateur = ?";
	public static final String UPDATE_USER = "UPDATE UTILISATEURS SET  pseudo=? , nom=?  , prenom=? , email=? , telephone=? , rue=? , code_postal=? , ville=? , credit=?, mot_de_passe=?, active=? WHERE no_utilisateur = ?";
	public static final String ACTIVATE_USER = "UPDATE UTILISATEURS SET  active=? WHERE no_utilisateur = ?";
	public static final String INSERT_USER = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe ,credit,administrateur,active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	public static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
	public static final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
	public static final String UNIQUE_PSEUDO_MAIL = "SELECT * FROM UTILISATEURS WHERE pseudo LIKE ? OR email LIKE ?";
	
	
	@Override
	public Utilisateur selectByLogin(String login) throws DALException, SQLException {
		
		Utilisateur utilisateur = null;
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_LOGIN);
		
		pstmt.setString(1, login);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"),
					rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"),
					rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"),
					rs.getInt("credit"), rs.getBoolean("administrateur"),rs.getBoolean("active"));
		}
		return utilisateur;
	}

	@Override
	public Utilisateur selectById(int id) throws DALException, SQLException {
		
		Utilisateur utilisateur = null;
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"),
					rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"),
					rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"),
					rs.getInt("credit"), rs.getBoolean("administrateur"),rs.getBoolean("active"));
		}
		return utilisateur;
	}

	@Override
	public List<Utilisateur> selectAll() throws DALException, SQLException {
		
		List<Utilisateur> catalogueUtilisateurs = new ArrayList<>();
		Utilisateur utilisateur;
		Connection cnx =JDBCTools.getConnection(); 
		
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
				utilisateur = new Utilisateur(
						rs.getInt("no_utilisateur"), rs.getString("pseudo"),
						rs.getString("nom"), rs.getString("prenom"),
						rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"),rs.getString("code_postal"),
						rs.getString("ville"),rs.getString("mot_de_passe"),
						rs.getInt("credit"),rs.getBoolean("administrateur"),rs.getBoolean("active"));
				catalogueUtilisateurs.add(utilisateur);
		}
		return catalogueUtilisateurs;
	}

	@Override
	public void update(Utilisateur utilisateur) throws DALException, SQLException {
			
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt = cnx.prepareStatement(UPDATE_USER);
		
		// Attrs
		pstmt.setString(1, utilisateur.getPseudo());
		pstmt.setString(2, utilisateur.getNom());
		pstmt.setString(3, utilisateur.getPrenom());
		pstmt.setString(4, utilisateur.getEmail());
		pstmt.setString(5, utilisateur.getTelephone());
		pstmt.setString(6, utilisateur.getRue());
		pstmt.setString(7, utilisateur.getCode_postal());
		pstmt.setString(8, utilisateur.getVille());
		pstmt.setInt(9, utilisateur.getCredit());
		pstmt.setString(10, utilisateur.getMot_de_passe());
		pstmt.setBoolean(11, utilisateur.getActive());
		// where
		pstmt.setInt(12, utilisateur.getno_utilisateur());
		// execute
		int rows = pstmt.executeUpdate();
		// check 1 utilisateur modifier
		if (rows < 1) {
			throw new DALException("Aucune modification n'a été éffectuées dans la BDD.");
		}
	}

	@Override
	public void updateActivate(boolean active,int id) throws DALException, SQLException {
			
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt = cnx.prepareStatement(ACTIVATE_USER);
		
		pstmt.setBoolean(1,active );
		pstmt.setInt(2,id);
		
		int rows = pstmt.executeUpdate();
		// check 1 utilisateur modifier
		if (rows < 1) {
			throw new DALException("Aucune modification n'a été éffectuées dans la BDD.");
		}
	}

	@Override
	public void insert(Utilisateur utilisateur) throws DALException, SQLException {
		
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, utilisateur.getPseudo());
		stmt.setString(2, utilisateur.getNom());
		stmt.setString(3, utilisateur.getPrenom());
		stmt.setString(4, utilisateur.getEmail());
		stmt.setString(5, utilisateur.getTelephone());
		stmt.setString(6, utilisateur.getRue());
		stmt.setString(7, utilisateur.getCode_postal());
		stmt.setString(8, utilisateur.getVille());
		stmt.setString(9, utilisateur.getMot_de_passe());
		stmt.setInt(10, utilisateur.getCredit());
		stmt.setBoolean(11, utilisateur.getAdministrateur());
		stmt.setBoolean(12, utilisateur.getActive());
		System.out.println(stmt);
		stmt.executeUpdate();
		
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
			utilisateur.setno_utilisateur(rs.getInt(1));
		}
	}

	@Override
	public void delete(int id) throws DALException, SQLException {
		
			Connection cnx = JDBCTools.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_USER);
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
	}

	@Override
	public boolean UniquePseudoMail(String pseudo, String email) throws SQLException, DALException {
		
		boolean isUnique = true;
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(UNIQUE_PSEUDO_MAIL);
		
		stmt.setString(1, pseudo);
		stmt.setString(2, email);
		stmt.execute();
		
		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			isUnique = false;
		}
		return isUnique;
	}

	@Override
	public Utilisateur selectByMail(String Mail) throws DALException, SQLException {
		
		Utilisateur utilisateur = null;
		Connection cnx = JDBCTools.getConnection();
		PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_MAIL);
		
		pstmt.setString(1, Mail);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"),
					rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"),
					rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"),
					rs.getInt("credit"), rs.getBoolean("administrateur"),rs.getBoolean("active"));
		}
		return utilisateur;
	}
}
