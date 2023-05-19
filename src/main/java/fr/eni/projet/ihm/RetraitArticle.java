package fr.eni.projet.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.bll.ArticlesVendusManager;

/**
 * Servlet implementation class RetraitArticle
 */
@WebServlet("/RetraitArticle")
public class RetraitArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticlesVendusManager artMG = new ArticlesVendusManager();
		Integer no_article = null;
		try {
				if (!request.getParameter("idArticle").equals("") && request.getParameter("idArticle") != null) {
					no_article = Integer.parseInt(request.getParameter("idArticle"));
					artMG.retraitArticle(no_article);
				}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Error500");
		}
		request.setAttribute("idArticle", no_article);
		request.setAttribute("titlePage", "Article");
		response.sendRedirect(request.getContextPath()+"/Article?idArticle="+no_article);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
