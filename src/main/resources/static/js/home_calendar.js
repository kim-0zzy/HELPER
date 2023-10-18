var rightDiv = document.getElementById("div.right");

// 달력 생성 함수
function createCalendar() {
  // 현재 날짜 가져오기
  var today = new Date();

  // 년도와 월 가져오기
  var year = today.getFullYear();
  var month = today.getMonth() + 1;

   // 달력 표시할 위치 선택 및 초기화
   var calendarDiv = document.getElementById("calendar");
   calendarDiv.innerHTML = "";

   // 년도와 월 표시하기
   var title = document.createElement("h2");
   title.textContent = year + "년 " + month + "월";
   calendarDiv.appendChild(title);

   // 요일 표시하기 (일 ~ 토)
   var daysOfWeek = ["일", "월", "화", "수", "목", "금", "토"];
   
   var dayOfWeekGrid = document.createElement("div");
   dayOfWeekGrid.classList.add("calendar-grid");

    for (var i = 0; i < daysOfWeek.length; i++) {
      var dayOfWeekElement = document.createElement("div");
      dayOfWeekElement.classList.add("calendar-cell");
      dayOfWeekElement.textContent = daysOfWeek[i];
      dayOfWeekGrid.appendChild(dayOfWeekElement);
     }

    calendarDiv.appendChild(dayOfWeekGrid);

    // 첫 번째 날짜의 요일 구하기 (0: 일요일, ..., 6: 토요일)
    var firstDayOfMonthIndex= new Date(year, month -1,1).getDay();

    // 달력 그리기 (칸으로 구성)
    var daysInMonth= new Date(year, month,0).getDate(); 

    var calendarGrid= document.createElement("div");
    calendarGrid.classList.add('calendar-grid');

     for(var i=0; i<firstDayOfMonthIndex; i++){ 
       let emptyCell=document.createElement('div'); 
       emptyCell.classList.add('calendar-cell'); 
       calendarGrid.appendChild(emptyCell); 
     }

     for(var i=1; i<=daysInMonth; i++){ 
       let cell=document.createElement('div'); 
       cell.classList.add('calendar-cell'); 
       cell.textContent=i.toString();  
       
        if(i===today.getDate() && month === today.getMonth()+1 && year === today.getFullYear()){
          cell.classList.add('today');
        }
       
        calendarGrid.appendChild(cell);  
     } 

     calendarDiv.appendChild(calendarGrid);   
}

// 페이지 로드 시 달력 생성 함수 호출하기
window.onload=createCalendar;

function divBtn() {
  location.href="/templates/members/calendar.html"
}

function divBtn1() {
  location.href="heal_pan.html"
}