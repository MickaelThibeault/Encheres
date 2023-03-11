package fr.eni.projet.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.Administration;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Utilisateur;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//INITIALISATION
		List<Utilisateur> user;
		List<Categorie> categorie;
		Administration administration = new Administration();
		//AUTHENTIFICATION
		try {
			boolean valid = administration.AuthentificationAdmin(request,response);
			if(valid) {//AUTH-if
				// Récupérer les utilisateurs
				user = new UtilisateurManager().AfficherTousUtilisateurs();
				request.setAttribute("listUser", user);
				// Récupérer les catégories
				categorie = new CategorieManager().getCategories();
				request.setAttribute("categorie", categorie);
			}//AUTH-if
			if(!valid) {
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath() + "/Connexion");
			}else {			
				request.setAttribute("titlePage", "Administrateur");
				request.getRequestDispatcher("WEB-INF/jsp/pages/Administrateur.jsp").forward(request, response);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
