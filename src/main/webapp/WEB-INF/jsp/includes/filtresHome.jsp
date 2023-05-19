<section class="box-filter">
<form action="Home" method="post">
	<div class="filtresArticles">
		<fieldset>
			<legend>Filtres</legend>
			<div class="formHome">

				<div class="search-user">
					<div class="searchBar">
						<input id="searchQueryInput" type="text" name="search"
							placeholder="Rechercher" value="" />
						<button id="searchQuerySubmit" type="submit"
							name="searchQuerySubmit">
							<svg style="width: 24px; height: 24px" viewBox="0 0 24 24">
							<path fill="#666666"
									d="M9.5,3A6.5,6.5 0 0,1 16,9.5C16,11.11 15.41,12.59 14.44,13.73L14.71,14H15.5L20.5,19L19,20.5L14,15.5V14.71L13.73,14.44C12.59,15.41 11.11,16 9.5,16A6.5,6.5 0 0,1 3,9.5A6.5,6.5 0 0,1 9.5,3M9.5,5C7,5 5,7 5,9.5C5,12 7,14 9.5,14C12,14 14,12 14,9.5C14,7 12,5 9.5,5Z" />
		      			</svg>
						</button>
					</div>
				</div>
				<div class="search-categories">
					<label for="categories" class="selWrapper">Rechercher un
						article</label> <select name="categorie" id="categories">
						<option value="Toutes">Toutes les catégories</option>
						<c:forEach var="cat" items="${categorie}">
							<option value="${cat.libelle}">${cat.libelle}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
	</div>
	<div class="box-filterHome">
		<c:if test="${utilisateur != null }">
			<fieldset>
				<legend>Vos transactions</legend>
				<div class="filterArticle">
					<div class="group-achats">
						<label for="achats">Mes achats <input type="radio"
							id="achats" name="filterConnected" value="achats"
							checked="checked" />
						</label>
						<div class="group-checks">
							<label> <input type="checkbox" name="e-achats" value="1" id="e-1" />enchères
								ouvertes
							</label> <label> <input type="checkbox" name="e-achats" value="2" id="e-2" />mes
								enchères en cours
							</label> <label> <input type="checkbox" name="e-achats" value="3" id="e-3" />mes
								enchères remportées
							</label>
						</div>
					</div>

					<div class="group-ventes">
						<label>Mes ventes <input type="radio"
							name="filterConnected" value="ventes" id="vente" /></label>
						<div class="group-checks">
							<label> <input type="checkbox" name="e-achats"  id="v-1" 
								disabled="disabled" value="4" />Mes ventes en cours
							</label> <label> <input type="checkbox" name="e-achats"  id="v-2" 
								disabled="disabled" value="5" />Ventes non débutées
							</label> <label> <input type="checkbox" name="e-achats"  id="v-3" 
								disabled="disabled" value="6" />Ventes terminées
							</label>
						</div>
					</div>
				</div>
			</fieldset>
		</c:if>
	</div>
</form>
</section>