document.addEventListener("DOMContentLoaded", function() {
	asyncSelectUser();
})

function asyncSelectUser() {
	let searchBar = document.querySelector(".search-input");
	searchBar.addEventListener("keyup", function(e) {
		if (e.target.value.length > 0) {
			xmlHttpRequest(e.target.value);
		}
		else {
			document.querySelector(".searchUsersList ul").style.display = "none";
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
	ul.style.display = "block";
	
	// 이벤트 발생 시 조회된 모든 노드 삭제 후 add
	while ( ul.hasChildNodes() ) {
		ul.removeChild(ul.firstChild);
	}
	
	if (data.length > 0) {
		for(let i = 0; i < data.length; i++) {
			let li = document.createElement("li");
			let spanId = document.createElement("span");
			let spanName = document.createElement("span");
			let ipnutUserIdx = document.createElement("input");
			ipnutUserIdx.setAttribute("type","hidden");
			
			spanId.innerText = data[i].userId;
			spanName.innerText = data[i].userName;
			spanName.setAttribute("style", "float:right;")
			ipnutUserIdx.innerText = data[i].idx;
			
			li.append(spanId);
			li.append(spanName);
			li.append(ipnutUserIdx);
			
			ul.append(li);
		}
		
		let li = document.createElement("li");
		li.setAttribute("style","font-weight:bold; font-size:1.2em; margin-bottom:15px;")
		let spanId = document.createElement("span");
		let spanName = document.createElement("span");
		
		spanId.innerText = "고객아이디";
		spanName.innerText = "고객명";
		spanName.setAttribute("style", "float:right;")
		
		li.append(spanId);
		li.append(spanName);
		
		ul.insertAdjacentElement("afterbegin", li);
		
		// 마우스 이벤트 함수 실행
		searchUsersMouseEvent();
	} else {
		let li = document.createElement("li");
		li.innerText = "조회된 유저 정보가 없습니다.";
		ul.append(li);
	}
}

function searchUsersMouseEvent() {
	let liList = document.querySelector(".searchUsersList ul").querySelectorAll("li");
	
	for (let i = 1; i < liList.length; i++) {
		liList[i].addEventListener("mouseover", function(e) {
			e.target.closest("li").style.backgroundColor = "darkGray";
		})
		
		liList[i].addEventListener("mouseout", function(e) {
			e.target.closest("li").style.backgroundColor = "ghostwhite";
		})
		
		liList[i].addEventListener("mousedown", function(e) {
			document.querySelector(".searchUsersList ul").style.display = "none";
			document.getElementById("user-search-bar").value = e.target.closest("li").firstChild.innerText;
			document.querySelector("#userIdx").value = e.target.closest("li").lastChild.innerText;
		})
	}
}