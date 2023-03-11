package fr.eni.projet.ihm;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import fr.eni.projet.exception.MessageConfException;
import fr.eni.projet.exception.codesmessages.MSG_CONF;

/**
 * Servlet implementation class ModifierArticle
 */
@WebServlet("/ModifierArticle")
public class ModifierArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticlesVendus article = null;
		ArticlesVendusManager articleMG = new ArticlesVendusManager();
		List<Categorie> categorie;

		try {

			if (!request.getParameter("idArticle").equals("") && request.getParameter("idArticle") != null) {
				// recuperation de l'article
				article = articleMG.getArticleById(Integer.parseInt(request.getParameter("idArticle")));
				request.setAttribute("article", article);
				// Récupération des catégories dans la BDD
				categorie = new CategorieManager().getCategories();
				request.setAttribute("categorie", categorie);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("titlePage", "Modifier Article");
		request.getRequestDispatcher("WEB-INF/jsp/pages/ModifierArticle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticlesVendus newArticle = null;
		ArticlesVendusManager articleMG = new ArticlesVendusManager();
		BusinessException exceptions = new BusinessException();
		MessageConfException messages = new MessageConfException();

		try {

				// Utilisateur courant dans la session
				Utilisateur curr_user = (Utilisateur) request.getSession().getAttribute("utilisateur");
				if (!request.getParameter("idArticle").equals("") && request.getParameter("idArticle") != null) {
					// Nouvelle article à créer
					newArticle = new ArticlesVendus(Integer.parseInt(request.getParameter("idArticle")),
							request.getParameter("name").toLowerCase(), request.getParameter("story").toLowerCase(),
							java.sql.Date.valueOf(LocalDate.parse(request.getParameter("date_debut"),
									DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
							java.sql.Date.valueOf(LocalDate.parse(request.getParameter("date_fin"),
									DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
							!request.getParameter("howmuch").equals("")
									? Integer.valueOf(request.getParameter("howmuch"))
									: 0,
							null, Integer.valueOf(curr_user.getno_utilisateur()), 1, "CR", curr_user.getPseudo());
					// Validation de l'article
					exceptions = articleMG.validateArticleVendu(newArticle);

					if (!exceptions.hasErreurs()) {
						articleMG.updateArticle(newArticle);
						messages.ajouterMessage(MSG_CONF.UPDATE_ARTICLES);
						request.setAttribute("listeCodesMessage", messages.getListeCodesMessage());
					} else {
						request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("idArticle", newArticle.getNo_article());
		request.setAttribute("titlePage", "Article");
		response.sendRedirect(request.getContextPath() + "/Article?idArticle=" + newArticle.getNo_article());
	}
}

