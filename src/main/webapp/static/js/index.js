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

     ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////门店管理////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.js-shop-panel').click(function(e){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", '');
        $('.js-laundry-order').css("display", 'none');
        $('.js-laundry-category').css("display",'none');
        $('.js-laundry-goods').css("display",'none');
        $('.js-person-info').css("display",'none');
        var $event =$(e.target);
        $event.addClass('active').siblings('li').removeClass('active');

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
            width:100,
            align:'center'
        }, {
            field: 'shop_no',
            title: '门店编号',
            width:100,
            align:'center'
        }, {
            field: 'shop_category',
            title: '门店分类',
            width:100,
            align:'center'
        },{
            field: 'shop_area',
            title: '所属区域',
            width:100,
            align:'center'
        }, {
            field: 'shop_name',
            title: '门店名',
            width:100,
            align:'center'
        },{
            field: 'admin_name',
            title: '负责人姓名',
            width:100,
            align:'center'
        }, {
            field: 'admin_tel_num',
            title: '手机号码',
            width:100,
            align:'center'
        },{
            field: 'shop_address',
            title: '门店地址',
            width:220,
            align:'center'
        },{
            field: 'condition',
            title: '业务分析',
            width:80,
            align:'center',
            formatter: function (value, row, index) {
                var result = "";
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"showShopServicePanel('" + row.shop_no + "')\" title='业务查看'><span class='glyphicon glyphicon-th'></span></a>";
                return result;
            }
        },{
            field: 'action',
            title: '操作',
            width:70,
            align:'center',
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
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteLaundryShop('" + obj + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
                return result;
            }
        }],
        cache: false,
        sidePagination: 'server',
        pagination: true,
        queryParamsType:'',
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 3,                     //每页的记录行数（*）
        pageList: [2,5,10],  //可供选择的每页的行数（*）
        showHeader: true,
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                roleIds:roleIDs,
                name:name,
                area:area
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
                    roleIds:roleIDs
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
            action = 'Add';
            showShopModal(data);
            $('#shop').modal('show');
        });
    /**
     * 选择门店负责人
     */
    $(".js-username-modal-shop").on(
        "click",function () {
            $('#selectWorker').modal('show');
            var roleId = $('.js-shop-cate-sel').val();
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
                        shopNo:cannot_shop_no,
                        roleIds:roleId
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
            var url = "";
            var msg = "";
            var errorMsg = "";
            if("Add" == action){
                url = "shop/add";
                msg = SUCCESS_ADD_MSG;
                errorMsg = FAIL_ADD_MSG;
            }else if("Edit" == action){
                url = "shop/update";
                msg = SUCCESS_UPDATE_MSG;
                errorMsg = FAIL_UPDAGE_MSG ;
            }
            callRemoteFunction(url,datas,msg,errorMsg);
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
            $('.js-laundry-category').css("display",'none');
            $('.js-laundry-goods').css("display",'none');
            $('.js-person-info').css("display",'none');
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////员工管理结束////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////订单管理开始////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    var orderId = $('.js-order-id').val();
    var status = $('.js-order-status').val();
    $('.js-order-panel').on(
        "click",function () {
            $('.js-laundry-worker').css("display", 'none');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-category').css("display",'none');
            $('.js-laundry-goods').css("display",'none');
            $('.js-laundry-order').css("display", '');
            $('.js-person-info').css("display",'none');
        }
    )
    /**
     * 初始化订单管理表格
     */
    $('.js-laundry-order-tab').bootstrapTable({
        url: 'order/getOrderList',
        height: 300,
        columns: [{
            field: 'shopNo',
            width:120,
            visible: false
        },{
            field: 'action',
            width:120,
            visible: false
        },{
            field: 'order_id',
            title: '订单编号',
            align:'center',
            width:120
        },{
            field: 'status',
            title: '洗衣单状态',
            align:'center',
            width:120
        }, {
            field: 'appointDate',
            title: '预约取衣时间',
            align:'center',
            width:120
        },{
            field: 'price',
            title: '订单金额',
            width:120,
            align:'center',
            formatter:function (value,row,index) {
                return row.price.toFixed(2);
            }
        },{
            field: 'shop_name',
            title: '处理门店',
            align:'center',
            width:120
        },{
            field: 'createDate',
            title: '创建时间',
            align:'center',
            width:120
        },{
            field: 'payDate',
            title: '支付时间',
            align:'center',
            width:120
        }, {
            field: 'takeDate',
            title: '取衣时间',
            align:'center',
            width:120
        },{
            field: 'sendDate',
            title: '送洗时间',
            align:'center',
            width:120
        },{
            field: 'washDate',
            title: '清洗时间',
            align:'center',
            width:120
        },{
            field: 'hangDate',
            title: '上挂时间',
            align:'center',
            width:120
        },{
            field: 'receiveDate',
            title: '领取时间',
            align:'center',
            width:120
        },{
            field: 'takeBackDate',
            title: '领回时间',
            align:'center',
            width:120
        },{
            field: 'deliverDate',
            title: '门店送衣时间',
            align:'center',
            width:120
        },{
            field: 'confirmDate',
            title: '顾客确认时间',
            align:'center',
            width:120
        }
        ,{
            field: 'act',
            title: '操作',
            align:'center',
            width:120,
            formatter:function(value,row,index){
                var result = "";
                var obj = new Array();
                obj[0] = row.order_id;
                obj[1] = row.action;
                obj[2] = data.roleId;
                obj.join(",");
                if(shop_admin_role_id == data.roleId || shop_worker_role_id  == data.roleId){
                    for(var j = 0,len = shopOrderList.length; j < len; j++){
                        if(row.status == shopOrderList[j]){
                            result += "<button class='btn btn-info btn-min-width js-action-order-btn' onclick=\"handleOrder('"+obj+"')\" type='button'>"+
                                row.action
                                +"</button>"
                        }
                    }
                }else if( center_worker_role_id == data.roleId || center_admin_role_id == data.roleId){
                    for(var j = 0,len = centerOrderList.length; j < len; j++){
                        if(row.status == centerOrderList[j]){
                            result += "<button class='btn btn-info btn-min-width js-action-order-btn' onclick=\"handleOrder('"+obj+"')\" type='button'>"+
                                row.action
                                +"</button>"
                        }
                    }
                }else {
                    for(var j = 0,len = shopOrderList.length; j < len; j++){
                        if(row.status == shopOrderList[j]){
                            result += "<button class='btn btn-info btn-min-width js-action-order-btn' onclick=\"handleOrder('"+obj+"')\" type='button'>"+
                                row.action
                                +"</button>"
                        }
                    }
                    for(var j = 0,len = centerOrderList.length; j < len; j++){
                        if(row.status == centerOrderList[j]){
                            result += "<button class='btn btn-info btn-min-width js-action-order-btn' onclick=\"handleOrder('"+obj+"')\" type='button'>"+
                                row.action
                                +"</button>"
                        }
                    }
                }
                return result;
            }
        }],
        cache: false,
        sidePagination: "server",
        pagination: true,
        queryParamsType:'',
        pageNumber: 1,//初始化加载第一页，默认第一页,并记录
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10,20], //可供选择的每页的行数（*）
        showHeader: true,
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                orderId:orderId,
                status:status
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
     * 查询订单
     */
    $('.js-order-search-btn').on(
        "click",function () {
            orderId = $('.js-order-id').val();
            status = $('.js-order-status').val();
            var opt = {
                url: "order/getOrderList",
                silent: true,
                query:{
                    orderId:orderId,
                    status:status
                }
            };
            //带参数 刷新
            $(".js-laundry-order-tab").bootstrapTable('refresh', opt);
        }
    )

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////订单管理结束////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////分类管理开始////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    var catName = $('.js-cat-name').val();
    $('.js-category-panel').on(
        "click",function () {
            $('.js-laundry-shop').css("display",'none');
            $('.js-laundry-worker').css("display",'none');
            $('.js-laundry-order').css("display",'none');
            $('.js-laundry-category').css("display",'');
            $('.js-laundry-goods').css("display",'none');
            $('.js-person-info').css("display",'none');
        }
    );
    /**
     * 分类管理表格初始化
     */
    $('.js-laundry-category-tab').bootstrapTable({
        url: 'cat/getCategoryList',
        height: 300,
        columns: [{
            field: 'cat_no',
            title: '分类编号',
            width: 120,
            align:'center'
        },{
            field: 'img_path',
            title: '图片',
            width: 120,
            align:'center',
            formatter:function(value,row,index){
                var s = '<a class = "view"  href="javascript:void(0)"><img style="width:60px;height:50px;"  src="'+row.img_path+'" /></a>';
                return s;
            },
        }, {
            field: 'cat_name',
            title: '分类名称',
            align:'center',
            width: 120
        }, {
            field: 'goods_manage',
            title: '物品管理',
            align:'center',
            width: 120,
            formatter: function (value, row, index) {
                var result = "";
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"showGoodsPanel('" + row.cat_no + "')\" title='物品管理'><span class='glyphicon glyphicon-th'></span></a>";
                return result;
            }
        }, {
            field: 'imgAction',
            title: '图片管理',
            align:'center',
            width: 120,
            formatter:function(value,row,index){
                var result = "";
                var obj = new Array();
                obj[0] = row.cat_no;
                obj[1] = row.cat_name;
                obj[2] = row.img_path;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editCatsImg('" + obj + "')\" title='修改图片'><span class='glyphicon glyphicon-picture'></span></a>";
                return result;
            }
        },{
            field: 'act',
            title: '操作',
            width: 120,
            align:'center',
            formatter: function (value, row, index) {
                var result = "";
                var obj = new Array();
                obj[0] = row.cat_no;
                obj[1] = row.cat_name;
                obj[2] = row.url;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editCategory('" + obj + "')\" title='编辑'><span class='glyphicon glyphicon-edit'></span></a>";
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteCategory('" + row.cat_no + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
                return result;
            }
        }],
        cache: false,
        sidePagination: "server",
        pagination: true,
        queryParamsType: '',
        pageNumber: 1,//初始化加载第一页，默认第一页,并记录
        pageSize: 3, //每页的记录行数（*）
        pageList: [3, 5, 10], //可供选择的每页的行数（*）
        showHeader: true,
        queryParams: function (params) {
            var param = {
                pageSize: params.pageSize,
                pageNumber: params.pageNumber,
                catName:catName
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////分类管理结束////////////////////////////////////////////////////
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
            $('.js-laundry-goods').css("display",'none');
            $('.js-setting').css('display','none');
            $('.js-account-lab').text(data.adminNo);
            $('.js-name-lab').text(data.name); //姓名
            $('.js-sex-lab').text(data.sex);//性别
            $('.js-tel-num-lab').text(data.telNum); //手机号
            $('.js-email-lab').text(data.email);//邮箱
            $('.js-email-lab').text();//邮箱
            $('.js-role-lab').text(data.roleName);
            $('.js-shop-lab').text(data.shopName);
        }
    );
    /**
     * 点击修改个人信息
     */
    $('.js-person-update-btn').on(
        "click",function () {
            $('.js-title-worker').text('修改个人信息');
            $('.js-worker-no-modal-div').css("display", '');
            $('.js-admin-no-modal').text(data.adminNo);
            $('.js-name-modal-worker').val(data.name); //姓名
            $('.js-tel-num-modal-worker').css("display", 'none'); //手机号
            $('.js-modal-tel-num-lab').text('所属分类');
            $('.js-worker-sex-sel').val(data.sex); //性别
            $('.js-email-per-div').css("display", 'none'); //邮箱

            $('.js-tel-num-worker-lab').css("display", '');
            $('.js-tel-num-worker-lab').text(data.roleName);
            //所属门店
            $('.js-role-lab').text('所属门店');
            $('.js-worker-cate-sel-lab').css("display", '');
            $('.js-worker-cate-sel-lab').text(data.shopName);
            $('.js-worker-cate-sel-div').css("display", 'none');
            action = "personEdit";
        }
    );
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////个人信息结束////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.js-setting-panel').on(
        "click",function () {
            $('.js-laundry-worker').css("display", 'none');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", 'none');
            $('.js-laundry-category').css("display",'none');
            $('.js-laundry-goods').css("display",'none');
            $('.js-person-info').css("display",'none');
            $('.js-setting').css('display','');
            $('.js-setting-admin-no').text(data.adminNo);

        }
    );
};

/**
 * 删除门店
 */
function deleteLaundryShop(data) {
    var url = "shop/delete";
    var msg = SUCCESS_DELETE_MSG;
    var errorMsg = FAIL_DELETE_MSG;
    var datas = data.split(',');
    var dataToObj = {
        "principalNo":datas[0],
        "shopNo":datas[1]
    }
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
                callRemoteFunction(url,dataToObj,msg,errorMsg);
            }
        }
    });

}

/**
 * 编辑门店信息
 */
function editLaundryShop(data){
    action = 'Edit';
    showShopModal(data);
    $('#shop').modal('show');
}
/**
 * 选择门店分类发生改变事件
 */
function selectShopCate(obj) {
    $('.js-worker-tab').bootstrapTable('destroy');
}

/**
 * 重置输入框
 */
$('.js-shop-reset-btn').on(
    "click",function () {
        $('.js-principal-name').val('');
        $('.js-shop-area').val('');
    }
)
/**
 * 显示添加/修改门店模态框
 * @param data
 */
function showShopModal(data) {
    if("Edit" == action){
        var datas = data.split(',');
        $('.js-title-shop').text('修改门店信息');
        $('.js-shop-no-modal-div').css("display", '');
        $('.js-user-account-modal-shop').val(datas[0]); //负责人工号
        $('.js-shop-no-modal').text(datas[1]); //门店编号
        $('.js-area-modal-shop').val(datas[2]); //门店所属区域
        $('.js-user-modal-shop').val(datas[3]); //门店负责人姓名
        $('.js-address-modal-shop').val(datas[4]); //门店地址
        $('.js-name-modal-shop').val(datas[5]); //门店名
    }else if("Add" == action){
        $('.js-title-shop').text('添加门店');
        $('.js-shop-no-modal-div').css("display", 'none');
        $('.js-user-account-modal-shop').val(''); //负责人工号
        $('.js-shop-no-modal').text(''); //门店编号
        $('.js-area-modal-shop').val(''); //门店所属区域
        $('.js-user-modal-shop').val(''); //门店负责人姓名
        $('.js-address-modal-shop').val(''); //门店地址
        $('.js-name-modal-shop').val(''); //门店名
    }
}

/**
 * 门店业务分析
 */
function showShopServicePanel() {

}
/*************************************************************************************************
 *                                   员工管理
 *************************************************************************************************/
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
/*******************************************************************************************************
 *                                        订单模块
 ********************************************************************************************************/
/**
 * 清空订单编号/订单状态输入框
 */
$('.js-order-reset-btn').on(
    "click",function () {
        $('.js-order-status').val('');
        $('.js-order-id').val('');
    }
)
/**
 * 处理订单
 */
function handleOrder(data) {
    var datas = data.split(',');
    var orderId = datas[0];
    var action = datas[1];
    var dataToObj = {
        "orderId":orderId,
        "action":action
    }
    var url = "order/handle";
    var msg = SUCCESS_ACTION_MSG;
    var errorMsg = FAIL_ACTION_MSG;
    callRemoteFunction(url,dataToObj,msg,errorMsg);
    var opt = {
        url: "order/getOrderList",
        silent: true
    };
    //带参数 刷新
    $(".js-laundry-order-tab").bootstrapTable('refresh', opt);

};
/*******************************************************************************************************
 *                                           分类模块
 ********************************************************************************************************/
/**
 * 根据分类名称查询分类
 */
$('.js-category-search-btn').on(
    "click",function () {
        var catName = $('.js-category-name').val();
        var queryParam = {
            "catName":catName
        }
        refreshCategoty(queryParam);
    }
);
/**
 * 添加分类
 */
$('.js-category-add-btn').on(
    "click",function () {
        action = 'Add';
        initFileInput('js-cat-img');
        showCategoryModal();
        //导入文件上传完成之后的事件
        $(".js-cat-img").on("fileuploaded", function (event, data, previewId, index) {
            var res = data.response;
            if (SUCCESS_CODE == res.code) {
                $('.js-cat-img-url').text(res.data.fileUrl);//得到图片路径
            }else{
                return;
            }
        });
    }
);
/**
 * 清空分类名称输入框
 */
$('.js-category-reset-btn').on(
    "click",function () {
        $('.js-category-name').val('');
    }
);
/**
 * 修改分类
 */
function editCategory(data) {
    action = 'Edit';
    $('.js-cat-title').text('修改分类');
    $('.js-cat-img-div').css('display','none');
    var datas = data.split(',');
    showCategoryModal(datas);
    $('#category').modal('show');
}

/**
 * 显示添加修改分类模态框
 * @param data
 */
function  showCategoryModal(data) {
    if('Add' == action){
        $('.js-cat-title').text('新增分类');
        $('.js-cat-no-div').css("display",'none');
        $('.js-cat-no').text('');
        $('.js-cat-name').val('');
        $('.js-cat-name-lab').css("display",'none');
        $('.js-cat-name-div').css("display",'');
    }else{
        $('.js-cat-title').text('修改分类');
        $('.js-cat-no-div').css("display",'');
        $('.js-cat-name-lab').css("display",'none');
        $('.js-cat-name-div').css("display",'');

        $('.js-cat-no').text(data[0]);
        $('.js-cat-name').val(data[1]);
    }
}
/**
 * 提交添加/修改分类事件
 */
$('.js-category-ok').on(
    "click",function () {
        var url = "";
        var msg = "";
        var errorMsg = "";
        var catName = $('.js-cat-name').val();
        var catNo = $('.js-cat-no').text();
        var imgUrl = $('.js-cat-img-url').text();
        var datas = {
            "catName":catName,
            "catNo":catNo,
            "imgPath":imgUrl
        };
        if(('' == imgUrl || null == imgUrl) && ( action == 'Add' || action == 'EditImg')){
            alert('请上传图片');
        } else{
            if(action == 'Add'){
                url = "cat/add";
                msg = SUCCESS_ADD_MSG;
                errorMsg = FAIL_ADD_MSG;
            }else{
                url = "cat/update";
                msg = SUCCESS_UPDATE_MSG;
                errorMsg = FAIL_UPDAGE_MSG;
            }
            callRemoteFunction(url,datas,msg,errorMsg);
            clearModal();
            $('#category').modal('hide');
        }
        var queryParam = {
            "catName":''
        }
        refreshCategoty(queryParam);

    }
);

/**
 * 删除分类
 */
function deleteCategory(data) {
    var url = "cat/delete";
    var msg = SUCCESS_DELETE_MSG;
    var errorMsg=FAIL_DELETE_MSG;
    var datas = {
        "catNo": data
    }
    BootstrapDialog.confirm({
        title: '提示',
        message: '确定删除该分类的信息吗?',
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
    var queryParam = {
        "catName":''
    }
    refreshCategoty(queryParam);
}

/**
 * 修改分类图片
 * @param obj
 */
function editCatsImg(obj) {
    var datas = obj.split(',');
    $('.js-cat-title').text('修改分类图片');
    $('.js-cat-name-lab').css('display','');
    $('.js-cat-name-lab').text(datas[1]);
    $('.js-cat-no-div').css('display','');
    $('.js-cat-no').text(datas[0]);
    $('.js-cat-name-div').css('display','none');
    $('.js-cat-img-div').css('display','');
    initFileInput('js-cat-img');
    $('#category').modal('show');
    //导入文件上传完成之后的事件
    $(".js-cat-img").on("fileuploaded", function (event, data, previewId, index) {
        var res = data.response;
        if (SUCCESS_CODE == res.code) {
            $('.js-cat-img-url').text(res.data.fileUrl);//得到图片路径
        }else{
            return;
        }
    });
    action='EditImg'

}
/**
 * 刷新分类管理表格
 */
function refreshCategoty(data) {
    var opt = {
        url: "cat/getCategoryList",
        silent: true,
        query:data
    };
    //带参数 刷新
    $(".js-laundry-category-tab").bootstrapTable('refresh', opt);
}
///////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////商品管理开始////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * 商品管理
 */
function showGoodsPanel(data) {
    $('.js-goods-cat-no').val(data);
    $('.js-laundry-goods').css("display",'');
    $('.js-laundry-category').css("display",'none');
    $('.js-person-info').css("display",'none');
    $('.js-laundry-goods-tab').bootstrapTable('destroy');
    initGoodsTab();
}
/**
 * 返回分类管理
 */
$('.js-go-back-category').on(
    "click",function () {
        $('.js-laundry-goods').css('display','none');
        $('.js-laundry-category').css('display','');
    }
);
/**
 * 初始化商品管理表格
 */
function initGoodsTab() {
    var goodsCatNo = $('.js-goods-cat-no').val();
    var goodsName = $('.js-goods-name').val();
    /**
     * 商品表格初始化
     */
    $('.js-laundry-goods-tab').bootstrapTable({
        url: 'goods/getGoodsList',
        height: 300,
        columns: [{
            field: 'cat_no',
            title: '分类编号',
            visible: false,
            width:100,
            align:'center',
        },{
            field: 'goods_no',
            title: '商品编号',
            width:100,
            align:'center',
        },{
            field: 'img_path',
            title: '图片',
            width:100,
            align:'center',
            formatter:function(value,row,index){
                var s = '<a class = "view"  href="javascript:void(0)"><img style="width:60px;height:50px;"  src="'+row.img_path+'" /></a>';
                return s;
            },
            // events: 'operateEvents'
        }, {
            field: 'cat_name',
            title: '所属分类',
            width:100,
            align:'center',
        },{
            field: 'goods_name',
            title: '商品名称',
            width:100,
            align:'center',
        },{
            field: 'price',
            title: '价格',
            width:100,
            align:'center',
            formatter:function (value,row,index) {
                return row.price.toFixed(2);
            }
        },{
            field: 'img-action',
            title: '图片管理',
            width:70,
            align:'center',
            formatter:function(value,row,index){
                var result = "";
                var obj = new Array();
                obj[0] = row.goods_no;
                obj[1] = row.cat_no;
                obj[2] = row.goods_name;
                obj[3] = row.cat_name;
                obj[4] = row.price;
                obj[5] = row.img_path;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editGoodsImg('" + obj + "')\" title='修改图片'><span class='glyphicon glyphicon-picture'></span></a>";
                return result;
            }
        },{
            field: 'action',
            title: '操作',
            width:70,
            align:'center',
            formatter:function(value,row,index){
                var result = "";
                var obj = new Array();
                obj[0] = row.goods_no;
                obj[1] = row.cat_no;
                obj[2] = row.goods_name;
                obj[3] = row.cat_name;
                obj[4] = row.price;
                obj[5] = row.img_path;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editGoods('" + obj + "')\" title='编辑'><span class='glyphicon glyphicon-edit'></span></a>";
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteGoods('" + row.goods_no + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
                return result;
            }
        }],
        cache: false,
        sidePagination: 'server',
        pagination: true,
        queryParamsType:'',
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 3,                     //每页的记录行数（*）
        pageList: [3,5,10],  //可供选择的每页的行数（*）
        showHeader: true,
        queryParams:function (params) {
            var param = {
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                roleIds:roleIDs,
                catNo:goodsCatNo,
                goodsName:goodsName
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
}

/**
 * 查询商品
 */
$('.js-goods-search-btn').on(
    "click",function () {
        refreshGoods();
    }
);
/**
 * 点击重置,清空输入框
 */
$('.js-goods-reset-btn').on(
    "click",function () {
        $('.js-goods-name').val('');
    }
);
/**
 * 点击添加商品
 */
$('.js-goods-add-btn').on(
    "click",function () {
        action = 'Add';
        $('.js-goods-no-div').css('display','none');
        $('.js-goods-title').text('新增商品');
        /**
         * 上传文件框初始化
         */
        initFileInput('js-goods-img');
        //导入文件上传完成之后的事件
        $(".js-goods-img").on("fileuploaded", function (event, data, previewId, index) {
            var res = data.response;
            if (SUCCESS_CODE == res.code) {
                $('.js-goods-img-url').text(res.data.fileUrl);//得到图片路径
            }else{
                return;
            }
        });
    }
);
/**
 * 编辑商品
 */
function editGoods(obj) {
    action = "Edit";
    var data = obj.split(',');
    $('.js-goods-no-div').css('display','');
    $('.js-goods-title').text('修改商品信息');
    $('.js-goods-img-div').css('display','none');
    $('.js-goods-no').text(data[0]);
    $('.js-goods-name-modal').val(data[2]);
    $('.js-goods-price').val(data[4]);
    $('#goods').modal('show');
}
/**
 * 提交添加/修改商品
 */
$('.js-goods-ok').on(
    "click",function () {
        var goodsNo =  $('.js-goods-no').text();
        var goodsName = $('.js-goods-name-modal').val();
        var price = $('.js-goods-price').val();
        var imgPath = $('.js-goods-img-url').text();
        var catNo = $('.js-goods-cat-no').val();
        var datas = {
            "goodsName":goodsName,
            "catNo":catNo,
            "price":price,
            "imgPath":imgPath,
            "goodsNO":goodsNo
        }
        var url = "";
        var msg = "";
        var errorMsg = "";
        if('Add' == action){
            url = "goods/add";
            msg = SUCCESS_ADD_MSG;
            errorMsg = FAIL_ADD_MSG;
        }else{
            url = "goods/update";
            msg = SUCCESS_UPDATE_MSG;
            errorMsg = FAIL_UPDAGE_MSG;
        }
        callRemoteFunction(url,datas,msg,errorMsg);
        $('.js-goods-img').val('');
        $('#goods').modal('hide');
        refreshGoods();
        clearModal();
    }
)


/**
 * 删除商品
 */
function deleteGoods(data) {
    var url = "goods/delete";
    var msg = SUCCESS_DELETE_MSG;
    var errorMsg=FAIL_DELETE_MSG;
    var datas = {
        "goodsNO": data
    }
    BootstrapDialog.confirm({
        title: '提示',
        message: '确定删除该商品的信息吗?',
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true,
        btnCancelLabel: '取消',
        btnOKLabel: '确定',
        btnOKClass: 'btn-warning',
        callback: function (result) {
            if (result) {
                callRemoteFunction(url,datas,msg,errorMsg);
                refreshGoods();
            }
        }
    });
}

/**
 * 修改图片
 * @param data
 */
function editGoodsImg(data) {
    var datas = data.split(',');
    $('.js-goods-no-img').text(datas[0]);
    $('.js-cat-name-modal-img').text(datas[3]);
    $('.js-goods-name-modal-img').text(datas[2]);
    $('#goodsImg').modal('show');
    initFileInput('js-goods-img-modal');
    //导入文件上传完成之后的事件
    $(".js-goods-img-modal").on("fileuploaded", function (event, data, previewId, index) {
        var res = data.response;
        if (SUCCESS_CODE == res.code) {
            $('.js-goods-img-url-modal').text(res.data.fileUrl);//得到图片路径
        }else{
            return;
        }
    });
};

/**
 * 提交修改图片
 */
$('.js-goods-edit-img-ok').on(
    "click",function () {
        var goodsNo = $('.js-goods-no-img').text();
        var imgpath = $('.js-goods-img-url-modal').text();
        var datas = {
            "goodsNO":goodsNo,
            "imgPath":imgpath,
            "goodsName":'',
            "price":''
        }
        var url = "goods/update";
        var msg = SUCCESS_UPDATE_MSG;
        var errorMsg = FAIL_UPDAGE_MSG;
        callRemoteFunction(url,datas,msg,errorMsg);
        $('#goodsImg').modal('hide');
        $('.js-goods-img-modal').val('');
        refreshGoods();
    }
);
/**
 * 刷新商品管理表格
 */
function refreshGoods() {
    var goodsCatNo = $('.js-goods-cat-no').val();
    var goodsName = $('.js-goods-name').val();
    var opt = {
        url: "goods/getGoodsList",
        query:{
            catNo:goodsCatNo,
            goodsName:goodsName
        }
    };
    //带参数 刷新
    $(".js-laundry-goods-tab").bootstrapTable('refresh', opt);
}
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
        $('.js-get-captcha').removeAttr('disabled');
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


