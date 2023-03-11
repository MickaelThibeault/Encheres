package fr.eni.projet.ihm;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.DALException;
import fr.eni.projet.exception.codesmessages.MSG_BLL;

/**
 * Servlet implementation class ModifierProfil
 */
@WebServlet("/ModifierProfil")
public class ModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("titlePage", "Modifier Mon Profil");
		request.getRequestDispatcher("WEB-INF/jsp/pages/ModifierProfil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BusinessException exceptions = new BusinessException();
		UtilisateurManager manager = UtilisateurManager.getInstance();
		Utilisateur utilisateur_courant = null;
		Utilisateur utilisateurModifier = null;
		HttpSession session = request.getSession();
		RequestDispatcher rd;

		// Récupère l'utilisateur connecté
		utilisateur_courant = (Utilisateur) session.getAttribute("utilisateur");
		// Récupère no_utilisateur, le crédit et le role de l'utilisateur connecté
		int id = utilisateur_courant.getno_utilisateur();
		int credit = utilisateur_courant.getCredit();
		boolean admin = utilisateur_courant.getAdministrateur();
		String mdpBDD = utilisateur_courant.getMot_de_passe();
		// Créé l'utilisateur modifié
		utilisateurModifier = new Utilisateur(id, (String) request.getParameter("pseudo"),
				(String) request.getParameter("nom"), (String) request.getParameter("prenom"),
				(String) request.getParameter("email"), (String) request.getParameter("telephone"),
				(String) request.getParameter("rue"), (String) request.getParameter("codePostal"),
				(String) request.getParameter("ville"), mdpBDD, credit, admin, true);
		// Validation du formulaire
		exceptions = manager.validateUtilisateur(utilisateurModifier);
		try {
		// si tout est OK
			if (!exceptions.hasErreurs()) {
				// Enregistre les modifications
				try {
					manager.modifierProfil(utilisateurModifier);
				} catch (DALException | SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Création d'une session
				session = request.getSession();
				// Réinitialise l'attribut "utilisateur" dans la session
				session.removeAttribute("utilisateur");
				// Set l'attribut "utilisateur" dans la session
				session.setAttribute("utilisateur", utilisateurModifier);
				request.setAttribute("titlePage", "Mon Profil");
				rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Profile.jsp");
				rd.forward(request, response);
			} else {
				exceptions.ajouterErreur(MSG_BLL.ERROR_UPDATE_UTILISATEUR);
				throw exceptions;
			}
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
			doGet(request, response);
		}
	}
}


