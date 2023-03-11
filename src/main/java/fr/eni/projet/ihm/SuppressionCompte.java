package fr.eni.projet.ihm;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.DALException;

/**
 * Servlet implementation class SuppressionCompte
 */
@WebServlet("/SuppressionCompte")
public class SuppressionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// INITIALISATION
		
		UtilisateurManager manager = UtilisateurManager.getInstance();
		HttpSession session = request.getSession();
		try {
			// On récupère l'utilisateur stocké dans la session
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
			// On supprime de la BDD
			manager.deleteUtilisateur(utilisateur.getno_utilisateur());
		} catch (DALException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		// On détruit la session
		session.invalidate();
		request.setAttribute("titlePage", "Administrateur");
		response.sendRedirect(request.getContextPath() + "/Admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
