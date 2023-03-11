package fr.eni.projet.bo;

import java.util.Date;

public class Encheres {
	private Integer no_utilisateur;
	private Integer no_article;
	private Date date_enchere;
	private Integer montant_enchere;
	private String pseudo;

	public Encheres() {
	}

	public Encheres(Integer no_utilisateur, Integer no_article, Date date_enchere, Integer montant_enchere,String pseudo) {

		this.no_utilisateur = no_utilisateur;
		this.no_article = no_article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
		this.pseudo = pseudo;
	}
	public Encheres(Integer no_utilisateur, Integer no_article, Date date_enchere, Integer montant_enchere) {

		this.no_utilisateur = no_utilisateur;
		this.no_article = no_article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}

	public Integer getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(Integer no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public Integer getNo_article() {
		return no_article;
	}

	public void setNo_article(Integer no_article) {
		this.no_article = no_article;
	}

	public Date getDate_enchere() {
		return date_enchere;
	}

	public void setDate_enchere(Date date_enchere) {
		this.date_enchere = date_enchere;
	}

	public Integer getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(Integer montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	@Override
	public String toString() {
		return "Encheres [no_utilisateur=" + no_utilisateur + ", no_article=" + no_article + ", date_enchere="
				+ date_enchere + ", montant_enchere=" + montant_enchere + ", pseudo=" + pseudo + "]";
	}
}
