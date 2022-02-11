document.addEventListener("DOMContentLoaded", function() {
	checkUserId();
	changeUserIdText();
	joinValidation();
})

// 아이디 중복체크 함수
function checkUserId() {
	let checkBtn = document.getElementById("check-btn");
	
	checkBtn.addEventListener("click", function() {
		let userId = document.getElementById("userId");
		if (userId.value.trim() == '') {
			alert("아이디를 입력해주세요");
			userId.focus();
			return;
		}
		
		let replaceStr = userId.value.replace(/\s/gi,'');
		
		let xhr = new XMLHttpRequest();
		xhr.addEventListener("load", function() {
			if (xhr.status == 200 && xhr.readyState == 4) {
				let data = JSON.parse(xhr.responseText);
				if (data.result) {
					useId();
				} else {
					alert("이미 사용중인 아이디입니다.");
					document.getElementById("userId").focus();
				}
			}
		})
		
		xhr.open("GET", "/checkUserId?userId="+replaceStr);
		xhr.send();
	})
}

// 아이디 중복 체크 이후 사용할지 처리하는 함수
function useId() {
	if(window.confirm("사용가능한 아이디입니다.\n아이디를 사용하시겠습니까??")){
		let userId = document.getElementById("userId");
		userId.setAttribute("check", true);
	}
}

// 아이디 중복체크 이후 변경 시 validation 처리
function changeUserIdText() {
	let userId = document.getElementById("userId");
	userId.addEventListener("change", ()=> {
		userId.removeAttribute("check");
	})
}

function joinValidation() {
	let joinBtn = document.getElementById("joinBtn");
	
	joinBtn.addEventListener("click", ()=> {
		
		let userId = document.getElementById("userId").value;
		let pwd = document.getElementById("password").value;
		let repeatPwd = document.getElementById("repeatPassword").value;
		let name = document.getElementById("userName").value;
		let phoneNum = document.getElementById("phoneNumber").value;
		
		let regPhone = /^01(0|1|6|7|8|9)([0-9]{3,4})([0-9]{4})$/;
		
		if (document.getElementById("userId").getAttribute("check") != "true") {
			alert("아이디 중복확인을 해주세요");
			document.getElementById("userId").focus();
			return;
		} 
		else if (pwd.trim() == '') {
			alert("비밀번호를 입력해주세요");
			document.getElementById("password").focus();
			return;
		}
		else if (repeatPwd.trim() == '') {
			alert("비밀번호 확인을 입력해주세요");
			document.getElementById("repeatPassword").focus();
			return;
		}
		else if (pwd != repeatPwd) {
			alert("비밀번호가 맞지 않습니다.");
			return;
		}
		else if (name.trim() == '') {
			alert("이름을 입력해주세요");
			document.getElementById("userName").focus();
			return;
		}
		else if (!regPhone.test(phoneNum)) {
			alert("전화번호형식이 올바르지 않습니다. 숫자만 입력하세요 (10 ~ 11 자리)");
			document.getElementById("phoneNumber").focus();
			return;
		}
		
		let form = document.getElementById("joinForm");
		form.submit();
	})
	
	
	
}