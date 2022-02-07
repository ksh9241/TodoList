document.addEventListener("DOMContentLoaded", function() {
	asyncSelectUser();
})

function asyncSelectUser() {
	let searchBar = document.querySelector(".search-input");
	console.log(searchBar);
	searchBar.addEventListener("keyup", function(e) {
		xmlHttpRequest(e.target.value);
	})
}

function xmlHttpRequest(str) {
	let xhr = XMLHttpRequest();
		
}