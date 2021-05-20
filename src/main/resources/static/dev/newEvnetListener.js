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
    console.log("color : " , $(this).find(':selected').data("color"));
    $("i[name=plan-color]").css("color", $(this).find(':selected').data("color"));
    planHelper.color = $(this).val();

    console.log(planHelper);
  });

  /** 기간 설정 **/
  $('input:radio[name="period"]').change(function() {
      var period = $(this).data("period");
      planHelper.setLimitedDays(period);
  });

  /** 회의시간  설정 **/
  $('input:radio[name="times"]').change(function() {
    const times = $(this).data("times");
    planHelper.setTimes(times);
  });


  /** 장소 검색 **/
  $('input[name="local"]').change(function() {
    var target = $(this).val();
    $("#localSearchList > tbody").empty();

        if(target.length <= 0) return;

        getPois(target, function(result){
            if(!result) return;

            result = result.searchPoiInfo.pois.poi;
            console.log("result : ", result);
            result.forEach((item, i) => {
                var address = item.newAddressList.newAddress.length > 0 ? item.newAddressList.newAddress[0].fullAddressRoad : upperAddrName + " " + middleAddrName + " " + lowerAddrName;
                $("#localSearchList > tbody").append("" +
                    "<tr onclick=\"onLocalClick(this)\">"  +
                        "<td>" +
                            "<div class=\"media align-items-left\">" +
                                "<div class=\"media-body\" style=\"padding:2px;\">" +
                                    "<p class=\"js-lists-values-employee-name\" name=\"local-name\"style=\"margin:0; font-family:'noto_demilight'; font-weight:bold;\">" + item.name + "</p>" +
                                    "<p class=\"js-lists-values-employee-name\" name=\"local-address\" style=\"margin:0; font-family:'noto_litght';\">" +
                                        address +
                                    "</p>" +
                                    "<p name=\"local-gcs\" data-lat=\"" + item.frontLat + "\" data-lon=\"" + item.frontLon + "\" style=\"display:none;\"></p>" +
                                "</div>" +
                            "</div>" +
                        "</td>" +
                        "<td style=\"padding:0.1rem 0.5rem;\">" +
                            "<div class=\"custom-control custom-checkbox\" style=\"float:right;\">" +
                                "<input type=\"checkbox\" class=\"custom-control-input js-check-selected-row\"style=\"border:1px\" />" +
                                "<label class=\"custom-control-label\"><span class=\"text-hide\">Check</span></label>" +
                            "</div>" +
                        "</td>" +
                    "</tr>"
                );
            })

            $("#localSearchList").show();

        });

  })

  /** 장소 검색 **/
  $('button[name="localSearch"]').click(function() {
    var target = $('input[name="local"]').val();

    $("#localSearchList > tbody").empty();

    if(target.length <= 0) return;

    getPois(target, function(result){
        if(!result) return;

        result = result.searchPoiInfo.pois.poi;
        console.log("result : ", result);
        result.forEach((item, i) => {
            var address = item.newAddressList.newAddress.length > 0 ? item.newAddressList.newAddress[0].fullAddressRoad : upperAddrName + " " + middleAddrName + " " + lowerAddrName;
            $("#localSearchList > tbody").append("" +
                "<tr onclick=\"onLocalClick(this)\">"  +
                    "<td>" +
                        "<div class=\"media align-items-left\">" +
                            "<div class=\"media-body\" style=\"padding:2px;\">" +
                                "<p class=\"js-lists-values-employee-name\" name=\"local-name\"style=\"margin:0; font-family:'noto_demilight'; font-weight:bold;\">" + item.name + "</p>" +
                                "<p class=\"js-lists-values-employee-name\" name=\"local-address\" style=\"margin:0; font-family:'noto_litght';\">" +
                                    address +
                                "</p>" +
                                "<p name=\"local-gcs\" data-lat=\"" + item.frontLat + "\" data-lon=\"" + item.frontLon + "\" style=\"display:none;\"></p>" +
                            "</div>" +
                        "</div>" +
                    "</td>" +
                    "<td style=\"padding:0.1rem 0.5rem;\">" +
                        "<div class=\"custom-control custom-checkbox\" style=\"float:right;\">" +
                            "<input type=\"checkbox\" class=\"custom-control-input js-check-selected-row\"style=\"border:1px\" />" +
                            "<label class=\"custom-control-label\"><span class=\"text-hide\">Check</span></label>" +
                        "</div>" +
                    "</td>" +
                "</tr>"
            );
        })

        $("#localSearchList").show();

    });
  })

  /** 가능한 시간 기준 선택 이벤트 **/
  $("#localSearchList > tbody > tr").on("click",function(event){
    console.log("click!!");
    var $rows = $("#localSearchList > tbody > tr").children().toArray();

    var $selectCheck = $(this).find("input[type='checkbox']");
    $selectCheck.prop('checked', true);
    $(this).addClass("selected");

    $("#localSearchList").hide();

  });

  /** 참석자 선택 이벤트 **/
  $("#joinMemberTable").change(function(){
      planHelper.members = $(this).val();
      console.log("planHelper : " , planHelper);
  });

  /** 추가설정 선택 이벤트 **/
  $('input:radio[name="option"]').change(function() {
      var option = $(this).data("option");
      switch(option){
        case "todoList" :
          $("#div-todoList").show();
          break;
        case "note" :
          $("#div-todoList").hide();
          break;
        case "link" :
          $("#div-todoList").hide();
          break;
      }
  });

  /** 가능한 시간 기준 선택 이벤트 **/
  $("#availableTimeTable > tr").on("click",function(event){
    var $rowCheck = $(this).find("input[type='checkbox']");
    var $rowLabel = $(this).find(".custom-control-label");

    $rowCheck.prop('checked', !$rowCheck.prop('checked'));
    var isCheck = $rowCheck.is(":checked");
    var daysIndex = $rowLabel.data("index");

    if (isCheck) {
      planHelper.addAvailableTimes(
        ($(this).find(".custom-control-label")).data("start"),
        ($(this).find(".custom-control-label")).data("end"));
      $(this).addClass("selected");
    }

    else{
      planHelper.removeAvailableTimes(
        ($(this).find(".custom-control-label")).data("start"),
        ($(this).find(".custom-control-label")).data("end"));
      $(this).removeClass("selected");
    }
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
