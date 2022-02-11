document.addEventListener("DOMContentLoaded", function() {
	asyncSelectUser();
})

function asyncSelectUser() {
	let searchBar = document.querySelector(".search-input");
	searchBar.addEventListener("keyup", function(e) {
		if (e.target.value.length > 0) {
			xmlHttpRequest(e.target.value);
		}
	})
}

function xmlHttpRequest(str) {
	let xhr = new XMLHttpRequest();
	xhr.addEventListener("load", () => {
		if (xhr.status == 200 && xhr.readyState == 4) {
			let data = JSON.parse(xhr.responseText);
			searchUsersAddViewList(data);
		} 
	})
	
	xhr.open("POST", "/user/searchUsers");
	xhr.send(str);
}

function searchUsersAddViewList(data) {
	let ul = document.querySelector(".searchUsersList ul");
	ul.setAttribute("display","block");
	
	// 이벤트 발생 시 조회된 모든 노드 삭제 후 add
	while ( ul.hasChildNodes() ) {
		ul.removeChild(ul.firstChild);
	}
	
	if (data.length > 0) {
		for(let i = 0; i < data.length; i++) {
			let li = document.createElement("li");
			li.innerText = data[i].userId + " " + data[i].userName;
			ul.append(li);
		}
	} else {
		let li = document.createElement("li");
		li.innerText = "조회된 유저 정보가 없습니다.";
		ul.append(li);
	}
}