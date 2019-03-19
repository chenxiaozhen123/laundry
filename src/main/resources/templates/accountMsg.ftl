<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>消息通知</title>
</head>

<body>
<div>
    <div class="row">
        <span>尊敬的${params.username}${params.gender}：</span>
    </div>
    <div class="row">
        <span>&nbsp;&nbsp;您好！您的入职流程已办理结束，请您仔细阅读以下材料，以免耽误您往后的日常工作以及工资的发放。祝您工作愉快！您的工号为：${params.jobNumber}（工号开通后到正常使用需要等待一至二天，请勿着急，等待正常使用）</span>
    </div>
    <div class="row">
         <span>
            <a>请访问：连锁干洗中心后台管理系统<a>
        </span>
    </div>
    <div class="row">
         <span>
             登录名为工号，初始密码为${params.initalPassword}，请及时修改初始密码（工作证会尽快以快递形式邮寄至您手中，请您耐心等待）。
         </span>
    </div>
</div>
</body>
</html>
