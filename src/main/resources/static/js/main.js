document.addEventListener("DOMContentLoaded", function() {
	openCalendar();
	newTodoList();
	findAllTodoList();
	//updateSuccessYn();		// 버튼 클릭 시 idx값 서버로 던져서 update 이후 disabled 처리
})

function newTodoList() {
	let todoBtn = document.querySelector("#newTodo");
	todoBtn.addEventListener("click" , function(){
		window.open("addTodoPop", "New Todo", "top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no");
	})
}

function findAllTodoList() {
	let userIdx = document.querySelector("#userIdx").value;
	let pageNum = 0; // 클릭 시 처리할 예정
	
	let xhr = new XMLHttpRequest();
	xhr.addEventListener("load", function(e) {
		if (xhr.status == 200 & xhr.readyState == 4) {
			let data = JSON.parse(e.target.responseText);
			
			replaceHandlebars(data);
			replacePageHandlebars(data);
			
			updateSuccessYn();
		}
	})
	
	xhr.open("GET", "/auth/"+userIdx+"?page="+pageNum);
	xhr.send();
}

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
				
				xhr.addEventListener("load", function() {
					if (xhr.status == 200 && xhr.readyState == 4) {
						successBt.setAttribute("class", "btn btn-primary");
						successBt.setAttribute("disabled", true);
						faildBt.setAttribute("disabled", true);
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
				
				xhr.addEventListener("load", function() {
					if (xhr.status == 200 && xhr.readyState == 4) {
						successBt.setAttribute("disabled", true);
						faildBt.setAttribute("class", "btn btn-danger");
						faildBt.setAttribute("disabled", true);
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

/*
$(function() {
       //input을 datepicker로 선언
       $("#datepicker").datepicker({
           dateFormat: 'yy-mm-dd' //달력 날짜 형태
           ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
           ,showMonthAfterYear:true // 월- 년 순서가아닌 년도 - 월 순서
           ,changeYear: true //option값 년 선택 가능
           ,changeMonth: true //option값  월 선택 가능                
           ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
           ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
           ,buttonImageOnly: true //버튼 이미지만 깔끔하게 보이게함
           ,buttonText: "선택" //버튼 호버 텍스트              
           ,yearSuffix: "년" //달력의 년도 부분 뒤 텍스트
           ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
           ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip
           ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 텍스트
           ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 Tooltip
           ,minDate: "-5Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
           ,maxDate: "+5y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)  
       });                    
       
       //초기값을 오늘 날짜로 설정해줘야 합니다.
       $('#datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)            
   });
*/