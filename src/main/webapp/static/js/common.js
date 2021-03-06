/**
 * ajax调用后台
 */
function callRemoteFunction(url,data,msg,errorMsg) {
    $.ajax({
        url:url,
        data:data,
        dataType:"json",
        type:"post",
        success:function(data){
            if( SUCCESS_CODE == data.code){
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
/**
 * 清空模态框信息
 */
function clearModal() {
    //添加门店模态框的数据
    $('.js-user-account-modal-shop').val(''); //负责人工号
    $('.js-shop-no-modal').text(''); //门店编号
    $('.js-area-modal-shop').val(''); //门店所属区域
    $('.js-user-modal-shop').val(''); //门店负责人姓名
    $('.js-address-modal-shop').val(''); //门店地址
    $('.js-name-modal-shop').val(''); //门店名
    //添加/修改员工
    $('.js-name-modal-worker').val(''); // 姓名
    $('.js-tel-num-modal-worker').val(''); //手机号码
    $('.js-worker-sex-sel').val(''); //性别
    $('.js-worker-cate-sel').val('');//分类
    $('.js-email-modal-shop').val('');//邮箱
    $('.js-worker-shop-name').val(''); //所属门店名
    //商品
    $('.js-goods-name-modal').val('');
    $('.js-goods-price').val('');
    $('.js-goods-img').fileinput('clear');
    $('.js-goods-img').siblings('.fileinput-remove').hide();
    //分类
    $('.js-cat-img').fileinput('clear');
    $('.js-cat-img').siblings('.fileinput-remove').hide();
    $('.js-cat-name').val('');


}
/**
 * fileinput初始化
 */
function initFileInput(clazz) {
    var control = $('.' + clazz);
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: "upload/img", //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png','jpeg'],//接收的文件后缀
        showUpload: true, //是否显示上传按钮
        showCaption: true,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        overwriteInitial: false,
        autoReplace:true,//再选择会替换当前文件
        dropZoneEnabled: false//是否显示拖拽区域
    });
}
function initWindow() {
    $("[data-toggle='offcanvas']").click(function(e) {
        e.preventDefault();
        //If window is small enough, enable sidebar push menu
        if ($(window).width() <= 992) {
            $('.row-offcanvas').toggleClass('active');
            $('.left-side').removeClass("collapse-left");
            $(".right-side").removeClass("strech");
            $('.row-offcanvas').toggleClass("relative");
        } else {
            //Else, enable content streching
            $('.left-side').toggleClass("collapse-left");
            $(".right-side").toggleClass("strech");
        }
    });
}

function formatDate (val) {
    // 格式化时间
    var start = new Date(val)
    var y = start.getFullYear()
    var m = (start.getMonth() + 1) >= 10 ? (start.getMonth() + 1) : '0' + (start.getMonth() + 1)
    var d = start.getDate() >= 10 ? start.getDate() : '0' + start.getDate()
    return y + '-' + m + '-' + d
}
var TEL_NUM_FORMAT=/^(13[0-9]\d{8}|15[0-35-9]\d{8}|18[0-9]\{8}|14[57]\d{8})$/;
var EMAIL_FORMAT=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/

