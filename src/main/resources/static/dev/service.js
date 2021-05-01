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
}
