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

        }
    })
});
var action = "Edit";
var roleIDs = '2,3,4';
/**
 * 初始化页面
 * @param data
 */
function initPage(data){
    $('.js-admin-name').text(data.name);//登录用户姓名
    $('.js-role-name').text(data.roleName);//登录用户角色名
    //门店管理员
    if( 4 == data.rolePriority ){
        $('.js-shop-panel').css("display", 'none');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-worker').css("display", '');
        //登录用户为门店管理员的时候，员工类型只能是门店员工
        $('.js-shop-admin').css("display", 'none');
        $('.js-center-worker').css("display", 'none');
        roleIDs = '3';
    }else if( 3 == data.rolePriority ){ //门店/干洗中心员工
        $('.js-shop-panel').css("display", 'none');
        $('.js-worker-panel').css("display", 'none');
        $('.js-laundry-order').css("display", 'none');

    }


     ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////门店管理////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.js-shop-panel').click(function(){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", '');
        $('.js-laundry-order').css("display", 'none');
    });
    var name = $('.js-principal-name').val();// 门店负责人
    var area = $('.js-shop-area').val();// 门店所属区域
    /**
     * 初始化门店管理表格
     */
    $('.js-laundry-shop-tab').bootstrapTable({
        url: 'shop/getLaundryShopList',
        height: 300,
        columns: [{
            field: 'principal_no',
            title: '门店负责工号',
            visible: false,
            width:100
        }, {
            field: 'shop_no',
            title: '门店编号',
            width:100
        },{
            field: 'shop_area',
            title: '所属区域',
            width:100
        }, {
            field: 'shop_name',
            title: '门店名',
            width:100
        },{
            field: 'admin_name',
            title: '负责人姓名',
            width:100
        }, {
            field: 'admin_tel_num',
            title: '手机号码',
            width:100
        },{
            field: 'shop_address',
            title: '门店地址',
            width:220
        },{
            field: 'action',
            title: '操作',
            width:70,
            formatter:function(value,row,index){
                var result = "";
                var obj = new Array();
                obj[0] = row.principal_no;
                obj[1] = row.shop_no;
                obj[2] = row.shop_area;
                obj[3] = row.admin_name;
                obj[4] = row.shop_address;
                obj[5] = row.shop_name;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editLaundryShop('" + obj + "')\" title='编辑'><span class='glyphicon glyphicon-edit'></span></a>";
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteLaundryShop('" + row.shop_no + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
                return result;
            }
        }],
        cache: false,
        sidePagination: 'server',
        pagination: true,
        queryParamsType:'',
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                roleId:'2',
                name:name,
                area:area
            }
            return param
        },
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 3,                     //每页的记录行数（*）
        pageList: [2,5,10],  //可供选择的每页的行数（*）
        showHeader: true
    });
    /**
     * 查找门店
     */
    $('.js-shop-search-btn').on(
        "click",function () {
            name = $('.js-principal-name').val();// 门店负责人
            area = $('.js-shop-area').val();// 门店所属区域
            var opt = {
                url: "shop/getLaundryShopList",
                query:{
                    name:name,
                    area:area,
                    roleId:'2'
                }
            };
            //带参数 刷新
            $(".js-laundry-shop-tab").bootstrapTable('refresh', opt);
        }
    )
    /**
     * 添加门店
     */
    $(".js-shop-add-btn").on(
        "click",function(){
            $('#shop').modal('show');
            action = 'Add';
        });
    /**
     * 选择门店负责人
     */
    $(".js-username-modal-shop").on(
        "click",function () {
            $('#selectWorker').modal('show');
            /**
             * 选择员工
             */
            $('.js-worker-tab').bootstrapTable({
                url: 'admin/getAdminInfoList',
                height: 250,
                columns: [{
                    checkbox: true
                }, {
                    field: 'admin_no',
                    title: '工号'
                },{
                    field: 'admin_name',
                    title: '姓名'
                },{
                    field: 'admin_tel_num',
                    title: '手机号码'
                }],
                singleSelect : true, // 单选checkbox
                cache: false,
                sidePagination: 'server',//指定服务器端分页
                queryParamsType:'',
                queryParams:function (params) {
                    var param = {
                        pageSize:params.pageSize,
                        pageNumber:params.pageNumber,
                        shopNo:'-1',
                        roleIds:'2'
                    }
                    return param
                },
                pagination: true,
                pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                pageSize: 3,                     //每页的记录行数（*）
                pageList: [2, 5],  //可供选择的每页的行数（*）
                showHeader: true
            });
        }
    );
    /**
     * 根据名字模糊查询员工
     */
    $('.js-select-worker-search-btn').on(
        "click",function () {
            var name = $('.js-name-worker-shop').val();
            var opt = {
                url: "admin/getAdminInfoList",
                silent: true,
                query:{
                    name:name
                }
            };
            //带参数 刷新
            $(".js-worker-tab").bootstrapTable('refresh', opt);
        }
    );
    /**
     * 修改/添加门店
     */
    $(".js-shop-ok-btn").on(
        "click",function(){
            var shopNo = $('.js-shop-no-modal').text();
            var shopName = $('.js-name-modal-shop').val();
            var principalNo = $('.js-user-account-modal-shop').val();
            var area = $('.js-area-modal-shop').val();
            var address = $('.js-address-modal-shop').val();
            var datas = {
                "shopName":shopName,
                "principalNo":principalNo,
                "area":area,
                "address":address,
                "shopNo":shopNo
            };
            if("Add" == action){
                $.ajax({
                    url:"shop/add",
                    data:datas,
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        if( 0 < data){
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '添加成功',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }else{
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '添加失败',
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
                            message: '添加失败！'+data,
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定'
                        });
                    }
                });

            }else if("Edit" == action){
                $.ajax({
                    url:"shop/update",
                    data:datas,
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        if( 0 < data){
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '修改成功',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }else{
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '修改失败',
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
                            message: '修改失败！'+data,
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定'
                        });
                    }
                });
            }
            $('#shop').modal('hide');
            clearModal();

        });
    /**
     * 选择员工
     */
    $('.js-select-worker-ok-btn').on(
        "click",function () {
            var user= $('.js-worker-tab').bootstrapTable('getSelections');
            if( 0 == user.length){
                BootstrapDialog.alert({
                    title: '提示',
                    message: '请选择员工',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    buttonLabel: '确定'
                });
            }else{
                $('.js-user-modal-shop').val(user[0].admin_name);
                $('.js-user-account-modal-shop').val(user[0].admin_no);
                $('#selectWorker').modal('hide');
                $('.js-worker-tab').bootstrapTable('destroy');
                //清空选择员工模态框的数据
                $('.js-name-worker-shop').val(''); //员工名
            }
        }
    );
    /**
     * 操作单元格格式化
     * @param value
     * @param row
     * @param index
     * @returns {string}
     */
    function actionFormatter(value, row, index){
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editById('" + row + "')\" title='编辑'><span class='glyphicon glyphicon-edit'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteById('" + row.shop_no + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
        return result;

    };
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////门店管理结束////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////员工管理////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.js-worker-panel').on(
        "click",function () {
            $('.js-laundry-worker').css("display", '');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", 'none');
        }
    );
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
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5, 10,20],  //可供选择的每页的行数（*）
        showHeader: true,
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                roleIds:roleIDs,
                shopName:shopName,
                roleName:roleName
            }
            return param
        },
    });

    /**
     * 查询员工
     */
    $('.js-worker-search-btn').on(
        "click",function () {
            var name = $('.js-worker-name').val();
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
     * 添加/修改员工
     */
    $('.js-worker-add-btn').on(
        "click",function () {
            action = 'Add';
            $('.js-shop-no-modal-worker').val(data.shopNo);
        }
    );
    /**
     * 选择门店
     */
    $('.js-select-shop-ok-btn').on(
        "click",function () {
            var user= $('.js-shop-tab').bootstrapTable('getSelections');
            if( 0 == user.length){
                BootstrapDialog.alert({
                    title: '提示',
                    message: '请选择门店',
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    draggable: true,
                    buttonLabel: '确定'
                });
            }else{
                $('.js-shop-no-modal-worker').val(user[0].shop_no);
                $('.js-worker-shop-name').val(user[0].shop_name);
                $('#selectShop').modal('hide');
                $('.js-shop-tab').bootstrapTable('destroy');
                //清空选择员工模态框的数据
                //$('.s-worker-shop').val(''); //员工名
            }
        }
    );
    /**
     * 根据门店所属区域查询门店
     */
    $('.js-select-shop-search-btn').on(
        "click",function () {
            var area = $('.js-area-shop-worker-modal').val();
            var opt = {
                url: "shop/getLaundryShopList",
                silent: true,
                query:{
                    area:area,
                    roleId:'2'
                }
            };
            //带参数 刷新
            $(".js-shop-tab").bootstrapTable('refresh', opt);
        }
    )
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
            if("Add" == action){
                $.ajax({
                    url:"admin/add",
                    data:datas,
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        if( 0 < data){
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '添加成功',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }else{
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '添加失败',
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
                            message: '添加失败！'+data.msg,
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定'
                        });
                    }
                });
            }else if("Edit" == action){
                $.ajax({
                    url:"admin/updateAdminInfo",
                    data:datas,
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        if( 0 < data){
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '修改成功',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }else{
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '修改失败',
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
                            message: '修改失败！'+JSON.stringify(data),
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定'
                        });
                    }
                });
            }else if("personEdit" == action){
                $.ajax({
                    url:"admin/updateAdminPersonInfo",
                    data:datas,
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        if( 0 < data){
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '修改成功!重新登录即可看到更新后的信息',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }else{
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '修改失败',
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
                            message: '修改失败！'+JSON.stringify(data),
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定'
                        });
                    }
                });
            }
            $('#worker').modal('hide');
            clearModal();
        }
    )
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////员工管理结束////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////订单管理开始////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.js-order-panel').on(
        "click",function () {
            $('.js-laundry-worker').css("display", 'none');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", '');
        }
    )
    /**
     * 初始化订单管理表格
     */
    $('.js-laundry-order-tab').bootstrapTable({
        data:[{id:1},{id:2},{id:1},{id:2}],
//			    url: 'data1.json',
        height: 300,
        columns: [{
            field: 'id',
            title: '订单编号',
            width:150
        },{
            field: 'price',
            title: '姓名',
            width:150
        },{
            field: 'name',
            title: '手机号码',
            width:150
        }, {
            field: 'price',
            title: '所属门店',
            width:150
        },{
            field: 'price',
            title: '操作',
            width:150,
            formatter:actionFormatter
        }],
        cache: false,
//			    sidePagination: "server",
        pagination: true,
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 3,                     //每页的记录行数（*）
        pageList: [2, 5],  //可供选择的每页的行数（*）
        showHeader: true
    });
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////订单管理结束////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////个人信息开始////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.js-person-info-panel').on(
        "click",function () {
            $('.js-person-info').css('display','');
            $('.js-laundry-worker').css("display", 'none');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", 'none');
            $('.js-account-lab').text(data.adminNo);
            $('.js-name-lab').text(data.name); //姓名
            $('.js-sex-lab').text(data.sex);//性别
            $('.js-tel-num-lab').text(data.telNum); //手机号
            $('.js-email-lab').text(data.email);//邮箱
            $('.js-role-lab').text(data.roleName);
            $('.js-shop-lab').text(data.shopName);
        }
    );

    $('.js-person-update-btn').on(
        "click",function () {
            $('.js-title-worker').text('修改个人信息');
            $('.js-worker-no-modal-div').css("display", '');
            $('.js-admin-no-modal').text(data.adminNo);
            $('.js-name-modal-worker').val(data.name); //姓名
            $('.js-tel-num-modal-worker').val(data.telNum); //手机号
            $('.js-worker-sex-sel').val(data.sex); //性别
            $('.js-email-modal-shop').val(data.email); //邮箱
            //员工角色
            $('.js-worker-cate-sel-div').css('display','none');
            $('.js-worker-cate-sel-lab').css('display','');
            $('.js-worker-cate-sel-lab').text(data.roleName);
            //所属门店
            $('.js-worker-shop-div').css('display','');
            $('.js-worker-shop-lab').css('display','');
            $('.js-worker-shop-divs').css('display','none');
            $('.js-worker-shop-lab').text(data.shopName);
            action = "personEdit";
        }
    )
}
function editById() {
    alert(111)
}
function deleteById(data) {
    alert(data)
}

/**
 * 删除门店
 */
function deleteLaundryShop(datas) {
    BootstrapDialog.confirm({
        title: '提示',
        message: '确定删除门店编号为'+datas+'的门店信息吗?',
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true,
        btnCancelLabel: '取消',
        btnOKLabel: '确定',
        btnOKClass: 'btn-warning',
        callback: function(result) {
            if(result) {
                $.ajax({
                    url:"shop/delete",
                    data:{"shopNo":datas},
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        if( 0 < data){
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '删除成功',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }else{
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '删除失败',
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
                            message: '删除失败'+data,
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定',
                        });
                    }
                })
            }
        }
    });

}

/**
 * 编辑门店信息
 */
function editLaundryShop(data){
    var datas = data.split(',');
    $('.js-title-shop').text('修改门店信息');
    $('.js-shop-no-modal-div').css("display", '');
    $('.js-user-account-modal-shop').val(datas[0]); //负责人工号
    $('.js-shop-no-modal').text(datas[1]); //门店编号
    $('.js-area-modal-shop').val(datas[2]); //门店所属区域
    $('.js-user-modal-shop').val(datas[3]); //门店负责人姓名
    $('.js-address-modal-shop').val(datas[4]); //门店地址
    $('.js-name-modal-shop').val(datas[5]); //门店名
    $('#shop').modal('show');
    action = 'Edit';
}

/**
 * 删除员工信息
 */
function deleteAdmin(datas) {
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
                $.ajax({
                    url: "admin/delete",
                    data: {"adminNo": datas},
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        if (0 < data) {
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '删除成功',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        } else {
                            BootstrapDialog.alert({
                                title: '提示',
                                message: '删除失败',
                                type: BootstrapDialog.TYPE_WARNING,
                                closable: true,
                                draggable: true,
                                buttonLabel: '确定'
                            });
                        }
                    },
                    error: function (data) {
                        BootstrapDialog.alert({
                            title: '提示',
                            message: '删除失败' + data,
                            type: BootstrapDialog.TYPE_WARNING,
                            closable: true,
                            draggable: true,
                            buttonLabel: '确定',
                        });
                    }
                })
            }
        }
    });
}

/**
 * 修改员工信息
 */
function editAdmin(obj) {
    var datas = obj.split(',');
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
    $('.js-shop-no-modal-worker').val(datas[0]); //所属门店
    if( 3 == datas[5]){
        $('.js-worker-shop-div').css('display','');
        $('.js-worker-shop-name').val(datas[6]); //所属门店名
    }
    $('#worker').modal('show');
    action = 'Edit';

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

}
function selectOnchang(obj) {
    var value = obj.options[obj.selectedIndex].value;
    if( 3 == value){
        $('.js-worker-shop-div').css('display','');
        $('.js-shop-tab').bootstrapTable({
            url: 'shop/getLaundryShopList',
            height: 200,
            columns: [{
                checkbox: true
            }, {
                field: 'shop_no',
                title: '门店编号'
            },{
                field: 'shop_area',
                title: '所属区域'
            }, {
                field: 'shop_name',
                title: '门店名',
            },{
                field: 'admin_name',
                title: '门店负责人姓名'
            },{
                field: 'admin_tel_num',
                title: '门店负责人手机号'
            }],
            singleSelect : true, // 单选checkbox
            cache: false,
            sidePagination: "server",
            pagination: true,
            queryParamsType:'',
            queryParams:function (params) {
                var param = {
                    pageSize:params.pageSize,
                    pageNumber:params.pageNumber,
                    roleId:'2'
                }
                return param
            },
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 3,                     //每页的记录行数（*）
            pageList: [2, 5],  //可供选择的每页的行数（*）
            showHeader: true
        });
    }else {
        $('.js-worker-shop-div').css('display','none');
    }
}

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
                window.location.href = getRootPath()+"/admin/login.html"
            },
            error:function (data) {

            }
        })
    }
);

/**
 *
 * @returns {string}
 */
function getRootPath(){
    //获取当前网址
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

