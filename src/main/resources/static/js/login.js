document.addEventListener("DOMContentLoaded", function() {
	clickLoginBt();
})

function clickLoginBt () {
	let loginBt = document.querySelector("#login-btn");
	loginBt.addEventListener("click", function () {
		
		let loginForm = document.querySelector("#loginForm");
		if (validation(loginForm)) {
			loginForm.submit();		
		};
	})
}

function validation (loginForm) {
	let id = loginForm.querySelector(".input_user").value;
	let pw = loginForm.querySelector(".input_pass").value;
	
	if (id.trim() == '' || id == null) {
		alert("아이디를 입력해주세요.");
		loginForm.querySelector(".input_user").focus();
		return;
	} else if (pw.trim() == '' || pw == null) {
		alert("패스워드를 입력해주세요.");
		loginForm.querySelector(".input_pass").focus();
		return;
	}
	
	return true;
}