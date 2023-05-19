package fr.eni.projet.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.Administration;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.exception.DALException;

@WebServlet("/AdminAjoutCategorie")
public class AdminAjoutCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/Connexion");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//INITIALISATION
		Administration administration = new Administration();
		CategorieManager manager = new CategorieManager();
		//AUTHENTIFICATION
		boolean valid = administration.AuthentificationAdmin(request,response);
		try {
			if(valid) {//AUTH-if
					String cat = (String) request.getParameter("categorie");
					manager.ajouterCategorie(cat);
			}else {
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath() + "/Connexion");
			}
		} catch (DALException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Administrateur");
		response.sendRedirect(request.getContextPath()+"/Admin");
	}
}


