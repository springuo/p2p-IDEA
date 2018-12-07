//登录后返回页面
var referrer = "";

// alert(!referrer);//true
// alert(referrer);//""

//获取到跳转至当前页面之前页面的URL
referrer = document.referrer;

// alert(!referrer);//false
// alert(referrer);//获取到跳转至当前页面之前页面的URL


if (!referrer) {
	try {
		if (window.opener) {                
			// IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性              
			referrer = window.opener.location.href;
		}  
	} catch (e) {
	}
}

//按键盘Enter键即可登录
$(document).keyup(function(event){
	if(event.keyCode == 13){
        login();
	}
});



function checkPhone() {
	//获取用户的手机号码
	var phone = $.trim($("#phone").val());

	if ("" == phone) {
		$("#showId").html("请输入手机号码");
		return false;
	} else if(!/^1[1-9]\d{9}$/.test(phone)) {
		$("#showId").html("请输入正确的手机号码");
		return false;
	} else {
		$("#showId").html("");
	}

	return true;
}


function checkLoginPassword() {
	//获取登录密码
	var loginPassword = $.trim($("#loginPassword").val());

	if ("" == loginPassword) {
		$("#showId").html("请输入登录密码");
		return false;
	} else {
		$("#showId").html("");
	}
	return true;
}

//验证图形验证码
/*function checkCaptcha() {
    //获取图形验证码
    var captcha = $.trim($("#captcha").val());
    var flag = true;

    if ("" == captcha) {
        $("#showId").html("请输入图形验证码");
        return false;
    } else {
        $.ajax({
            url:"loan/checkCaptcha",
            type:"post",
            data:"captcha="+captcha,
            async:false,
            success:function (jsonObject) {
                if (jsonObject.errorMessage == "OK") {
                    $("#showId").html("");
                    flag = true;
                } else {
                    $("#showId").html(jsonObject.errorMessage);
                    flag = false;
                }
            },
            error:function () {
                $("#showId").html("系统繁忙，请稍后重试...");
                flag = false;

            }
        });
    }

    if (!flag) {
        return false;
    }

    return true;
}*/

function checkMessageCode() {
    var messageCode = $.trim($("#messageCode").val());
    if ("" == messageCode) {
        $("#showId").html("请输入短信验证码");
        return false;
    } else {
        $("#showId").html("");
    }
    return true;
}

//用户登录
function login() {
	//获取用户登录的信息
	var phone = $.trim($("#phone").val());
	var loginPassword = $.trim($("#loginPassword").val());
	var messageCode = $.trim($("#messageCode").val());

	if(checkPhone() && checkLoginPassword() && checkMessageCode()){
		$("#loginPassword").val($.md5(loginPassword));

		$.ajax({
			url:"loan/login",
			type:"post",
			data:"phone="+phone+"&loginPassword="+$("#loginPassword").val()+"&messageCode="+messageCode,
			success:function (jsonObject) {
				//验证成功之后，从哪来回哪儿去
				if (jsonObject.errorMessage == "OK") {
					window.location.href = referrer;
				} else {
                    $("#showId").html("用户名或密码有误，请重新登录");
				}
            },
			error:function () {
				$("#showId").html("系统繁忙，请稍后重试...");

            }
		});
	}
}

/*$(document).ready(function () {

});*/

$(function () {
	//加载平台信息
	loadStat();

    //60秒倒计时
    $("#dateBtn1").on("click",function(){
        var phone = $.trim($("#phone").val());

        var _this=$(this);
        if ("" != phone) {
            $.ajax({
                url: "loan/messageCode",
                type: "get",
                data: "phone=" + phone,
                success: function (jsonObject) {
                    if (jsonObject.errorMessage == "OK") {
                        alert("您手机收到的短信验证码是:" + jsonObject.messageCode);
                        if (!$(this).hasClass("on")) {
                            $.leftTime(60, function (d) {
                                if (d.status) {
                                    _this.addClass("on");
                                    _this.html((d.s == "00" ? "60" : d.s) + "秒后重新获取");
                                } else {
                                    _this.removeClass("on");
                                    _this.html("获取验证码");
                                }
                            });
                        }
                    } else {
                        showError("message", "请稍后重试...");
                    }

                }
            });

        } else {
            $("#showId").html("请输入手机号码");
        }
    });
});

function loadStat() {
	$.ajax({
		url:"loan/loadStat",
		type:"get",
		success:function (jsonObject) {
			$(".historyAverageRate").html(jsonObject.historyAverageRate);
			$("#allUserCount").html(jsonObject.allUserCount);
			$("#allBidMoney").html(jsonObject.allBidMoney);
        }
	});
}
















