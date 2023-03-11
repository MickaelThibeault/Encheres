package fr.eni.projet.exception.codesmessages;

public abstract class MSG_BLL {
	
	public static final int ID_MDP_KO = 20000;
	public static final int ERROR_PSEUDO_OR_MAIL_ALREADY_TAKEN = 20001;
	public static final int ERROR_LENGTH_PSEUDO_UTILISATEUR = 20002;
	public static final int ERROR_PSEUDO_NOT_SPECIAL_CHAR = 20003;
	public static final int ERROR_LENGTH_NOM_UTILISATEUR = 20004;
	public static final int ERROR_LENGTH_PRENOM_UTILISATEUR = 20005;
	public static final int ERROR_LENGTH_EMAIL_UTILISATEUR = 20006;
	public static final int ERROR_FORMAT_EMAIL_UTILISATEUR = 20007;
	public static final int ERROR_LENGTH_TELEPHONE_UTILISATEUR = 20008;
	public static final int ERROR_FORMAT_TELEPHONE_UTILISATEUR = 20009;
	public static final int ERROR_LENGTH_RUE_UTILISATEUR = 20010;
	public static final int ERROR_LENGTH_CODE_POSTAL_UTILISATEUR = 20011;
	public static final int ERROR_LENGTH_VILLE_UTILISATEUR = 20012;
	public static final int ERROR_CREATE_UTILISATEUR = 20013;
	public static final int ERROR_UPDATE_UTILISATEUR = 20014;
	public static final int ERROR_MDP_NO_EQUALS = 20015;
	public static final int NOMBRE_DE_POINTS_INSUFFISANT = 20016;
	public static final int ENCHERE_NULL_PROPOSITION = 20017;
	public static final int PROPOSITION_INFERIEUR_ENCHERE = 20018;

	public static final int ERROR_LENGTH_NOM_ARTICLE = 0;
	public static final int ERROR_LENGTH_DESCRIPTION_ARTICLE = 0;
	public static final int ERROR_DATE_BEFORE_TODAY = 0;
	public static final int ERROR_START_DATE_AFTER_END_DATE = 0;

	public static final int ERROR_NULL_ENCHERES = 20021;

	public static final int ERROR_ZERO_CATEGORIES = 20098;
	public static final int ERROR_ZERO_ENCHERES = 20099;

	public static final int CRITICAL_ERROR_SERVER = 50000;
}
