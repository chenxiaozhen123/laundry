function initOrder(data) {
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
            $('.js-business-analyse').css("display",'none');
            $('.js-order-panel').addClass('active').siblings('li').removeClass('active');
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
            var param = {}
            if(shop_admin_role_id == data.roleId || shop_worker_role_id  == data.roleId){
                param = {
                    pageSize:params.pageSize,
                    pageNumber:params.pageNumber,
                    orderId:orderId,
                    status:status,
                    shopNo:data.shopNo
                }
            }else{
                param = {
                    pageSize:params.pageSize,
                    pageNumber:params.pageNumber,
                    orderId:orderId,
                    status:status
                }
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
                    "orderId":orderId,
                    "status":status,
                    "shopNo":data.shopNo
                }
            };
            //带参数 刷新
            $(".js-laundry-order-tab").bootstrapTable('refresh', opt);
        }
    )

}
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
        query:{
            "shopNo":data.shopNo
        },
        silent: true
    };
    //带参数 刷新
    $(".js-laundry-order-tab").bootstrapTable('refresh', opt);

};