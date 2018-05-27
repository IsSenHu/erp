$(function () {

    /*
    * 显示添加权限的弹出框
    * */
    $("#add").click(function () {
        $("#dialog").dialog({
            width : 500,
            height : 300,
            draggable : true,
            //打开对话框时是否使用特效
            show : "slide",
            //关闭对话框时是否使用特效动画
            hide : "slide",
            closeOnEscape : false,
            buttons: {
                "确定": function() {
                    var name = $("input[name='menuName']").val().trim();
                    var url = $("input[name='menuUrl']").val().trim();
                    var icon = $("input[name='icon']").val().trim();
                    //todo 校验手机号
                    $.ajax({
                        type : 'post',
                        url : '/saveMenu',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify({
                            "name" : name,
                            "url" : url,
                            "icon" : icon
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else if (data.code == 400){
                                $(data.data).each(function (index, obj) {
                                    if(obj.field == 'name'){
                                        showError("input[name='menuName']", obj.defaultMessage);
                                    }else if(obj.field == 'url'){
                                        showError("input[name='menuUrl']", obj.defaultMessage);
                                    }
                                });
                            }else {
                                $("#resultMessage").html("保存失败");
                                $("#result").dialog();
                            }
                        }
                    });
                },
                "取消": function() {
                    $(this).dialog("close");
                }
            },
        });
    });
    $("input[name='menuName']").focus(resetInput);
    $("input[name='menuUrl']").focus(resetInput);
    /*
    * 搜索
    * */
    $("#search").click(function () {
        var menuName = $("#menuName").val().trim();
        location.href = "/menus?name=" + menuName;
    });
    /*
    * 删除
    * */
    $(".deleteMenu").click(function () {
        var menuId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteMenu',
            data : 'menuId=' + menuId,
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("删除失败");
                    $("#result").dialog();
                }
            }
        });
    });
    /*
    * 显示角色信息
    * */
    $(".updateMenu").click(function () {
        var menuId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findMenuById',
            data : 'menuId=' + menuId,
            success : function (data) {
                $("input[name='menuName']").val(data.name);
                $("input[name='menuUrl']").val(data.url);
                $("#menuId").val(menuId);
                $("input[name='icon']").val(data.icon);
                $("#dialog").dialog({
                    width : 500,
                    height : 260,
                    draggable : true,
                    //打开对话框时是否使用特效
                    show : "slide",
                    //关闭对话框时是否使用特效动画
                    hide : "slide",
                    closeOnEscape : false,
                    buttons: {
                        "确定": function() {
                            var name = $("input[name='menuName']").val().trim();
                            var url = $("input[name='menuUrl']").val().trim();
                            var menuId = $("#menuId").val();
                            var icon = $("input[name='icon']").val().trim();
                            //todo 校验手机号
                            $.ajax({
                                type : 'post',
                                url : '/saveMenu',
                                contentType : 'application/json;charset=UTF-8',
                                data : JSON.stringify({
                                    "menuId" : menuId,
                                    "name" : name,
                                    "url" : url,
                                    "icon" : icon
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        $(data.data).each(function (index, obj) {
                                            if(obj.field == 'name'){
                                                showError("input[name='menuName']", obj.defaultMessage);
                                            }else if(obj.field == 'url'){
                                                showError("input[name='menuUrl']", obj.defaultMessage);
                                            }
                                        });
                                    }else {
                                        $("#resultMessage").html("保存失败");
                                        $("#result").dialog();
                                    }
                                }
                            });
                        },
                        "取消": function() {
                            $(this).dialog("close");
                        }
                    },
                });
            }
        });
    });
});
/*
* 显示输入错误的信息
* */
function showError(select, message) {
    $(select).css("color", "red").val(message).addClass("errorMessage");
}
/*
* 重置输入框
* */
function resetInput() {
    if($(this).hasClass("errorMessage")){
        $(this).css("color", "black").val("").removeClass("errorMessage");
    }
}