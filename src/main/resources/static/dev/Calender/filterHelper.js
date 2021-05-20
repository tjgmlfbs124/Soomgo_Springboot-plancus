class FilterHelper {
	constructor() {
		this.resultArray = new Array()
		this.resultFormatArrayCnt = 0;
		this.selectStartDate = null;
		this.selectEndDate = null;
	}
	getUniqueObjectArray(array, key) {
       return array.filter((item, i) => {
         return array.findIndex((item2, j) => {
           return item.key === item2.key;
         }) !== i;
       });
     }


	showResult(result, maxLength) {
		this.result = result;

		const resultArray = result;

		var aaa = {};
		 resultArray.attendances.forEach((item, i) => {
		 	if (typeof (aaa[item.date + "T" + item.time]) == "undefined") aaa[item.date + "T" + item.time] = new Array();
		 	if(item.member != null)
		 		aaa[item.date + "T" + item.time].push(item.member);
		 });

		console.log("aaa : " , aaa);

		var bbb = new Array();
		Object.keys(aaa).forEach((item, i) => {
			bbb.push({
				'date': item,
				'member': aaa[Object.keys(aaa)[i]]
			})
		});


		bbb.sort(function (a, b) {
			if (a.member.length < b.member.length) {
				return 1;
			}
			if (a.member.length > b.member.length) {
				return -1;
			}
			return 0;
		});

		console.log("bbb : ", bbb);

		bbb.slice(0, maxLength).forEach((item, i) => {
			$("#list-priority").append("" +
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
				"       <p class=\"js-lists-values-employee-name\" style=\"margin:0\" name=\"result-date\">" + item.date.replace("T", " ") + " </p>" +
				// "       <p class=\"js-lists-values-employee-name\" style=\"margin:0\"> " + this.date_to_time(filter.filterStartTime) + " ~ " + this.date_to_time(filter.filterEndTime) +  " </p>" +
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

			this.resultFormatArrayCnt = bbb.length;

			var conflictHTML = "" +
				"<tr data-index=\"list-conflict-" + i + "\">" +
				" <td style=\"padding:0px;\">" +
				"   <div class=\"media align-items-left\">" +
				"     <div class=\"media-body\">" +
				"       <span class=\"js-lists-values-employee-name\" style=\"margin:10px;\">" + item.date.replace("T", " ") + "</span>" +
				"     </div>" +
				"   </div>" +
				" </td>" +
				" <td>";
            if(item.member.length > 0) {
                var removeArray = this.removeMember(resultArray.joinMembers, item.member);

                if(removeArray.length > 0){
                    removeArray.forEach((item, i) => {
                        conflictHTML += "" +
                            "<div class=\"media align-items-center\" style=\"float:left;\">" +
                            " <div class=\"avatar avatar-xs mr-2\">" +
                            "   <span class=\"avatar-title rounded-circle\">" + item.member_initial + "</span>" +
                            " </div>" +
                            "</div>";
                    });
                }
                 else{
                     conflictHTML += "" +
                         "<div class=\"media align-items-center\" style=\"float:left;\">" +
                         " <div class=\"avatar avatar-xs mr-2\">" +
                         " </div>" +
                         "</div>";
                 }
            }
            else{
                var removeArray = this.removeMember(resultArray.joinMembers, item.member);
								removeArray.forEach((item, i) => {
										conflictHTML += "" +
												"<div class=\"media align-items-center\" style=\"float:left;\">" +
												" <div class=\"avatar avatar-xs mr-2\">" +
												"   <span class=\"avatar-title rounded-circle\">" + item.member_initial + "</span>" +
												" </div>" +
												"</div>";
								});
            }

			conflictHTML += "</td></tr>";
			$("#list-conflict").append(conflictHTML);
		});
	}

	removeMember(totalMembers, joinMembers) {
		var copyTotalMembers = totalMembers.slice();

		joinMembers.forEach((jMember, i) => {
			const findItem = copyTotalMembers.find(function (item) {
				return item.member_id == jMember.member_id;
			});

			const idx = copyTotalMembers.indexOf(findItem);
			copyTotalMembers.splice(idx, 1);
		});

		return copyTotalMembers;
	}

	onClick(el) {
		var $el = $(el);
		var idx = $el.data("index");
		var $rows = $(el).parent().children().toArray();
		var $conflictTable = $("#list-conflict").children().toArray();
		var conflictIndex = $el.index();

		$rows.forEach((item, i) => {
			var $rowCheck = $(item).find("input[type='checkbox']");
			$(item).removeClass("selected");
			$($conflictTable[i]).removeClass("selected");

			$rowCheck.prop('checked', false);
		});

		var $selectCheck = $el.find("input[type='checkbox']");
		$selectCheck.prop('checked', true);
		$el.addClass("selected");
		$($conflictTable[conflictIndex]).addClass("selected");

		$el.find(".js-lists-values-employee-name").text();

    var msg = $el.find("p[name=result-date]").text();
		this.selectStartDate = msg.split(" ")[0] + "T" +  msg.split(" ")[1].split("~")[0] + ":00";
		this.selectEndDate = msg.split(" ")[0] + "T" +  msg.split(" ")[1].split("~")[1] + ":00";

		console.log("msg : " , msg);

		var msg1 = this.date_to_date(new Date(msg.split(" ")[0] + "T00:00:00" ));
		var msg2 = msg.split(" ")[1].split("~")[0] + " ~ " + msg.split(" ")[1].split("~")[1];
		this.setModalMessage(msg1, msg2);

	}

	setModalMessage(msg1, msg2) {
		$("#modal-confirm-date").text(msg1);
		$("#modal-confirm-time").text(msg2);
	}

	showMore() {
		$("#list-priority").empty();
		$("#list-conflict").empty();

		let toggle = $("#label-show-toggle").data("toggle");
		$("#label-show-toggle").data("toggle", !toggle);

		if ($("#label-show-toggle").data("toggle") == true) {
			$("#label-show-toggle").text("간략히보기");

			this.showResult(this.result, this.resultFormatArrayCnt);
		} else {
			$("#label-show-toggle").text("전체보기")

			const maxLength = this.resultFormatArrayCnt > 5 ? 5 : this.resultFormatArrayCnt;

			this.showResult(this.result, maxLength);
		}
	}

	date_to_date(format) {
		var year = format.getFullYear();
		var month = format.getMonth() + 1;
		var date = format.getDate();
		var hour = format.getHours();
		var min = format.getMinutes();

		if (month < 10) month = '0' + month;
		if (date < 10) date = '0' + date;
		if (hour < 10) hour = '0' + hour;
		if (min < 10) min = '0' + min;

		console.log("format.getDay() : ", format.getDay());

		return year + "년 " + month + "월 " + date + "일 " + this.days_to_word(format.getDay());
	}

	date_to_time(date) {
		var format = new Date(date);
		var hour = format.getHours();
		var min = format.getMinutes();

		if (hour < 10) hour = '0' + hour;
		if (min < 10) min = '0' + min;

		return hour + ":" + min;
	}

	date_to_days(date) {
		var days = new Date(date + "T00:00:00");

		return this.days_to_word(days.getDay());
	}

	days_to_word(days) {
		var words = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];

		return words[days];

	}

}
