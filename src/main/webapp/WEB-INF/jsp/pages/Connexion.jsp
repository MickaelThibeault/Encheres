<main class="inscription">
	<form action="Connexion" method="post" class="Connexion" >
		<h1>Connexion</h1>
		<%@include file="../includes/erreurs.jsp" %>
		<div class="groupForm">
			<label for="Pseudo">Pseudo</label> <input type="text" id="Pseudo"
				name="Pseudo" />
		</div>
		<div class="groupForm">
			<label for="MDP">Mot de passe</label> <input type="password" id="MDP"
				name="MDP" />
		</div>
		<div class="groupForm souvenir">
         	<input id="connection-remember" type="checkbox" name="connection-remember" value="connection-remember">
		 	<label for="connection-remember">Se souvenir de moi </label>
		</div>
		<div class="btnsForm">
			<a class="btn-orange" href="${context}/Inscription" >Inscription</a>
			<button type="submit">Connexion</button>
		</div>
	</form>
</main>