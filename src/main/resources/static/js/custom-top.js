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
	let xhr = new XMLHttpRequest();
	xhr.addEventListener("load", (e) => {
		if (this.status == 200 && this.readyState == 4) {
			console.log(e);
		}
	})
	
	xhr.open("POST", "/user/findByUserName");
	xhr.send(str);
}