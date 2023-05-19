<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main class="inscription">
	<form action="" method="post" class="ModifMonProfil">
		<h1>Modifier vos informations</h1>
		<%@include file="../includes/erreurs.jsp"%>
		<div class="box-form">
			<div class="groupForm">
				<label for="pseudo">pseudo</label>
				<input required type="text" id="pseudo"  name="pseudo" value="${utilisateur != null ? utilisateur.getPseudo():''}"/>
			</div>
			<div class="groupForm">
				<label for="nom">nom</label>
				<input required type="text" id="nom" name="nom" value="${utilisateur != null ? utilisateur.getNom():''}"/>
			</div>
			<div class="groupForm">
				<label for="prenom">pr�nom</label>
				<input required type="text" id="prenom" name="prenom" value="${utilisateur != null ? utilisateur.getPrenom():''}"/>
			</div>
			<div class="groupForm">
				<label for="email">email</label>
				<input required type="email" id="email" name="email" value="${utilisateur != null ? utilisateur.getEmail():''}"/>
			</div>
			<div class="groupForm">
				<label for="telephone">t�l�phone</label>
				<input required type="tel" id="telephone" name="telephone" placeholder="FR 0605040302"  pattern="[0-9]{10}" value="${utilisateur != null ? utilisateur.getTelephone():''}" />
			</div>
			<div class="groupForm">
				<label for="rue">rue</label>
				<input required type="text" id="rue" name="rue" value="${utilisateur != null ? utilisateur.getRue():''}"/>
			</div>
			<div class="groupForm">
				<label for="codePostal">code Postal</label>
				<input required type="text" id="codePostal" name="codePostal" placeholder="75000" pattern="[0-9]{5}" value="${utilisateur != null ? utilisateur.getCode_postal():''}"/>
			</div>
			<div class="groupForm">
				<label for="ville">ville</label>
				<input required type="text" id="ville" name="ville" value="${utilisateur != null ? utilisateur.getVille():''}"/>
			</div>
			<div class="groupForm">
				<p class="credit">Cr�dit : <span>${utilisateur != null ? utilisateur.getCredit():''}</span> points</p>
			</div>
		</div>
		<div class="btnsForm">
			<button class="btn-save" type="submit">Enregistrer</button>
			<a  class="delete jsDelete" href="DesactiverCompte?idUser=${utilisateur.no_utilisateur}">Supprimer</a>
			<a class="return" href="MonProfil">Retour</a>
		</div>
	</form>
</main>