package fr.eni.projet.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.dal.UtilisateurDAO;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.DALException;
import fr.eni.projet.exception.codesmessages.MSG_BLL;

public class UtilisateurManager {
	/**
	 * instance Manager
	 */
	private static UtilisateurManager instance;

	/**
	 * @return Instance
	 */
	public static UtilisateurManager getInstance() {
		if (instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}

	/**
	 * Connexion
	 * 
	 * @param pseudo envoyé par ( jsp) l'utilisateur
	 * @param mdp    envoyé par ( jsp) l'utilisateur
	 * @return Si tout est OK on renvoi un utilisateur , sinon utilisateur = null
	 * @throws BusinessException
	 * @throws SQLException
	 * @throws DALException
	 */
	public Utilisateur connexion(String pseudo, String mdp) throws BusinessException, DALException, SQLException {
		
		BusinessException exceptions = new BusinessException();
		Utilisateur utilisateur = new Utilisateur();
		boolean verification = false;
			// Tente de récupérer l'utilisateur avec l'identififiant si il existe
			utilisateur = ValidateMail(pseudo);
			
		if (utilisateur != null) {
			// Si l'utilisateur existe => on compare si le password reçu correspond à celui
			// de l'utilisateur trouvé
			verification = UtilisateurManager.hashPwd(mdp).equals(utilisateur.getMot_de_passe());
			
			if (!verification) {
				// Si le password est incorrect => on ajoute le message d'erreur et on lève
				// l'exception
				exceptions.ajouterErreur(MSG_BLL.ID_MDP_KO);
				throw exceptions;
			}
			// Si le password est correct => on retourne l'utilisateur trouvé
		}
		return utilisateur;
	}

	public Utilisateur ValidateMail(String email) throws BusinessException, DALException, SQLException {
		
		Utilisateur utilisateur=null;
		UtilisateurDAO userDao =DAOFactory.getUtilisateurDAO();

		if (email.contains("@")) {
		utilisateur=userDao.selectByMail(email);
		
		}
		else {
		utilisateur=userDao.selectByLogin(email);

		}
		return utilisateur;
	}

	/**
	 * Créer un nouvel utilisateur
	 * 
	 * @param utilisateur Nouveau utilisateur à enregistrer
	 * @param exceptions  BLL
	 * @return la liste des exceptions
	 * @throws DALException
	 * @throws SQLException
	 */
	public BusinessException createUtilisateur(Utilisateur utilisateur, BusinessException exceptions)
			throws SQLException, DALException {
		
		UtilisateurDAO utilisateurDao = DAOFactory.getUtilisateurDAO();
		// Vérifie si l'email ou le pseudo existe déjà dans la BDD
		if (!utilisateurDao.UniquePseudoMail(utilisateur.getPseudo(), utilisateur.getEmail()))
			// Si oui => on ajouter un message d'erreur
			exceptions.ajouterErreur(MSG_BLL.ERROR_PSEUDO_OR_MAIL_ALREADY_TAKEN);
		else
			// Si non => on enregistre dans la BDD
			utilisateurDao.insert(utilisateur);
		return exceptions;
	}

	/**
	 * Validation du formulaire d'enregistrement
	 * 
	 * @param utilisateur utilisateur a créer
	 * @return la liste des exceptions
	 */
	public BusinessException validateUtilisateur(Utilisateur utilisateur) {
		
		String pseudoValidationRegEx = "[A-Za-z0-9]+";
		// le regex de validation de mail viens de ce site : https://emailregex.com/
		String emailValidationRegEx = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
		// Ce regexp gere seulement les numéros de téléphone francais.
		String telephoneNumberValidationRegEx = "^0[1-9][0-9]{8}$";
		BusinessException BusinessException = new BusinessException();
		
		if (utilisateur.getPseudo().length() > 30) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_PSEUDO_UTILISATEUR);
		}
		if (!Pattern.matches(pseudoValidationRegEx, utilisateur.getPseudo())) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_PSEUDO_NOT_SPECIAL_CHAR);
		}
		if (utilisateur.getNom().length() > 30) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_NOM_UTILISATEUR);
		}
		if (utilisateur.getPrenom().length() > 30) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_PRENOM_UTILISATEUR);
		}
		if (utilisateur.getEmail().length() > 40) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_EMAIL_UTILISATEUR);
		}
		if (!Pattern.matches(emailValidationRegEx, utilisateur.getEmail())) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_FORMAT_EMAIL_UTILISATEUR);
		}
		if (utilisateur.getTelephone().length() > 10) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_TELEPHONE_UTILISATEUR);
		}
		if (!Pattern.matches(telephoneNumberValidationRegEx, utilisateur.getTelephone())) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_FORMAT_TELEPHONE_UTILISATEUR);
		}
		if (utilisateur.getRue().length() > 60) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_RUE_UTILISATEUR);
		}
		if (utilisateur.getCode_postal().length() > 5) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_CODE_POSTAL_UTILISATEUR);
		}
		if (utilisateur.getVille().length() > 30) {
			BusinessException.ajouterErreur(MSG_BLL.ERROR_LENGTH_VILLE_UTILISATEUR);
		}
		return BusinessException;
	}

	/**
	 * Supprimer un utilisateur
	 * 
	 * @param id no_utilisateur courrant
	 * @throws SQLException
	 * @throws DALException
	 */
	public void deleteUtilisateur(Integer id) throws DALException, SQLException {
		DAOFactory.getUtilisateurDAO().delete(id);
	}

	/**
	 * Modifier un profil
	 * 
	 * @param utilisateur Nouveau utilisateur créé avec les modifications
	 * @throws SQLException
	 * @throws DALException
	 */
	public void modifierProfil(Utilisateur utilisateur) throws DALException, SQLException {
		DAOFactory.getUtilisateurDAO().update(utilisateur);
	}

	/**
	 * Hash le password en SHA-256
	 * 
	 * @param chaine le password
	 * @return le password crypté
	 */
	public static String hashPwd(String chaine) {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		md.update(chaine.getBytes());
		byte byteData[] = md.digest();
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public List<Utilisateur> AfficherTousUtilisateurs() throws DALException, SQLException {
		return DAOFactory.getUtilisateurDAO().selectAll();
	}

	public Utilisateur GetUtilisateur(Integer no_utilisateur) throws DALException, SQLException {
		return DAOFactory.getUtilisateurDAO().selectById(no_utilisateur);
	}

	public Utilisateur connexionCookies(String pseudo) throws DALException, SQLException {
		return DAOFactory.getUtilisateurDAO().selectByLogin(pseudo);
	}

	// Connexion Cookies
	public Utilisateur ConnexionCookies(String pseudo, String mdp)
			throws BusinessException, DALException, SQLException {
		
		BusinessException exceptions = new BusinessException();
		UtilisateurDAO utilisateurDao = DAOFactory.getUtilisateurDAO();
		Utilisateur utilisateur = new Utilisateur();
		boolean verification = false;
		
		// Tente de récupérer l'utilisateur avec l'identififiant si il existe
		utilisateur = utilisateurDao.selectByLogin(pseudo);
		
		if (utilisateur != null) {
			// Si l'utilisateur existe => on compare si le password reçu correspond à celui
			// de l'utilisateur trouvé
			verification = mdp.equals(utilisateur.getMot_de_passe());
			
			if (!verification) {
				// Si le password est incorrect => on ajoute le message d'erreur et on lève
				// l'exception
				exceptions.ajouterErreur(MSG_BLL.ID_MDP_KO);
				throw exceptions;
			}
			// Si le password est correct => on retourne l'utilisateur trouvé
		}
		return utilisateur;
	}

	public void ActivateUser(boolean active, int id) throws DALException, SQLException {
		DAOFactory.getUtilisateurDAO().updateActivate(active, id);
	}
}
