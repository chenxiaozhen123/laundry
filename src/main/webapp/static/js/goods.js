/**
 * 商品管理
 */
function showGoodsPanel(data) {
    $('.js-goods-cat-no').val(data);
    $('.js-laundry-goods').css("display",'');
    $('.js-laundry-category').css("display",'none');
    $('.js-person-info').css("display",'none');
    $('.js-business-analyse').css("display",'none');
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