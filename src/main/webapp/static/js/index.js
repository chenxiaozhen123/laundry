$(function() {
    $.ajax({
        url:"sys/status",
        data:"",
        dataType:"json",
        type:"get",
        success:function(data){
            initPage(data);
        },
        error:function (data) {
            alert("初始化出错");
        }
    })
});

/**
 * 初始化页面
 * @param data
 */
function initPage(data){
    initWindow();
    $('.js-admin-name').text(data.name);//登录用户姓名
    $('.js-role-name').text(data.roleName);//登录用户角色名
    //超级管理员
    if( admin_root_priority == data.rolePriority ){
        $('.js-center-worker').css("display", 'none');
        $('.js-shop-worker').css("display", 'none');
        //登录用户为超级管理员的时候，员工类型只能是干洗中心管理员/门店管理员
        roleIDs = shop_admin_role_id +','+ center_admin_role_id;
    }else if( admin_priority == data.rolePriority ){ //门店管理员/干洗中心管理员
        //显示菜单
        $('.js-shop-panel').css("display", 'none');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-worker').css("display", '');
        $('.js-center-admin').css("display", 'none');//不显示 员工类型为干洗中心管理员
        $('.js-shop-admin').css("display", 'none');//不显示 员工类型为门店管理员
        shopNo = data.shopNo;
        if(shop_admin_role_id == data.roleId){
            //登录用户为门店管理员的时候，员工类型只能是门店员工
            $('.js-center-worker').css("display", 'none');
            $('.js-category-panel').css("display", 'none');
            roleIDs = shop_worker_role_id;
            $('.js-worker-cate-sel').val(shop_worker_role_id);
        }else {
            //登录用户为干洗中心管理员的时候，员工类型只能是干洗中心员工
            $('.js-shop-worker').css("display", 'none');
            $('.js-worker-cate-sel').val(center_worker_role_id);
            roleIDs = center_worker_role_id;
        }
    }else if( worker_priority == data.rolePriority ){ //门店/干洗中心员工
        shopNo = data.shopNo;
        $('.js-shop-panel').css("display", 'none');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-worker-panel').css("display", 'none');
        $('.js-category-panel').css("display", 'none');
        $('.js-laundry-order').css("display", '');
    }
    initShop(data);
    initWorker(data);
    initOrder(data);
    initCategory(data);
    initPerson(data);
    initSettings(data);
};

/**
 * 退出登录
 */
$('.js-login-out').on(
    "click",function () {
        $.ajax({
            url:"sys/loginOut",
            data:{},
            dataType:"json",
            type:"post",
            success:function(data){
                window.location.href = "/admin/login.html"
            },
            error:function (data) {
            }
        })
    }
);


