<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import=" java.util.List" %>
<%@ page import="fr.eni.projet.exception.codesmessages.LecteurMessage" %>
<% List<Integer> listeCodesErreur = (List<Integer>) request.getAttribute("listeCodesErreur");  %>

<section class="messagesErreurs">
<%if(listeCodesErreur != null){ %>
	<h2>Oups! il y a des erreurs !</h2>
	<%for(int erreur : listeCodesErreur) {%>
		<p>_ <%= LecteurMessage.getMessageErreur(erreur)%></p>
<%}} %>
</section>