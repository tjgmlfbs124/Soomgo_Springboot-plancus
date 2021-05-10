$(document).ready(function() {

  function dateToTimes(format){
    var hour = format.getHours();
    var min = format.getMinutes();

    if(hour<10) hour = '0' + hour;
    if(min<10) min = '0' + min;

    console.log('hour + ":" + min = ' + hour + ":" + min)
    return hour + ":" + min;
  }


  /** 일정추가 페이지 색상 선택**/
  $("select[name=color]").on("change", function(){
    $("i[name=plan-color]").css("color", $(this).val());
  });

  /** 기간 설정 **/
  $('input:radio[name="period"]').change(function() {
      var period = $(this).data("period");
      planHelper.setLimitedDays(period);
  });

  /** 회의시간  설정 **/
  $('input:radio[name="times"]').change(function() {
      // 가능한 시간 기준에서 선택된 Row를 가져와서, 시작시간을 파악한다. => 시작시간 ~ 회의시간을 표현해주기 위해
      var start = "";
      var end = "";
      var selecTimes = $(this).data("times");
      var $availableTimeRows = $("#availableTimeTable").children().toArray();

      $availableTimeRows.forEach((item, i) => {
        var $rowCheck = $(item).find("input[type='checkbox']");
        if($rowCheck.is(":checked")){
          start = $(item).find(".custom-control-label").data("start");
          end = Number(start.split(":")[0]) + selecTimes + ":" + start.split(":")[1];
        }
      });

      planHelper.setTimes(start, end);
      $("label[name=result-times]").text("(  ~ +" + selecTimes + "시간)");
  });

  /** 참석자 선택 이벤트 **/
  $("#joinMemberTable > tr").on("click",function(event){
    var $rowCheck = $(this).find("input[type='checkbox']");
    var $rowLabel = $(this).find(".custom-control-label");

    $rowCheck.prop('checked', !$rowCheck.prop('checked'));

    var isCheck = $rowCheck.is(":checked");
    var userId = $rowLabel.attr("name").split("_")[1];

    if (isCheck) {
      planHelper.addMember(userId);
      $(this).addClass("selected");
    }
    else{
      planHelper.removeMember(userId);
      $(this).removeClass("selected");
    }
  })

  /** 가능한 시간 기준 선택 이벤트 **/
  $("#availableTimeTable > tr").on("click",function(event){
    var $rows = $("#availableTimeTable").children().toArray();

    $rows.forEach((item, i) => {
      var $rowCheck = $(item).find("input[type='checkbox']");
      $(item).removeClass("selected");
      $rowCheck.prop('checked', false);
    });

    var $selectCheck = $(this).find("input[type='checkbox']");
    $selectCheck.prop('checked', true);
    $(this).addClass("selected");

    planHelper.setTimes(
      ($(this).find(".custom-control-label")).data("start"),
      ($(this).find(".custom-control-label")).data("end")
    )
  });

  /** 가능한 요일 기준 선택 이벤트 **/
  $("#availableDaysTable > tr").on("click",function(event){
    var $rowCheck = $(this).find("input[type='checkbox']");
    var $rowLabel = $(this).find(".custom-control-label");

    $rowCheck.prop('checked', !$rowCheck.prop('checked'));
    var isCheck = $rowCheck.is(":checked");
    var daysIndex = $rowLabel.data("index");

    if (isCheck) {
      planHelper.addAvailableDays(daysIndex);
      $(this).addClass("selected");
    }
    else{
      planHelper.removeAvaliableDays(daysIndex);
      $(this).removeClass("selected");
    }
  });

  /** 종일 토글선택 이벤트 **/
  $('#checkbox-today').on('change',function() {
    if($(this).is(":checked")){
      $("#form-select-times").hide();
    }
    else{
      $("#form-select-times").show();
    }
  });

  /** 가능한 시간 기준 선택 이벤트 **/
  $("#list-priority > tr").on("click",function(event){
    var $rows = $("#list-priority").children().toArray();

    $rows.forEach((item, i) => {
      var $rowCheck = $(item).find("input[type='checkbox']");
      $(item).removeClass("selected");
      $rowCheck.prop('checked', false);
    });

    var $selectCheck = $(this).find("input[type='checkbox']");
    $selectCheck.prop('checked', true);
    $(this).addClass("selected");

  });
});
