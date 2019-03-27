function initWorker(data) {
    $('.js-worker-panel').on(
        "click",function () {
            $('.js-laundry-worker').css("display", '');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", 'none');
            $('.js-laundry-category').css("display",'none');
            $('.js-laundry-goods').css("display",'none');
            $('.js-person-info').css("display",'none');
            $('.js-business-analyse').css("display",'none');
            $('.js-worker-panel').addClass('active').siblings('li').removeClass('active');
        }
    );
    var name = $('.js-worker-name').val();
    var shopName = $('.js-worker-shop').val();
    var roleName = $('.js-worker-role').val();
    /**
     * 初始化员工管理表格
     */
    $('.js-laundry-worker-tab').bootstrapTable({
        url: 'admin/getAdminInfoList',
        height: 300,
        columns: [{
            field: 'shop_no',
            title: '所属门店',
            width:120,
            visible: false
        },{
            field: 'role_id',
            width:120,
            visible: false
        },{
            field: 'admin_no',
            title: '员工编号',
            width:120
        },{
            field: 'admin_name',
            title: '姓名',
            width:120
        },{
            field: 'admin_sex',
            title: '性别',
            width:120
        },{
            field: 'admin_tel_num',
            title: '手机号码',
            width:120
        }, {
            field: 'admin_email',
            title: '邮箱',
            width:120
        }, {
            field: 'role_name',
            title: '员工角色',
            width:120
        },{
            field: 'shop_name',
            title: '所属门店',
            width:120
        },{
            field: 'action',
            title: '操作',
            width:70,
            formatter:function(value,row,index){
                var result = "";
                var obj = new Array();
                obj[0] = row.shop_no;
                obj[1] = row.admin_no;
                obj[2] = row.admin_tel_num;
                obj[3] = row.admin_name;
                obj[4] = row.admin_email;
                obj[5] = row.role_id;
                obj[6] = row.shop_name;
                obj[7] = row.admin_sex;
                obj[8] = data.shopNo;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editAdmin('" + obj + "')\" title='编辑'><span class='glyphicon glyphicon-edit'></span></a>";
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteAdmin('" + row.admin_no + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
                return result;
            }
        }],
        cache: false,
        sidePagination: "server",
        pagination: true,
        queryParamsType:'',
        pageNumber: 1,   //初始化加载第一页，默认第一页,并记录
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10,20],  //可供选择的每页的行数（*）
        showHeader: true,
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                roleIds:roleIDs,
                shopName:shopName,
                shopNo:shopNo,
                roleName:roleName
            }
            return param
        },
        responseHandler: function(res) {
            return {
                "total": res.data.total,//总页数
                "rows": res.data.rows   //数据
            };
        }
    });

    /**
     * 查询员工
     */
    $('.js-worker-search-btn').on(
        "click",function () {
            name = $('.js-worker-name').val();
            shopName = $('.js-worker-shop').val();
            roleName = $('.js-worker-role').val();
            var opt = {
                url: "admin/getAdminInfoList",
                silent: true,
                query:{
                    name:name,
                    shopName:shopName,
                    roleName:roleName,
                    roleIds:roleIDs
                }
            };
            //带参数 刷新
            $(".js-laundry-worker-tab").bootstrapTable('refresh', opt);
        }
    );
    /**
     * 添加/员工
     */
    $('.js-worker-add-btn').on(
        "click",function () {
            action = 'Add';
            $('.js-shop-no-modal-worker').val(data.shopNo);
            showWorkerModal();
        }
    );
    /**
     * 添加/修改员工
     */
    $('.js-worker-ok-btn').on(
        "click",function () {
            var name = $('.js-name-modal-worker').val();//姓名
            var telNum = $('.js-tel-num-modal-worker').val(); //手机号码
            var role = $('.js-worker-cate-sel').val();//员工类别
            var sex = $('.js-worker-sex-sel').val(); //性别
            var email = $('.js-email-modal-shop').val();//邮箱
            var shopNo = $('.js-shop-no-modal-worker').val(); //所属门店
            var adminNo = $('.js-admin-no-modal').text(); //工号
            var datas = {
                "name":name,
                "telNum":telNum,
                "roleId":role,
                "sex":sex,
                "shopNo":shopNo,
                "email":email,
                "adminNo":adminNo
            };
            var url = "";
            var msg = "";
            var errorMsg = ""
            if("Add" == action){
                url = "admin/add";
                msg = SUCCESS_ADD_MSG;
                errorMsg = FAIL_ADD_MSG;
            }else if("Edit" == action){
                url = "admin/updateAdminInfo";
                msg = SUCCESS_UPDATE_MSG ;
                errorMsg = FAIL_UPDAGE_MSG;
            }else if("personEdit" == action){
                url = "admin/updateAdminPersonInfo";
                msg = SUCCESS_UPDATE_MSG +"!重新登录即可看到更新后的信息";
                errorMsg =FAIL_UPDAGE_MSG;
            }
            callRemoteFunction(url,datas,msg,errorMsg);
            $('#worker').modal('hide');
            //刷新数据
            var opt = {
                url: "admin/getAdminInfoList",
                silent: true,
                query:{
                    roleIds:roleIDs
                }
            };
            //带参数 刷新
            $(".js-laundry-worker-tab").bootstrapTable('refresh', opt);
            clearModal();
        }
    );
    /***************************************************************************************************
     *     超级管理员添加干洗中心/门店管理员的时候不选择门店，此时添加的干洗中心/门店管理员为未分配门店
     *     则在添加门店或者修改门店负责人的是再选择刚刚添加的干洗中心/门店管理员
     *     因此超级管理员添加员工的时候不需要显示选择门店模态框，所以将涉及到的方法和html注释掉
     ***************************************************************************************************/
    // /**
    //  * 选择门店
    //  */
    // $('.js-select-shop-ok-btn').on(
    //     "click",function () {
    //         var user= $('.js-shop-tab').bootstrapTable('getSelections');
    //         if( 0 == user.length){
    //             BootstrapDialog.alert({
    //                 title: '提示',
    //                 message: '请选择门店',
    //                 type: BootstrapDialog.TYPE_WARNING,
    //                 closable: true,
    //                 draggable: true,
    //                 buttonLabel: '确定'
    //             });
    //         }else{
    //             $('.js-shop-no-modal-worker').val(user[0].shop_no);
    //             $('.js-worker-shop-name').val(user[0].shop_name);
    //             $('#selectShop').modal('hide');
    //             $('.js-shop-tab').bootstrapTable('destroy');
    //             //清空选择员工模态框的数据
    //             $('.js-worker-shop').val(''); //员工名
    //         }
    //     }
    // );
    // /**
    //  * 根据门店所属区域查询门店
    //  */
    // $('.js-select-shop-search-btn').on(
    //     "click",function () {
    //         var area = $('.js-area-shop-worker-modal').val();
    //         var opt = {
    //             url: "shop/getLaundryShopList",
    //             silent: true,
    //             query:{
    //                 area:area,
    //                 roleId:roleIDs
    //             }
    //         };
    //         //带参数 刷新
    //         $(".js-shop-tab").bootstrapTable('refresh', opt);
    //     }
    // )
    // /**
    //  * 门店管理员/门店员工选择门店
    //  */
    // $('.js-username-modal-worker').on(
    //     "click",function () {
    //         var val = $('.js-worker-cate-sel').val();
    //         showWorkerForShop(val);
    //     }
    // )
}
$('.js-worker-reset-btn').on(
    "click",function () {
        $('.js-worker-name').val('');
        $('.js-worker-shop').val('');
        $('.js-worker-role').val('');
    }
)
/**
 * 删除员工信息
 */
function deleteAdmin(data) {
    var url = "admin/delete";
    var msg = SUCCESS_DELETE_MSG;
    var errorMsg=FAIL_DELETE_MSG;
    var datas = {
        "adminNo": data
    }
    BootstrapDialog.confirm({
        title: '提示',
        message: '确定删除该员工的信息吗?',
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true,
        btnCancelLabel: '取消',
        btnOKLabel: '确定',
        btnOKClass: 'btn-warning',
        callback: function (result) {
            if (result) {
                callRemoteFunction(url,datas,msg,errorMsg);
            }
        }
    });
}

/**
 * 修改员工信息
 */
function editAdmin(obj) {
    action = 'Edit';
    showWorkerModal(obj);
    $('#worker').modal('show');
}

/**
 * 显示修改/添加员工模态框
 * @param data
 */
function showWorkerModal(data) {
    if("Edit" == action){
        var datas = data.split(',');
        $('.js-title-worker').text('修改员工信息');
        $('.js-worker-no-modal-div').css("display", '');
        $('.js-admin-no-modal').text(datas[1]); //门店编号js-shop-no-modal-worker
        // 姓名
        $('.js-name-worker-div').css('display','none');
        $('.js-name-worker-lab').css('display','');
        $('.js-name-worker-lab').text(datas[3]);
        // 手机号码
        $('.js-tel-num-worker-div').css('display','none');
        $('.js-tel-num-worker-lab').css('display','');
        $('.js-tel-num-worker-lab').text(datas[2]);
        //性别
        $('.js-worker-sex-div').css('display','none');
        $('.js-worker-sex-lab').css('display','');
        $('.js-worker-sex-lab').text(datas[7]);
        //分类
        $('.js-worker-cate-sel').val(datas[5]);
        //邮箱
        $('.js-worker-email-div').css('display','none');
        $('.js-worker-email-lab').css('display','');
        $('.js-worker-email-lab').text(datas[4]);
        $('.js-shop-no-modal-worker').val(datas[8]); //所属门店
        // if( shop_worker_role_id == datas[5] || shop_admin_role_id == datas[5] || center_admin_role_id == datas[5]){
        //     $('.js-worker-shop-div').css('display','');
        //     $('.js-worker-shop-name').val(datas[6]); //所属门店名
        // }else{
        //     $('.js-worker-shop-div').css('display','none');
        // }
    }else {
        $('.js-title-worker').text('添加员工');
        $('.js-worker-no-modal-div').css("display", 'none');
        // 姓名
        $('.js-name-worker-div').css('display','');
        $('.js-name-worker-lab').css('display','none');
        // 手机号码
        $('.js-tel-num-worker-div').css('display','');
        $('.js-tel-num-worker-lab').css('display','none');
        //性别
        $('.js-worker-sex-div').css('display','');
        $('.js-worker-sex-lab').css('display','none');
        //邮箱
        $('.js-worker-email-div').css('display','');
        $('.js-worker-email-lab').css('display','none');
        //所属门店
        $('.js-worker-shop-div').css('display','none');

    }
}
/**
 * 选择员工类型
 * @param obj
 */
// function selectOnchang(obj) {
//     var value = obj.options[obj.selectedIndex].value;
//     showWorkerForShop(value);
// }

/**
 * 显示员工可选的门店
 * @param value
 */
// function showWorkerForShop(value) {
//     if("Edit" == action && ( center_admin_role_id == value ||shop_worker_role_id == value || shop_admin_role_id == value) ){
//         showSelectShopForWorker(value);
//     }else if("Add" == action && shop_worker_role_id == value) {
//         showSelectShopForWorker(value);
//     }else{
//         $('.js-worker-shop-div').css('display','none');
//     }
// }

/**
 * 显示员工可选所属门店
 */
// function showSelectShopForWorker(val) {
//     $('.js-worker-shop-div').css('display','');
//     $('.js-shop-tab').bootstrapTable({
//         url: 'shop/getLaundryShopList',
//         height: 200,
//         columns: [{
//             checkbox: true
//         }, {
//             field: 'shop_no',
//             title: '门店编号'
//         },{
//             field: 'shop_area',
//             title: '所属区域'
//         }, {
//             field: 'shop_name',
//             title: '门店名',
//         },{
//             field: 'admin_name',
//             title: '门店负责人姓名'
//         },{
//             field: 'admin_tel_num',
//             title: '门店负责人手机号'
//         }],
//         singleSelect : true, // 单选checkbox
//         cache: false,
//         sidePagination: "server",
//         pagination: true,
//         queryParamsType:'',
//         queryParams:function (params) {
//             var param = {
//                 pageSize:params.pageSize,
//                 pageNumber:params.pageNumber,
//                 roleIds:val
//             }
//             return param;
//
//         },
//         pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
//         pageSize: 3,                     //每页的记录行数（*）
//         pageList: [2, 5],  //可供选择的每页的行数（*）
//         showHeader: true
//     });
// }
