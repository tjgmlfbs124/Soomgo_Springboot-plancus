function getPois(inputKeyword, callback) {
	var searchKeyword = inputKeyword; // 검색 키워드
	$.ajax({
		method: "GET", // 요청 방식
		url: "https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result", // url 주소
		async: false, // 동기설정
		data: { // 요청 데이터 정보
			"appKey": "l7xxf1ae5e123bf94ba0a636baaf24928eac", // 발급받은 Appkey
			"searchKeyword": searchKeyword, // 검색 키워드
			"resCoordType": "EPSG3857", // 요청 좌표계
			"reqCoordType": "WGS84GEO", // 응답 좌표계
			"count": 10 // 가져올 갯수
		},
		success: function (response) {
//			console.log("response : ", response);
			callback(response);

		},
		error: function (request, status, error) {
			console.log("code:" +
				request.status + "\n" +
				"message:" +
				request.responseText +
				"\n" + "error:" + error);
		}
	});
}