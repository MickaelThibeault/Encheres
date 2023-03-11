package fr.eni.projet.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Deconnexion
 */
@WebServlet("/Deconnexion")
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// RÉCUPÈRE LA SESSION DANS LA REQUETE
		HttpSession session = request.getSession();
		// DÉTRUIT LA SESSION
		session.invalidate();
		//RECUPERE LES COOKIES
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// SUPPRIME LES COOKIES MDP
				if (cookie.getName().equals("Pseudo")) {
					Cookie cookiePass = new Cookie("Pseudo", cookie.getValue());
					cookiePass.setMaxAge(0);
					response.addCookie(cookiePass);
				}
				// SUPPRIME LES COOKIES MDP
				if (cookie.getName().equals("MDP")) {
					Cookie cookiePass = new Cookie("MDP", cookie.getValue());
					cookiePass.setMaxAge(0);
					response.addCookie(cookiePass);
				}
			}
		}
		// redirection sur la page de Home en déconnecter
		response.sendRedirect(request.getContextPath()+"/ENI-Encheres");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
