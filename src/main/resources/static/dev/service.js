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
   * 최적화 일정 조회
   * @param  {[type]}   planHelper [선택한 일정설정이 담겨있는 오브젝트]
   * @param  {Function} callback   [서버로부터 응답받은 값을 Callback 처리]
   * @return {[type]}              [서버로부터 응답받은 값을 json으로 return]
   */
  findAvaiablePlan(planHelper, callback){
    const title = $("input[name=title]").val();
    const color = $("select[name=color]").val();
    const local = $("input[name=local]").val();
    let formData = new FormData();

    formData.append("title", title);
    formData.append("color", color);
    formData.append("limitedDays", planHelper.limitDays);
    formData.append("memberIds", planHelper.members);
    formData.append("local", local);
    formData.append("availableStartTime", planHelper.availableStartTime);
    formData.append("availableEndTime", planHelper.availableEndTime);
    formData.append("availableDaysIndex", planHelper.availableDays);

    this.restfulApi.postAPI("/findPlan", formData, function(result){
      var json = JSON.parse(result);
      callback(json);
    })
  }

  /**
   * 일정 저장
   * @param  {Function} callback [서버로부터 응답받은 값을 Callback 처리]
   */
 submitPlan(callback){
   let formData = new FormData();
   const joinMemberIds = (filterHelper.filter.joinMembers).map(x=>x.member_id);
   const positiveMembersIds =  ((filterHelper.selectFilter).positiveMembers).map(x=>x.member_id);

   console.log("filterHelper : " , filterHelper);

   formData.append("planTitle", filterHelper.filter.planTitle);
   formData.append("color", filterHelper.filter.color);
   formData.append("local", filterHelper.filter.local);
   formData.append("hostId", filterHelper.filter.host.member_id);
   formData.append("joinMemberIds", joinMemberIds);
   formData.append("positiveMemberIds", positiveMembersIds);
   formData.append("selectDate",((filterHelper.selectFilter).date));
   formData.append("startDate", filterHelper.filter.startDate);
   formData.append("endDate", filterHelper.filter.endDate);
   formData.append("filterEndTime", filterHelper.filter.filterEndTime);
   formData.append("filterStartTime", filterHelper.filter.filterStartTime);
   formData.append("result", filterHelper.selectFilter);
   formData.append("availableDaysIndexs", filterHelper.filter.availableDaysIndexs);

   this.restfulApi.postAPI("/plan/submitPlan", formData, function(result){
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


}
