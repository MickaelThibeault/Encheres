package fr.eni.projet.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.Administration;
import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.Utilisateur;

/**
 * Servlet implementation class DesactiverCompte
 */
@WebServlet("/DesactiverCompte")
public class DesactiverCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Administration administration = new Administration();
		// AUTHENTIFICATION
		boolean valid = administration.AuthentificationAdmin(request, response);
		
		try {
		
				UtilisateurManager manager = UtilisateurManager.getInstance();
				if (!request.getParameter("idUser").equals("") && request.getParameter("idUser") != null) {
					String idStr = (String) request.getParameter("idUser");
					int id = Integer.parseInt(idStr);
					Utilisateur utilisateur = manager.GetUtilisateur(id);
					if (utilisateur != null) {
						boolean active = false;
						if (!utilisateur.getActive()) {
							active = true;
						}
						manager.ActivateUser(active, id);
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		if(valid) {			
			request.setAttribute("titlePage", "Administrateur");
			response.sendRedirect(request.getContextPath() + "/Admin");
		}else {
			request.getSession().invalidate();
			request.setAttribute("titlePage", "Accueil");
			response.sendRedirect(request.getContextPath() + "/Home");
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
