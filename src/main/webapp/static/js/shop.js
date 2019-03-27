function initShop(data) {
    $('.js-shop-panel').click(function(e){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", '');
        $('.js-laundry-order').css("display", 'none');
        $('.js-laundry-category').css("display",'none');
        $('.js-laundry-goods').css("display",'none');
        $('.js-person-info').css("display",'none');
        $('.js-business-analyse').css("display",'none');
        $('.js-shop-panel').addClass('active').siblings('li').removeClass('active');

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
                var obj = new Array();
                obj[0] = row.shop_no;
                obj[1] = row.shop_name;
                obj.join(",")
                result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"showBusiAnalyse('" + obj + "')\" title='业务查看'><span class='glyphicon glyphicon-th'></span></a>";
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
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5,10,20],  //可供选择的每页的行数（*）
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
}
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