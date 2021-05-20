class PlanHelper{
  constructor(){
      this.title = "", // 제목
      this.color = "", // 색상
      this.allMembers = new Array(), // 선택할수 있는 전체 인원

      this.limitDays = this.setLimitedDays("days"), // 기간 설정
      this.map = {}, // 장소
      this.members = new Array(), // 참여인원 설정

      this.useTimes = "01:00", // 희망하는 일정 시작시간

      this.availableTimes = new Array()
  }
  /** 선택할수 있는 전체 인원 초기화 **/
  setAllMembers(members){
    this.allMembers = members;

    console.log("this : " , this);
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

  setMap(map){
    this.map = map;

    $("input[name='local']").val("[" + map.name + "] " + map.address);
    $("input[name='local']").attr("data-lon", map.lon);
    $("input[name='local']").attr("data-lat", map.lat);
    $("input[name='local']").attr("data-address", map.address);

    this.consoleHelper("setMap");
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

  /** 회의 시간 **/
  setTimes(times){
    const hours = Number(times.split(":")[0]) + "시간";
    const minute = Number(times.split(":")[1]) == 0 ? "" : Number(times.split(":")[1]) + "분";

    $("label[name=result-times]").text("( " + hours + minute + " )");

    this.useTimes = times;

    this.consoleHelper("setTimes");
  }

  /** 가능한 시간 추가**/
  addAvailableTimes(start, end){
    const time = {
      'start' : start,
      'end' : end
    };

    if((this.availableTimes).indexOf(time) < 0)
        (this.availableTimes).push(time);


    this.consoleHelper("addAvailableTimes");
  }

  /** 가능한 시간 삭제**/
  removeAvailableTimes(start, end){
    var time = {
      'start' : start,
      'end' : end
    };
    (this.availableTimes).forEach((item, i) => {
      if (item.start == time.start && item.end == time.end) {
        (this.availableTimes).splice(i,1);
      }
    });

    this.consoleHelper("removeAvailableTimes");
  }

  // addAvailableDays(index){
  //   if((this.availableDays).indexOf(index) < 0){
  //     (this.availableDays).push(index);
  //   }
  //
  //   this.consoleHelper("addAvailableDays");
  // }
  //
  // removeAvaliableDays(index){
  //   if((this.availableDays).indexOf(index) > 0){
  //     this.availableDays = (this.availableDays)
  //                           .filter(item => item !== index)
  //                           .sort(function(a,b){
  //                             return a-b;
  //                           })
  //   }
  //
  //   this.consoleHelper("removeAvaliableDays");
  // }

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
