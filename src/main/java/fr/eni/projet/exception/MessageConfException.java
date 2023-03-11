package fr.eni.projet.exception;

import java.util.ArrayList;
import java.util.List;

public class MessageConfException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private List<Integer> listeCodesErreur;

	public MessageConfException() {
		super();
		this.listeCodesErreur = new ArrayList<>();
	}

	/**
	 * 
	 * @param code Code de l'erreur. Doit avoir un message associé dans un fichier
	 *             properties.
	 */
	public void ajouterMessage(int code) {
		if (!this.listeCodesErreur.contains(code)) {
			this.listeCodesErreur.add(code);
		}
	}

	public boolean hasMessages() {
		return this.listeCodesErreur.size() > 0;
	}

	public List<Integer> getListeCodesMessage() {
		return this.listeCodesErreur;
	}
}
