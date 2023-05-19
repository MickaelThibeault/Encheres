<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main class="inscription">
	<form action="" method="post">
		<h1>Gestion des utilisateurs et des catégories</h1>
<%-- 		<%@include file="../includes/erreurs.jsp"%> --%>
		<div class="box-userAdmin">
			<h2 class="h2Admin">Gestion des utilisateurs</h2>
			<c:forEach var="user" items="${listUser}">
				<div class="groupForm">
					<div>
						<Strong style="width:100px;">Id*${user.no_utilisateur} : </Strong><Strong>${user.pseudo}</Strong> 
					</div>
					<div>
						<a class="delete jsAdminDelete"
							href="AdminSuppressionCompte?id=${user.no_utilisateur}"><i class="fa fa-times" aria-hidden="true"></i></a>
						<a <c:if test="${user.active}">class="delete on cascade"</c:if><c:if test="${!user.active}">class="delete off cascade"</c:if>
							 href="DesactiverCompte?idUser=${user.no_utilisateur}">
							<c:if test="${user.active}"><i class="fa-solid fa-power-off on"></i></c:if><c:if test="${!user.active}"><i class="fa-solid fa-power-off off"></i></c:if>
						</a>
					</div>
				</div>
				<hr>
			</c:forEach>
		</div>
	</form>

	<div class="adminCat">
		<h2 class="h2Admin">Gestion des catégories</h2>
		<c:forEach var="cat" items="${categorie}">
		<div>
		<form action="" method="post">
				<strong>${cat.libelle}</strong> <a class="delete jsAdminDelete"
					href="AdminSuppressionCategorie?id=${cat.no_categorie}"><i class="fa fa-times" aria-hidden="true"></i></a>
			</form>
		
			<form action="AdminModifierCategorie?id=${cat.no_categorie}"
				method="post">
				<input required type="text" id="categorie" name="categorie"
					placeholder="Nouveau nom" />
				<button type="submit"><i class="fa-solid fa-pen"></i></button>
			</form>
			<hr>
		</div>
			
		</c:forEach>
		<form id="formAdminCategorie" action="AdminAjoutCategorie" method="post">
			<h3>Ajouter une catégorie</h3>
			<input required type="text" id="categorie" name="categorie"
				placeholder="Jeux vidéo" />
			<button id="ajoutCat" type="submit">Ajouter</button>
		</form>
	</div>


</main>