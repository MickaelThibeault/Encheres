<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<main class="Inscription">
	<%@include file="../includes/erreurs.jsp"%>
	<section class="MonProfil">
		<h1>Profil Vendeur</h1>
		<div class="MonProfil-card">
			<div>
				<div class="groupForm">
					<p>pseudo :</p>
					<p>${otherUser.getPseudo()}</p>
				</div>
				<div class="groupForm">
					<p>nom :</p>
					<p>${otherUser.getNom()}</p>
				</div>
				<div class="groupForm">
					<p>pr�nom :</p>
					<p>${otherUser.getPrenom()}</p>
				</div>
				<div class="groupForm">
					<p>email :</p>
					<p>${otherUser.getEmail()}</p>
				</div>
			</div>
			<div>
				<div class="groupForm">
					<p>t�l�phone :</p>
					<p>${otherUser.getTelephone()}</p>
				</div>
				<div class="groupForm">
					<p>rue :</p>
					<p>${otherUser.getRue()}</p>
				</div>
				<div class="groupForm">
					<p>code Postal :</p>
					<p>${otherUser.getCode_postal()}</p>
				</div>
				<div class="groupForm">
					<p>ville :</p>
					<p>${otherUser.getVille()}</p>
				</div>
			</div>
		</div>
		
	</section>
</main>