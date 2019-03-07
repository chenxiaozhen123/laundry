$(function(){
    var gridHeight = $(window).height() - $('.js-laundry-shop-tab').offset().top - $('.navbar').height()-$('footer').height()-80;
    $('.js-shop-panel').click(function(){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", '');
        $('.js-laundry-order').css("display", 'none');
    });
    /**
     * 初始化门店管理表格
     */
    $('.js-laundry-shop-tab').bootstrapTable({
        data:[{id:1},{id:2},{id:1},{id:2}],
//			    url: 'data1.json',
        height: gridHeight,
        columns: [{
            field: 'id',
            title: '门店编号'
        },{
            field: 'price',
            title: '所属区域'
        },{
            field: 'name',
            title: '负责人姓名'
        }, {
            field: 'price',
            title: '手机号码'
        },{
            field: 'price',
            title: '地址'
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
    function actionFormatter(value, row, index){
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editById('" + row.id + "')\" title='编辑'><span class='icon-edit'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteById('" + row.id + "')\" title='删除'><span class=' icon-remove'></span></a>";
        return result;

    };
    //////////////////////////////////////////////////////////////////
    ///////////////////////员工管理开始//////////////////////////////
    /////////////////////////////////////////////////////////////////
    $('.js-worker-panel').click(function(){
        $('.js-laundry-worker').css("display", '');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-order').css("display", 'none');
    });
    /**
     * 初始化员工管理表格
     */
    $('.js-laundry-worker-tab').bootstrapTable({
        data:[{id:1},{id:2},{id:1},{id:2}],
//			    url: 'data1.json',
        height: gridHeight,
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
    function actionFormatter(value, row, index){
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editById('" + row.id + "')\" title='编辑'><span class='icon-edit'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteById('" + row.id + "')\" title='删除'><span class=' icon-remove'></span></a>";
        return result;

    };

    //////////////////////////////////////////////////////////////////
    ///////////////////////订单管理开始//////////////////////////////
    /////////////////////////////////////////////////////////////////
    $('.js-order-panel').click(function(){
        $('.js-laundry-worker').css("display", 'none');
        $('.js-laundry-shop').css("display", 'none');
        $('.js-laundry-order').css("display", '');
    });
    $('.js-laundry-order-tab').bootstrapTable({
        data:[{id:1},{id:2},{id:1},{id:2}],
//			    url: 'data1.json',
        height: gridHeight,
        columns: [{
            field: 'id',
            title: '订单编号'
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
    function actionFormatter(value, row, index){
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs blue edit' onclick=\"editById('" + row.id + "')\" title='编辑'><span class='icon-edit'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteById('" + row.id + "')\" title='删除'><span class=' icon-remove'></span></a>";
        return result;

    };

});
function editById(val){
    alert(val)
}
function deleteById(val){
    alert(val)
    console.log(JSON.stringify(val));
};