<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="VenteArticle" value="/venteArticle" />


<main class="inscription artForm">
	<form method="post" action="VendreUnArticle">
	<h1>Vente d'un Article</h1>
    <%@include file="../includes/erreurs.jsp" %>
    <div class="img"></div>
    <div class="groupForm artForm">
        <label for="Article">Nom</label>
        <input type="text" id="name" name="name" required >
    </div>
    <div class="groupForm artForm">
        <label for="story">Description</label>
        <textarea id="story" name="story" ></textarea>
    </div>
    <div class="artGroup">
	    <div class="groupForm artForm">
	        <label for="categorie">Catégorie</label>
	        <select name="categorie" id="categorie">
	            <c:forEach var="cat" items="${categorie}">
	            	<option value="${cat.no_categorie}">${cat.libelle}</option>
	            </c:forEach>
	        </select>
	    </div>
	    <div class="groupForm artForm">
	        <label for="prix">Prix initial : </label>
	        <input type="number" id="prix" name="howmuch"  min="0" value="0">
	    </div>
    </div>
    <div class="artGroup">
	    <div class="groupForm artForm">
	        <label for="date_debut">Début de l'enchére :<span>(min J+1)</span></label>
	        <input type="date" id="date_debut" name="date_debut">
	    </div>
	    <div class="groupForm artForm">
	        <label for="date_fin">fin de l'enchére :</label>
	        <input type="date"name="date_fin" id="date_fin">
	    </div>
    </div>
    <div class="btnsForm ">
        <button type="submit">Enregistrer</button>
        <a href="${context}/"> annuler</a>
    </div>
    </form>
</main>