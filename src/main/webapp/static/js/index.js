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
function initPage(data){
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
    $('.js-admin-name').text(data.name);
    $('.js-role-name').text(data.roleName);

    $('.js-laundry-shop-tab').bootstrapTable({
        data:[{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2},{id:1},{id:2}],
//			    url: 'data1.json',
        height: 300,
        columns: [{
            field: 'id',
            title: '门店编号'
        },{
            field: 'area',
            title: '所属区域'
        },{
            field: 'name',
            title: '负责人姓名'
        }, {
            field: 'telNum',
            title: '手机号码'
        },{
            field: 'address',
            title: '地址'
        },{
            field: 'action',
            title: '操作',
            formatter:actionFormatter
        }],
        cache: false,
//			    sidePagination: "server",
        pagination: true,
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 3,                     //每页的记录行数（*）
        pageList: [2,5,10],  //可供选择的每页的行数（*）
    });
    function actionFormatter(value, row, index){
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editById('" + row.id + "')\" title='编辑'><span class='glyphicon glyphicon-edit'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteById('" + row.id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
        return result;

    };
    $('.js-worker-panel').click(function(){
        $('.js-laundry-worker').css("display", '');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-order').css("display", 'none');
    });
    $('.js-laundry-worker-tab').bootstrapTable({
        data:[{id:1},{id:2},{id:1},{id:2}],
//			    url: 'data1.json',
        height: 300,
        columns: [{
            field: 'id',
            title: '员工编号'
        },{
            field: 'price',
            title: '姓名'
        },{
            field: 'name',
            title: '手机号码'
        }, {
            field: 'price',
            title: '所属门店'
        },{
            field: 'price',
            title: '操作',
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
function deleteById() {

}

