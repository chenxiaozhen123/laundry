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
            if( 0 < data  || data == true){
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
                    message: errorMsg+'!'+data.responseJSON.message,
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
    var obj = $('.js-goods-img');
    obj.select();
    document.selection.clear();

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