function postcomment() {
    var questionId = $("#question_id").val();
    var context = $("#comment_context").val();
    if(context.length==0||context==null){
        alert("评论不能为空");
    }
    $.ajax({
        url: "/comment",
        type: "post",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "parentId": questionId,
            "context": context,
            "type": 1
        }),
        dataType: "json",//返回的数据格式为json
        success: function (data) {
            if (data==200) {
                $("#comment_section").hide();
            } else {
                alert("提交错误");
            }
        }
    })
}

function login(){
    window.localStorage.setItem("close",true);
    window.open("https://github.com/login/oauth/authorize?client_id=af512df7287f4930dfe6&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
}