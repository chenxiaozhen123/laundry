function initSettings(data) {
    $('.js-setting-panel').on(
        "click",function () {
            $('.js-laundry-worker').css("display", 'none');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", 'none');
            $('.js-laundry-category').css("display",'none');
            $('.js-laundry-goods').css("display",'none');
            $('.js-person-info').css("display",'none');
            $('.js-business-analyse').css("display",'none');
            $('.js-setting').css('display','');
            $('.js-setting-admin-no').text(data.adminNo);

        }
    );
}


/**
 * 手机号码
 */
$('.js-tel-num-li').on(
    "click",function () {
        $('.js-tel-num-panel').css('display','');
        $('.js-update-pwd-panel').css('display','none');
        $('.js-email-panel').css('display','none');
        $('.js-tel-num-li').addClass('active').siblings('li').removeClass('active');
        $('.js-setting-title').text('修改手机号码')
    }
);
$('.js-pwd-li').on(
    "click",function () {
        $('.js-pwd-li').addClass('active').siblings('li').removeClass('active');
        $('.js-tel-num-panel').css('display','none');
        $('.js-email-panel').css('display','none');
        $('.js-update-pwd-panel').css('display','');
        $('.js-setting-title').text('修改登录密码')
    }
);
$('.js-old-pwd').blur(function () {
    var username = $('.js-setting-admin-no').text();
    var pwd = $('.js-old-pwd').val();
    var datas = {
        "username":username,
        "password":pwd
    }
    $.ajax({
        url:"sys/login",
        data:datas,
        dataType:"json",
        type:"post",
        success:function(data){
            if(JSON.stringify(data) == "false"){
                alert('旧密码不正确,请重新输入');
                $('.js-old-pwd').focus();
            }
        }
    })
});
/**
 * 保存修改密码
 */
$('.js-update-pwd-btn').on(
    "click",function () {
        var username = $('.js-setting-admin-no').text();
        var newPwd = $('.js-new-pwd').val();
        var confirmPwd = $('.js-confirm-pwd').val();
        var msg = SUSSESS_UPDATE_PWD;
        var errorMsg = FAIL_UPDAGE_MSG;
        var datas = {
            "adminNo":username,
            "password":confirmPwd
        }
        if(confirmPwd != newPwd){
            alert("新密码和确认密码不一样，请重新输入");
            $('.js-new-pwd').focus();
        }else{
            $.ajax({
                url:"admin/updateAdminPersonInfo",
                data:datas,
                dataType:"json",
                type:"post",
                success:function(data){
                    if( 0 < data){
                        alert(msg);
                        window.location.href = "/admin/login.html"
                    }else{
                        BootstrapDialog.alert({
                            title: '提示',
                            message: errorMsg,
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定'
                        });
                    }
                },
                error:function (data) {
                    BootstrapDialog.alert({
                        title: '提示',
                        message: errorMsg+'!  '+data.responseJSON.message,
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        buttonLabel: '确定'
                    });
                }
            });
        }
    })
$('.js-email-li').on(
    "click",function () {
        $('.js-email-li').addClass('active').siblings('li').removeClass('active');
        $('.js-tel-num-panel').css('display','none');
        $('.js-email-panel').css('display','');
        $('.js-update-pwd-panel').css('display','none');
        $('.js-setting-title').text('修改邮箱')
    }
);
/**
 * 手机号码input失去焦点
 */
$('.js-new-tel-num').blur(function () {
    var format=TEL_NUM_FORMAT;
    var telNum = $('.js-new-tel-num').val();
    if(format.test(telNum) == true){
        $('.js-get-captcha-tel').removeAttr('disabled');
    }else{
        BootstrapDialog.alert({
            title: '提示',
            message: '请输入有效的手机号码',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            buttonLabel: '确定'
        });
    }
});

var randomNumStr = "";
$('.js-get-captcha-tel').on(
    "click",function () {
        var randomNum = ""
        for(var i = 0; i < 5;i++){
            randomNum = Math.floor(Math.random() * 10);
            randomNumStr += randomNum;
        }
        var data ={
            "mobile":$('.js-new-tel-num').val(),
            "codeT":randomNumStr
        }
        var url = "/sendSMS"
        var msg = "发送验证码成功,请及时查收"
        var errorMsg = "发送验证码失败"
        $.ajax({
            url:url,
            data:data,
            dataType:"json",
            type:"post",
            success:function(data){
                if( data == true){
                    BootstrapDialog.alert({
                        title: '提示',
                        message: msg,
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        buttonLabel: '确定'
                    });
                }else{
                    BootstrapDialog.alert({
                        title: '提示',
                        message: errorMsg+'!',
                        type: BootstrapDialog.TYPE_WARNING,
                        closable: true,
                        draggable: true,
                        buttonLabel: '确定'
                    });
                }
            },
            error:function (data) {
                BootstrapDialog.alert({
                    title: '提示',
                    message: errorMsg+'!  '+data.responseJSON.desc,
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    buttonLabel: '确定'
                });
            }
        });
    }
)
$('.js-update-tel').on(
    "click",function () {
        var captcha = $('.js-get-captcha-tel-val').val();
        var telNum = $('.js-new-tel-num').val()
        if(randomNumStr != captcha){
            alert("验证码不正确")
        }else{
            var username = $('.js-setting-admin-no').text();
            var datas =  {
                "telNum":telNum,
                "adminNo":username
            }
            var msg = SUSSESS_UPDATE_TEL;
            var errorMsg = FAIL_UPDAGE_MSG;
            var url = "admin/updateAdminPersonInfo";
            callRemoteFunction(url,datas,msg,errorMsg);
        }
        randomNumStr = "";
    }
)
/**
 * 邮箱input失去焦点
 */
$('.js-new-email').blur(function () {
    var format=EMAIL_FORMAT;
    var email = $('.js-new-email').val();
    if(format.test(email) == true){
        $('.js-get-captcha-email').removeAttr('disabled');
    }else{
        BootstrapDialog.alert({
            title: '提示',
            message: '请输入有效的邮箱',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            buttonLabel: '确定'
        });
    }
});
var captcha = null;
var newEmail = null;
/**
 * 获取邮箱验证码
 */
$('.js-get-captcha-email').on(
    "click",function () {
        var email = $('.js-new-email').val();
        var url = "admin/captcha";
        var msg = "验证码发送成功，请及时查收";
        var errorMsg = "验证码发送失败";
        var datas = {
            "email":email
        }
        $.ajax({
            url:url,
            data:datas,
            dataType:"json",
            type:"post",
            success:function(data){
                BootstrapDialog.alert({
                    title: '提示',
                    message: msg,
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    buttonLabel: '确定'
                });
                captcha = data.uuid;
                newEmail = data.email;
            },
            error:function (data) {
                BootstrapDialog.alert({
                    title: '提示',
                    message: errorMsg+'!  '+data.responseJSON.message,
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    buttonLabel: '确定'
                });
            }
        });

    }
);
/**
 * 保存修改的邮箱
 */
$('.js-update-email-ok-btn').on(
    "click",function () {
        var uuid = $('.js-uuid').val();
        var msg = SUSSESS_UPDATE_EMAIL;
        var errorMsg = FAIL_UPDAGE_MSG;
        var url = "admin/updateAdminPersonInfo";
        var email = $('.js-new-email').val();
        var username = $('.js-setting-admin-no').text();
        var datas =  {
            "email":email,
            "adminNo":username
        }
        if(uuid != captcha && email != newEmail){
            BootstrapDialog.alert({
                title: '提示',
                message: "验证码不正确，请重新输入",
                type: BootstrapDialog.TYPE_WARNING,
                closable: true,
                draggable: true,
                buttonLabel: '确定'
            });
        }else {
            callRemoteFunction(url,datas,msg,errorMsg);
        }
    }
)