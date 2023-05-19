const E_checks = document.querySelectorAll('*[id^="e-"]')
const V_checks = document.querySelectorAll('*[id^="v-"]')
const radios = document.querySelectorAll('[name="filterConnected"]')
const clickRadio = (V,E)=>{
    V.forEach(input => {
        input.checked=false
        input.disabled=true
    })
    E.forEach(input => {
        input.disabled=false
    })
}
radios.forEach(radio => {
    radio.addEventListener("click",()=>{
        if(radio.getAttribute("id")=="achats"){
            clickRadio(V_checks,E_checks)
        }else
            clickRadio(E_checks,V_checks)
    })
})

const supprimer = () =>  {
	if(window.confirm("Voulez vous supprimer votre compte ? ")){
		  alert("Navré de vous voir partir !")
  	}else{
		e.preventDefault()
		alert("Heureux de vous voir rester !")
	}
}

$(()=>{
	$('#btn-menu').click(()=>{
		$('.Menu-App').slideToggle();
	})
	$('.jsDelete').click((e)=>{
		if(window.confirm("Voulez vous supprimer votre compte ? ")){
		  alert("Au plaisir de vous voir partir !")
  	}else{
		e.preventDefault()
		alert("Heureux de vous voir rester !")
	}
	})
	$('.jsAdminDelete').click((e)=>{
		if(window.confirm("Voulez vous supprimer ce compte ? ")){
		  alert("C'est fait !")
  	}else{
		e.preventDefault()
		alert("La commande est annulée.")
	}
	})
})