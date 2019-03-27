function initCategory(data) {
    var catName = $('.js-cat-name').val();
    $('.js-category-panel').on(
        "click",function () {
            $('.js-laundry-shop').css("display",'none');
            $('.js-laundry-worker').css("display",'none');
            $('.js-laundry-order').css("display",'none');
            $('.js-laundry-category').css("display",'');
            $('.js-laundry-goods').css("display",'none');
            $('.js-person-info').css("display",'none');
            $('.js-business-analyse').css("display",'none');
            $('.js-category-panel').addClass('active').siblings('li').removeClass('active');
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
}
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