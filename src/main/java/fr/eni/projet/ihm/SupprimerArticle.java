package fr.eni.projet.ihm;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.ArticlesVendusManager;
import fr.eni.projet.exception.DALException;

/**
 * Servlet implementation class SupprimerArticle
 */
@WebServlet("/SupprimerArticle")
public class SupprimerArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticlesVendusManager manager = new ArticlesVendusManager();
		try {
			manager.deleteArticle(Integer.parseInt(request.getParameter("idArticle")));
		} catch (DALException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("titlePage", "Accueil");
		response.sendRedirect(request.getContextPath() + "/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
