/*
* （一级和二级评论）提交评论以json数据向后台传参
* */
function comment(parentId,type,context) {

    if (context.length == 0 || context == null) {
        alert("评论不能为空");
        return;
    }
    $.ajax({
        url: "/comment",
        type: "post",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "parentId": parentId,
            "context": context,
            "type": type
        }),
        dataType: "json",//返回的数据格式为json
        success: function (data) {
            if (data == 200) {
                window.location.reload();  //刷新页面
                if(type==1){
                    document.getElementById("comment_context").value = "";  //当页面刷新的时候，清空textarea的内容
                }else {
                    document.getElementById("second"+parentId).value = "";  //当页面刷新的时候,清空二级评论textarea的内容
                }
                // $("#comment_section").hide();
            } else {
                alert("提交错误");
            }
        }
    })
}

/*
* 一级评论提交
* */
function postcomment() {
    var questionId = $("#question_id").val();
    var context = $("#comment_context").val();
    comment(questionId,1,context);
}

/*
* 二级评论提交
* */
function secondcommment(e) {
    var dataId = e.getAttribute("data-second");
    var replycontext = $("#second"+dataId).val();
    comment(dataId,2,replycontext);
}


/*
* 超链接登陆请求
* */
function login() {
    window.localStorage.setItem("close", true);
    window.open("https://github.com/login/oauth/authorize?client_id=af512df7287f4930dfe6&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
}


/*
* 二级评论(打开与折叠)
* */
function collapseComment(e) {
    //拿到data-id的值,即一级评论的id
    var dataId = e.getAttribute("data-id");
    //根据data-id的值拼接为二级评论的id,拿到div的标签对象
    var comment = $("#comment"+dataId);
    var commentspan = $("#"+dataId);

    if (e.getAttribute("data-collapse")!=null){
        //折叠二级评论
        comment.removeClass("in");//给class删除一个样式
        commentspan.removeClass("active");  //也可以 e.classList.remove("active");e是标签
        e.removeAttribute("data-collapse");
    }
    else{
        //展开二级评论
        comment.addClass("in");//给class添加一个样式
        commentspan.addClass("active"); //e.classList.add("active")
        e.setAttribute("data-collapse","true");
    }
}
