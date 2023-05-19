package fr.eni.projet.bll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.bo.ArticlesVendus;
import fr.eni.projet.bo.Encheres;
import fr.eni.projet.bo.Utilisateur;
import fr.eni.projet.dal.ArticlesVendusDAO;
import fr.eni.projet.exception.BusinessException;
import fr.eni.projet.exception.DALException;
import fr.eni.projet.exception.codesmessages.MSG_BLL;
import fr.eni.projet.dal.DAOFactory;

public class ArticlesVendusManager {
	
	private static ArticlesVendusDAO articleDao;

	public ArticlesVendusManager() {
		articleDao = DAOFactory.getArticlesVendusDAO();
	}

	/**
	 * Créer un article
	 * 
	 * @param articleVendu
	 * @throws BLLException
	 * @throws DALException 
	 */
	public Integer creerArticle(ArticlesVendus articleVendu) throws DALException {
		return articleDao.insertArticle(articleVendu);
	}

	/**
	 * Validation du formulaire de création d'un article
	 * 
	 * @param articleVendu
	 * @return
	 */
	public BusinessException validateArticleVendu(ArticlesVendus articleVendu) {
		
		BusinessException exceptions = new BusinessException();
		LocalDate date_debut = articleVendu.getDate_debut_encheres().toLocalDate();
		LocalDate date_fin = articleVendu.getDate_fin_encheres().toLocalDate();
		
		if (articleVendu.getNom_article().length() > 30) {
			exceptions.ajouterErreur(MSG_BLL.ERROR_LENGTH_NOM_ARTICLE);
		}
		if (articleVendu.getDescription().length() > 300) {
			exceptions.ajouterErreur(MSG_BLL.ERROR_LENGTH_DESCRIPTION_ARTICLE);
		}
		if (articleVendu.getDate_debut_encheres().after(articleVendu.getDate_fin_encheres())) {
			exceptions.ajouterErreur(MSG_BLL.ERROR_START_DATE_AFTER_END_DATE);
		}
		if (date_debut.isBefore(LocalDate.now().plusDays(1)) || !date_fin.isAfter(date_debut)) {
			exceptions.ajouterErreur(MSG_BLL.ERROR_DATE_BEFORE_TODAY);
		}
		return exceptions;
	}

	/**
	 * Récupération du catalogue entier
	 * 
	 * @return Le catalogue entier
	 * @throws BLLException
	 * @throws SQLException 
	 * @throws DALException 
	 */
	public List<ArticlesVendus> getCatalogue() throws DALException, SQLException {
		return articleDao.selectAll();
	}

	/**
	 * Récupération du catalogue par catégories (id)
	 * 
	 * @param id idCatégorie
	 * @return le catalogue par catégories
	 * @throws BLLException
	 * @throws DALException 
	 */
	public List<ArticlesVendus> getCatalogueCategorie(int id) throws DALException {
		return articleDao.selectCategorie(id);

	}

	/**
	 * Récupération du catalogue par mot clé
	 * 
	 * @param nom mot clé
	 * @return le catalogue par mot clé
	 * @throws BLLException
	 * @throws DALException 
	 */
	public List<ArticlesVendus> getCatalogueByName(String nom) throws  DALException {
		return articleDao.selectAllByName(nom);

	}

	public ArticlesVendus getArticleById(int id) throws DALException, SQLException {
		return articleDao.selectById(id);
	}

	/**
	 * Récupération du catalogue des encheres ouvertes
	 * 
	 * @param catalogue enitier
	 * @return le catalogue des encheres ouvertes
	 * @throws BLLException
	 * @throws DALException 
	 * @throws SQLException 
	 */
	public List<ArticlesVendus> getEnchereOuverte(List<ArticlesVendus> catalogue) throws DALException, SQLException {
		
		List<ArticlesVendus> enchereOuverte = new ArrayList<>();
		EncheresManager enchereMG = new EncheresManager();
		ArticlesVendusManager artMG = new ArticlesVendusManager();
		
		if (catalogue != null) {
			for (ArticlesVendus art : catalogue) {
				if (!art.getEtat_vente().equals("TR") && !art.getEtat_vente().equals("RE")) {
//						&& enchereMG.etatEnCours(art)) {
					// enchere ouverte
					if (art.getEtat_vente().equals("CR")) {

						if (enchereMG.etatEnCours(art)) {
							// change l'etat
							art.setEtat_vente("EC");
							// MAJ dans la BDD
							artMG.ModifierEtat(art);
							enchereOuverte.add(art);
						}
						if (enchereMG.etatTerminer(art)) {
							// change l'etat
							art.setEtat_vente("TR");
							// MAJ dans la BDD
							artMG.ModifierEtat(art);
						}
						if (art.getEtat_vente().equals("CR")) {
							// Ajoute a la liste des encheres ouvertes
							enchereOuverte.add(art);
						}
					} else {
						// etat = "EC" non demarrer => Ajoute a la liste des encheres ouvertes
						if (enchereMG.etatTerminer(art)) {
							// change l'etat
							art.setEtat_vente("TR");
							// MAJ dans la BDD
							artMG.ModifierEtat(art);
						}

						if (art.getEtat_vente().equals("EC")) {
							enchereOuverte.add(art);
						}
					}
				}
			}
		}
		return enchereOuverte;
	}

	/**
	 * Mise jour de l'etat d'une enchère
	 * 
	 * @param article article a mettre à jour
	 * @throws DALException 
	 * @throws SQLException 
	 * @throws BLLException
	 */
	private void ModifierEtat(ArticlesVendus article) throws DALException, SQLException{
			articleDao.update(article);
	}

	public List<ArticlesVendus> getUserAchats(List<String> list, List<ArticlesVendus> catalogueAll, Utilisateur userCurrent, List<Encheres> listEncheres) throws DALException {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for (String val : list) {
			Integer valInt =  Integer.parseInt(val);

			List<ArticlesVendus> tmpCR = valInt.equals(1) ? getOuvertes(catalogueAll,userCurrent,listEncheres) : new ArrayList<>();
			
			for(ArticlesVendus article : tmpCR) 
				aRetourner.add(article);
			
			List<ArticlesVendus> tmpEC = valInt.equals(2) ? getEnCours(catalogueAll,userCurrent ,listEncheres) : new ArrayList<>();
			
			for(ArticlesVendus article : tmpEC)
				aRetourner.add(article);
			
			List<ArticlesVendus> tmpTR =  valInt.equals(3) ? getRemportees(catalogueAll,userCurrent,listEncheres ) : new ArrayList<>();
			
			for(ArticlesVendus article : tmpTR)
				aRetourner.add(article);
			
			List<ArticlesVendus> tmpVEC =  valInt.equals(4)  ? getVentesCours(catalogueAll,userCurrent ) : new ArrayList<>();
			
			for(ArticlesVendus article : tmpVEC)
				aRetourner.add(article);
			
			List<ArticlesVendus> tmpVCR =  valInt.equals(5) ? getNonDebutees(catalogueAll,userCurrent ) : new ArrayList<>();
			
			for(ArticlesVendus article : tmpVCR)
				aRetourner.add(article);
			
			List<ArticlesVendus> tmpVTR = valInt.equals(6) ? getTerminees(catalogueAll,userCurrent ) : new ArrayList<>();
			
			for(ArticlesVendus article : tmpVTR)
				aRetourner.add(article);
		}
		return aRetourner;
	}

	private List<ArticlesVendus> getTerminees(List<ArticlesVendus> catalogueAll, Utilisateur userCurrent) {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for(ArticlesVendus article : catalogueAll) {
			if(article.getEtat_vente().equals("TR") || article.getEtat_vente().equals("RE")) {
					if(article.getNo_utilisateur() == userCurrent.getno_utilisateur())
						aRetourner.add(article);						
			}
		}
		return aRetourner;
	}

	private List<ArticlesVendus> getNonDebutees(List<ArticlesVendus> catalogueAll, Utilisateur userCurrent) {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for(ArticlesVendus article : catalogueAll) {
			if(article.getEtat_vente().equals("CR")) {
					if(article.getNo_utilisateur() == userCurrent.getno_utilisateur())
						aRetourner.add(article);						
			}
		}
		return aRetourner;
	}

	private List<ArticlesVendus> getVentesCours(List<ArticlesVendus> catalogueAll, Utilisateur userCurrent) {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for(ArticlesVendus article : catalogueAll) {
			if(article.getEtat_vente().equals("EC")) {
					if(article.getNo_utilisateur() == userCurrent.getno_utilisateur())
						aRetourner.add(article);						
			}
		}
		return aRetourner;
	}

	private List<ArticlesVendus> getRemportees(List<ArticlesVendus> catalogueAll, Utilisateur userCurrent, List<Encheres> listEncheres) throws DALException {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for(ArticlesVendus article : catalogueAll) {
			if(article.getEtat_vente().equals("TR")) {	
				Encheres maxEnchere = new EncheresManager().rechercheMaxEnchere(article.getNo_article());
				if(maxEnchere != null) {
					if(maxEnchere.getNo_utilisateur() != null)
						if(maxEnchere.getNo_utilisateur() == userCurrent.getno_utilisateur())
							aRetourner.add(article);											
				}
			}
		}
		return aRetourner;
	}

	private List<ArticlesVendus> getEnCours(List<ArticlesVendus> catalogueAll, Utilisateur userCurrent, List<Encheres> listEncheres) {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for(ArticlesVendus article : catalogueAll) {
			if(article.getEtat_vente().equals("EC")) {	
				for( Encheres enchere : listEncheres) {
					if(enchere.getNo_utilisateur().equals(userCurrent.getno_utilisateur()) && enchere.getNo_article().equals(article.getNo_article()) ) {
						aRetourner.add(article);						
					}
				}
			}
		}
		return aRetourner;
	}

	private List<ArticlesVendus> getOuvertes(List<ArticlesVendus> catalogueAll, Utilisateur userCurrent, List<Encheres> listEncheres) {
		
		List<ArticlesVendus> aRetourner = new ArrayList<>();
		
		for(ArticlesVendus article : catalogueAll) {
			if(article.getEtat_vente().equals("EC")) {	
				aRetourner.add(article);						
			}
		}
		return aRetourner;
	}

	public  void updateArticle(ArticlesVendus article) throws DALException, SQLException {
		articleDao.update(article);
	}

	public void retraitArticle(int id) throws DALException {
		articleDao.retraitArticle(id);
		
	}
	
	public void deleteArticle(int id) throws DALException, SQLException {
		articleDao.delete(id);
		}
}
