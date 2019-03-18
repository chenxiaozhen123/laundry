/**
 * index.js用到的常量
 * @type {string}
 */
var action = "Edit";
var roleIDs = '2,3,4,5';
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
shopOrderList[2] = '上挂';
shopOrderList[3] = '领取';
shopOrderList[4] = '取回';
//干洗中心能操作的订单的状态
var centerOrderList = new Array();
centerOrderList[0] = '正在送洗';
centerOrderList[1] = '正在清洗';