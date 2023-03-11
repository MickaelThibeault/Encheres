<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${utilisateur != null }">
		<aside class="user-connected">
			<div class="card-connected">
		        <img src="https://www.nautec.com/wp-content/uploads/2018/04/placeholder-person.png" alt="">
		        <h2>Bienvenue ${utilisateur.getPseudo()}<br> ${utilisateur.credit} pts</h2>
		        
		    </div>
		</aside>
</c:if>