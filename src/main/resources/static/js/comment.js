/*
* （一级和二级评论）提交评论以json数据向后台传参
* */
function comment(parentId, type, context,annotype) {

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
            "type": type,
            "annoType":annotype
        }),
        dataType: "json",//返回的数据格式为json
        success: function (data) {
            if (data == 200) {
                window.location.reload();  //刷新页面
                if (type == 1) {
                    document.getElementById("comment_context").value = "";  //当页面刷新的时候，清空textarea的内容
                } else {
                    document.getElementById("second" + parentId).value = "";  //当页面刷新的时候,清空二级评论textarea的内容
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
    var anno=document.getElementById("anno").value;
    //anno为实名，表示现在为匿名提交，anntype=1
    comment(questionId, 1, context,anno=='实名'? 1:0);
}

/*
* 二级评论提交
* */
function secondcommment(e) {
    var dataId = e.getAttribute("data-second");
    var replycontext = $("#second" + dataId).val();
    var secondanno=document.getElementById("secondanno"+dataId).value;
    comment(dataId,2,replycontext,secondanno=='实名'? 1: 0);
}


/*
* 超链接登陆请求
* */
function login() {
    window.localStorage.setItem("close", true);
    window.open("https://github.com/login/oauth/authorize?client_id=af512df7287f4930dfe6&redirect_uri="+window.location.protocol+"//"+window.location.host+"/callback&scope=user&state=1");
}


/*
* 二级评论(打开与折叠)
* */
function collapseComment(e) {
    //拿到data-id的值,即一级评论的id
    var dataId = e.getAttribute("data-id");
    //根据data-id的值拼接为二级评论的id,拿到div的标签对象
    var comment = $("#comment" + dataId);
    var commentspan = $("#" + dataId);

    if (e.getAttribute("data-collapse") != null) {
        //折叠二级评论
        comment.removeClass("in");//给class删除一个样式
        commentspan.removeClass("active");  //也可以 e.classList.remove("active");e是标签
        e.removeAttribute("data-collapse");
    } else {
        var twolevel = $("#twolevel" + dataId);
        //当twolevel下子元素只有不为1的时候，不在加载二级评论
        if (twolevel.children().length != 1) {
            //展开二级评论
            comment.addClass("in");//给class添加一个样式
            commentspan.addClass("active"); //e.classList.add("active")
            e.setAttribute("data-collapse", "true");
        } else {
            $.getJSON("/comment/" + dataId, function (data) {
                $.each(data.reverse(), function (index, commentDTO) {
                    var mediacommentuser = $("<div/>", {
                        "class": "media commentuser",
                    })

                    var medialeft = $("<div/>", {
                        "class": "media-left",
                    })

                    var commentimg = $("<a/>", {
                        "href": (commentDTO.annoType==0?commentDTO.user.avatarUrl:'/images/anonymous.png'),
                        "class": "commentimg",
                    })

                    var imgcss = $("<img/>", {
                        "class": "media-object img-thumbnail imgcss",
                        "src": (commentDTO.annoType==0?commentDTO.user.avatarUrl:'/images/anonymous.png')
                    })

                    mediacommentuser.append(medialeft);
                    medialeft.append(commentimg);
                    commentimg.append(imgcss);

                    //下方div
                    var mediabody = $("<div/>", {
                        "class": "media-body"
                    })

                    var commentname = $("<h6/>", {
                        "class": "commentname",
                    })

                    var namecss = $("<span/>", {
                        "class": "namecss",
                        "html": (commentDTO.annoType==0?commentDTO.user.name:'匿名用户')
                    })

                    var span2element = $("<span/>", {
                        "html": commentDTO.context
                    })

                    var span3element = $("<span/>", {
                        "class": "pull-right commenttime",
                        "html": moment(commentDTO.gmtCreate).format("YYYY-MM-DD")
                    })

                    mediacommentuser.append(mediabody);
                    mediabody.append(commentname);
                    commentname.append(namecss);
                    mediabody.append(span2element);
                    mediabody.append(span3element);

                    twolevel.prepend(mediacommentuser);  //在mediacommentuser之前插入twolevel
                });

            });
            //展开二级评论
            comment.addClass("in");//给class添加一个样式
            commentspan.addClass("active"); //e.classList.add("active")
            e.setAttribute("data-collapse", "true");
        }
    }
}


/*
* 标签的提交
* */
function submitTag(value) {
    var previous = $("#tag").val();
    //如果==-1代表没有添加value标签
    if (previous.indexOf(value) == -1) {
        if (previous) {
            //如果有添加过标签就直接把前面的标签加上后面的标签
            $("#tag").val(previous + ',' + value);
        } else {
            //反之...
            $("#tag").val(value);
        }
    }
}

/*
* 点击文本框展示标签
* */
function showTag() {
    var tag = $("#selectTag");
    var tagclass = tag.attr("class");
    if (tagclass == "hidetag") {
        tag.removeClass("hidetag");
        tag.addClass("showtag");
    } else {
        tag.removeClass("showtag")
        tag.addClass("hidetag");
    }
}


/*
* 评论点赞功能实现
* */
function likeCount(e) {
    var id = e.getAttribute("data-like");
    var xmlhttp = new XMLHttpRequest();
    var url = window.location.protocol+"//"+window.location.host+"/like/" + id;
    //window.localStorage.setItem("like", true);
    xmlhttp.open("POST", url, true); //第三个参数是同步异步,主线程只能异步
    xmlhttp.send();
    window.location.reload();  //刷新本页面
}

/*
* 匿名提交
* */
function anonymous() {
    var userId = document.getElementById("user");
    var anonymous = document.getElementById("anonymous");
    if (userId.style.display == 'block') {
        userId.style.display = 'none';
        anonymous.style.display = 'block';
        $("#anno").attr("value","实名");
    } else {
        userId.style.display = 'block';
        anonymous.style.display = 'none';
        $("#anno").attr("value","匿名");
    }
}
function twolevelanonymous(e) {
    var datatwolevel = e.getAttribute("data-twolevel");
    var secondanno = document.getElementById("secondanno"+datatwolevel).value;
    if(secondanno=='实名'){
        $("#secondanno"+datatwolevel).attr("value","匿名");
    }else {
        $("#secondanno"+datatwolevel).attr("value","实名");
    }
}