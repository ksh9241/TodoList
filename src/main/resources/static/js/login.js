document.addEventListener("DOMContentLoaded", function() {
	clickLoginBt();
})

function clickLoginBt () {
	let loginBt = document.querySelector(".login_btn");
	loginBt.addEventListener("click", function () {
		alert("클릭됨.");
	})
}