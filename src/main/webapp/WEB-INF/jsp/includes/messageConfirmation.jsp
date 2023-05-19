<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import=" java.util.List" %>
<%@ page import="fr.eni.projet.exception.codesmessages.LecteurMessage" %>
<% List<Integer> listeMessages= (List<Integer>) request.getSession().getAttribute("listeCodesMessage");  %>

<section class="messagesConfirmation">
<%if(listeMessages != null){ %>
	<%for(int erreur : listeMessages) {%>
		<p>_ <%= LecteurMessage.getMessageErreur(erreur)%></p>
<%}} %>
</section>