/**
 * index.js用到的常量
 * @type {string}
 */
var action = "Edit";
var roleIDs = '2,3,4,5';
var shopNo = '';
//角色id
var center_admin_role_id = '5'; // 干洗中心管理员的角色id
var center_worker_role_id = '4'; // 干洗中心员工的角色id
var shop_admin_role_id = '2'; // 门店管理员的角色id
var shop_worker_role_id = '3';// 门店员工的角色id
//权限等级
var admin_root_priority ='5';//超级管理员权限等级
var admin_priority ='4'; //干洗中心管理员/门店管理员权限等级
var worker_priority ='3'; //干洗中心员工/门店员工权限等级
var cannot_shop_no = '-1';//未分配门店的干洗中心管理员/门店管理员的分店编号
//门店能操作的订单的状态
var shopOrderList = new Array();
shopOrderList[0] = '待取衣';
shopOrderList[1] = '已取衣';
// shopOrderList[2] = '正在送洗';
shopOrderList[2] = '已上挂';
shopOrderList[3] = '已领取';
shopOrderList[4] = '已取回';
//干洗中心能操作的订单的状态
var centerOrderList = new Array();
centerOrderList[0] = '送洗中';
centerOrderList[1] = '清洗中';

// 待付款->待取衣->已取衣->正在送洗->正在清洗->上挂->领取->取回->待顾客确认收衣->待评价

//提示信息
var SUCCESS_ADD_MSG = "添加成功";
var FAIL_ADD_MSG = "添加失败";
var SUCCESS_UPDATE_MSG = "修改成功";
var FAIL_UPDAGE_MSG = "修改失败";
var SUCCESS_DELETE_MSG = "删除成功";
var FAIL_DELETE_MSG = "删除失败";
var SUCCESS_ACTION_MSG = "操作成功";
var FAIL_ACTION_MSG = "操作失败";
var SUSSESS_UPDATE_PWD="修改成功!请重新登录"
var SUSSESS_UPDATE_EMAIL="邮箱修改成功!重新登录后可看到更新信息"
var SUSSESS_UPDATE_TEL="手机号码修改成功!重新登录后可看到更新信息"

var SUSSESS_BUSI_ANALYSE = "获取门店业务信息成功";
var FAIL_BUSI_ANALYSE = "获取门店业务信息失败";

