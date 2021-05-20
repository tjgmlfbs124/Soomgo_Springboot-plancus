class Service{
  constructor(){
    this.restfulApi = new RestfulApi();
  }

  /**
   * 회원가입 서비스
   * @param  formData [id, pw, name, role]
   * @param  callback [응답받은뒤 callback Function 전달]
   */
  signup(formData, callback){
    this.restfulApi.postAPI("/signup",formData, function(result){
      var json = JSON.parse(result);
      callback(json);
    })
  }

  /**
   * 로그인 서비스
   * @param  formData [id, pw]
   * @param  callback [응답받은뒤 callback Function 전달]
   */
  signin(formData, callback){
    this.restfulApi.postAPI("/signin", formData, function(result){
      var json = JSON.parse(result);
      callback(json);
    })
  }

  /**
   * 참석자들에게 일정 공유신청
   * @param  {[type]}   planHelper [선택한 일정설정이 담겨있는 오브젝트]
   * @param  {Function} callback   [서버로부터 응답받은 값을 Callback 처리]
   */
  requestPlanToMember(planHelper, todoList, callback){
    const title = $("input[name=title]").val();
    const color = $("select[name=color]").val();
    let formData = new FormData();

    formData.append("title", title);
    formData.append("color", color);
    formData.append("limitedDays", planHelper.limitDays);
    formData.append("memberIds", planHelper.members);
    formData.append("mapName", planHelper.map.name);
    formData.append("mapAddress", planHelper.map.address);
    formData.append("mapLon", planHelper.map.lon);
    formData.append("mapLat", planHelper.map.lat);
    formData.append("useTimes", planHelper.useTimes);
    formData.append("todoList", todoList);

    this.restfulApi.postAPI("/plan/request", formData, function(result){
      var json = JSON.parse(result);
      callback(json);
    })
  }
   /**
    * 개인 일정 추가
    * @param  {String}      title           일정 제목
    * @param  {String}      color           일정 태그
    * @param  {String}      local           일정 장소
    * @param  {new Date()}  startDate       일정 시작시간
    * @param  {new Date()}  endDate         일정 종료시간
    * @param  {Array}       todoList        투두리스트
    * @param  {Function}    callback        [서버로부터 응답받은 값을 Callback 처리]
    */
  addPlan(title, color,  local, startDate, endDate, todoList, callback){
    let formData = new FormData();

    formData.append("title", title);
    formData.append("color", color);
    formData.append("mapName", planHelper.map.name);
    formData.append("mapAddress", planHelper.map.address);
    formData.append("mapLon", planHelper.map.lon);
    formData.append("mapLat", planHelper.map.lat);
    formData.append("startTime", startDate);
    formData.append("endTime", endDate);
    formData.append("todoList", todoList);

    this.restfulApi.postAPI("/plan/add", formData, function(result){
      var json = JSON.parse(result);
      callback(json);
    });
  }

  /**
   * detail에 대한 상세정보 요청
   * @param  {[type]}   id       head Id
   * @param  {Function} callback   [서버로부터 응답받은 값을 Callback 처리]
   */
  getDetail(id, callback){
    this.restfulApi.getAPI("/plan/detailToJson?id=" + id, null, function(result){
      var json = JSON.parse(result);
      callback(json);
    })
  }

  /**
   * 신청온 일정에 대한 시간설정
   * @param  {[type]}   planHelper [선택한 일정설정이 담겨있는 오브젝트]
   * @param  {Function} callback   [서버로부터 응답받은 값을 Callback 처리]
   */
  joinPlan(headId, planHelper, callback){
    var obj = {
        'headId' : headId,
        'availableTimes' : planHelper.availableTimes
    }

    this.restfulApi.postApiToJson("/plan/join", obj, function(result){
      var json = JSON.parse(result);
      callback(json);
    })

  }

  /**
   * 최적화 일정 조회
   * @param  {[type]}   planHelper [선택한 일정설정이 담겨있는 오브젝트]
   * @param  {Function} callback   [서버로부터 응답받은 값을 Callback 처리]
   * @return {[type]}              [서버로부터 응답받은 값을 json으로 return]
   */
   findAvaiablePlan(id, callback){
     this.restfulApi.getAPI("/plan/confirm/find?id=" + id, null, function(result){
       var json = JSON.parse(result);
       callback(json);
     })
   }

  /**
   * 일정 저장
   * @param  {Function} callback [서버로부터 응답받은 값을 Callback 처리]
   */
 confirmPlan(headId, filterHelper, callback){
   let formData = new FormData();
   formData.append("headId", headId);
   formData.append("startTime", filterHelper.selectStartDate);
   formData.append("endTime", filterHelper.selectEndDate);

   this.restfulApi.postAPI("/plan/confirm", formData, function(result){
     var json = JSON.parse(result);
     callback(json);
   });
 }

 findMemberPlan(callback){
    this.restfulApi.getAPI("/plan/month/all", null, function(result){
      var json = JSON.parse(result);
      callback(json);
    });
 }

/**
 * 로그인한 멤버의 정해진 기간안에 각 태그별 통계를 요청
 * @param  {[type]}   startDate [검색 시작날짜]
 * @param  {[type]}   endDate   [검색 종료날짜]
 * @param  {Function} callback  [서버로부터 응답받은 값을 Callback 처리]
 * @return {[type]}             [description]
 */
 findTagChartDataByMember(startDate, endDate, callback){
    this.restfulApi.getAPI("/plan/tags?startDate="+startDate+"&endDate="+endDate, null, function(result){
      var json = JSON.parse(result);
      callback(json);
    });
 }

 updateTodoList(planId, todolist, callback){
    let formData = new FormData();
    formData.append("planId", planId);
    formData.append("todoList", todolist);

    this.restfulApi.postAPI("/todoList/update", formData, function(result){
      var json = JSON.parse(result);
      callback(json);
    });
 }
}
