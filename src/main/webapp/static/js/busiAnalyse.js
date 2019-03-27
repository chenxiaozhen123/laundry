var counts =new Array();
var dataChart = [];
function showBusiAnalyse(data){
    var height = $(".js-business-analyse").height()+$('.header').height();
    $(".left-side").css("min-height", height + "px");
    $('.js-laundry-worker').css("display", 'none');
    $('.js-laundry-shop').css("display", 'none');
    $('.js-laundry-order').css("display", 'none');
    $('.js-laundry-category').css("display",'none');
    $('.js-laundry-goods').css("display",'none');
    $('.js-person-info').css("display",'none');
    $('.js-business-analyse').css("display",'');
    var datas = data.split(',')
    $('.js-analyse-shop-name').text(datas[1]);
    var param = {
        "shopNo":datas[0]
    }
    /**
     * 获取前30天的日期
     * @type {Array}
     */
    var thrityMonth = [];
    for(var i = 0;i<30;i++){
        thrityMonth.unshift(new Date(new Date().setDate(new Date().getDate()-i)).toLocaleDateString())
    }
    for(var j = 0;j<thrityMonth.length;j++){
        thrityMonth[j] =formatDate(thrityMonth[j])
    }
    $.ajax({
        url:'order/shopBusiAnalyse',
        data:param,
        dataType:"json",
        type:"post",
        success:function(data){
            if( SUCCESS_CODE == data.code){
                for(var j =0;j<thrityMonth.length;j++){
                    for(var i = 0;i<data.data.rows.length;i++){
                        if(data.data.rows[i].time == thrityMonth[j]){
                            counts[counts.length] = data.data.rows[i].count;
                            break;
                        }else{
                            counts[counts.length] = 0;
                            break;
                        }
                    }
                }
                for(var i = 0;i<thrityMonth.length;i++){
                    var lne = {};
                    var tm = thrityMonth[i]
                    var someDate = new Date(Date.parse(tm));//把字符串类型专程Date类型
                    var tms=Date.UTC(someDate.getFullYear(), someDate.getMonth(), someDate.getDate());//进行Date.UTC处理
                    lne['x']= tms
                    lne['y']=counts[i]
                    dataChart.push(lne);
                }
                chart(dataChart)
            }else{
                BootstrapDialog.alert({
                    title: '提示',
                    message: FAIL_BUSI_ANALYSE +' '+data.desc,
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
                message: FAIL_BUSI_ANALYSE +' '+data.desc,
                type: BootstrapDialog.TYPE_WARNING,
                closable: true,
                draggable: true,
                buttonLabel: '确定'
            });
        }
    });

}

/**
 * 画折线图表格
 * @param dataChart
 */
function chart(dataChart) {
    Highcharts.chart('container', {
        credits: {
            enabled: false
        },
        chart: {
            type: 'line'                          //指定图表的类型，默认是折线图（line）
        },
        title: {
            text: '门店业务查看'                 // 标题
        },
        xAxis: {
            type: 'datetime',
            tickInterval: 7* 24 * 3600 * 1000, // 坐标轴刻度间隔为一星期
            tickWidth: 1,
            gridLineWidth: 1,
            labels: {
                align: 'left',
                x: 3,
                y: -3
            },
            // 时间格式化字符
            // 默认会根据当前的刻度间隔取对应的值，即当刻度间隔为一周时，取 week 值
            dateTimeLabelFormats: {
                week: '%m-%d',
                day:"%m-%e",
                month:"%Y-%m",
            },
        },
        yAxis: {
            title: {
                text: '订单量'                // y 轴标题
            },
            allowDecimals:false
        },
        tooltip:{
            shared:true,
            crosshairs:true,
            dateTimeLabelFormats:{
                hour:"%m-%e %H:%M",
                day:"%m-%e",
                month:"%Y-%m",
            },
        },
        series: [{                              // 数据列
            lineWidth: 1,
            name: '订单量',                        // 数据列名
            data: dataChart             // 数据

        }]
    });
}
var rate =$('.js-rate-praise').text();
$('.js-rate-praise').on(
    "click",function () {
        $('.js-rate-praise').addClass('active').siblings('li').removeClass('active');
        rate = $('.js-rate-praise').text();
        var data = {
            "rate":rate
        }
        refreshReview(data)
    }
)
$('.js-rate-middle').on(
    "click",function () {
        $('.js-rate-middle').addClass('active').siblings('li').removeClass('active');
        rate = $('.js-rate-middle').text();
        var data = {
            "rate":rate
        }
        refreshReview(data)
    }
)
$('.js-rate-negative').on(
    "click",function () {
        $('.js-rate-negative').addClass('active').siblings('li').removeClass('active');
        rate = $('.js-rate-negative').text();
        var data = {
            "rate":rate
        }
        refreshReview(data)
    }
)
/**
 * 评价表格初始化
 */
$('.js-review-tab').bootstrapTable({
    url: 'review/getReviewList',
    height: 300,
    columns: [{
        field: 'order_id',
        title: '订单编号',
        width: 150,
        align:'center'
    },{
        field: 'comment',
        title: '评价内容',
        align:'center',
        width: 420
    }, {
        field: 'cname',
        title: '评价用户',
        align:'center',
        width: 150
    },{
        field: 'createDate',
        title: '评价时间',
        align:'center',
        width: 160
    }],
    sidePagination: "server",
    pagination: true,
    queryParamsType: '',
    pageNumber: 1,//初始化加载第一页，默认第一页,并记录
    pageSize: 10, //每页的记录行数（*）
    pageList: [10,20,50], //可供选择的每页的行数（*）
    showHeader: true,
    queryParams: function (params) {
        var param = {
            pageSize: params.pageSize,
            pageNumber: params.pageNumber,
            rate:rate
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
function refreshReview(data) {
    var opt = {
        url: 'review/getReviewList',
        silent: true,
        query:data
    };
    //带参数 刷新
    $(".js-review-tab").bootstrapTable('refresh', opt);
}

