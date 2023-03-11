package fr.eni.projet.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.Administration;
import fr.eni.projet.bll.CategorieManager;

/**
 * Servlet implementation class AdminSuppressionCategorie
 */
@WebServlet("/AdminSuppressionCategorie")
public class AdminSuppressionCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// INITIALISATION
		CategorieManager manager = new CategorieManager();
		Administration administration = new Administration();
		// AUTHENTIFICATION
		boolean valid = administration.AuthentificationAdmin(request, response);
		try {
			if (valid) {// AUTH-if
				String id = (String) request.getParameter("id");
				manager.deleteCategorie(Integer.parseInt(id));
			}else {
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath() + "/Connexion");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Administrateur");
		response.sendRedirect(request.getContextPath() + "/Admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
