/* 함수명: chkSubmit(유효성 체크 대상, 메시지 내용)
 * 출력영역: alert으로
 * 예시 : if(!chkSubmit($('#keyword'),"검색어를")) return;
 **/

function chkSubmit(item, msg) {
	if (item.val().replace(/\s/g, "") == "") {
		alert(msg + " 입력해 주세요.");
		item.val("");
		item.focus();
		return false;
	} else {
		return true;
	}

}

/*
 * 함수명: checkForm(유효성 체크 대상, 메시지 내용) 출력영역: placeholder 속성을 이용. 예시 :
 * if(!chkSubmit($('#keyword'),"검색어를")) return;
 */
function checkForm(item, msg) {
	var message = "";
	if (item.val().replace(/\s/g, "") == "") {
		message = msg + " 입력해 주세요.";
		item.attr("placeholder", message);
		return false;
	} else {
		return true;
	}
}

/*
 * 함수명: formCheck(유효성 체크 대상, 출력 영역, 메시지 내용) 출력영역: 매개변수 두번째 출력영역에. 예시 :
 * if(!formCheck($('#keyword'),$('#msg'),"검색어를")) return;
 */
function formCheck(main, item, msg) {
	if (main.val().replace(/\s/g, "") == "") {
		item.css("color", "#000099").html(msg + " 입력해 주세요");
		main.val("");
		return false;
	} else {
		return true;
	}
}

/*
 * 함수명: chkData(유효성 체크 대상, 메시지 내용) 예시 : if (!chkData("#keyword","검색어를")) return;
 */
function chkData(item, msg) {
	if ($(item).val().replace(/\s/g, "") == "") {
		alert(msg + " 입력해 주세요.");
		$(item).val("");
		$(item).focus();
		return false;
	} else {
		return true;
	}
}
