$(function () {
    $("#add").click(function () {
        $("#dialog").dialog({
            width : 500,
            height : 290,
            draggable : true,
            //打开对话框时是否使用特效
            show : "slide",
            //关闭对话框时是否使用特效动画
            hide : "slide",
            closeOnEscape : false,
            buttons: {
                "确定": function() {
                    var name = $("#newName").val().trim();
                    var phone = $("#newPhone").val().trim();
                    var address = $("#newAddress").val().trim();
                    $.ajax({
                        type : 'post',
                        url : '/saveCustomer',
                        contentType : 'application/json;charset=utf-8',
                        data : JSON.stringify({
                            "name" : name,
                            "phone" : phone,
                            "address" : address
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else if (data.code == 400){
                                var errors = data.data;
                                $(errors).each(function (index, obj) {
                                    if(obj.field == 'name'){
                                        showError("#newName", obj.defaultMessage);
                                    }else if(obj.field == 'phone'){
                                        showError("#newPhone", obj.defaultMessage);
                                    }else if(obj.field == 'address'){
                                        showError("#newAddress", obj.defaultMessage);
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
    $("#newName").focus(resetInput);
    $("#newName2").focus(resetInput);
    $("#newPhone").focus(resetInput);
    $("#newPhone2").focus(resetInput);
    $("#newAddress").focus(resetInput);
    $("#newAddress2").focus(resetInput);

    $(".updateRow").click(function () {
        var customerId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findCustomerById',
            data : 'customerId=' + customerId,
            success : function (data) {
                $("#newName2").val(data.name);
                $("#newPhone2").val(data.phone);
                $("#newAddress2").val(data.address);
                $("#dialog2").dialog({
                    width : 500,
                    height : 290,
                    draggable : true,
                    //打开对话框时是否使用特效
                    show : "slide",
                    //关闭对话框时是否使用特效动画
                    hide : "slide",
                    closeOnEscape : false,
                    buttons: {
                        "确定": function() {
                            var name = $("#newName2").val().trim();
                            var phone = $("#newPhone2").val().trim();
                            var address = $("#newAddress2").val().trim();
                            //todo 校验手机号
                            $.ajax({
                                type : 'post',
                                url : '/saveCustomer',
                                contentType : 'application/json;charset=utf-8',
                                data : JSON.stringify({
                                    "customerId" : customerId,
                                    "name" : name,
                                    "phone" : phone,
                                    "address" : address
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        var errors = data.data;
                                        $(errors).each(function (index, obj) {
                                            if(obj.field == 'name'){
                                                showError("#newName", obj.defaultMessage);
                                            }else if(obj.field == 'phone'){
                                                showError("#newPhone", obj.defaultMessage);
                                            }else if(obj.field == 'address'){
                                                showError("#newAdress", obj.defaultMessage);
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
                    }
                });
            }
        });
    });
    $(".deleteRow").click(function () {
        var customerId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteCustomer',
            data : 'customerId=' + customerId,
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

    $("#search").click(function () {
        var customerId = $("#id").val().trim();
        var name = $("#name").val().trim();
        var phone = $("#phone").val().trim();
        location.href = '/customers?customerId=' + customerId + '&name=' + name + '&phone=' + phone;
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