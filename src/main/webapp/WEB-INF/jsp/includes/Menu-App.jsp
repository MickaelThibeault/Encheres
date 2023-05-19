<section class="Menu-App" style="display:none;">
	<nav>
		<a href="${context}/Home">Accueil</a>
		<c:choose>
			<c:when test="${utilisateur != null }">
			<c:if test="${utilisateur.administrateur}">			
				<a href="${context}/Admin">Admin</a>
			</c:if>
				<a href="${context}/MonProfil">Compte</a>
				<a href="${context}/VendreUnArticle">Vendre</a>
				<a href="${context}/Deconnexion">Déconnexion</a>
			</c:when>
			<c:otherwise>
				<a href="${context}/Inscription">Inscription</a>
				<a href="${context}/Connexion">Connexion</a>
			</c:otherwise>
		</c:choose>
	</nav>
</section>