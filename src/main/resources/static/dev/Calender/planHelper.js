class PlanHelper{
  constructor(){
      this.title = "", // 제목
      this.color = "", // 색상

      this.limitDays = this.setLimitedDays("days"), // 기간 설정
      this.local = "", // 장소
      this.members = new Array(), // 참여인원 설정

      this.availableStartTime = "09:00", // 희망하는 일정 시작시간
      this.availableEndTime = "12:00", // 희망하는 일정 종료시간

      this.availableDays = [1,2,3,4,5,6,7],
      this.filterResult = null
  }

  /** 기간설정 메소드 **/
  setLimitedDays(value){
    var today = new Date();
    switch (value) {
      case "days":
        this.limitDays = this.date_to_str(new Date(today.setDate(today.getDate() + 7)));
        break;
      case "weeks":
        this.limitDays = this.date_to_str(this.getSunday(today));
        break;
      case "month":
        this.limitDays = this.date_to_str(new Date(today.getFullYear(), today.getMonth()+1,0));
        break;
      default:
        this.limitDays = this.date_to_str(value);
        break;
    }

    $("label[name=result-period]").text("( ~" + this.date_to_str2(new Date(this.limitDays)) + ")");
    $("input[name=input-select-period]").val("~" + this.date_to_str2(new Date(this.limitDays)));
    this.consoleHelper("setLimitedDays");
  }

  /** 참여 멤버 추가 **/
  addMember(memberId){
    if((this.members).indexOf(memberId) < 0)
      (this.members).push(memberId);

    this.consoleHelper("addMember");
  }

  /** 참여 멤버 삭제 **/
  removeMember(memberId){
    if((this.members).indexOf(memberId) > 0)
      this.members = (this.members).filter(item => item !== memberId)

    this.consoleHelper("removeMember");
  }

  /** 가능한 시간 설정 **/
  setTimes(start, end){
    $("label[name=result-times]").text("( " + start +" ~ " + end + " )");

    this.availableStartTime = start;
    this.availableEndTime = end;

    this.consoleHelper("setTimes");
  }

  addAvailableDays(index){
    if((this.availableDays).indexOf(index) < 0){
      (this.availableDays).push(index);
    }

    this.consoleHelper("addAvailableDays");
  }

  removeAvaliableDays(index){
    if((this.availableDays).indexOf(index) > 0){
      this.availableDays = (this.availableDays)
                            .filter(item => item !== index)
                            .sort(function(a,b){
                              return a-b;
                            })
    }

    this.consoleHelper("removeAvaliableDays");
  }
  
  /** 이번주 일요일을 찾는 함수 **/
  getSunday(now){
    var nowDayOfWeek = now.getDay();
    var nowDay = now.getDate();
    var nowMonth = now.getMonth();
    var nowYear = now.getYear();
    nowYear += (nowYear < 2000) ? 1900 : 0;

    var weekEndSunday = new Date(nowYear, nowMonth, nowDay + (7 - nowDayOfWeek));
    return weekEndSunday;
  }

  date_to_str(format){
    var year = format.getFullYear();
    var month = format.getMonth() + 1;
    var date = format.getDate();
    var hour = format.getHours();
    var min = format.getMinutes();
    var sec = format.getSeconds();

    if(month<10) month = '0' + month;
    if(date<10) date = '0' + date;
    if(hour<10) hour = '0' + hour;
    if(min<10) min = '0' + min;
    if(sec<10) sec = '0' + sec;

    return year + "-" + month + "-" + date + "T" + hour + ":" + min + ":" + sec;
  }

  date_to_str2(format){
      var year = format.getFullYear();
      var month = format.getMonth() + 1;
      var date = format.getDate();

      if(month<10) month = '0' + month;
      if(date<10) date = '0' + date;
      return year + "-" + month + "-" + date;
  }

  consoleHelper(methodName){
    console.log("[" + methodName + "]")
    console.log(this);
  }

}
