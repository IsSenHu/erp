$(function () {

    /*
    * 显示添加角色的弹出框
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
                    var name = $("input[name='roleName']").val().trim();
                    var description = $("textarea[name='roleDescription']").val().trim();
                    //todo 校验手机号
                    $.ajax({
                        type : 'post',
                        url : '/saveRole',
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
                                showError("input[name='roleName']", error.defaultMessage)
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
    $("input[name='roleName']").focus(resetInput);
    /*
    * 搜索
    * */
    $("#search").click(function () {
        var roleName = $("#roleName").val().trim();
        location.href = "/roles?roleName=" + roleName;
    });
    /*
    * 删除
    * */
    $(".deleteRole").click(function () {
        var roleId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteRole',
            data : 'roleId=' + roleId,
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
    $(".updateRole").click(function () {
        var roleId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findRoleById',
            data : 'roleId=' + roleId,
            success : function (data) {
                $("input[name='roleName']").val(data.name);
                $("textarea[name='roleDescription']").val(data.description);
                $("#roleId").val(data.roleId);
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
                            var name = $("input[name='roleName']").val().trim();
                            var description = $("textarea[name='roleDescription']").val().trim();
                            var roleId = $("#roleId").val();
                            //todo 校验手机号
                            $.ajax({
                                type : 'post',
                                url : '/saveRole',
                                contentType : 'application/json;charset=UTF-8',
                                data : JSON.stringify({
                                    "roleId" : roleId,
                                    "name" : name,
                                    "description" : description
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        var error = data.data;
                                        showError("input[name='roleName']", error.defaultMessage)
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
    /**
     * 选择操作
     * */
    var selectOt;
    $(".setPermission").click(function () {
        var roleId = $(this).val();
        $("#addIt").val(roleId);
        $("#delete").val(roleId);
        selectOt = $("#selectOt").dialog({
            width : 260,
            height : 126,
            draggable : true,
            //打开对话框时是否使用特效
            show : "slide",
            //关闭对话框时是否使用特效动画
            hide : "slide",
            closeOnEscape : false,
        });
    });

    /**
     * 查询出已拥有的权限和可以添加的权限
     * */
    $("#addIt").click(function () {
        var roleId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findPermissionHaveByRoleId',
            data : 'roleId=' + roleId + '&op=add',
            success : function (data) {
                $("#message").html("未拥有的权限，请选择添加(多选)");
                var table = $("#table");
                var select = $("#select");
                table.empty();
                var have = data.have;
                table.append('<tr>\n' +
                    '<th style="width: 16%;">序号</th>' +
                    '<th>权限</th>' +
                    '<th>权限描述</th>' +
                    '</tr>');
                $(have).each(function (index, obj) {
                    table.append('<tr>' +
                        '<td>' + (index + 1) +'</td>' +
                        '<td>' + obj.name + '</td>' +
                        '<td>' + obj.description + '</td>' +
                        '</tr>');
                });
                var none = data.none;
                select.empty();
                $(none).each(function (index, obj) {
                    select.append('<option value="' + obj.permissionId + '">' + obj.name + '</option>');
                });
                selectOt.dialog("close");
                var opt = $("#opt").dialog({
                    width : 600,
                    height : 400,
                    draggable : true,
                    //打开对话框时是否使用特效
                    show : "slide",
                    //关闭对话框时是否使用特效动画
                    hide : "slide",
                    closeOnEscape : false,
                    buttons: {
                        "确定": function() {
                            var permissionIdStr = $("#select").val();
                            $.ajax({
                                type : 'post',
                                url : '/addRolePermissions',
                                data : 'roleId=' + $("#addIt").val() + '&permissionIdStr=' + permissionIdStr,
                                success : function (data) {
                                    if(data.code == 200){
                                        $("#resultMessage").html("添加成功");
                                        var result = $("#result").dialog();
                                        window.setTimeout(function () {
                                            result.dialog("close");
                                            opt.dialog("close");
                                        }, 500)
                                    }else if(data.code == 400){
                                        $("#resultMessage").html("请至少选择一个权限");
                                        $("#result").dialog();
                                    }else if(data.code == 500){
                                        $("#resultMessage").html("添加失败");
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
     * 获取该角色可删除的权限
     * */
    $("#delete").click(function () {
        var roleId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findPermissionHaveByRoleId',
            data : 'roleId=' + roleId + '&op=delete',
            success : function (data) {
                var have = data.have;
                var table = $("#table");
                var select = $("#select");
                table.empty();
                select.empty();
                table.append('<tr>\n' +
                    '<th style="width: 16%;">序号</th>' +
                    '<th>权限</th>' +
                    '<th>权限描述</th>' +
                    '</tr>');
                $("#message").html("已拥有的权限，请选择移除(多选)");
                $(have).each(function (index, obj) {
                    table.append('<tr>' +
                        '<td>' + (index + 1) +'</td>' +
                        '<td>' + obj.name + '</td>' +
                        '<td>' + obj.description + '</td>' +
                        '</tr>');
                    select.append('<option value="' + obj.permissionId + '">' + obj.name + '</option>');
                });
                selectOt.dialog("close");
                var opt = $("#opt").dialog({
                    width : 600,
                    height : 400,
                    draggable : true,
                    //打开对话框时是否使用特效
                    show : "slide",
                    //关闭对话框时是否使用特效动画
                    hide : "slide",
                    closeOnEscape : false,
                    buttons: {
                        "确定": function() {
                            var permissionIdStr = $("#select").val();
                            $.ajax({
                                type : 'post',
                                url : '/deleteRolePermissions',
                                data : 'roleId=' + $("#delete").val() + '&permissionIdStr=' + permissionIdStr,
                                success : function (data) {
                                    if(data.code == 200){
                                        $("#resultMessage").html("移除成功");
                                        var result = $("#result").dialog();
                                        window.setTimeout(function () {
                                            result.dialog("close");
                                            opt.dialog("close");
                                        }, 500)
                                        $("#result").dialog();
                                    }else if(data.code == 400){
                                        $("#resultMessage").html("请至少选择一个权限");
                                        $("#result").dialog();
                                    }else if(data.code == 500){
                                        $("#resultMessage").html("移除失败");
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