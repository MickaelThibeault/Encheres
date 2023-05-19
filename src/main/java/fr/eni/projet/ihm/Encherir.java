package fr.eni.projet.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.ArticlesVendusManager;
import fr.eni.projet.bll.EncheresManager;
import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.ArticlesVendus;
import fr.eni.projet.bo.Encheres;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.MessageConfException;
import fr.eni.projet.exception.codesmessages.MSG_BLL;
import fr.eni.projet.exception.codesmessages.MSG_CONF;
import fr.eni.projet.exception.codesmessages.MSG_DAL;

import java.sql.Date;
import java.time.LocalDate;
/**
 * Servlet implementation class Encherir
 */
@WebServlet("/Encherir")
public class Encherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect(request.getContextPath() + "/Error500");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BusinessException exceptions = new BusinessException();
		MessageConfException confirmations = new MessageConfException();
		EncheresManager enchereMG = new EncheresManager();
		UtilisateurManager userMG = new UtilisateurManager();
		Utilisateur encherisseur = new Utilisateur();
		Utilisateur userMAJ = new Utilisateur();
		Integer somme = null;
		ArticlesVendus article = null;
		Encheres enchereMax = null;
		Encheres newEnchere = null;

	
		try {

				if (!request.getParameter("id").equals("") && request.getParameter("id") != null) {
					// recupération de la somme
					if (!request.getParameter("howmuch").equals("") && request.getParameter("howmuch") != null) {
						somme = Integer.parseInt(request.getParameter("howmuch"));
					} else {
						exceptions.ajouterErreur(MSG_BLL.ENCHERE_NULL_PROPOSITION);
					}
					if (!exceptions.hasErreurs()) {
						// recuperation de l'article
						article = new ArticlesVendusManager()
								.getArticleById(Integer.parseInt(request.getParameter("id")));
						// Récupération de l'enchere si elle existe
						enchereMax = enchereMG.rechercheMaxEnchere(article.getNo_article());
						if (enchereMax == null) {
							enchereMax = new Encheres(article.getNo_article(), 0, Date.valueOf(LocalDate.now()), 0);
						}
						// validation
						if (!enchereMG.sommeSuperieurPrixInitial(somme, article.getPrix_initial())) {
							exceptions.ajouterErreur(MSG_BLL.PROPOSITION_INFERIEUR_ENCHERE);
						}
						// Récupération des infos de l'encherisseur
						Utilisateur tmp = (Utilisateur) request.getSession().getAttribute("utilisateur");
						encherisseur = userMG.GetUtilisateur(tmp.getno_utilisateur());
						if (encherisseur == null) {
							exceptions.ajouterErreur(MSG_DAL.ERROR_SERVER);
							request.setAttribute("listeCodesErreur", exceptions);
							response.sendRedirect(request.getContextPath() + "/Error500");
						}
						// Validation de l'enchere
						if (!enchereMG.validerSoldeSuffisant(somme, encherisseur.getCredit())) {
							exceptions.ajouterErreur(MSG_BLL.NOMBRE_DE_POINTS_INSUFFISANT);
						}
						if (!enchereMG.enchereSuperieurAncienne(enchereMax, somme)) {
							exceptions.ajouterErreur(MSG_BLL.PROPOSITION_INFERIEUR_ENCHERE);
						}
						if (!exceptions.hasErreurs()) {
							newEnchere = new Encheres(encherisseur.getno_utilisateur(), article.getNo_article(),
									Date.valueOf(LocalDate.now()), somme);
							// ajouter l'enchere
							if (enchereMG.verifExisteEnchere(newEnchere.getNo_utilisateur(),
									newEnchere.getNo_article())) {
								enchereMG.miseAjour(newEnchere);
								// credit l'ancien
								enchereMG.recrediterUtilisateur(enchereMax.getMontant_enchere(),
										enchereMax.getNo_utilisateur());
								// debit le nouveau
								userMAJ = enchereMG.debiterUtilisateur(somme, encherisseur.getno_utilisateur());
								confirmations.ajouterMessage(MSG_CONF.ENCHERE_OK);
							} else {
								enchereMG.ajouteEnchere(newEnchere);
								// debit le nouveau
								userMAJ = enchereMG.debiterUtilisateur(somme, encherisseur.getno_utilisateur());
								confirmations.ajouterMessage(MSG_CONF.ENCHERE_OK);
							}
							if (confirmations.hasMessages()) {
								// set dans la requete la liste de messages a la jsp
								request.setAttribute("listeCodesMessage",confirmations.getListeCodesMessage());
							}
							// MAJ de l'utilisateur dans la session
							request.setAttribute("utilisateur", userMAJ);
							request.setAttribute("utilisateur", userMAJ);
							request.setAttribute("bestEnchere", userMAJ);
							request.setAttribute("titlePage", "Article");
							request.setAttribute("id", article.getNo_article());
						} else {
							// set dans la session la liste d'erreurs a la jsp
							request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
						}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.getRequestDispatcher("Article").forward(request, response);

	}
}


