<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<main class="home">
	<h1>
		ENI-Enchère <br /> <span>La plateforme web qui vous permet de
			céder ou d'acheter des objets sans transaction financière.</span>
	</h1>

	<%@include file="../includes/filtresHome.jsp"%>

	<%@include file="../includes/erreurs.jsp"%>
	<%@include file="../includes/messageConfirmation.jsp"%>
	<div class="box-articles">
		<c:forEach var="article" items="${catalogue}">
			<article class="container">
				<div class="poster">
					<div class="poster__img"></div>
					<div class="poster__info">
						<h3 class="poster__title">${article.nom_article}</h3>
						<hr /><br>
						<p class="poster__text">
							<span><strong>Prix:</strong><strong>${article.prix_initial } pts</strong></span>
							<span><strong>Fin de l'enchére:</strong><strong><fmt:formatDate pattern="dd/MM/yy" value="${article.date_fin_encheres}" /></strong></span>
							<c:forEach var="user" items="${users}">
								<c:if test="${user.no_utilisateur == article.no_utilisateur }">								
									<span><strong>Retrait: </strong><strong class="rue">${user.rue}<br> ${user.code_postal} ${user.ville }</strong></span>
								</c:if>
							</c:forEach>
							  <span><strong>Vendeur:</strong><strong> ${article.vendeur}</strong></span>
						</p>
					</div>
				</div>
				<a class="btn-article" href="Article?idArticle=${article.no_article}">Voir les détails</a>
			</article>
		</c:forEach>
	</div>


</main>