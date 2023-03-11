<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="VenteArticle" value="/venteArticle" />


<main class="inscription artForm">
	<form method="post" action="ModifierArticle?idArticle=${article.no_article }">
	<h1>Vente d'un Article</h1>
    <%@include file="../includes/erreurs.jsp" %>
    <div class="img"></div>
    <div class="groupForm artForm">
        <label for="Article">Nom</label>
        <input type="text" id="name" name="name" required  value="${article.nom_article }">
    </div>
    <div class="groupForm artForm">
        <label for="story">Description</label>
        <textarea id="story" name="story" >${article.description }</textarea>
    </div>
    <div class="artGroup">
	    <div class="groupForm artForm">
	        <label for="categorie">Catégorie</label>
	        <select name="categorie" id="categorie">
	            <c:forEach var="cat" items="${categorie}">
	            	<option value="${cat.libelle}">${cat.libelle}</option>
	            </c:forEach>
	        </select>
	    </div>
	    <div class="groupForm artForm">
	        <label for="prix">Prix initial : </label>
	        <input type="number" id="prix" name="howmuch"  min="0" value="${article.prix_initial}">
	    </div>
    </div>
    <div class="artGroup">
	    <div class="groupForm artForm">
	        <label for="date_debut">Début de l'enchére : <span>(min J+1)</span></label>
	        <input type="date" id="date_debut" name="date_debut" value="${article.date_debut_encheres}" >
	    </div>
	    <div class="groupForm artForm">
	        <label for="date_fin">fin de l'enchére :</label>
	        <input type="date"name="date_fin" id="date_fin" value="${article.date_fin_encheres }">
	    </div>
    </div>
    <div class="btnsForm ">
        <button type="submit">Enregistrer</button>
        <a href="${context}/Article?idArticle=${article.no_article}"> annuler</a>
    </div>
    </form>
</main>