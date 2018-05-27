$(function () {

    /*
    * 显示添加权限的弹出框
    * */
    $("#add").click(function () {
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
                    var name = $("input[name='permissionName']").val().trim();
                    var description = $("textarea[name='permissionDescription']").val().trim();
                    //todo 校验手机号
                    $.ajax({
                        type : 'post',
                        url : '/savePermission',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify({
                            "name" : name,
                            "description" : description
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else if (data.code == 400){
                                var error = data.data;
                                showError("input[name='permissionName']", error.defaultMessage)
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
    $("input[name='permissionName']").focus(resetInput);
    /*
    * 搜索
    * */
    $("#search").click(function () {
        var permissionName = $("#permissionName").val().trim();
        location.href = "/permissions?permissionName=" + permissionName;
    });
    /*
    * 删除
    * */
    $(".deletePermission").click(function () {
        var permissionId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deletePermission',
            data : 'permissionId=' + permissionId,
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
    $(".updatePermission").click(function () {
        var permissionId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findPermissionById',
            data : 'permissionId=' + permissionId,
            success : function (data) {
                $("input[name='permissionName']").val(data.name);
                $("textarea[name='permissionDescription']").val(data.description);
                $("#permissionId").val(permissionId);
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
                            var name = $("input[name='permissionName']").val().trim();
                            var description = $("textarea[name='permissionDescription']").val().trim();
                            var permissionId = $("#permissionId").val();
                            //todo 校验手机号
                            $.ajax({
                                type : 'post',
                                url : '/savePermission',
                                contentType : 'application/json;charset=UTF-8',
                                data : JSON.stringify({
                                    "permissionId" : permissionId,
                                    "name" : name,
                                    "description" : description
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        var error = data.data;
                                        showError("input[name='permissionName']", error.defaultMessage)
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
                    }
                });
            }
        });
    });
    /**
     * 显示主菜单设置的面板
     * */
    $(".setMenu").click(function () {
        var permissionId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findMenuByPermissionId',
            data : 'permissionId=' + permissionId,
            success : function (data) {
                var self = data.self;
                if(self == null){
                    $("#menuName").html("还未设置主菜单")
                }else {
                    $("#menuName").html(self.name);
                }
                var enableSet = data.enableSet;
                var select = $("#select");
                select.empty();
                $(enableSet).each(function (index, obj) {
                    select.append('<option value="' + obj.menuId + '">' + obj.name + '</option>');
                });
            }
        });
        var setMenu = $("#setMenu").dialog({
            width : 500,
            height : 230,
            draggable : true,
            //打开对话框时是否使用特效
            show : "slide",
            //关闭对话框时是否使用特效动画
            hide : "slide",
            closeOnEscape : false,
            buttons: {
                "取消主菜单" : function () {
                    $.ajax({
                        type : 'post',
                        url : '/cancelMenu',
                        data : 'permissionId=' + permissionId,
                        success : function (data) {
                            if(data.code == 200){
                                $("#resultMessage").html("取消成功");
                                var result = $("#result").dialog();
                                window.setTimeout(function () {
                                    setMenu.dialog("close");
                                    result.dialog("close");
                                }, 500)
                            }else if(data.code == 500) {
                                $("#resultMessage").html("取消失败");
                                var result = $("#result").dialog();
                                window.setTimeout(function () {
                                    setMenu.dialog("close");
                                    result.dialog("close");
                                }, 500)
                            }else {
                                $("#resultMessage").html("无法取消");
                                var result = $("#result").dialog();
                                window.setTimeout(function () {
                                    setMenu.dialog("close");
                                    result.dialog("close");
                                }, 500)
                            }
                        }
                    });
                },
                "确定": function() {
                    var menuId = $("#select").val();
                    $.ajax({
                        type : 'post',
                        url : '/setMajorMenu',
                        data : 'permissionId=' + permissionId + '&menuId=' + menuId,
                        success : function (data) {
                            if(data.code == 200){
                                $("#resultMessage").html("设置成功");
                                var result = $("#result").dialog();
                                window.setTimeout(function () {
                                    setMenu.dialog("close");
                                    result.dialog("close");
                                }, 500)
                            }else if (data.code == 500) {
                                $("#resultMessage").html("设置失败");
                                var result = $("#result").dialog();
                                window.setTimeout(function () {
                                    setMenu.dialog("close");
                                    result.dialog("close");
                                }, 500)
                            }
                        }
                    });
                },
                "取消": function() {
                    $(this).dialog("close");
                }
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