document.addEventListener("DOMContentLoaded", function() {
	openCalendar();
	newTodoList();
	findAllTodoList();
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
		}
	})
	
	xhr.open("GET", "/auth/"+userIdx+"?page="+pageNum);
	xhr.send();
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