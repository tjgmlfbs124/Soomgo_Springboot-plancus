class RestfulApi{
	constructor(){

	}

	postAPI(url, data, successCallback){
		  $.ajax({
		      type : 'POST',
		      url : url,
		      dataType : 'text',
		      data : data,
		      contentType : false,
		      processData: false,
		      success : function(result) {
		        successCallback(result);
		      },
		      error: function(request, status, error) {
		        console.log("Error post Api state : " + status);
		        console.log("Error post Api error : " + error);
		      }
		  }) // End Ajax Request
	}

	postApiToJson(url, data, successCallback){
		  $.ajax({
		      type : 'POST',
		      url : url,
		      dataType : 'text',
		      data : JSON.stringify(data),
		      contentType : 'application/json',
		      processData: false,
		      success : function(result) {
		        successCallback(result);
		      },
		      error: function(request, status, error) {
		        console.log("Error post Api state : " + status);
		        console.log("Error post Api error : " + error);
		      }
		  }) // End Ajax Request
	}

	getAPI(url, data, successCallback){
		  $.ajax({
		      type : 'GET',
		      url : url,
		      dataType : 'text',
		      data : data,
		      contentType : false,
		      processData: false,
		      success : function(result) {
		        successCallback(result);
		      },
		      error: function(request, status, error) {
		        console.log("Error post Api state : " + status);
		        console.log("Error post Api error : " + error);
		      }
		  }) // End Ajax Request
	}

	getApiToJson(url, data, successCallback){
		  $.ajax({
		      type : 'GET',
		      url : url,
		      dataType : 'text',
		      data : data,
		      contentType : 'application/json',
		      processData: false,
		      success : function(result) {
		        successCallback(result);
		      },
		      error: function(request, status, error) {
		        console.log("Error post Api state : " + status);
		        console.log("Error post Api error : " + error);
		      }
		  }) // End Ajax Request
	}
}
