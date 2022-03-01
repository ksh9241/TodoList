document.addEventListener("DOMContentLoaded", function() {
	openCalendar();
	newTodoList();
	findAllTodoList(0);
	//updateSuccessYn();		// 버튼 클릭 시 idx값 서버로 던져서 update 이후 disabled 처리
})

function newTodoList() {
	let todoBtn = document.querySelector("#newTodo");
	todoBtn.addEventListener("click" , function(){
		window.open("addTodoPop", "New Todo", "top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no");
	})
}

/**
	로드 시 고객정보를 통한 데이터 조회
	- 고객 검색 시 사용 예정
 */
function findAllTodoList(pageNum) {
	let userIdx = document.querySelector("#userIdx").value;
	
	let xhr = new XMLHttpRequest();
	xhr.addEventListener("load", function(e) {
		if (xhr.status == 200 & xhr.readyState == 4) {
			let data = JSON.parse(e.target.responseText);
			
			replaceHandlebars(data.todoList);		// 일정리스트 세팅
			replacePageHandlebars(data.todoList);	// 페이징처리
			
			updateSuccessYn();						// 할일 성공여부 업데이트
			
			// 달성률 세팅
			document.querySelector("#totalTodoCount").value = data.todoList.totalElements;
			document.querySelector("#successTodoCount").value = data.successCount;
			SuccessRateInfo();					
			
		}
	})
	
	xhr.open("GET", "/auth/"+userIdx+"?page="+pageNum);
	xhr.send();
}

/**
	Handlebars 데이터 바인딩
 */
function replaceHandlebars(data) {
	/*todoList Handlebar*/
	let todoList = document.querySelector("#todoListTemplate").innerHTML;
	let todoListHandlebar = Handlebars.compile(todoList);
	let todoListHTML = todoListHandlebar(data);
	let todoListResult = '';
	todoListResult += todoListHTML;
	let todoSpace = document.querySelector("#todoList-space");
	todoSpace.innerHTML = todoListResult;
}

/* 페이징 수정 해야됨. Handlebars로 int 값 하나만으로 반복문 어떻게 돌릴 지 찾아봐야 함. */
function replacePageHandlebars(data) {
	
	/*Pagination Handlebars*/
	let pagination = document.querySelector("#pagingTemplate").innerHTML;
	let paginationHandlebar = Handlebars.compile(pagination);
	let paginationHTML = paginationHandlebar(data.pageable);
	
	
	Handlebars.registerHelper("for", function(options) {
		console.log("prev == "+this);
		return true;
	})
	
	Handlebars.registerHelper("checkNext", function(options) {
		console.log("next == "+this);
		return true;
	})
	
	
	let paginationResult = '';
	paginationResult += paginationHTML;
	let pageSpace = document.querySelector("#pagination-space");
	pageSpace.innerHTML = paginationResult;
}

/**
	하루 일정 성공 실패 버튼 클릭 시 비동기 처리
 */
function updateSuccessYn() {
	let successBtnArr = document.querySelectorAll(".successTag");
	for (let i = 0; i < successBtnArr.length; i++) {
		let successBt = successBtnArr[i].firstElementChild;
		let faildBt = successBtnArr[i].lastElementChild;
		let idx = successBtnArr[i].closest("tr").firstElementChild.innerText;
		
		
		successBt.addEventListener("click", function() {
			if(confirm("할일을 완료하였습니까??")) {
				let xhr = new XMLHttpRequest();
				let data = {"successYn" : "Y"
							, "idx" : idx};
				
				xhr.addEventListener("load", function(e) {
					if (xhr.status == 200 && xhr.readyState == 4) {
						callBack("todoSuccess", e.target.responseText, successBtnArr[i]);
					}
				})
				xhr.open("POST","/auth/updateSuccessYn");
				xhr.setRequestHeader("Content-type","application/json");
				xhr.send(JSON.stringify(data));
			}
		});
		
		faildBt.addEventListener("click", function() {
			if(confirm("할일을 정말 포기하시겠습니까??")) {
				let xhr = new XMLHttpRequest();
				let data = {"successYn" : "N", "idx" : idx};
				
				xhr.addEventListener("load", function(e) {
					if (xhr.status == 200 && xhr.readyState == 4) {
						callBack("todoFail", e.target.responseText, successBtnArr[i]);
					}
				})
				xhr.open("POST","/auth/updateSuccessYn");
				xhr.setRequestHeader("Content-type","application/json");
				xhr.send(JSON.stringify(data));
			}
		});			
	}
}

function openCalendar() {
	let calendar = document.querySelector(".calendar");
	calendar.addEventListener("click", ()=>{
		//let calendal = document.querySelector("#calendal").innerText;
		//document.querySelector(".mainHeader").innerHTML += calendal;
		//buildCalendal();
	})
}

function buildCalendal() {
	let date = new Date();
	let cDate = new Date();
	let year = date.getFullYear();
	let month = date.getMonth() + 1 > 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	
	let p = new Date(year, date.getMonth(), 0); // 저번달 마지막날
	let f = new Date(year, date.getMonth(), 1); // 이번달 첫날
	let l = new Date(year, date.getMonth() + 1, 0); // 이번달 마지막날
	
	let dates = [];
	if (f.getDay() != 0) {
		for (let i = 0; i < f.getDay(); i++) {
			dates.unshift(p.getDate() - i);
		}
	}
	
	for (let i = 1; i <= l.getDate(); i++) {
		dates.push(i);
	}
	
	for (let i = 1; i <= 13 - l.getDay(); i++) {
		dates.push(i);
	}
	
	let htmlDates = '';
	for (let i = 0; i < 42; i++) {
		if (date.getDate() == dates[i] && date.getMonth() == cDate.getMonth() && date.getFullYear() == cDate.getFullYear()) {
			htmlDates += `<div class="calendalDate today">${dates[i]}</div>`
		}
		else {
			htmlDates += `<div class="calendalDate">${dates[i]}</div>`
		}
	}
	
	document.querySelector(".calendarDates").innerHTML = htmlDates;
}

/**
파라미터
1. Id
2. Data
 */
function callBack(id, data, target) {
	switch (id) {
		case "todoSuccess" : 
			if (data == "success") {
				target.firstElementChild.setAttribute("class", "btn btn-primary");
				target.firstElementChild.setAttribute("disabled", true);
				target.lastElementChild.setAttribute("disabled", true);
				target.closest("tr").setAttribute("style","background-color:yellow");
				
				document.querySelector("#successTodoCount").value = parseInt(document.querySelector("#successTodoCount").value) + 1;
				SuccessRateInfo();
			}
			else {
				alert("문제가 발생하였습니다.\n잠시 후 다시 시도해주시기 바랍니다..");
			}
		break;
		
		case "todoFail" :
			if (data == "success") {
				target.firstElementChild.setAttribute("disabled", true);
				target.lastElementChild.setAttribute("class", "btn btn-danger");
				target.lastElementChild.setAttribute("disabled", true);
				target.closest("tr").setAttribute("style","background-color:tomato");
			}
			else {
				alert("문제가 발생하였습니다.\n잠시 후 다시 시도해주시기 바랍니다..");
			}
		break;
	}
}

/**
	달성률 서버에서 조회
 */
function SuccessRateInfo () {
	let totalCount = document.querySelector("#totalTodoCount").value;
	let successCount = document.querySelector("#successTodoCount").value;
	
	let rate = 0;
	if (successCount > 0) {
		rate = Math.ceil( (successCount/ totalCount) * 100 );
	}
	document.querySelector("#rateValue").innerText = rate + "%";
}