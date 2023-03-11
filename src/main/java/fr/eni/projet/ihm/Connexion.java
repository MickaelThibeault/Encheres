package fr.eni.projet.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.bll.UtilisateurManager;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.codesmessages.MSG_BLL;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = null;
		
		try {
			Cookie[] cookies = request.getCookies();
			String mdpcookie = "";
			String pseudocookie = "";
			
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("Pseudo")) {
						pseudocookie = cookie.getValue();
					}
					if (cookie.getName().equals("MDP")) {
						mdpcookie = cookie.getValue();
					}
				}
			}
			Utilisateur user = new UtilisateurManager().ConnexionCookies(pseudocookie, mdpcookie);
			
			if (user != null && user.getActive()) {
				// Création d'une session
				HttpSession session = request.getSession();
				// Set dans la session l'attribut "utilisateur"
				session.setAttribute("utilisateur", user);
				// redirection sur la page Home en mode connecter
				request.setAttribute("titlePage", "Liste des enchères");
//				rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Home.jsp");
				response.sendRedirect(request.getContextPath() + "/Home");
			}else {				
				rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Connexion.jsp");
				rd.forward(request, response);
			}
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BusinessException exceptions = new BusinessException();
		UtilisateurManager manager = new UtilisateurManager();
		Utilisateur utilisateur;
		
		String password = request.getParameter("MDP");
		String sha3Hex = UtilisateurManager.hashPwd(password);
		String pseudo = request.getParameter("Pseudo");
		String sesouvenir = "";
		RequestDispatcher rd=null;
		
		sesouvenir = request.getParameter("connection-remember");
		
		if (sesouvenir != null) {
			Cookie cookiePseudo = new Cookie("Pseudo", pseudo);
			cookiePseudo.setMaxAge(604800);
			response.addCookie(cookiePseudo);
			Cookie cookiePass = new Cookie("MDP", sha3Hex);
			cookiePass.setMaxAge(604800);
			response.addCookie(cookiePass);
		}
		try {
			// essai connexion

			utilisateur = manager.connexion(request.getParameter("Pseudo"), request.getParameter("MDP"));
			// OK
			if (utilisateur != null && utilisateur.getActive() && !request.getParameter("Pseudo").equals("")
					&& !request.getParameter("MDP").equals("")) {
				// Création d'une session
				HttpSession session = request.getSession();
				// Set dans la session l'attribut "utilisateur"
				session.setAttribute("utilisateur", utilisateur);
				// redirection sur la page Home en mode connecter
				response.sendRedirect(request.getContextPath()+"/");
			} else {
				// PAS OK si erreurs identifiants
				exceptions.ajouterErreur(MSG_BLL.ID_MDP_KO);
				request.setAttribute("listeCodesErreur", exceptions.getListeCodesErreur());
				request.setAttribute("titlePage", "Liste des enchères");
				rd = request.getRequestDispatcher("WEB-INF/jsp/pages/Connexion.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// set dans la requete la liste d'erreurs a la jsp
		}
	}
}


