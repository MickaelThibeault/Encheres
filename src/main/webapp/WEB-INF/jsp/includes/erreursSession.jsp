<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import=" java.util.List" %>
<%@ page import="fr.eni.projet.exception.codesmessages.LecteurMessage" %>
<% List<Integer> listeCodesErreur = (List<Integer>) request.getSession().getAttribute("listeCodesErreur");  %>
<section class="messagesErreurs">
<%if( listeCodesErreur!= null && listeCodesErreur.size() > 0 ){ %>
	<h2>Oups! il y a des erreurs !</h2>
	<%for(int erreur : listeCodesErreur) {%>
		<p>_ <%= LecteurMessage.getMessageErreur(erreur)%></p>
<%}} %>
</section>