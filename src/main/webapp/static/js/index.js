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

    $('body').on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });
    //门店管理员
    if( 4 == data.rolePriority ){
        $('.js-shop-panel').css("display", 'none');
    }else if( 3 == data.rolePriority ){ //门店/干洗中心员工
        $('.js-shop-panel').css("display", 'none');
        $('.js-worker-panel').css("display", 'none');
    }
    $('.js-shop-panel').click(function(){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", '');
        $('.js-laundry-order').css("display", 'none');
    });

    $('.js-admin-name').text(data.name);//登录用户姓名
    $('.js-role-name').text(data.roleName);//登录用户角色名

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
                roleId:'2'
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
            var name = $('.js-principal-name').val();// 门店负责人
            var area = $('.js-shop-area').val();// 门店所属区域
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
                url: 'admin/getAdminList',
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
                url: "admin/getAdminList",
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
            $('.js-user-modal-shop').val(user[0].admin_name);
            $('.js-user-account-modal-shop').val(user[0].admin_no);
            $('#selectWorker').modal('hide');
            $('.js-worker-tab').bootstrapTable('destroy');
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
    $('.js-worker-panel').click(function(){
        $('.js-laundry-worker').css("display", '');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-order').css("display", 'none');
    });
    $('.js-laundry-worker-tab').bootstrapTable({
        url: 'admin/getAdminInfoList',
        height: 300,
        columns: [{
            field: 'shop_no',
            title: '所属门店',
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
            formatter:actionFormatter
        }],
        cache: false,
        sidePagination: "server",
        pagination: true,
        queryParamsType:'',
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 3,                     //每页的记录行数（*）
        pageList: [2, 5],  //可供选择的每页的行数（*）
        showHeader: true,
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                roleIds:roleIDs
                // shopNo:'-1'
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
            var shopName = $('.js-worker-shop').val();
            var opt = {
                url: "admin/getAdminInfoList",
                silent: true,
                query:{
                    name:name,
                    shop:shopName,
                    roleIds:'2,3,4'
                }
            };
            //带参数 刷新
            $(".js-laundry-worker-tab").bootstrapTable('refresh', opt);
    }
    )
    $('.js-order-panel').click(function(){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-order').css("display", '');
    });
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
 * 清空模态框信息
 */
function clearModal() {
    $('.js-user-account-modal-shop').val(''); //负责人工号
    $('.js-shop-no-modal').text(''); //门店编号
    $('.js-area-modal-shop').val(''); //门店所属区域
    $('.js-user-modal-shop').val(''); //门店负责人姓名
    $('.js-address-modal-shop').val(''); //门店地址
    $('.js-name-modal-shop').val(''); //门店名
}

