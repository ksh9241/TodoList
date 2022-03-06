document.addEventListener("DOMContentLoaded", function() {
	openCalendar();
	newTodoList();
	findAllTodoList(0);
})

/**
	할일 성공여부 체크
 */
function todoStyleSetting() {
	let successBtnArr = document.querySelectorAll(".successTag");
	
	for (let i = 0; i < successBtnArr.length; i++) {
		let value = successBtnArr[i].getAttribute("value");
		let tagB = document.createElement("b");
		switch (value) {
			case "Y" :
				successBtnArr[i].firstElementChild.setAttribute("class", "btn btn-primary");
				successBtnArr[i].firstElementChild.setAttribute("disabled", true);
				successBtnArr[i].lastElementChild.setAttribute("disabled", true);
				tagB.innerText = "성공";
				tagB.style = "color:darkseagreen";
				successBtnArr[i].closest("tr").firstElementChild.insertAdjacentElement("beforeend", tagB);
			break;
			case "N" :
				successBtnArr[i].firstElementChild.setAttribute("disabled", true);
				successBtnArr[i].lastElementChild.setAttribute("class", "btn btn-danger");
				successBtnArr[i].lastElementChild.setAttribute("disabled", true);
				tagB.innerText = "실패";
				tagB.style = "color:darkred";
				successBtnArr[i].closest("tr").firstElementChild.insertAdjacentElement("beforeend", tagB);
		}
		
	}
}

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
	let findDate = dateSetting();
	console.log(findDate);
	
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
			
			todoStyleSetting();
			
			// 페이징 클릭 이벤트 (버튼 클릭 시 this.Method를 호출해서 callbackHell 위험이 없을까 걱정인데 현재는 이상없음.)
			pageClickEvt();
		}
	})
	
	xhr.open("GET", "/auth/"+userIdx+"?page="+pageNum+"&findDate="+findDate);
	xhr.send();
}

/**
	달력 데이터 처리하는 메서드
	디폴트값 input에 담아주거나 달력으로 input값 변경하면 날짜 반환해줌.
 */
function dateSetting() {
	let findDate = document.querySelector("#findDate").value;
	
	if (findDate.length == 0) {
		let date = new Date();
		let year = date.getFullYear();
		let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		let result = year+month+day;
		findDate = result;
	}
	
	return findDate;
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

/* 페이징 수정 해야됨. 개같이 만들고있음.. */
function replacePageHandlebars(data) {
	console.log(data);
	
	let endNum = Math.ceil( (data.number + 1) / data.pageable.pageSize ) * data.pageable.pageSize;
	let startNum = endNum + 1 - data.pageable.pageSize;
	
	let lastPage = Math.ceil(data.totalElements / data.pageable.pageSize);
	endNum = endNum > lastPage ? lastPage : endNum; 
	
	/*Pagination Handlebars*/
	let str = '';
	
	str += '<ul class="pagination">';
	
	 
	if (startNum > 1) {
		str += `<li class="page-item" value=${startNum - 2}>
			      <a class="page-link" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>`
	}
	
	for (let i = startNum; i <= endNum; i++) {
		if (i == data.number + 1) {
			str += `<li class="page-item" value=${i-1}><a class="page-link check">${i}</a></li>`
			continue;
		}
		str += `<li class="page-item" value=${i-1}><a class="page-link">${i}</a></li>`
	}
	
	if (endNum < lastPage) {
		str += `<li class="page-item" value=${endNum}>
			      <a class="page-link" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>`
	}
	
	str += '</ul>'
	
	let pageSpace = document.querySelector("#pagination-space");
	pageSpace.innerHTML = str;
}

function pageClickEvt() {
	let li = document.querySelectorAll(".pagination li");
	for (let i = 0; i < li.length; i++) {
		li[i].addEventListener("click", function(){
			findAllTodoList(this.value);
		})
	}
}

/**
	하루 일정 성공 실패 버튼 클릭 시 비동기 처리
 */
function updateSuccessYn() {
	let successBtnArr = document.querySelectorAll(".successTag");
	for (let i = 0; i < successBtnArr.length; i++) {
		let successBt = successBtnArr[i].firstElementChild;
		let faildBt = successBtnArr[i].lastElementChild;
		let idx = successBtnArr[i].closest("tr").firstElementChild.getAttribute("value");
		
		
		successBt.addEventListener("click", function() {
			if(confirm("할일을 완료하였습니까??")) {
				let xhr = new XMLHttpRequest();
				let data = {"successYn" : "Y", "idx" : idx};
				
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
		if (document.querySelector("#calendal-span").style.display == "block") {
			document.querySelector("#calendal-span").style.display = "none";
		} 
		else {
			let calendal = document.querySelector("#calendal").innerText;
	
			let calendalSpan = document.querySelector("#calendal-span")
			calendalSpan.innerHTML = calendal;
			calendalSpan.style.display = "block";
			buildCalendal();
			
			findTodoByCalendal();
		}
	})
}

function buildCalendal() {
	
	let date = new Date();
	let year = date.getFullYear();
	let month = date.getMonth() + 1 > 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	
	document.querySelector(".yearMonthTitle").innerText = year + "." + month;
	
	
	let p = new Date(year, date.getMonth(), 0); // 저번달 마지막날
	let f = new Date(year, date.getMonth(), 1); // 이번달 첫날
	let l = new Date(year, date.getMonth() + 1, 0); // 이번달 마지막날
	
	let dates = [];
	if (f.getDay() != 0) {
		for (let i = 0; i < f.getDay(); i++) {
			let y = p.getFullYear();
			let m = p.getMonth() > 10 ? (date.getMonth()) : "0" + (date.getMonth());
			let d = p.getDate() - i; 
			dates.unshift(y+m+d);
		}
	}
	for (let i = 1; i <= l.getDate(); i++) {
		let d = i < 10 ? "0"+i : i;
		dates.push(year + month + d);
	}
	
	let nextMonth = new Date(date.getFullYear(), date.getMonth() + 2);
	for (let i = 1; i <= 13 - l.getDay(); i++) {
		let m = nextMonth.getMonth() > 10 ? nextMonth.getMonth() : "0" + nextMonth.getMonth();
		let d = i < 10 ? "0"+i : i;
		dates.push(nextMonth.getFullYear() + m + d);
	}
	
	let htmlDates = '';
	for (let i = 0; i < 42; i++) {
		let calendalDay = "";
		if (dates[i].charAt(6) == "0") calendalDay = dates[i].substring(7);
		else calendalDay = dates[i].substring(6);
		if (dateSetting() == dates[i]) {
			htmlDates += `<div class="calendalDate today" value="${dates[i]}">${calendalDay}</div>`
		}
		else {
			htmlDates += `<div class="calendalDate" value="${dates[i]}">${calendalDay}</div>`
		}
	}
	
	document.querySelector(".calendarDates").innerHTML = htmlDates;
}

function findTodoByCalendal() {
	let calendal = document.querySelectorAll(".calendalDate");
	
	for (let i = 0; i < calendal.length; i++) {
		calendal[i].addEventListener("click", function() {
			// CSS 속성 값 변경
			for (let j = 0; j < calendal.length; j++) {
				if(calendal[j].getAttribute("class").indexOf("today") > 0) {
					calendal[j].setAttribute("class", "calendalDate");
					break;
				}
			}
			this.setAttribute("class", "calendalDate today");
			// CSS 속성 값 변경 END
			
			// 클릭한 날짜로 변경
			let date = this.getAttribute("value");
			document.querySelector("#findDate").setAttribute("value", date);
			
			// 달력 숨기기
			document.querySelector("#calendal-span").style.display = "none";
			
			findAllTodoList(0);
			
		})
	}
}

/**
파라미터
1. Id
2. Data
 */
function callBack(id, data, target) {
	let tagB = document.createElement("b");
	switch (id) {
		case "todoSuccess" : 
			if (data == "success") {
				target.firstElementChild.setAttribute("class", "btn btn-primary");
				target.firstElementChild.setAttribute("disabled", true);
				target.lastElementChild.setAttribute("disabled", true);
				
				tagB.innerText = "성공";
				tagB.style = "color:darkseagreen";
				target.closest("tr").firstElementChild.insertAdjacentElement("beforeend", tagB);
				
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
				
				tagB.innerText = "실패";
				tagB.style = "color:darkred";
				target.closest("tr").firstElementChild.insertAdjacentElement("beforeend", tagB);
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