$(function () {

    /*
    * 显示添加用户的弹出框
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
            /*
                        设置对话框底部的按钮
                    */
            buttons: {
                "确定": function() {
                    var name = $("input[name='realName']").val().trim();
                    var phone = $("input[name='newPhone']").val().trim();
                    var email = $("input[name='newEmail']").val().trim();
                    //todo 校验手机号
                    $.ajax({
                        type : 'post',
                        url : '/saveUser',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify({
                            "name" : name,
                            "phone" : phone,
                            "email" : email
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else if (data.code == 400){
                                var errors = data.data;
                                $(errors).each(function (index, obj) {
                                   if(obj.field == 'name'){
                                       showError("input[name='realName']", obj.defaultMessage)
                                   }else if(obj.field == 'email'){
                                       showError("input[name='newEmail']", obj.defaultMessage)
                                   }else if(obj.field == 'phone'){
                                       showError("input[name='newPhone']", obj.defaultMessage)
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
    $("input[name='realName']").focus(resetInput);
    $("input[name='newPhone']").focus(resetInput);
    $("input[name='newEmail']").focus(resetInput);
    /*
    * 搜索
    * */
    $("#search").click(function () {
        var username = $("#username").val().trim();
        var name = $("#name").val().trim();
        var phone  = $("#phone").val().trim();
        location.href = "/users?username=" + username + "&name=" + name + "&phone=" + phone;
    });
    /*
    * 删除
    * */
    $(".deleteUser").click(function () {
        var userId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteUser',
            data : 'userId=' + userId,
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else if(data.code == 500){
                    $("#resultMessage").html("删除失败");
                    $("#result").dialog();
                }
            }
        });
    });
    /*
    * 用户启用
    * */
    $(".userEnabled").click(function () {
        var userId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/changeUserStatus',
            data : 'userId=' + userId,
            success :function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    ("#resultMessage").html("启用/禁用失败");
                    $("#result").dialog();
                }
            }
        });
    });

    /*
    * 用户禁用
    * */
    $(".userDisabled").click(function () {
        var userId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/changeUserStatus',
            data : 'userId=' + userId,
            success :function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    ("#resultMessage").html("启用/禁用失败");
                    $("#result").dialog();
                }
            }
        });
    });
    /**
     * 选择操作
     * */
    var selectOt;
    $(".setRole").click(function () {
        var userId = $(this).val();
        $("#addRoles").val(userId);
        $("#deleteRoles").val(userId);
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
    /*
    * 查询出用户已拥有的角色和设置多选未拥有的角色
    * */
    $("#addRoles").click(function () {
        var userId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/getEditRoleVo',
            data : 'userId=' + userId + '&op=add',
            success : function (data) {
                $("#message").html("未拥有的角色，请选择添加（多选）");
                var have = data.have;
                var none = data.none;
                var table = $("#tableRole");
                table.empty();
                table.append('<tr>\n' +
                                '<th style="width: 16%;">序号</th>' +
                                '<th>角色</th>' +
                                '<th>角色描述</th>' +
                            '</tr>');
                $(have).each(function (index, obj) {
                    table.append('<tr>' +
                                    '<td>' + (index + 1) +'</td>' +
                                    '<td>' + obj.name + '</td>' +
                                    '<td>' + obj.description + '</td>' +
                                '</tr>');
                });
                var select = $("#selectRole");
                select.empty();
                $(none).each(function (index, obj) {
                    select.append('<option value="' + obj.roleId + '">' + obj.name + '</option>');
                });
                selectOt.dialog("close");
                var opt = $("#addRolesDialog").dialog({
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
                           var roleIdStr = $("#selectRole").val();
                           $.ajax({
                               type : 'post',
                               url : '/addUserRoles',
                               data : 'userId=' + $("#addRoles").val() + '&roleIdStr=' + roleIdStr,
                               success : function (data) {
                                   if(data.code == 200){
                                       $("#resultMessage").html("添加成功");
                                       $("#result").dialog();
                                       var result = $("#result").dialog();
                                       window.setTimeout(function () {
                                           result.dialog("close");
                                           opt.dialog("close");
                                       }, 500)
                                       $("#result").dialog();
                                   }else if(data.code == 400){
                                       $("#resultMessage").html("请至少选择一个角色");
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
    /*
    * 查询出用户已拥有的角色和设置要删除的角色
    * */
    $("#deleteRoles").click(function () {
        var userId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/getEditRoleVo',
            data : 'userId=' + userId + '&op=del',
            success : function (data) {
                $("#message").html("已拥有的角色，请选择删除（多选）");
                var have = data.have;
                var table = $("#tableRole");
                var select = $("#selectRole");
                table.empty();
                select.empty();
                table.append('<tr>\n' +
                    '<th style="width: 16%;">序号</th>' +
                    '<th>角色</th>' +
                    '<th>角色描述</th>' +
                    '</tr>');
                $(have).each(function (index, obj) {
                    table.append('<tr>' +
                        '<td>' + (index + 1) +'</td>' +
                        '<td>' + obj.name + '</td>' +
                        '<td>' + obj.description + '</td>' +
                        '</tr>');
                    select.append('<option value="' + obj.roleId + '">' + obj.name + '</option>');
                });
                selectOt.dialog("close");
                var opt = $("#addRolesDialog").dialog({
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
                            var roleIdStr = $("#selectRole").val();
                            $.ajax({
                                type : 'post',
                                url : '/deleteUserRoles',
                                data : 'userId=' + $("#addRoles").val() + '&roleIdStr=' + roleIdStr,
                                success : function (data) {
                                    if(data.code == 200){
                                        $("#resultMessage").html("删除成功");
                                        var result = $("#result").dialog();
                                        window.setTimeout(function () {
                                            result.dialog("close");
                                            opt.dialog("close");
                                        }, 500)
                                    }else if(data.code == 400){
                                        $("#resultMessage").html("请至少选择一个角色");
                                        $("#result").dialog();
                                    }else if(data.code == 500){
                                        $("#resultMessage").html("删除失败");
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