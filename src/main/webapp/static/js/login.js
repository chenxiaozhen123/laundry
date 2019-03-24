$(function(){
    $(".btn").on(
        "click",function(){
            $.ajax({
                url:"sys/login",
                data:{"username":$(".username").val(), "password":$(".password").val()},
                dataType:"json",
                type:"post",
                success:function(data){
                    if(SUCCESS_CODE== data.code){
                        window.location.href = "/admin/index.html"
                    }else{
                        alert(data.desc);
                    }
                },
                error:function (data) {
                    alert(data.desc);
                }
            })

        });
})