class FilterHelper{
  constructor(){
    this.filter = null;
    this.selectFilter = null;
  }

  showResult(filter, maxLength){
    $("#list-priority").empty();
    $("#list-conflict").empty();

    console.log("filter : ", filter);

    this.filter = filter;

    var result = filter.result;

    result.sort(function (a, b) {

      if (a.positiveCnt < b.positiveCnt) {  return 1;  }
      if (a.positiveCnt > b.positiveCnt) {  return -1; }
      // a must be equal to b
      return 0;
    });

    for (var i = 0; i < maxLength; i++) {

      $("#list-priority").append(""+
        "<tr onclick=\"filterHelper.onClick(this);\" data-index=\"" + i + "\">" +
        " <td>" +
        "   <div class=\"media align-items-left\">" +
        "     <div class=\"media-body\">" +
        "       <span class=\"js-lists-values-employee-name\">" + (i + 1) + "</span>" +
        "     </div>" +
        "   </div>" +
        " </td>" +
        " <td>" +
        "   <div class=\"media align-items-left\">" +
        "     <div class=\"media-body\">" +
        "       <p class=\"js-lists-values-employee-name\" style=\"margin:0\"> " + result[i]['date'] + " " + this.date_to_days(result[i]['date']) + " </p>" +
        "       <p class=\"js-lists-values-employee-name\" style=\"margin:0\"> " + this.date_to_time(filter.filterStartTime) + " ~ " + this.date_to_time(filter.filterEndTime) +  " </p>" +
        "     </div>" +
        "   </div>" +
        " </td>" +
        " <td style=\"padding:0.1rem 0.5rem;\">" +
        "   <div class=\"custom-control custom-checkbox\" style=\"float:right;\">" +
        "     <input type=\"checkbox\" class=\"custom-control-input js-check-selected-row\" id=\"customCheck" + i + "\" style=\"border:1px\">" +
        "     <label class=\"custom-control-label\" for=\"customCheck" + i + "\"><span class=\"text-hide\">Check</span></label>" +
        "     </div>" +
        "   </div>" +
        " </td>" +
        "</tr>"
      );

      var conflictHTML = "" +
          "<tr data-index=\"list-conflict-" + i + "\">" +
          " <td>" +
          "   <div class=\"media align-items-left\">" +
          "     <div class=\"media-body\">" +
          "       <span class=\"js-lists-values-employee-name\">" + result[i]['date'] + " " + this.date_to_days(result[i]['date']) + "</span>" +
          "     </div>" +
          "   </div>" +
          " </td>" +
          " <td>";

      (result[i].negativeMembers).forEach((item, i) => {
        conflictHTML += "" +
          "<div class=\"media align-items-center\" style=\"float:left;\">" +
          " <div class=\"avatar avatar-xs mr-2\">" +
          "   <span class=\"avatar-title rounded-circle\">" + item.member_initial + "</span>" +
          " </div>" +
          "</div>";
      });

      conflictHTML += "</td></tr>";

      $("#list-conflict").append(conflictHTML);
    }
  }

  onClick(el){
    var $el = $(el);
    var idx = $el.data("index");
    var $rows = $(el).parent().children().toArray();

    $rows.forEach((item, i) => {
      var $rowCheck = $(item).find("input[type='checkbox']");
      $(item).removeClass("selected");

      $rowCheck.prop('checked', false);
    });

    var $selectCheck = $el.find("input[type='checkbox']");
    $selectCheck.prop('checked', true);
    $el.addClass("selected");

    this.selectFilter = this.filter.result[idx];

    console.log("filter : ", this.filter);
    console.log("selecFilter : " , this.selectFilter);

    var msg1 = this.date_to_date(new Date(this.filter.result[idx].date + "T00:00:00" ));
    var msg2 = this.date_to_time(this.filter.filterStartTime) + " ~ " + this.date_to_time(this.filter.filterEndTime);
    this.setModalMessage(msg1, msg2);
  }

  setModalMessage(msg1, msg2){
    $("#modal-confirm-date").text(msg1);
    $("#modal-confirm-time").text(msg2);
  }

  showMore(){
    $("#list-priority").empty();
    $("#list-conflict").empty();

    let toggle = $("#label-show-toggle").data("toggle");
    $("#label-show-toggle").data("toggle", !toggle);

    if($("#label-show-toggle").data("toggle") == true){
      $("#label-show-toggle").text("간략히보기")
      this.showResult(this.filter, this.filter.result.length);
    }
    else{
      $("#label-show-toggle").text("전체보기")
      const maxLength = this.filter.result.length > 5 ? 5 : this.filter.result.length;
      this.showResult(this.filter, maxLength);
    }
  }

  date_to_date(format){
    var year = format.getFullYear();
    var month = format.getMonth() + 1;
    var date = format.getDate();
    var hour = format.getHours();
    var min = format.getMinutes();

    if(month<10) month = '0' + month;
    if(date<10) date = '0' + date;
    if(hour<10) hour = '0' + hour;
    if(min<10) min = '0' + min;

    console.log("format.getDay() : " , format.getDay());

    return year + "년 " + month + "월 " + date + "일 " + this.days_to_word(format.getDay());
  }

  date_to_time(date){
    var format = new Date(date);
    var hour = format.getHours();
    var min = format.getMinutes();

    if(hour<10) hour = '0' + hour;
    if(min<10) min = '0' + min;

    return hour + ":" + min;
  }

  date_to_days(date){
    var days = new Date(date+"T00:00:00");

    return this.days_to_word(days.getDay());
  }

  days_to_word(days){
    var words = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];

    return words[days];

  }

}
