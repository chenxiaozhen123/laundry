$(function(){
    $(".btn").on(
        "click",function(){
            $.ajax({
                url:"sys/login",
                data:{"username":$(".username").val(), "password":$(".password").val()},
                dataType:"json",
                type:"post",
                success:function(data){
                    if(JSON.stringify(data) == "true"){
                        window.location.href = "/admin/index.html"
                    }else{
                        alert("用户名或密码错误");
                    }
                },
                error:function (data) {
                    alert("用户名或密码错误");
                }
            })
        });
})