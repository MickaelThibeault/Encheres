package fr.eni.projet.ihm;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.ArticlesVendusManager;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bo.ArticlesVendus;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.DALException;
import fr.eni.projet.exception.MessageConfException;
import fr.eni.projet.exception.codesmessages.MSG_CONF;

/**
 * Servlet implementation class VenteArticle
 */
@WebServlet("/VenteArticle")
public class VenteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Categorie> categorie;
		RequestDispatcher rd;

		try {
				categorie = new CategorieManager().getCategories();
				request.setAttribute("categorie", categorie);
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Creer un Article");
		rd = request.getRequestDispatcher("WEB-INF/jsp/pages/VendreUnArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticlesVendusManager articleMG = new ArticlesVendusManager();
		MessageConfException messages = new MessageConfException();
		Integer no_article = null;
		BusinessException exceptions = new BusinessException();

		try {

				// Utilisateur courant dans la session
				Utilisateur curr_user = (Utilisateur) request.getSession().getAttribute("utilisateur");
				// Nouvelle article à créer
				ArticlesVendus newArticle = new ArticlesVendus(request.getParameter("name").toLowerCase(),
						request.getParameter("story").toLowerCase(),
						java.sql.Date.valueOf(LocalDate.parse(request.getParameter("date_debut"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
						java.sql.Date.valueOf(LocalDate.parse(request.getParameter("date_fin"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
						!request.getParameter("howmuch").equals("") ? Integer.valueOf(request.getParameter("howmuch"))
								: 0,
						null, Integer.valueOf(curr_user.getno_utilisateur()),
						Integer.valueOf(request.getParameter("categorie")), "CR", curr_user.getPseudo());
				// Id de l'article pour le renvoyer dans l'url pour l'affichage
				// Validation de l'article
				exceptions = articleMG.validateArticleVendu(newArticle);
				if (!exceptions.hasErreurs()) {
					// Insère l'article et récupère son id
					no_article = articleMG.creerArticle(newArticle);
					messages.ajouterMessage(MSG_CONF.NEW_ARTICLES);
				} 
	
		} catch (DALException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("listeCodesMessage",messages.getListeCodesMessage()); 
		request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
		request.setAttribute("idArticle", no_article);
		request.setAttribute("titlePage", "Article");
//		request.getRequestDispatcher("WEB-INF/jsp/pages/Article.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/Article?idArticle=" + no_article);
	}

}
