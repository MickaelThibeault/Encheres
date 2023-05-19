package fr.eni.projet.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.Utilisateur;

/**
 * Servlet implementation class MonProfil
 */
@WebServlet("/MonProfil")
public class MonProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// INITIALISATION
		
		RequestDispatcher rd = null;
		UtilisateurManager userMG = new UtilisateurManager();
		Utilisateur user = null;

		if (request.getParameter("idVendeur") != null) {
			if (!request.getParameter("idVendeur").equals("")) {
				try {
					user = userMG.GetUtilisateur(Integer.parseInt(request.getParameter("idVendeur")));
					request.setAttribute("otherUser", user);
					request.setAttribute("titlePage", "Profil Vendeur");
					rd = request.getRequestDispatcher("WEB-INF/jsp/pages/OtherProfile.jsp");
				} catch (Exception e) {
					e.printStackTrace();
					response.sendRedirect(request.getContextPath() + "/Error500");
				}
			}
		} else {
			request.setAttribute("titlePage", "Mon Profil");
			rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Profile.jsp");
		}
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
